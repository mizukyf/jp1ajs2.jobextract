package org.doogwood.jp1ajs2.jobextract;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

final class Condition {
	private String fullQualifiedName;
	private String attrUnitName;
	private String attrOwnerName;
	private String attrPermissionMode;
	private String attrResourceGroupName;
	private final Map<String, String> params = new HashMap<String, String>();
	
	public void setFullQualifiedName(String fullQualifiedName) {
		this.fullQualifiedName = fullQualifiedName;
	}
	public void setAttrUnitName(String attrUnitName) {
		this.attrUnitName = attrUnitName;
	}
	public void setAttrOwnerName(String attrOwnerName) {
		this.attrOwnerName = attrOwnerName;
	}
	public void setAttrPermissionMode(String attrPermissionMode) {
		this.attrPermissionMode = attrPermissionMode;
	}
	public void setAttrResourceGroupName(String attrResourceGroupName) {
		this.attrResourceGroupName = attrResourceGroupName;
	}
	public String getFullQualifiedName() {
		return fullQualifiedName;
	}
	public String getAttrUnitName() {
		return attrUnitName;
	}
	public String getAttrOwnerName() {
		return attrOwnerName;
	}
	public String getAttrPermissionMode() {
		return attrPermissionMode;
	}
	public String getAttrResourceGroupName() {
		return attrResourceGroupName;
	}
	public Map<String, String> getParams() {
		return Collections.unmodifiableMap(params);
	}
	public void addParam(final String name, final String value) {
		params.put(name, value);
	}
}
