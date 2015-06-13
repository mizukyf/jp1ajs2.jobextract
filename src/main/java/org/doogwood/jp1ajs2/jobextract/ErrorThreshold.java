package org.doogwood.jp1ajs2.jobextract;

public enum ErrorThreshold {
	NEVER(0),
	INPUT_NOT_FOUND(100),
	UNITDEF_MALFORMED(200),
	UNITDEF_NOT_FOUND(300),
	EVER(1000);
	
	private int level;
	private ErrorThreshold(final int level) {
		this.level = level;
	}
	public boolean greaterThan(final ErrorThreshold other) {
		return this.level > other.level;
	}
	public boolean greaterThanOrEqualTo(final ErrorThreshold other) {
		return this.level >= other.level;
	}
}
