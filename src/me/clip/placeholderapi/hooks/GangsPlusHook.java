package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import net.brcdev.gangs.GangsPlusAPI;

import org.bukkit.entity.Player;

public class GangsPlusHook extends IPlaceholderHook {

	public GangsPlusHook(InternalHook hook) {
		super(hook);
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
		return GangsPlusAPI.getPlayersGang(p).getOwnerMemberData().getName();
	}
	
	private String getGangSize(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getMembers().size());
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
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getKdRatio());
	}
	
	private String getGangBankMoney(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getBankMoney());
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
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getFightsLost());
	}
	
	private String getGangWLR(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getWlRatio());
	}
	
	private String getGangWins(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getFightsWon());
	}
	
	private String getGangOnline(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "0";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getOnlineMembers().size());
	}
	
	private String getGangRank(Player p) {
		if (!GangsPlusAPI.isInGang(p)) {
			return "";
		}
		return String.valueOf(GangsPlusAPI.getPlayersGang(p).getMemberData(p).getRank());
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

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (p == null) {
			return "";
		}
		
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
		}
		return null;
	}
	
}
