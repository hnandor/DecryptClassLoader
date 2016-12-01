package secret_project.loader;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassForEncryption {

	private static final Logger LOGGER = Logger.getLogger(ClassForEncryption.class.getName());

	private static final String SECRET_METHOD_MESSAGE = "WOW! Such algorithm! Much security!";
	
	public static void someMethod() {
		LOGGER.log(Level.INFO, SECRET_METHOD_MESSAGE);
	}
}
