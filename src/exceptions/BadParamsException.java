package exceptions;

/**
 * LogicまたはDaoに渡されたパラメータが間違っている例外
 * @author SCI01961
 *
 */
public class BadParamsException extends Exception {
	public BadParamsException() {
	}

	public BadParamsException(String message) {
		super(message);
	}
}
