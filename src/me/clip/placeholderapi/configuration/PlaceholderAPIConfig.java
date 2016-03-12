package me.clip.placeholderapi.configuration;

import me.clip.placeholderapi.PlaceholderAPIPlugin;

import org.bukkit.configuration.file.FileConfiguration;

public class PlaceholderAPIConfig {

	private PlaceholderAPIPlugin plugin;
	
	public PlaceholderAPIConfig(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	public void loadDefConfig() {
		
		FileConfiguration c = plugin.getConfig();
		
		c.options().header("PlaceholderAPI version "+plugin.getDescription().getVersion()+""
				+ "\nCreated by extended_clip"
				+ "\n");
		c.addDefault("check_updates", true);
		c.addDefault("boolean.true", "yes");
		c.addDefault("boolean.false", "no");
		c.addDefault("date_format", "MM/dd/yy HH:mm:ss");
		c.options().copyDefaults(true);
		plugin.saveConfig();
		plugin.reloadConfig();
	}
	
	public boolean checkUpdates() {
		return plugin.getConfig().getBoolean("check_updates");
	}
	
	public String booleanTrue() {
		return plugin.getConfig().getString("boolean.true");
	}
	
	public String booleanFalse() {
		return plugin.getConfig().getString("boolean.false");
	}
	
	public String dateFormat() {
		return plugin.getConfig().getString("date_format");
	}	
}
