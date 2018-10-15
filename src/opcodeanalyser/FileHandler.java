package opcodeanalyser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author msgeden
 */
public class FileHandler {


	private static final String configFile = "config.properties";
	public static String readConfigValue(String key) {
		Properties prop = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(configFile);
			prop.load(input);
		} catch (IOException e) {
			System.out.println("Cannot read configuration file(s)\n" + e.getMessage());
		}
		return prop.getProperty(key);
	}

	public static void writeConfigValue(String key, String val) {
		Properties prop = new Properties();
		InputStream input;
		OutputStream output;
		try {
			input = new FileInputStream(configFile);
			prop.load(input);
		} catch (IOException e) {
			System.out.println("Cannot read configuration file(s)\n" + e.getMessage());
		}
		prop.setProperty(key, val);
		try {
			output = new FileOutputStream(configFile);
			prop.store(output, "");
		} catch (IOException e) {
			System.out.println("Cannot read configuration file(s)\n" + e.getMessage());
		}
	}

	public static String createDirectory(String path, String dir) {
		File directory = new File(path + dir);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		return directory.getAbsolutePath();
	}

	public static void moveFileToDirectory(String filePath, String destinationPath) throws IOException {
		FileUtils.moveFileToDirectory(new File(filePath), new File(destinationPath), true);
	}

	public static byte[] readFileToByteArray(String filePath) throws IOException {
		return FileUtils.readFileToByteArray(FileUtils.getFile(filePath));
	}

	public static String readFileToString(String filePath) throws IOException {
		return FileUtils.readFileToString(FileUtils.getFile(filePath), Charset.defaultCharset());
	}

}