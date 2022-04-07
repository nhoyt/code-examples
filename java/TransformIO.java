/*
*	TransformIO.java
*
*	Copyright (c) 1999-2002 Web UI, Inc. All Rights Reserved.
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
*	TransformIO provides methods for checking the file type, aka extension,
*	of the input filename, and for creating the output filename. The target
*	directory and/or the file type for the output file may optionally be
*	specified in a .ini file.
*
*	@author Nicholas Hoyt
*/

public class TransformIO
{
	public TransformIO (String inputFileType, String outputFileType,
						String iniFileName, String targetRootDir)
	{
		this.inputFileType	= inputFileType;
		this.outputFileType	= outputFileType;
		this.iniFileName	= iniFileName;
		this.targetRootDir	= targetRootDir;

		readIniFile();
	}

	/**
	*	Searches each line of the lines argument for specified pattern.
	*
	*	@return The first line that contains pattern, or null if pattern not found.
	*/
	private final String grepFirst (String[] lines, String pattern)
	{
		for (int i = 0; i < lines.length; i++)
		{
			if (lines[i] != null && lines[i].indexOf(pattern) != -1)
				return lines[i];
		}
		return null;
	}

	/**
	*	Reads the initialization text file, if it exists, into a String array
	*	and saves values specified for target directory and/or output file type.
	*/
	private void readIniFile()
	{
		String targetPattern = "target=";
		String typePattern = "type=";

		File iniFile;
		String line;
		int offset;

		try
		{
			iniFile = new File (iniFileName);
			if (!iniFile.exists()) return;

			BufferedReader input = new BufferedReader (new FileReader (iniFile));
			String[] lines = new String[maxLines];

			int index = 0;
			while ((line = input.readLine()) != null && index < maxLines)
			{
				lines[index++] = line;
			}
			input.close();

			line = grepFirst (lines, targetPattern);
			if (line != null)
			{
				offset = targetPattern.length();
				targetDirectory = line.substring(offset, line.length()).trim();
			}

			line = grepFirst (lines, typePattern);
			if (line != null)
			{
				offset = typePattern.length();
				outputFileType = line.substring(offset, line.length()).trim();
			}
		}
		catch (Exception e)
		{
			System.err.println (e);
			return;
		}
	}

	/**
	*	Checks the input file type to determine whether it matches the value
	*	of the inputFileType property.
	*
	*	@param fileName Name of the file to check.
	*	@return true if fileName ends with inputFileType, otherwise false.
	*/
	public boolean checkFileType (String fileName)
	{
		if (inputFileType != null && fileName.endsWith(inputFileType))
			return true;

		System.err.println (wrongExtension + inputFileType);
		System.err.println ("Skipping " + fileName + "...");
		return false;
	}

	/**
	*	Constructs the name of the output file based on the name of the
	*	input file and an optionally specified target directory and/or
	*	output file type.
	*
	*	@see TransformIO#readIniFile
	*/
	public String getOutputFileName (String inputFileName)
	{
		int index;
		File inputFile;
		String  fileName, filePath;
		StringBuffer outputFileName;

		inputFile = new File ((String)null, inputFileName);

		if (targetDirectory != null)
		{
			// since targetDirectory will be prepended to outputFileName,
			// have to handle absolute path specification separately
			if (inputFile.isAbsolute())
			{
				// strip off path information
				index = inputFileName.lastIndexOf (File.separator);
				fileName = inputFileName.substring (index+1, inputFileName.length());
			}
			else
			{
				fileName = inputFileName;
			}

			// replace inputFileType with outputFileType
			index = fileName.lastIndexOf (inputFileType);
			outputFileName = new StringBuffer (fileName);
			outputFileName.setLength (index);
			outputFileName.append (outputFileType);

			index = targetDirectory.lastIndexOf (File.separator);
			if (index == targetDirectory.length()-1)
				return targetDirectory + outputFileName;
			else
				return targetDirectory + File.separator + outputFileName;
		}

		// if (targetDirectory == null) we use the input filename...
		filePath = inputFile.getAbsolutePath();
		outputFileName = new StringBuffer (filePath);

		// strip off the input filename extension...
		index = filePath.lastIndexOf (inputFileType);
		outputFileName.setLength (index);

		// and append the output filename extension
		outputFileName.append (outputFileType);

		/*
		*	If targetDirectory was not specified and we're in a subdirectory
		*	of \XML, we want the output to go to same subdirectory off of the
		*	target root directory.
		*/
		int rootDirIndex = filePath.indexOf (File.separator) + 1;
		if (filePath.indexOf ("XML" + File.separator) == rootDirIndex ||
			filePath.indexOf ("xml" + File.separator) == rootDirIndex ||
			filePath.indexOf ("Xml" + File.separator) == rootDirIndex)
		{
			outputFileName.delete (rootDirIndex, rootDirIndex + 3);
			outputFileName.insert (rootDirIndex, targetRootDir);
		}

		return outputFileName.toString();
	}

	private String inputFileType	= null;
	private String outputFileType	= null;
	private String iniFileName		= null;
	private String targetRootDir	= null;

	// optionally specified in iniFile
	private String targetDirectory	= null;

	private final int maxLines = 6;

	// error messages
	private static final String wrongExtension = "Expecting <file> with extension ";
}
