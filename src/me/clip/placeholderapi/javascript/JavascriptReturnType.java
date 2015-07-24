package me.clip.placeholderapi.javascript;

public enum JavascriptReturnType {

	BOOLEAN("boolean"), STRING("string");
	
	private String type;
	
	JavascriptReturnType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	public static JavascriptReturnType getType(String type){
		for(JavascriptReturnType e : values()){
			if (e.getType().equalsIgnoreCase(type)) {
				return e;
			}
		}
		return null;
	}
	
}
