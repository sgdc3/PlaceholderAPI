package me.clip.placeholderapi.hooks;

import me.armar.plugins.autorank.Autorank;
import me.armar.plugins.autorank.api.API;
import me.armar.plugins.autorank.util.AutorankTools;
import me.armar.plugins.autorank.util.AutorankTools.Time;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import me.clip.placeholderapi.util.TimeUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AutoRankHook extends IPlaceholderHook {
	
	private API autorank;

	public AutoRankHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public boolean hook() {
		
		Autorank ar = (Autorank) Bukkit.getPluginManager().getPlugin(InternalHook.AUTORANK.getPluginName());
		
		if (ar != null) {
			
			autorank = ar.getAPI();
			
			if (autorank == null) {
				return false;
			}
		}
		
		return PlaceholderAPI.registerPlaceholderHook(super.getIdentifier(), this);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (autorank == null || p == null) {
			return "";
		}
		
		switch(identifier) {
		
		case "current_rank":
			return autorank.getPrimaryGroup(p) != null ? autorank.getPrimaryGroup(p) : "";
		case "time_of_player":
			return String.valueOf(autorank.getTimeOfPlayer(p));
		case "local_time":
			String localTime = AutorankTools.timeToString(autorank.getLocalPlayTime(p.getUniqueId()), Time.MINUTES);
			return localTime != null ? localTime : ""; 
		case "local_time_formatted":
			String localTimeFormatted = AutorankTools.timeToString(autorank.getLocalPlayTime(p.getUniqueId()), Time.SECONDS);
			if (localTimeFormatted == null) {
				return "";
			}
			try {
				int t = Integer.parseInt(localTimeFormatted);
				return TimeUtil.getTime(t);
			} catch (NumberFormatException ex) {
				return "";
			}
		case "global_time":
			String globalTime = AutorankTools.timeToString(autorank.getGlobalPlayTime(p.getUniqueId()), Time.MINUTES);
			return globalTime != null ? globalTime : ""; 
		case "global_time_formatted":
			String globalTimeFormatted = AutorankTools.timeToString(autorank.getGlobalPlayTime(p.getUniqueId()), Time.SECONDS);
			if (globalTimeFormatted == null) {
				return "";
			}
			try {
				int t = Integer.parseInt(globalTimeFormatted);
				return TimeUtil.getTime(t);
			} catch (NumberFormatException ex) {
				return "";
			}
		}
	
		return null;
	
	}
}
