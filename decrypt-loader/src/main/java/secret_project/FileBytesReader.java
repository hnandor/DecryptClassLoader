package secret_project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileBytesReader {

	public byte[] read(String path) {
		try {
			return Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
