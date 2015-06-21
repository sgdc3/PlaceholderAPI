package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import at.pcgamingfreaks.MarriageMaster.Bukkit.MarriageMaster;

public class MarriageMasterHook {
	
	private PlaceholderAPIPlugin plugin;

	private MarriageMaster marriage;
	
	public MarriageMasterHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {
		if (Bukkit.getPluginManager().isPluginEnabled("MarriageMaster")) {

			marriage = (MarriageMaster) Bukkit.getPluginManager().getPlugin("MarriageMaster");

			if (marriage != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(
						marriage, new PlaceholderHook() {

							@Override
							public String onPlaceholderRequest(Player p, String identifier) {

								switch (identifier) {
								
								case "married":
									return married(p);
								case "partner":
									return getPartner(p);
								
								}
								return null;
							}

						}, true);
				
				if (hooked) {
					plugin.log.info("Hooked into MarriageMaster for paceholders!");
				} else {
					marriage = null;
				}
			}
		}

	}
	
	private boolean isMarried(Player p) {
		return marriage.HasPartner(p);
	}
	
	private String married(Player p) {
		return isMarried(p) ? "&c\u2665" : "&8\u2665";
	}
	
	private String getPartner(Player p) {
		if (!isMarried(p)) {
			return "";
		}
		String partner = marriage.DB.GetPartner(p);
	   
		if (partner == null) {
			return "";
		}
		return partner;
	}
	
}
