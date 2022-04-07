/*
* update.java
*
* Copyright (c) 1999-2005 Web UI, Inc. All Rights Reserved.
*/

// java.io
import java.io.InputStream;
import java.io.IOException;

// java.net
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

// java.util
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

// javax.xml.parsers
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;

// javax.xml.transform
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.sax.SAXSource;

// org.xml.sax
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
* Wrapper class for calling the Xerces XML SAX parser and either the Xalan
* or Saxon XSLT processor. If the parsing executes without errors, the output
* result is a file whose name is the same as the input base file name, and
* whose file extension is specified in the properties file as the value of
* the outputFileType property.
* <p>
* See properties file, ~/.java/update.prop, for descriptions
* of other application properties.
*
* @author Nicholas Hoyt
*/

public class update
{
  /**
  * The main method does the following:
  * <ul>
  * <li>Checks for correct number of command-line arguments.</li>
  * <li>Processes -s (alternative stylesheet) and -p (stylesheet parameter)
  * command-line arguments.</li>
  * <li>Calls the initialize method, which reads the properties file,
  * and instantiates reusable objects.</li>
  * <li>Attempts to process each file specified on the command-line.</li>
  * </ul>
  * Processing each file consists of the following steps:
  * <ul>
  * <li>Check that the input file has the correct file type.</li>
  * <li>Validate the input file using the SAX parser and an error handler which
  * displays all errors and their line numbers. If validation fails, the file is
  * placed in the filesNotProcessed list and processing is terminated.</li>
  * <li>Construct the output file name.</li>
  * <li>Create new version of output file using the XSLT processor.</li>
  * </ul>
  */
  public static void main (String args[])
  {
    String inputFile, outputFile;
    update processor = new update();
    int index = 0;

    try
    {
      if (args.length == 0) throw new Exception (syntax);

      // instantiate default handler
      defaultHandler = new UpdateErrorHandler();

      while (index < args.length && args[index].startsWith("-"))
      {
        if (args[index].compareTo ("-s") == 0)
        {
          if (args.length <= (index + 2)) throw new Exception (syntax);
          defaultHandler.setPiTarget (args[++index]);
          index++;
        }

        if (args[index].compareTo ("-p") == 0)
        {
          if (args.length <= (index + 3)) throw new Exception (syntax);
          String paramName = args[++index];
          String paramValue = args[++index];
          processor.saveParam (paramName, paramValue);
          index++;
        }
      }

      processor.initialize();

      for (int i = index; i < args.length; i++)
      {
        if (i > 0) System.out.println();

        inputFile = args[i];
        if (!trio.checkFileType (inputFile)) continue;

        long before = System.currentTimeMillis();

        // validate the input file
        System.out.println ("Validating " + inputFile + "...");
        processor.validate (inputFile);
        if (processor.defaultHandler.reportErrors ("Validation")) continue;

        // construct name of output file
        outputFile = trio.getOutputFileName (inputFile);
        if (debug) System.out.println ("outputFile: " + outputFile);

        // run the XSLT processor
        System.out.println ("Creating new version of " + outputFile + "...");
        processor.transform (inputFile, outputFile);
        if (processor.defaultHandler.reportErrors ("Transformation")) continue;

        long after = System.currentTimeMillis();
        System.out.println ("XSL Transform successful! (" + (after-before) + " ms)");
      }
    }
    catch (SAXException se) {
      printException (se);
      System.exit (1);
    }
    catch (IOException ioe) {
      printException (ioe);
      System.exit (1);
    }
    catch (Exception e) {
      printException (e);
      System.exit (1);
    }
  }

