package org.doogwood.jp1ajs2.jobextract;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public final class Condition {
	private Pattern fullQualifiedName;
	private Pattern attrUnitName;
	private Pattern attrOwnerName;
	private Pattern attrPermissionMode;
	private Pattern attrResourceGroupName;
	private final Map<String, Pattern> params = new HashMap<String, Pattern>();
	
	public void setFullQualifiedName(Pattern fullQualifiedName) {
		this.fullQualifiedName = fullQualifiedName;
	}
	public void setAttrUnitName(Pattern attrUnitName) {
		this.attrUnitName = attrUnitName;
	}
	public void setAttrOwnerName(Pattern attrOwnerName) {
		this.attrOwnerName = attrOwnerName;
	}
	public void setAttrPermissionMode(Pattern attrPermissionMode) {
		this.attrPermissionMode = attrPermissionMode;
	}
	public void setAttrResourceGroupName(Pattern attrResourceGroupName) {
		this.attrResourceGroupName = attrResourceGroupName;
	}
	public Pattern getFullQualifiedName() {
		return fullQualifiedName;
	}
	public Pattern getAttrUnitName() {
		return attrUnitName;
	}
	public Pattern getAttrOwnerName() {
		return attrOwnerName;
	}
	public Pattern getAttrPermissionMode() {
		return attrPermissionMode;
	}
	public Pattern getAttrResourceGroupName() {
		return attrResourceGroupName;
	}
	public Map<String, Pattern> getParams() {
		return Collections.unmodifiableMap(params);
	}
	public void addParam(final String name, final Pattern value) {
		params.put(name, value);
	}
}
