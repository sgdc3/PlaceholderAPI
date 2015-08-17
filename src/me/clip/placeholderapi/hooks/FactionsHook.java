package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.massivecraft.factions.entity.MPlayer;

public class FactionsHook extends IPlaceholderHook {

	public FactionsHook(InternalHook hook) {
		super(hook);
	}

	private boolean isCompatible(String version) {
		String v = version.replace(".", "");
		int i = 0;
		try {
			i = Integer.parseInt(v);
		} catch (Exception e) {
			return false;
		}
		return i >= 275;
	}

	@Override
	public boolean hook() {

		boolean hooked = false;
		
		Plugin factions = Bukkit.getPluginManager().getPlugin(getPlugin());
		
		if (factions != null) {
			
			String version = factions.getDescription().getVersion();

			if (isCompatible(version)) {

				hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);				
			}
		}
		
		return hooked;
	}
	

	private boolean hasFaction(Player p) {
		if (MPlayer.get(p) == null) {
			return false;
		}

		return MPlayer.get(p).hasFaction();
	}

	private String getFaction(Player p) {
		if (!hasFaction(p)) {
			return "";
		}

		return String.valueOf(MPlayer.get(p).getFactionName());
	}

	private String getFactionTitle(Player p) {
		if (!hasFaction(p)) {
			return "";
		}

		return String.valueOf(MPlayer.get(p).getTitle());
	}

	private String getFactionRole(Player p) {
		if (!hasFaction(p)) {
			return "";
		}

		return String.valueOf(MPlayer.get(p).getRole().getPrefix());
	}

	private String getFactionClaims(Player p) {
		if (!hasFaction(p)) {
			return "0";
		}

		return String.valueOf(MPlayer.get(p).getFaction().getLandCount());
	}

	private String getFactionMembers(Player p) {
		if (!hasFaction(p)) {
			return "0";
		}

		return String.valueOf(MPlayer.get(p).getFaction().getMPlayers().size());
	}

	private String getOnlineFactionMembers(Player p) {
		if (!hasFaction(p)) {
			return "0";
		}

		return String.valueOf(MPlayer.get(p).getFaction().getOnlinePlayers().size());
	}

	private String getFPower(Player p) {

		MPlayer uplayer = MPlayer.get(p);

		if (uplayer == null) {
			return "0";
		}

		return String.valueOf(uplayer.getPowerRounded());
	}

	private String getFPowerMax(Player p) {

		MPlayer uplayer = MPlayer.get(p);

		if (uplayer == null) {
			return "0";
		}

		return String.valueOf(uplayer.getPowerMaxRounded());
	}

	private String getFacPower(Player p) {
		if (!hasFaction(p)) {
			return "0";
		}

		return String.valueOf(MPlayer.get(p).getFaction().getPowerRounded());
	}

	private String getFacPowerMax(Player p) {
		if (!hasFaction(p)) {
			return "0";
		}

		return String.valueOf(MPlayer.get(p).getFaction().getPowerMaxRounded());
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
		}
		return null;
	
	}

}
