package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import src.john01dav.sqlperms.SQLPerms;
import src.john01dav.sqlperms.database.DataStore;

public class SQLPermsHook {

	private PlaceholderAPIPlugin plugin;

	private SQLPerms sqlperms;

	public SQLPermsHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {

		if (Bukkit.getPluginManager().isPluginEnabled("SQLPerms")) {

			sqlperms = (SQLPerms) Bukkit.getPluginManager().getPlugin("SQLPerms");
		
			if (sqlperms != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(sqlperms, new PlaceholderHook() {

							@Override
							public String onPlaceholderRequest(Player p, String identifier) {
								if (identifier.contains("rank_")) {
									
									String channel = identifier.split("rank_")[1];

									return getPrefix(p, channel);
									
								} else if (identifier.contains("prefix_")) {
									
									String channel = identifier.split("prefix_")[1];
									
									return getPrefix(p, getGroup(p, channel));
								}
								return null;
							}

						});

				if (hooked) {
					plugin.log.info("Hooked into SQLPerms for placeholders!");
				}
			}
		}
	}
	
	private String getPrefix(Player p, String rank) {
		
		if (rank == null || rank.isEmpty()) {
			return rank;
		}
		
		String prefix = sqlperms.getConfigManager().getPermissionsConfigurationManager().getPrefix(rank);
		
		if (prefix == null || prefix.isEmpty()) {
			return "";
		}
		
		return prefix;
	}

	private String getGroup(Player p, String s) {
		
		DataStore store = sqlperms.getDataStore();
		
		if (store == null) {
			return "";
		}
		 
		String rank = store.getRank(p.getUniqueId(), s);
		 
		if (rank != null) {
			return rank;
		}
		
		return "";
	}

}
