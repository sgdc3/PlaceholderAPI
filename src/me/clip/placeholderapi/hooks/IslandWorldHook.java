package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

import pl.islandworld.api.IslandWorldApi;

public class IslandWorldHook extends IPlaceholderHook {

	public IslandWorldHook(InternalHook hook) {
		super(hook);
	}
	
	private String getPoints(Player p, boolean b) {
				return String.valueOf(IslandWorldApi.getPoints(p.getName(), b, false));
	}
	
	private String inParty(Player p) {
		return IslandWorldApi.isHelpingIsland(p.getName()) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
	}
	
	private String getMembers(Player p, boolean b) {
		
		String[] players = IslandWorldApi.getMembers(p.getName(), b);
		
		if (players == null) {
			return "0";
		}
		
		return String.valueOf(players.length);
	}
	
	private String canBuild(Player p, boolean b) {
		return IslandWorldApi.canBuildOnLocation(p, p.getLocation(), b) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (p == null) {
			return "";
		}

		switch (identifier) {
		
		case "points_with_party":
			return getPoints(p, true);
		case "points_without_party":
			return getPoints(p, false);
		case "island_members":
			return getMembers(p, false);
		case "all_members":
			return getMembers(p, true);
		case "can_build":
			return canBuild(p, true);
		case "in_party":
			return inParty(p);
		}
		return null;
	}
}
