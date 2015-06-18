package org.doogwood.jp1ajs2.jobextract;

enum Format {
	LIST    ("ユニット一覧フォーマット. ユニット名のみの一覧が出力される."), 
	FQN_LIST("完全名一覧フォーマット. ユニット完全名のみの一覧が出力される."), 
	COMPACT ("コンパクトなフォーマット. 改行やインデントは付与されない."), 
	READABLE("可読性の高いフォーマット. 改行とインデントが付与される.");
	
	public static Format defaultFormat() {
		return READABLE;
	}
	
	private final String desc;
	private Format(final String desc) {
		this.desc = desc;
	}
	public String getDesc() {
		return desc;
	}
}