  /**
  * Reads the properties file and initializes the TransformIO, SAXParser,
  * XMLReader and TransformerFactory objects.
  */
  private void initialize() throws IOException, SAXException
  {
    // read the properties file
    getProperties();
    trio = new TransformIO (inputFileType, outputFileType, iniFileName, targetRootDir);

    // instantiate SAXParser for validation step (will detect stylesheet PI)
    SAXParserFactory parserFactory = SAXParserFactory.newInstance();
    try {
      parserFactory.setFeature ("http://xml.org/sax/features/validation", true);
      parserFactory.setFeature ("http://xml.org/sax/features/namespaces", true);
      parserFactory.setFeature ("http://apache.org/xml/features/continue-after-fatal-error", true);
      parserFactory.setFeature ("http://apache.org/xml/features/xinclude", true);
      parser = parserFactory.newSAXParser();
    }
    catch (ParserConfigurationException pce) {
      printException (pce);
    }

    // create an XMLReader with XInclude capabilities
    reader = XMLReaderFactory.createXMLReader();

    // set reader features
    try {
      reader.setFeature ("http://xml.org/sax/features/validation", true);
      reader.setFeature ("http://xml.org/sax/features/namespaces", true);
      reader.setFeature ("http://apache.org/xml/features/continue-after-fatal-error", true);
      reader.setFeature ("http://apache.org/xml/features/xinclude", true);
    }
    catch (SAXException se) {
      printException (se);
    }

    // Output name of package
    if (debug) printPackageName (reader);

    // instantiate TransformerFactory
    // The system property setting for javax.xml.transform.TransformerFactory
    // determines the actual TransformerFactory class to instantiate.
    transformerFactory = TransformerFactory.newInstance();
    transformerFactory.setErrorListener (defaultHandler);

    // Output name of package
    if (debug) printPackageName (transformerFactory);
  }

  /**
  * Utility method to print name of package being used
  *
  * @param obj The object from which to extract the package name.
  */
  private void printPackageName (Object obj)
  {
    String className = obj.getClass().getName();
    String packageName = className.substring(0, className.lastIndexOf("."));
    System.out.println ("Using " + packageName + " package...");
  }

  /**
  * Calls the XML parser to validate the input file. A side effect occurs by means
  * of the UpdateErrorHandler: if the target stylesheet PI is encountered, its
  * stylesheetURI string is set to the PI data value;
  *
  * @param uri The input source file URI.
  */
  private void validate (String uri) throws SAXException, IOException
  {
    defaultHandler.reset();
    defaultHandler.resetStylesheetURI();
    parser.parse (uri, defaultHandler);
  }

  /**
  * Calls the XSLT processor, in conjunction with the XML Reader, to produce the output file.
  *
  * @param inputFile The XML input source file
  * @param outputFile The HTML output result file
  */
  private void transform (String inputFile, String outputFile)
      throws IOException, MalformedURLException, SAXException, TransformerException
  {
    defaultHandler.reset();

    // Create SAXSource from XML input file
    SAXSource saxSource = new SAXSource (new InputSource (inputFile));
    saxSource.setXMLReader (reader);

    // Get the stylesheet specified in XML source document, either from PI detected
    // in parsing above, or xml-stylesheet directive
    String media = null, title = null, charset = null;
    String stylesheetURI = defaultHandler.getStylesheetURI();

    Source stylesheet = (stylesheetURI != null) ?
      new StreamSource (stylesheetURI) :
      transformerFactory.getAssociatedStylesheet (saxSource, media, title, charset);

    if (stylesheet == null)
    {
      System.err.println (msgPrefix + "Error: Stylesheet not found. Unable to transform " + inputFile);
      return;
    }

    reportOnStylesheet (stylesheet);

    // Instantiate a Transformer that will work with the assoc. stylesheet
    Transformer transformer = transformerFactory.newTransformer (stylesheet);
    transformer.setErrorListener (defaultHandler);
    transformer.setParameter ("inputFile", inputFile);

    // Set stylesheet parameters if specified
    if (!paramList.isEmpty()) setParameters (transformer);

    // Create StreamResult object from output file name
    StreamResult streamResult = new StreamResult (outputFile);

    // Do the XSLT transformation
    transformer.transform (saxSource, streamResult);
  }

  /**
  * Saves stylesheet parameters specified on command line for later retrieval.
  *
  * @param name The name of the stylesheet parameter
  * @param value The value of the stylesheet parameter
  */
  private void saveParam (String name, String value)
  {
    paramList.put (name, value);
  }

