package org.doogwood.jp1ajs2.jobextract;

public final class Messages {
	public static final String ERROR_WHILE_PARSING_OPTIONS = "コマンドライン・オプションの読み取り中にエラーが発生しました. "
			+ "オプションの指定方法が誤っている可能性があります.";
	public static final String ERROR_WHILE_SEARCHING_INPUTS = "読み取り対象のファイルが見つかりませんでした. "
			+ "ディレクトリのパスやファイル名パターンの指定内容を確認して下さい.";
	public static final String ERROR_WHILE_PARSING_UNITDEFS = "ユニット定義情報の読み取り中にエラーが発生しました. "
			+ "ユニット定義情報が記載されたファイルが見つからなかったりキャラクターセットの指定が誤っていたりした可能性があります.";
	public static final String ERROR_WHILE_EXTRACTING_UNITDEFS = "条件にマッチするユニット定義情報が見つかりませんでした.";
	public static final String ERROR_WHILE_OPEN_DESTFILE = "出力先ファイルをオープンするときにエラーが発生しました.";
	public static final String ERROR_WHILE_CHECKING_SOURCE = "読み取り対象として指定されたファイルは存在しません. ";
	public static final String ERROR_WHILE_CHECKING_DEST = "出力先として指定されたパスはファイルではありません. ";
	public static final String ERROR_WHILE_CHECKING_X_CHARSET = "指定されたキャラクターセット名はサポートされていません.";
	public static final String ERROR_WHILE_CHECKING_FORMAT = "指定されたフォーマット名はサポートされていません.";

	private Messages() {}
	
	
}
