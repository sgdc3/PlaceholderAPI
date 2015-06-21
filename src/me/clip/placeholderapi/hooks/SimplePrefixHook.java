package me.clip.placeholderapi.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.flabaliki.simpleprefix.SimplePrefix;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

public class SimplePrefixHook {

	private PlaceholderAPIPlugin plugin;
	
	public SimplePrefixHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("SimplePrefix")) {

			Plugin simpleprefix = (SimplePrefix) Bukkit.getPluginManager().getPlugin("SimplePrefix");
			
			if (simpleprefix != null) {

				boolean hooked = PlaceholderAPI.registerPlaceholderHook(
						simpleprefix, new PlaceholderHook() {

							@Override
							public String onPlaceholderRequest(Player p, String identifier) {
								if (identifier.equals("prefix")) {
									return getPrefixSuffix(p, "prefix");
								} else if (identifier.equals("suffix")) {
									return getPrefixSuffix(p, "suffix");
								}
								return null;
							}

						}, true);

				if (hooked) {
					plugin.log.info("Hooked into SimplePrefix for placeholders!");
				}
			}
		}
	}
	
	public String getPrefixSuffix(Player player, String type){    
	    if(player.hasMetadata(type)){
	        return player.getMetadata(type).get(0).asString();
	    }
	    return "";
	}

}
