package opcodeanalyser;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;


public class ObjectDumper {
	public static void dumpBinaryFile(String filePath) {
		try {
				ProcessBuilder pb = new ProcessBuilder();
				pb.directory(new File(FileHandler.readConfigValue(Definitions.BINARY_PATH)));
				pb.command("./objdump -d -M intel "+filePath);
				pb.start();				
			} catch (Exception e) {
				e.printStackTrace();
			}
		

	}

	public static void dumpBinaryFiles(String dirPath) {
		Collection<File> files = FileUtils.listFiles(FileUtils.getFile(dirPath),
				Definitions.LINUX_ELFS, true);
		for (File file : files) {
			dumpBinaryFile(file.getAbsolutePath());
		}
	}
}
