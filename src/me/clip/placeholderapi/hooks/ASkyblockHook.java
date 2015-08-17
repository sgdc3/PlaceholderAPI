package me.clip.placeholderapi.hooks;

import org.bukkit.entity.Player;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

public class ASkyblockHook extends IPlaceholderHook {

	
	public ASkyblockHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		
		if (ASkyBlockAPI.getInstance() == null) {
			return "";
		}
		
		if (p == null) {
			return "";
		}
		
		switch (identifier) {
		
		case "level":
			return String.valueOf(ASkyBlockAPI.getInstance().getIslandLevel(p.getUniqueId()));
		case "island_x":
			return String.valueOf(ASkyBlockAPI.getInstance().getIslandLocation(p.getUniqueId()).getBlockX());
		case "island_y":
			return String.valueOf(ASkyBlockAPI.getInstance().getIslandLocation(p.getUniqueId()).getBlockY());
		case "island_z":
			return String.valueOf(ASkyBlockAPI.getInstance().getIslandLocation(p.getUniqueId()).getBlockZ());
		case "island_world":
			return ASkyBlockAPI.getInstance().getIslandLocation(p.getUniqueId()).getWorld().getName();
		case "team_size":
			return ASkyBlockAPI.getInstance().getTeamMembers(p.getUniqueId()) != null ? 
					String.valueOf(ASkyBlockAPI.getInstance().getTeamMembers(p.getUniqueId()).size()) : "0";
		case "coop_islands":
			return ASkyBlockAPI.getInstance().getCoopIslands(p) != null ? 
					String.valueOf(ASkyBlockAPI.getInstance().getCoopIslands(p).size()) : "0";
		}
		return null;
	
	}
}
