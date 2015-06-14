package org.doogwood.jp1ajs2.jobextract.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.doogwood.jp1ajs2.jobextract.Condition;
import org.doogwood.jp1ajs2.jobextract.Parameters;
import org.doogwood.jp1ajs2.unitdef.Param;
import org.doogwood.jp1ajs2.unitdef.Unit;
import org.doogwood.jp1ajs2.unitdef.Units;
import org.springframework.stereotype.Service;

@Service
public class UnitService {
	public List<Unit> parseUnits(final Parameters params) throws IOException {
		if (params.getSource() == null) {
			return Units.fromStream(System.in, params.getSourceCharset());
		} else {
			return Units.fromFile(params.getSource(), params.getSourceCharset());
		}
	}
	
	public List<Unit> extractUnitsByStringMatching(final Condition cond,
			final boolean ignoreCase, final List<Unit> unitDefs) {

		final Set<Entry<String, String>> condParamSet = cond.getParams().entrySet();
		final List<Unit> result = new ArrayList<Unit>();
		
		for (final Unit u : unitDefs) {
			inner:
			for (final Unit v : Units.asList(u)) {
				if (cond.getFullQualifiedName() != null &&
						! cond.getFullQualifiedName().equalsIgnoreCase(v.getFullQualifiedName())) {
					continue;
				}
				if (cond.getAttrUnitName() != null &&
						! cond.getAttrUnitName().equalsIgnoreCase(v.getName())) {
					continue;
				}
				if (cond.getAttrOwnerName() != null &&
						! cond.getAttrOwnerName().equalsIgnoreCase(v.getOwnerName().orElse(""))) {
					continue;
				}
				if (cond.getAttrPermissionMode() != null &&
						! cond.getAttrPermissionMode().equalsIgnoreCase(v.getPermissionMode().orElse(""))) {
					continue;
				}
				if (cond.getAttrResourceGroupName() != null &&
						! cond.getAttrResourceGroupName().equalsIgnoreCase(v.getResourceGroupName().orElse(""))) {
					continue;
				}
				if (!condParamSet.isEmpty()) {
					for (final Entry<String, String> e : condParamSet) {
						for (final Param p : v.getParams(e.getKey())) {
							if (!e.getValue().equalsIgnoreCase(p.getValue())) {
								continue inner;
							}
						}
					}
				}
				result.add(v);
			}
		}
		
		return result;
	}
	
	private Pattern makePattern(final String s, final boolean ignoreCase) {
		return s == null ? null : (ignoreCase ?
				Pattern.compile(s, Pattern.CASE_INSENSITIVE) : Pattern.compile(s));
	}
	
	public List<Unit> extractUnitsByRegexMatching(final Condition cond,
			final boolean ignoreCase, final List<Unit> unitDefs) {
		// FQN
		final Pattern fqn = makePattern(cond.getFullQualifiedName(), ignoreCase);
		// Attr
		final Pattern attrUnitName = makePattern(cond.getAttrUnitName(), ignoreCase);
		final Pattern attrOwnerName = makePattern(cond.getAttrOwnerName(), ignoreCase);
		final Pattern attrPermissionMode = makePattern(cond.getAttrPermissionMode(), ignoreCase);
		final Pattern attrResourceGroupName = makePattern(cond.getAttrResourceGroupName(), ignoreCase);
		//
		final Set<Entry<String, Pattern>> condParamPatternSet = new HashSet<Entry<String,Pattern>>();
		for (final Entry<String, String> e : cond.getParams().entrySet()) {
			condParamPatternSet.add(new Map.Entry<String, Pattern>() {
				private final String k = e.getKey();
				private final Pattern v = makePattern(e.getValue(), ignoreCase);
				@Override
				public String getKey() {
					return k;
				}
				@Override
				public Pattern getValue() {
					return v;
				}
				@Override
				public Pattern setValue(Pattern value) {
					return v;
				}
			});
		}
		final List<Unit> result = new ArrayList<Unit>();
		
		for (final Unit u : unitDefs) {
			inner:
			for (final Unit v : Units.asList(u)) {
				if (fqn != null &&
						! fqn.matcher(v.getFullQualifiedName()).matches()) {
					continue;
				}
				if (attrUnitName != null &&
						! attrUnitName.matcher(v.getName()).matches()) {
					continue;
				}
				if (attrOwnerName != null &&
						! attrOwnerName.matcher(v.getOwnerName().orElse("")).matches()) {
					continue;
				}
				if (attrPermissionMode != null &&
						! attrPermissionMode.matcher(v.getPermissionMode().orElse("")).matches()) {
					continue;
				}
				if (attrResourceGroupName != null &&
						! attrResourceGroupName.matcher(v.getResourceGroupName().orElse("")).matches()) {
					continue;
				}
				if (!condParamPatternSet.isEmpty()) {
					for (final Entry<String, Pattern> e : condParamPatternSet) {
						for (final Param p : v.getParams(e.getKey())) {
							if (!e.getValue().matcher(p.getValue()).matches()) {
								continue inner;
							}
						}
					}
				}
				result.add(v);
			}
		}
		
		return result;
	}
	
	public void printUnitDefs(final Parameters params, final List<Unit> unitDefs) throws IOException {
		final File dest = params.getDest();
		final OutputStream out;
		if (dest == null) {
			out = System.out;
		} else {
			out = new FileOutputStream(dest, true);
		}
		for (final Unit u : unitDefs) {
			Units.writeToStream(u, out, params.getDestCharset());
		}
		if (dest != null) {
			out.close();
		}
	}
}
