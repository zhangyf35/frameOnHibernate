package org.webframe.Exception;

/**
 * 不唯一异常对象
 * @author 张永葑
 *
 */
public class NotUniqueException extends RuntimeException{

	private static final long serialVersionUID = 8328738926932944863L;

	public NotUniqueException() {
		super();
	}

	public NotUniqueException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotUniqueException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotUniqueException(String message) {
		super(message);
	}

	public NotUniqueException(Throwable cause) {
		super(cause);
	}
	
}
