package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;
import me.staartvin.simplesuffix.SimpleSuffix;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SimpleSuffixHook {

	private PlaceholderAPIPlugin plugin;
	
	private SimpleSuffix ss;
	
	public SimpleSuffixHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	public void hook() {
		if (Bukkit.getPluginManager().isPluginEnabled("Simple Suffix")) {

			ss = (SimpleSuffix) Bukkit.getPluginManager().getPlugin("Simple Suffix");
			
			if (ss != null) {

				boolean hooked = PlaceholderAPI.registerPlaceholderHook("SimpleSuffix", new PlaceholderHook() {

							@Override
							public String onPlaceholderRequest(Player p, String identifier) {
								if (identifier.equals("prefix")) {
									return getPrefix(p);
								} else if (identifier.equals("suffix")) {
									return getSuffix(p);
								}
								return null;
							}

						});

				if (hooked) {
					plugin.log.info("Hooked into Simple Suffix for placeholders!");
				}
			}
		}
	}
	
	private String getPrefix(Player p) {
		String pre = ss.permHandler.getPrefix(p);
		if (pre == null) {
			return "";
		}
		return pre;
	}
	
	private String getSuffix(Player p) {
		String su = ss.permHandler.getSuffix(p);
		if (su == null) {
			return "";
		}
		return su;
	}
}
