package me.clip.placeholderapi.external;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;

public abstract class EZPlaceholderHook extends PlaceholderHook {

	private String placeholderName;
	
	private String plugin;
	
	public EZPlaceholderHook(Plugin plugin, String placeholderName) {
		Validate.notNull(plugin, "Plugin can not be null!");
		Validate.isTrue(placeholderName != null && !placeholderName.isEmpty(), "Placeholder name can not be null or empty!");
		this.placeholderName = placeholderName;
		this.plugin = plugin.getName();
	}
	
	public boolean isHooked() {
		return PlaceholderAPI.getRegisteredPlaceholderPlugins().contains(placeholderName);
	}
	
	public boolean hook() {
		return PlaceholderAPI.registerPlaceholderHook(placeholderName, this);
	}
	
	public String getPlaceholderName() {
		return placeholderName;
	}
	
	public String getPluginName() {
		return plugin;
	}
}
