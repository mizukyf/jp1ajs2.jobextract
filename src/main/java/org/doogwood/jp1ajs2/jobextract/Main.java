package org.doogwood.jp1ajs2.jobextract;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.doogwood.jp1ajs2.jobextract.service.ConfigService;
import org.doogwood.jp1ajs2.jobextract.service.UnitService;
import org.doogwood.jp1ajs2.unitdef.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Main {
	@Autowired
	private ConfigService configService;
	@Autowired
	private UnitService unitService;
	
	public static void main(final String[] args) throws IOException {
		
		final Class<Main> classMeta = Main.class;
		final String packageName = classMeta.getPackage().getName();
		final AnnotationConfigApplicationContext ctx = 
				new AnnotationConfigApplicationContext(packageName);
		int exitCode = 0;
		
		try {
			ctx.getBean(classMeta).execute(args);
		} catch (final Exception e) {
			e.printStackTrace();
			exitCode = 1;
		} finally {
			ctx.close();
		}
		
		System.exit(exitCode);
	}
	
	public void execute(final String[] args) throws ApplicationException {
		final Options options = configService.defineOptions();
		
		if (args.length == 0) {
			configService.printHelp(options);
			return;
		}
		
		try {
			final Parameters params = configService.parseArguments(options, args);
			final List<Unit> unitDefs = unitService.parseUnits(params);
			if (unitDefs.isEmpty()) {
				throw new ApplicationException(Messages.ERROR_WHILE_PARSING_UNITDEFS);
			}
			final Condition cond = params.getCondition();
			final boolean ignoreCase = params.isIgnoreCase();
			final boolean regexMatching = params.isRegexMatching();
			final List<Unit> extractedUnitDefs = 
					regexMatching ? unitService.extractUnitsByRegexMatching(cond, ignoreCase, unitDefs)
							: unitService.extractUnitsByStringMatching(cond, ignoreCase, unitDefs);
			if (extractedUnitDefs.isEmpty()) {
				throw new ApplicationException(Messages.ERROR_WHILE_EXTRACTING_UNITDEFS);
			}
			unitService.printUnitDefs(params, extractedUnitDefs);
			
		} catch (final ParseException e) {
			throw new ApplicationException(Messages.ERROR_WHILE_PARSING_OPTIONS, e);
		} catch (final FileNotFoundException e) {
			throw new ApplicationException(Messages.ERROR_WHILE_OPEN_DESTFILE, e);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
