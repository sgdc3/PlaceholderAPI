package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.mydeblob.prisongangs.PrisonGangs;
import com.mydeblob.prisongangs.api.GangsAPI;

public class PrisonGangsHook {

	private PlaceholderAPIPlugin plugin;

	public PrisonGangsHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {
		if (Bukkit.getPluginManager().isPluginEnabled("PrisonGangs")) {

			PrisonGangs gangs = (PrisonGangs) Bukkit.getPluginManager().getPlugin("PrisonGangs");
			
			if (gangs != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(gangs, new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {

						switch (identifier) {
						
						case "in_gang":
							return inGang(p);
						case "gang_name":
							return getGang(p);
						case "gang_size":
							return getGangSize(p);
						case "gang_rank":
							return getGangRank(p);
						}

						return null;
					}
				});
				
				if (hooked) {
					plugin.log.info("Hooked into PrisonGangs for placeholders!");
				}
			}
		}
	}

	private String inGang(Player p) {
		
		if (GangsAPI.inGang(p)) {
			return "&aYes";
		}
		return "&cNo";
	}
	
	private String getGang(Player p) {
		return GangsAPI.inGang(p) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
	}
	
	private String getGangSize(Player p) {
		if (GangsAPI.inGang(p) && GangsAPI.getGang(p) != null
				&& GangsAPI.getGang(p).getAllPlayers() != null) {
			return String.valueOf(GangsAPI.getGang(p).getAllPlayers().size());
		}
		return "0";
	}
	
	private String getGangRank(Player p) {
		if (GangsAPI.inGang(p) && GangsAPI.getGang(p) != null) {
			String rank = GangsAPI.getGang(p).getPlayer(p).getRank().name();
			if (rank != null) {
				return rank;
			}
		}
		
		return "";
	}
	
}
