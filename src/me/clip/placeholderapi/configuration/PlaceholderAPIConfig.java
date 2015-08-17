package me.clip.placeholderapi.configuration;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.InternalHook;

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
				+ "\n"
				+ "\nPlaceholder hooks:"
				+ "\n"
				+ "\nThere are currently " + InternalHook.values().length + " internal placeholder hooks"
				+ "\nprovided by PlaceholderAPI that can be enabled."
				+ "\n" + InternalHook.getHookAmountWithDependency() + " of these hooks require you have the associated"
				+ "\ndependency installed and enabled on your server if you choose to use them."
				+ "\n"
				+ "\nTo enable placeholders of a specific type/plugin, you MUST have the hook set to true"
				+ "\nin the placeholder_hooks section of this config."
				+ "\n"
				+ "\nInternal hooks are refreshed with /placeholderapi reload"
				+ "\n"
				+ "\nIn order to use a placeholder from a specific hook, you must use the hook identifier"
				+ "\nspecified in this config followed by the placeholder value identifier:"
				+ "\n%<hook identifier>_<value identifier>%"
				+ "\nAll hook identifiers are listed in the placeholder_hooks section of this file."
				+ "\n"
				+ "\n"
				+ "\nInjector information:"
				+ "\n"
				+ "\nREQUIREMENT: (ProtocolLib)"
				+ "\n"
				+ "\nThe injector function allows you to use placeholders in many plugins / functions"
				+ "\nwithout the need for the plugin you want to use placeholders in to support / hook into"
				+ "\nPlaceholderAPI."
				+ "\nAll injectors are optional and can be enabled / disabled as you wish."
				+ "\nNOTE: injector only loads on startup and a full restart is required to enable / disable specific"
				+ "\ninjector functions."
				+ "\n"
				+ "\nAllow placeholders in any chat window message from any plugin"
				+ "\nIf you want to use placeholders in your essentials chat formatting"
				+ "\nyou must use {<placeholder>} instead of %<placeholder>%"
				+ "\ninjector:"
				+ "\n  chat:"
				+ "\n    enabled: true/false"
				+ "\n"
				+ "\nAllow placeholders in any ItemStack name, lore, or inventory title"
				+ "\ninjector:"
				+ "\n  inventory: "
				+ "\n    enabled: true/false"
				+ "\n"
				+ "\nAllow placeholders in any title or subtitle from any plugin"
				+ "\ninjector:"
				+ "\n  title:"
				+ "\n    enabled: true/false"
				+ "\n"
				+ "\nAllow placeholders in the tab list header and footer"
				+ "\ninjector:"
				+ "\n  tab:"
				+ "\n    enabled: true/false"
				+ "\n"
				+ "\nAllow placeholders in signs:"
				+ "\ninjector:"
				+ "\n  signs:"
				+ "\n    enabled: true/false"
				+ "\n    update_interval: <time in seconds to update sign placeholders, 0 to disable>"
				+ "\n"
				+ "\nAllow placeholders in HolographicDisplays holograms"
				+ "\ninjector:"
				+ "\n  holographicdisplays:"
				+ "\n    enabled: true/false"
				+ "\n    update_interval: <time in seconds to update holo placeholders>"
				+ "\n"
				+ "\n"
				+ "\nTo add placeholders in chat messages, you need the permission node:"
				+ "\nplaceholderapi.injector.chat.bypass"
				+ "\n"
				+ "\nTo add placeholders in sign lines, you need the permission node:"
				+ "\nplaceholderapi.injector.signs.bypass"
				+ "\n"
				+ "\nTo add placeholders to items in anvils, you need the permission node:"
				+ "\nplaceholderapi.injector.anvil.bypass"
				+ "\n");
		c.addDefault("check_updates", true);
		c.addDefault("boolean.true", "yes");
		c.addDefault("boolean.false", "no");
		c.addDefault("date_format", "MM/dd/yy HH:mm:ss");
		c.set("hooks", null);

		for (InternalHook hooks : InternalHook.values()) {
			c.addDefault("placeholder_hooks." + hooks.getIdentifier(), false);
		}
		
		c.addDefault("injector.enabled", false);
		c.addDefault("injector.chat.enabled", true);
		c.addDefault("injector.inventory.enabled", true);
		c.addDefault("injector.title.enabled", true);
		c.addDefault("injector.tab.enabled", true);
		c.addDefault("injector.signs.enabled", true);
		c.addDefault("injector.signs.update_interval", 30);
		c.addDefault("injector.holographicdisplays.enabled", true);
		c.addDefault("injector.holographicdisplays.update_interval", 30);
		
		c.options().copyDefaults(true);
		plugin.saveConfig();
		plugin.reloadConfig();
	}
	
	public boolean checkUpdates() {
		return plugin.getConfig().getBoolean("check_updates");
	}
	
	public boolean injectorEnabled() {
		return plugin.getConfig().getBoolean("injector.enabled");
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
	
	public int getTaskInterval(String hook) {
		return plugin.getConfig().getInt(hook + ".check_interval", 30);
	}
	
	public String getPingOnline() {
		return plugin.getConfig().getString("pinger.online");
	}
	
	public String getPingOffline() {
		return plugin.getConfig().getString("pinger.offline");
	}
	
	
}
