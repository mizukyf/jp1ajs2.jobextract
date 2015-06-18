package org.doogwood.jp1ajs2.jobextract;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
	private static final String LINE_SEP = System.getProperty("line.separator");
	private static final String OPTION_SOURCE_PATH = "s";
	private static final String OPTION_SOURCE_CHARSET = "c";
	private static final String OPTION_DEST_PATH = "d";
	private static final String OPTION_DEST_CHARSET = "C";
	private static final String OPTION_FORMAT = "f";
	private static final String OPTION_COND_FQN = "F";
	private static final String OPTION_COND_NAME = "N";
	private static final String OPTION_COND_ATTR_X = "A";
	private static final String OPTION_COND_PARAM_X = "P";
	private static final String OPTION_IGNORE_CASE = "i";
	private static final String OPTION_REGEX_MATCHING = "r";
	
	public Options defineOptions() {
		final Options options = new Options();
		
		options.addOption(Option.builder(OPTION_SOURCE_PATH)
				.required(false)
				.hasArg(true)
				.argName("source-filepath")
				.desc("ユニット定義ファイルのパス. "
						+ "絶対パスのほか\".\"（カレント・ディレクトリ）を起点にした相対パスも使用できる. "
						+ "ファイルパスを指定しなかった場合は標準入力からユニット定義情報を読み取る.")
				.build());
		options.addOption(Option.builder(OPTION_DEST_PATH)
				.required(false)
				.hasArg(true)
				.argName("destination-filepath")
				.desc("ユニット定義情報の抽出結果を出力するファイル. "
						+ "指定しない場合は標準出力ファイルに出力される.")
				.build());
		options.addOption(Option.builder(OPTION_SOURCE_CHARSET)
				.required(false)
				.hasArg(true)
				.argName("input-charset")
				.desc("ユニット定義ファイルのキャラクターセット. "
						+ "指定しない場合はJVMのデフォルト・キャラクターセット("
						+ Charset.defaultCharset().displayName()
						+ ")が使用される.")
				.build());
		options.addOption(Option.builder(OPTION_DEST_CHARSET)
				.required(false)
				.hasArg(true)
				.argName("output-charset")
				.desc("抽出結果ファイルのキャラクターセット. "
						+ "指定しない場合はJVMのデフォルト・キャラクターセット("
						+ Charset.defaultCharset().displayName()
						+ ")が使用される.")
				.build());
		options.addOption(Option.builder(OPTION_FORMAT)
				.required(false)
				.hasArg(true)
				.argName("format-name")
				.desc("抽出結果ファイルのフォーマット. "
						+ "デフォルトは\"" + Format.defaultFormat()
						+ "\". "
						+ "指定できるフォーマットは次の通り: " + LINE_SEP
						+ joinFormatNames())
				.build());
		options.addOption(Option.builder(OPTION_COND_NAME)
				.required(false)
				.hasArg(true)
				.argName("unit-name")
				.desc("ユニットが指定された名前である場合に抽出対象とする. "
						+ "\"-Aname=...\"のエイリアス. ")
				.build());
		options.addOption(Option.builder(OPTION_COND_FQN)
				.required(false)
				.hasArg(true)
				.argName("full-qualified-name")
				.desc("ユニットが指定された完全名である場合に抽出対象とする.")
				.build());
		options.addOption(Option.builder(OPTION_COND_ATTR_X)
				.required(false)
				.hasArg(true)
				.argName("attr=value")
				.numberOfArgs(2)
				.valueSeparator()
				.desc("ユニット属性パラメータが指定された値をとる場合に抽出対象とする. "
						+ "属性名として指定できるのは: name, ownerName, permissionMode, resourceGroupName")
				.build());
		options.addOption(Option.builder(OPTION_COND_PARAM_X)
				.required(false)
				.hasArg(true)
				.argName("param=value")
				.numberOfArgs(2)
				.valueSeparator()
				.desc("ユニット定義パラメータが指定された値をとる場合に抽出対象とする.")
				.build());

		options.addOption(Option.builder("i")
				.required(false)
				.hasArg(false)
				.desc("抽出条件のマッチングで大文字小文字のちがいを無視する.")
				.build());
		options.addOption(Option.builder("r")
				.required(false)
				.hasArg(false)
				.desc("抽出条件として指定された文字列を正規表現パターンとみなしてマッチングを行う.")
				.build());

		return options;
	}
	
	private String joinFormatNames() {
		final StringBuilder sb = new StringBuilder();
		for (final Format f : Format.values()) {
			if (sb.length() > 0) {
				sb.append(LINE_SEP);
			}
			sb.append('"').append(f).append('"').append(" - ").append(f.getDesc());
		}
		return sb.toString();
	}
	
	public Parameters parseArguments(final Options options, final String[] arguments) throws ParseException {
		final CommandLine cmd = new DefaultParser().parse(options, arguments);
		final Parameters params = new Parameters();
		
		if (cmd.hasOption(OPTION_SOURCE_PATH)) {
			final File f = new File(cmd.getOptionValue(OPTION_SOURCE_PATH));
			if (f.isFile()) {
				params.setSource(f);
			} else {
				throw new IllegalArgumentException(Messages.ERROR_WHILE_CHECKING_SOURCE);
			}
		}
		
		if (cmd.hasOption(OPTION_DEST_PATH)) {
			final File f = new File(cmd.getOptionValue(OPTION_DEST_PATH));
			if (f.isDirectory()) {
				throw new IllegalArgumentException(Messages.ERROR_WHILE_CHECKING_DEST);
			} else {
				params.setDest(f);
			}
		}
		
		final Charset jvmDefaultCharset = Charset.defaultCharset();
		if (cmd.hasOption(OPTION_SOURCE_CHARSET)) {
			final String c = cmd.getOptionValue(OPTION_SOURCE_CHARSET);
			if (Charset.isSupported(c)) {
				params.setSourceCharset(Charset.forName(c));
			} else {
				throw new IllegalArgumentException(Messages.ERROR_WHILE_CHECKING_X_CHARSET);
			}
		} else {
			params.setSourceCharset(jvmDefaultCharset);
		}
		
		if (cmd.hasOption(OPTION_DEST_CHARSET)) {
			final String c = cmd.getOptionValue(OPTION_DEST_CHARSET);
			if (Charset.isSupported(c)) {
				params.setDestCharset(Charset.forName(c));
			} else {
				throw new IllegalArgumentException(Messages.ERROR_WHILE_CHECKING_X_CHARSET);
			}
		} else {
			params.setDestCharset(jvmDefaultCharset);
		}
		
		if (cmd.hasOption(OPTION_FORMAT)) {
			try {
				params.setFormat(Format.valueOf(cmd.getOptionValue(OPTION_FORMAT)));
			} catch (final IllegalArgumentException e) {
				throw new IllegalArgumentException(Messages.ERROR_WHILE_CHECKING_FORMAT);
			}
		} else {
			params.setFormat(Format.defaultFormat());
		}
		
		params.setIgnoreCase(cmd.hasOption(OPTION_IGNORE_CASE));
		params.setRegexMatching(cmd.hasOption(OPTION_REGEX_MATCHING));
		
		final Condition cond = new Condition();
		
		if (cmd.hasOption(OPTION_COND_FQN)) {
			cond.setFullQualifiedName(cmd.getOptionValue(OPTION_COND_FQN));
		}
		
		if (cmd.hasOption(OPTION_COND_NAME)) {
			cond.setAttrUnitName(cmd.getOptionValue(OPTION_COND_NAME));
		}
		
		final Properties attrProps = cmd.getOptionProperties(OPTION_COND_ATTR_X);
		for (final String s : attrProps.stringPropertyNames()) {
			if (s.equals("name")) {
				cond.setAttrUnitName(attrProps.getProperty(s));
			} else if (s.equals("ownerName")) {
				cond.setAttrOwnerName(attrProps.getProperty(s));
			} else if (s.equals("permissionMode")) {
				cond.setAttrPermissionMode(attrProps.getProperty(s));
			} else if (s.equals("resourceGroupName")) {
				cond.setAttrResourceGroupName(attrProps.getProperty(s));
			}
		}
		
		final Properties paramProps = cmd.getOptionProperties(OPTION_COND_PARAM_X);
		for (final String s : paramProps.stringPropertyNames()) {
			cond.addParam(s, paramProps.getProperty(s));
		}
		
		if (cond.getFullQualifiedName() == null &&
				cond.getAttrOwnerName() == null &&
				cond.getAttrPermissionMode() == null &&
				cond.getAttrResourceGroupName() == null &&
				cond.getAttrUnitName() == null &&
				cond.getParams().isEmpty()) {
			cond.setFullQualifiedName("^/[^/]+$");
			params.setRegexMatching(true);
		}
		
		params.setCondition(cond);
		
		return params;
	}
	
	public void printHelp(final Options options) {
		final HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("jobextract(java -jar jp1ajs2-jobextract-...jar)",
				"ユニット定義ファイルから指定された条件にマッチするユニットを抜き出す. "
				+ "条件が指定されていない場合はユニット定義ファイル上ルートレベルにあたるユニットを抜き出す. ",
				options,
				"",
				true);
	}
}
