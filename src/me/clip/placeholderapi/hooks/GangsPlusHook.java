package me.clip.placeholderapi.hooks;

import me.brcdev.gangs.GangsPlugin;
import me.brcdev.gangs.GangsPlusAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GangsPlusHook {

	private PlaceholderAPIPlugin plugin;

	public GangsPlusHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {

		if (Bukkit.getPluginManager().isPluginEnabled("GangsPlus")) {

			GangsPlugin gp = (GangsPlugin) Bukkit.getPluginManager().getPlugin("GangsPlus");
			
			if (gp != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook(gp, new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {
						switch (identifier) {
						
						case "in_gang":
							return inGang(p);
						case "gang":
							return getGang(p);
						case "gang_tag":
							return getGangTag(p);
						case "gang_rank":
							return getGangRank(p);
						case "gang_homes":
							return getGangHomes(p);
						case "gang_friendlyfire":
							return getGangFF(p);
						case "gang_online":
							return getGangOnline(p);
						case "gang_size":
							return getGangSize(p);
						case "gang_leader":
							return getGangLeader(p);
						case "gang_level":
							return getGangLevel(p);
						case "gang_wins":
							return getGangWins(p);
						case "gang_losses":
							return getGangLosses(p);
						case "gang_wlr":
							return getGangWLR(p);
						case "gang_kills":
							return getGangKills(p);
						case "gang_deaths":
							return getGangDeaths(p);
						case "gang_kdr":
							return getGangKDR(p);
						case "gang_bank_money":
							return getGangBankMoney(p);
						case "gang_business_money":
							return getGangBusinessMoney(p);
						}
						return null;
					}
				});
				
				if (hooked) {
					plugin.log.info("Hooked into GangsPlus for placeholders!");
				}	
			}
		}
	}

	private String inGang(Player p) {
		return GangsPlusAPI.isInGang(p) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
	}
	
	private String getGang(Player p) {
		if (GangsPlusAPI.isInGang(p)) {
			return GangsPlusAPI.getPlayersGang(p).getName();
		}
		return "";
	}
	
	private String getGangTag(Player p) {
		if (GangsPlusAPI.isInGang(p)) {
			return GangsPlusAPI.getPlayersGang(p).getFormattedName();
		}
		return "";
	}
	
	private String getGangLeader(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "";
		}
		return GangsPlusAPI.getPlayersGang(p).getLeadersName();
	}
	
	private String getGangSize(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getMembersNumber());
	}
	
	private String getGangKills(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getKills());
	}
	
	private String getGangDeaths(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getDeaths());
	}
	
	private String getGangKDR(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getKdr());
	}
	
	private String getGangBankMoney(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getBankMoney());
	}
	
	private String getGangBusinessMoney(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getBusinessMoney());
	}
	
	private String getGangLevel(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getLevel());
	}
	
	private String getGangLosses(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getLost());
	}
	
	private String getGangWLR(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getWlr());
	}
	
	private String getGangWins(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getWon());
	}
	
	private String getGangOnline(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getOnlinePlayers().size());
	}
	
	private String getGangRank(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getPlayersRankName(p));
	}
	
	private String getGangHomes(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		if (GangsPlusAPI.getPlayersGang(p).getHomes() != null) {
			return String.valueOf(GangsPlusAPI.getPlayersGang(p).getHomes().size());
		}
		return "0";
	}
	
	private String getGangFF(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "";
		}
		
		return GangsPlusAPI.getPlayersGang(p).isFriendlyFire() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
	}
	
}
