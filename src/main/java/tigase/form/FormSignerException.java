package tigase.form;

public class FormSignerException extends Exception {

	private static final long serialVersionUID = 1L;

	public FormSignerException() {
		super();
	}

	public FormSignerException(String message) {
		super(message);
	}

	public FormSignerException(String message, Throwable cause) {
		super(message, cause);
	}

	public FormSignerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FormSignerException(Throwable cause) {
		super(cause);
	}

}
