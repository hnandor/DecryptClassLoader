package secret_project.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileBytesReader {
	
	public byte[] read(String path) throws IOException {
		return Files.readAllBytes(Paths.get(path));
	}
}
