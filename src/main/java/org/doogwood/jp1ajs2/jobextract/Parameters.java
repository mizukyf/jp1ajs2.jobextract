package org.doogwood.jp1ajs2.jobextract;

import java.io.File;
import java.nio.charset.Charset;

public class Parameters {
	private File source;
	private Charset sourceCharset;
	private File dest;
	private Charset destCharset;
	private Format format;
	private boolean ignoreCase;
	private Condition condition;
	
	public boolean isIgnoreCase() {
		return ignoreCase;
	}
	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	public File getSource() {
		return source;
	}
	public void setSource(File source) {
		this.source = source;
	}
	public Charset getSourceCharset() {
		return sourceCharset;
	}
	public void setSourceCharset(Charset sourceCharset) {
		this.sourceCharset = sourceCharset;
	}
	public File getDest() {
		return dest;
	}
	public void setDest(File dest) {
		this.dest = dest;
	}
	public Charset getDestCharset() {
		return destCharset;
	}
	public void setDestCharset(Charset destCharset) {
		this.destCharset = destCharset;
	}
	public Format getFormat() {
		return format;
	}
	public void setFormat(Format format) {
		this.format = format;
	}
}
