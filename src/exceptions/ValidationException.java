package exceptions;

/**
 * Logicのバリデーションでの例外。借出し中なので退会できませんとか。
 * @author SCI01961
 *
 */
public class ValidationException extends Exception {
	public ValidationException(String message) {
		super(message);
	}
}
