package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import us.talabrek.ultimateskyblock.uSkyBlock;

public class USkyblockHook {
	
	private PlaceholderAPIPlugin plugin;
	
	private uSkyBlock skyblock;
	
	public USkyblockHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {

		if (Bukkit.getPluginManager().isPluginEnabled("uSkyBlock")) {
			
			skyblock = (uSkyBlock) Bukkit.getPluginManager().getPlugin("uSkyBlock");
			
			if (skyblock != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(skyblock, new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {
						
						if (p == null) {
							return "";
						}

						switch (identifier) {
						
						case "island_level":
							return String.valueOf(skyblock.getIslandLevel(p));
						case "island_rank":
							return skyblock.getIslandRank(p) != null ? String.valueOf(skyblock.getIslandRank(p).getRank()) : "0";
						case "biome":
							return skyblock.getCurrentBiome(p) != null ? skyblock.getCurrentBiome(p) : "";
						case "is_party_leader":
							return skyblock.isPartyLeader(p) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
						}
						return null;
					}
				}, true);
				
				if (hooked) {
					plugin.log.info("Successfully hooked into uSkyBlock for placeholders!");
				}
			}
		}
	}
}
