package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import src.john01dav.sqltokens.SQLTokens;
import src.john01dav.sqltokens.database.DataStore;

public class SQLTokensHook {

	private PlaceholderAPIPlugin plugin;

	private SQLTokens sqlTokens;

	public SQLTokensHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {

		if (Bukkit.getPluginManager().isPluginEnabled("SQLTokens")) {

			sqlTokens = (SQLTokens) Bukkit.getPluginManager().getPlugin("SQLTokens");
		
			if (sqlTokens != null) {
			
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(
						sqlTokens, new PlaceholderHook() {

							@Override
							public String onPlaceholderRequest(Player p, String identifier) {
								if (identifier.equals("tokens")) {
									
									return getTokens(p);
								} 
								return null;
							}

						}, true);

				if (hooked) {
					plugin.log.info("Hooked into SQLTokens for placeholders!");
				}
			
			}
		}
	}

	private String getTokens(Player p) {
		
		DataStore store = sqlTokens.getDataStore();
		
		if (store == null) {
			return "";
		}
		 
		return String.valueOf(store.getBalance(p));
	}
}
