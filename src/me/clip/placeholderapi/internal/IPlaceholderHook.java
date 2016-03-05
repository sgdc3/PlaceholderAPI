package me.clip.placeholderapi.internal;


import org.bukkit.Bukkit;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

public abstract class IPlaceholderHook extends PlaceholderHook {
	
	private String identifier;
	
	private String plugin;
	
	public boolean canHook() {
		return plugin == null ? true : Bukkit.getPluginManager().isPluginEnabled(plugin);
	}
	
	public boolean isHooked() {
		return PlaceholderAPI.getRegisteredPlaceholderPlugins().contains(identifier);
	}
	
	public boolean hook() {
		return PlaceholderAPI.registerPlaceholderHook(identifier, this);
	}
	
	public IPlaceholderHook(InternalHook hook) {
		this.identifier = hook.getIdentifier();
		this.plugin = hook.getPluginName();
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public String getPlugin() {
		return plugin;
	}
	
	public PlaceholderAPIPlugin getPlaceholderAPI() {
		return PlaceholderAPIPlugin.getInstance();
	}
}
