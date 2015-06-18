package org.doogwood.jp1ajs2.jobextract;

import org.doogwood.jp1ajs2.unitdef.util.Formatter;

/**
 * JP1/AJS2のユニット定義を文字列化するオブジェクト.
 */
final class CompactFormatter extends Formatter {

	@Override
	protected void handleIndentation(final int depth, final Appender context) throws Exception {
		// Do nothing
	}
	
	@Override
	protected void handleEol(final Appender context) throws Exception {
		// Do nothing.
	}
}