  /**
  * Sets all stylesheet parameters on the transformer object.
  *
  * @param transformer The transformer on which to set the stylesheet parameters
  */
  private void setParameters (Transformer transformer)
  {
    Set keys = paramList.keySet();
    Iterator iter = keys.iterator();
    while (iter.hasNext()) {
      String paramName = (String) iter.next();
      String paramValue = paramList.get (paramName);
      transformer.setParameter (paramName, paramValue);
      System.out.println ("Setting '" + paramName + "' parameter to '" + paramValue + "'...");
    }
  }

  /**
  * If a PI target was specified as a command line.argument, report on which
  * stylesheet is being used for the transformation.
  *
  * @param stylesheet The Source object containing information on which
  * stylesheet is set to be used by the transformer object
  */
  private void reportOnStylesheet (Source stylesheet)
  {
    if (defaultHandler.getPiTarget() == null)
      return;

    String systemId = null;
    String stylesheetName = null;
    String udsPrefix = "Using default stylesheet: ";

    try {
      systemId = new URI(stylesheet.getSystemId()).getPath();
      int index = systemId.lastIndexOf ('/');
      stylesheetName = systemId.substring (index + 1);
    }
    catch (URISyntaxException use) {
      stylesheetName = "UNKNOWN";
    }

    if (defaultHandler.getStylesheetURI() == null)  {
      System.err.println (msgPrefix + "Warning: " +
        defaultHandler.getPiTarget() + " stylesheet not found...");
      System.err.println (udsPrefix + stylesheetName + "...");
    }
    else {
      System.out.println ("Using " + defaultHandler.getPiTarget() +
        " stylesheet: " + stylesheetName + "...");
    }
  }

  /**
  * Loads the properties file whose name is specified in propertiesURL
  * and if no exceptions are thrown, calls the initFromProperties() method.
  *
  * @see update#initFromProperties()
  */
  private void getProperties()
  {
    p = new Properties();
    try {
      URL propSource = new URL (propertiesURL);
      InputStream propIS = propSource.openStream();
      p.load (propIS);
      if (debug) p.list (System.out);
      initFromProperties();
      propIS.close();
    }
    catch (MalformedURLException mue) {
      printException (mue);
      System.exit (1);
    }
    catch (IOException ioe) {
      printException (ioe);
      System.exit (1);
    }
    initFromProperties();
  }

  /**
  * Initializes all variables whose values are specified in the properties
  * file. A fatal error occurs if the method is unable to initialize any of
  * the variables.
  */
  private void initFromProperties()
  {
    try {
      inputFileType   = getProperty ("update.inputFileType");
      outputFileType    = getProperty ("update.outputFileType");
      iniFileName     = getProperty ("update.iniFileName");
      targetRootDir   = getProperty ("update.targetRootDir");
    }
    catch (Exception e) {
      printException (e);
      System.exit (1);
    }
  }

  /**
  * Retrieves the value of the property specified by prop.
  *
  * @param prop Name of a property specified in property file.
  * @return Value of the property specified by prop.
  */
  private String getProperty (String prop) throws Exception
  {
    String value = p.getProperty (prop);
    if (value == null)
      throw new Exception ("Property " + prop + " not found in " + propertiesURL);
    return value;
  }

  /**
  * Utility method to print exception message and stack trace
  *
  * @param e The exception whose message or stack trace is printed
  */
  private static void printException (Exception e)
  {
    System.err.println (e.getMessage());
    if (debug) e.printStackTrace (System.err);
  }

  // static variables
  private static boolean debug = false;
  private static final String syntax = "Usage: java update [-s <piTarget> | -p <paramName> <paramValue>] <file> [file...]";

  private static TransformIO trio;
  private static UpdateErrorHandler defaultHandler;

  // instance variables
  private SAXParser parser;
  private XMLReader reader;
  private TransformerFactory transformerFactory;

  private Map<String,String> paramList = new HashMap<String,String>();

  private Properties p;
  private final String propertiesURL = "file:///Users/nhoyt/.java/update.prop";
  private final String msgPrefix = "<-*-> ";

  // variables initialized from properties file
  private String inputFileType  = null;
  private String outputFileType = null;
  private String iniFileName    = null;
  private String targetRootDir  = null;
}
