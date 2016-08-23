package me.clip.placeholderapi.expansion;

public enum NMSVersion {

	UNKNOWN("unknown"),
	SPIGOT_1_7_R1("v1_7_R1"),
	SPIGOT_1_7_R2("v1_7_R2"),
	SPIGOT_1_7_R3("v1_7_R3"),
	SPIGOT_1_7_R4("v1_7_R4"),
	SPIGOT_1_8_R1("v1_8_R1"),
	SPIGOT_1_8_R2("v1_8_R2"),
	SPIGOT_1_8_R3("v1_8_R3"),
	SPIGOT_1_9_R1("v1_9_R1");
	
	private String version;
	
	private NMSVersion(String version) {
		this.version = version;
	}
	
	public String getVersion() {
		return version;
	}
	
	public static NMSVersion getVersion(String version) {
		for (NMSVersion v : values()) {
			if (v.getVersion().equalsIgnoreCase(version)) {
				return v;
			}
		}
		return NMSVersion.UNKNOWN;
	}
	
	public boolean is1_7() {
		return version.indexOf("7") == 3;
	}
	
	public boolean is1_8() {
		return version.indexOf("8") == 3;
	}
	
	public boolean is1_9() {
		return version.indexOf("9") == 3;
	}
}
