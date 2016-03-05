package me.clip.placeholderapi.hooks;

import net.lightshard.prisonmines.PrisonMines;
import net.lightshard.prisonmines.mine.Mine;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

public class PrisonMinesHook extends IPlaceholderHook {

	public PrisonMinesHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		if (p == null) {
			return "";
		}
		
		if (identifier.startsWith("blocks_mined_")) {
			
			String mine = identifier.split("blocks_mined_")[1];
			
			Mine m = PrisonMines.getAPI().getByName(mine);
			
			if (m == null) {
				return null;
			}
			
			return String.valueOf(PrisonMines.getAPI().getBlocksMined(m));
		}
		
		if (identifier.startsWith("percent_mined_")) {
			
			String mine = identifier.split("percent_mined_")[1];
			
			Mine m = PrisonMines.getAPI().getByName(mine);
			
			if (m == null) {
				return null;
			}
			
			return String.valueOf(PrisonMines.getAPI().getPercentMined(m));
		}
		
		if (identifier.startsWith("percent_left_")) {
			
			String mine = identifier.split("percent_left_")[1];
			
			Mine m = PrisonMines.getAPI().getByName(mine);
			
			if (m == null) {
				return null;
			}
			
			return String.valueOf(PrisonMines.getAPI().getPercentLeft(m));
		}
		
		if (identifier.startsWith("time_until_reset_")) {
			
			String mine = identifier.split("time_until_reset_")[1];
			
			Mine m = PrisonMines.getAPI().getByName(mine);
			
			if (m == null) {
				return null;
			}
			
			return String.valueOf(PrisonMines.getAPI().getTimeUntilReset(m));
		}
		
		if (identifier.startsWith("has_timed_reset_")) {
			
			String mine = identifier.split("has_timed_reset_")[1];
			
			Mine m = PrisonMines.getAPI().getByName(mine);
			
			if (m == null) {
				return null;
			}
			
			return PrisonMines.getAPI().hasTimedReset(m) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		}
		
		if (identifier.startsWith("has_percentage_reset_")) {
			
			String mine = identifier.split("has_percentage_reset_")[1];
			
			Mine m = PrisonMines.getAPI().getByName(mine);
			
			if (m == null) {
				return null;
			}
			
			return PrisonMines.getAPI().hasPercentageReset(m) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		}
		
		Mine m = PrisonMines.getAPI().getByLocation(p.getLocation());
		
		if (m == null) {
			return "";
		}
		if (identifier.equalsIgnoreCase("name")) {
			return m.getName();
		}
		if (identifier.equalsIgnoreCase("blocks_mined")) {
			return String.valueOf(PrisonMines.getAPI().getBlocksMined(m));
		}
		if (identifier.equalsIgnoreCase("percent_mined")) {
			return String.valueOf(PrisonMines.getAPI().getPercentMined(m));
		}
		if (identifier.equalsIgnoreCase("percent_left")) {
			return String.valueOf(PrisonMines.getAPI().getPercentLeft(m));
		}
		if (identifier.equalsIgnoreCase("time_until_reset")) {
			return String.valueOf(PrisonMines.getAPI().getTimeUntilReset(m));
		}
		if (identifier.equalsIgnoreCase("has_timed_reset")) {
			return PrisonMines.getAPI().hasTimedReset(m) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		}
		if (identifier.equalsIgnoreCase("has_percentage_reset")) {
			return PrisonMines.getAPI().hasPercentageReset(m) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		}
		return null;
	}

}
