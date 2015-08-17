package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

public class FactionsUUIDHook extends IPlaceholderHook {

	public FactionsUUIDHook(InternalHook hook) {
		super(hook);
	}
	
	@Override
	public boolean hook() {

		boolean hooked = false;
		
		Plugin factions = Bukkit.getPluginManager().getPlugin(getPlugin());

		String version = factions.getDescription().getVersion();

		if (version.startsWith("1.6.9.5")) {

			hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
		}
		return hooked;
	}

	private boolean hasFaction(Player p) {
		if (FPlayers.getInstance().getByPlayer(p) == null) {
			return false;
		}

		return FPlayers.getInstance().getByPlayer(p).hasFaction();
	}

	private String getFaction(Player p) {
		if (!hasFaction(p)) {
			return "";
		}

		return String.valueOf(FPlayers.getInstance().getByPlayer(p).getFaction().getTag());
	}

	private String getFactionTitle(Player p) {
		if (!hasFaction(p)) {
			return "";
		}

		return String.valueOf(FPlayers.getInstance().getByPlayer(p).getTitle());
	}

	private String getFactionRole(Player p) {
		if (!hasFaction(p)) {
			return "";
		}

		return String.valueOf(FPlayers.getInstance().getByPlayer(p).getRole().getPrefix());
	}

	private String getFactionClaims(Player p) {
		if (!hasFaction(p)) {
			return "0";
		}

		return String.valueOf(FPlayers.getInstance().getByPlayer(p).getFaction().getLandRounded());
	}

	private String getFactionMembers(Player p) {
		if (!hasFaction(p)) {
			return "0";
		}

		return String.valueOf(FPlayers.getInstance().getByPlayer(p).getFaction().getFPlayers().size());
	}

	private String getOnlineFactionMembers(Player p) {
		if (!hasFaction(p)) {
			return "0";
		}

		return String.valueOf(FPlayers.getInstance().getByPlayer(p).getFaction().getOnlinePlayers().size());
	}

	private String getFPower(Player p) {

		FPlayer fplayer = FPlayers.getInstance().getByPlayer(p);

		if (fplayer == null) {
			return "0";
		}

		return String.valueOf(fplayer.getPowerRounded());
	}

	private String getFPowerMax(Player p) {

		FPlayer fplayer = FPlayers.getInstance().getByPlayer(p);

		if (fplayer == null) {
			return "0";
		}

		return String.valueOf(fplayer.getPowerMaxRounded());
	}

	private String getFacPower(Player p) {
		if (!hasFaction(p)) {
			return "0";
		}

		return String.valueOf(FPlayers.getInstance().getByPlayer(p).getFaction().getPowerRounded());
	}

	private String getFacPowerMax(Player p) {
		if (!hasFaction(p)) {
			return "0";
		}

		return String.valueOf(FPlayers.getInstance().getByPlayer(p).getFaction().getPowerMaxRounded());
	}

	private String getChatTag(Player p) {

		FPlayer fplayer = FPlayers.getInstance().getByPlayer(p);

		if (fplayer == null) {
			return "";
		}

		return String.valueOf(fplayer.getChatTag());
		
		
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		
		if (p == null) {
			return "";
		}
		
		switch (identifier) {
		
		case "faction":
			return getFaction(p);
		case "power":
			return getFPower(p);
		case "powermax":
			return getFPowerMax(p);
		case "factionpower":
			return getFacPower(p);
		case "factionpowermax":
			return getFacPowerMax(p);
		case "title":
			return getFactionTitle(p);
		case "role":
			return getFactionRole(p);
		case "claims":
			return getFactionClaims(p);
		case "onlinemembers":
			return getOnlineFactionMembers(p);
		case "allmembers":
			return getFactionMembers(p);
		case "chat_tag":
			return getChatTag(p);
		}
		return null;
	
	}
}
