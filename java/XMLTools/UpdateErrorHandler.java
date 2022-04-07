/*
*	UpdateErrorHandler.java
*
*	Copyright (c) 1999-2005 Web UI, Inc. All Rights Reserved.
*/

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

public class UpdateErrorHandler extends DefaultHandler implements ErrorListener
{
	// org.xml.sax.helpers.DefaultHandler methods

	public void processingInstruction (String target, String data)
	{
		if (piTarget != null && target.compareTo (piTarget) == 0) setStylesheetURI (data);
	}

	// org.xml.sax.ErrorHandler methods

	public void warning (SAXParseException spe) throws SAXException
	{
		printException ("Warning", spe);
		warnings++;
	}

	public void error (SAXParseException spe) throws SAXException
	{
		printException ("Error", spe);
		errors++;
	}

	public void fatalError (SAXParseException spe) throws SAXException
	{
		printException ("Fatal Error", spe);
		System.exit(1);
		errors++;
	}

	// javax.xml.transform.ErrorListener methods

	public void warning (TransformerException te) throws TransformerException
	{
		printException ("Warning", te);
		warnings++;
	}

	public void error (TransformerException te) throws TransformerException
	{
		printException ("Error", te);
		errors++;
	}

	public void fatalError (TransformerException te) throws TransformerException
	{
		printException ("Fatal Error", te);
		System.exit(1);
		errors++;
	}

	// protected and private methods

	protected boolean reportErrors (String process)
	{
		int errorCount = getErrors();
		if (errorCount == 0) return false;

		System.err.println (process + " failed with " + errorCount
		+ (errorCount == 1 ? " error" : " errors") + "...");
		return true;
	}

	protected void reset()
	{
		errors = 0;
		warnings = 0;
	}

	protected void setPiTarget (String target)
	{
		piTarget = target;
	}

	protected String getPiTarget()
	{
		return piTarget;
	}

	protected void setStylesheetURI (String data)
	{
		if (!data.startsWith ("href=")) return;
		stylesheetURI = data.substring (data.indexOf ("\"") + 1, data.lastIndexOf ("\""));
	}

	protected String getStylesheetURI()
	{
		return stylesheetURI;
	}

	protected void resetStylesheetURI()
	{
		stylesheetURI = null;
	}

	private int getErrors()		{ return errors; }
	private int getWarnings()	{ return warnings; }

	private void printException (String prefix, SAXParseException spe)
	{
		System.err.println ("** " + prefix + ", line "
			+ spe.getLineNumber() + ": " + spe.getMessage());
	}

	private void printException (String prefix, TransformerException te)
	{
		System.err.println ("** " + prefix + ", line "
			+ te.getLocationAsString() + ": " + te.getMessage());
	}

	// private data

	private int errors = 0;
	private int warnings = 0;
	private String piTarget = null;
	private String stylesheetURI = null;
}
