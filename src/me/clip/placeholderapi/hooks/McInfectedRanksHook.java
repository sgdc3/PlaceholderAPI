package me.clip.placeholderapi.hooks;

import me.bimmr.mcinfectedranks.McInfectedRanks;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class McInfectedRanksHook {

	private PlaceholderAPIPlugin plugin;

	public McInfectedRanksHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {

		if (Bukkit.getPluginManager().isPluginEnabled("McInfected-Ranks")) {

			boolean hooked = PlaceholderAPI.registerPlaceholderHook("McInfected-Ranks", new PlaceholderHook() {

				@Override
				public String onPlaceholderRequest(Player p, String identifier) {
					switch (identifier) {

					case "rank":
						return getRank(p);
					case "prefix":
						return getPrefix(p);

					}
					return null;
				}
			}, true);

			if (hooked) {
				plugin.log.info("Hooked into McInfected-Ranks for placeholders!");
			}
		}
	}

	private String getRank(Player p) {
		
		String rank = McInfectedRanks.getRankManager().getPlayersRank(p).getName();
		
		return rank != null ? rank : "";
	}

	private String getPrefix(Player p) {
		
		String pre = McInfectedRanks.getRankManager().getPlayersRank(p).getPrefix();
		return pre != null ? pre : "";
	}
}