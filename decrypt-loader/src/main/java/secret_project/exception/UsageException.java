package secret_project.exception;

public class UsageException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private Throwable cause;
	
	public UsageException(String message) {
		super(message);
	}
	
	public UsageException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		if (cause != null) {
			return cause.getMessage();
		}
		return getMessage();
	}
}
