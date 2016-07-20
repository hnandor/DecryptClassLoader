package secret_project.exception;

public class UsageException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UsageException(String message) {
		super(message);
	}
	
	@Override
	public String toString() {
		return getMessage();
	}
}
