package org.doogwood.jp1ajs2.jobextract.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
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
	public List<Unit> extractUnits(final Parameters params, final List<Unit> unitDefs) {
		final Condition cond = params.getCondition();
		final Set<Entry<String, Pattern>> condParamSet = cond.getParams().entrySet();
		final List<Unit> result = new ArrayList<Unit>();
		
		for (final Unit u : unitDefs) {
			inner:
			for (final Unit v : Units.asList(u)) {
				System.out.println(v.getFullQualifiedName());
				if (cond.getFullQualifiedName() != null &&
						! cond.getFullQualifiedName().matcher(v.getFullQualifiedName()).matches()) {
					continue;
				}
				if (cond.getAttrUnitName() != null &&
						! cond.getAttrUnitName().matcher(v.getName()).matches()) {
					continue;
				}
				if (cond.getAttrOwnerName() != null &&
						! cond.getAttrOwnerName().matcher(v.getOwnerName().orElse("")).matches()) {
					continue;
				}
				if (cond.getAttrPermissionMode() != null &&
						! cond.getAttrPermissionMode().matcher(v.getPermissionMode().orElse("")).matches()) {
					continue;
				}
				if (cond.getAttrResourceGroupName() != null &&
						! cond.getAttrResourceGroupName().matcher(v.getResourceGroupName().orElse("")).matches()) {
					continue;
				}
				if (!condParamSet.isEmpty()) {
					for (final Entry<String, Pattern> e : condParamSet) {
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
