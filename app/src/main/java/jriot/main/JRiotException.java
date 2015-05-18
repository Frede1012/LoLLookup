package jriot.main;

/**
 * Created by Admin on 10.01.14.
 */
public class JRiotException extends Exception {

	private static final long serialVersionUID = 1L;
	// CUSTOM
	public static final int ERROR_UNKNOWN = 0;
	public static final int ERROR_NETWORK = 1337;
	// DEFAULT
	public static final int ERROR_API_KEY_LIMIT = 429;
	public static final int ERROR_API_KEY_WRONG = 401;
	public static final int ERROR_DATA_NOT_FOUND = 404;
	public static final int ERROR_BAD_REQUEST = 400;
	public static final int ERROR_INTERNAL_SERVER_ERROR = 500;
	public static final int ERROR_SERVICE_UNAVAILABLE = 503;

	private final int errorCode;
	private final String errorMessage;

	public JRiotException(int code) {
		super(getError(code));
		this.errorMessage = getError(code);
		this.errorCode = code;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public static String getError(int code) {
		switch (code) {
		case ERROR_UNKNOWN:
			return "Unknown Error";
		case ERROR_NETWORK:
			return "Network Error";
		case ERROR_API_KEY_LIMIT:
			return "API Limit reached";
		case ERROR_API_KEY_WRONG:
			return "Unauthorized";
		case ERROR_BAD_REQUEST:
			return "Bad request";
		case ERROR_INTERNAL_SERVER_ERROR:
			return "Internal server error";
		case ERROR_DATA_NOT_FOUND:
			return "Data not found";
		case ERROR_SERVICE_UNAVAILABLE:
			return "Service unavailable";
		}
		return null;
	}
}