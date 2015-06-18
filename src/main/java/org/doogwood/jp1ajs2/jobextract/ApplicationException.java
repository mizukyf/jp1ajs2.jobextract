package org.doogwood.jp1ajs2.jobextract;

final class ApplicationException extends Exception {
	private static final long serialVersionUID = -6496770376758634407L;
	public ApplicationException(final String message, final Throwable cause) {
		super(message, cause);
	}
	public ApplicationException(final String message) {
		super(message);
	}
}
