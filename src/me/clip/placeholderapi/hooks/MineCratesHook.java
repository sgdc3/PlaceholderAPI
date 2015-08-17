package me.clip.placeholderapi.hooks;

import me.clip.minecrates.MineCrates;
import me.clip.minecrates.area.AreaHandler;
import me.clip.minecrates.rewards.RewardArea;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

public class MineCratesHook extends IPlaceholderHook {

	public MineCratesHook(InternalHook hook) {
		super(hook);
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		AreaHandler h = MineCrates.getInstance().getAreaHandler();
		
		if (identifier.equalsIgnoreCase("area_handler_type")) {
			return h.getType().getName();
		}
		
		String area = h.getArea(p.getLocation());
		
		if (identifier.equalsIgnoreCase("area_name")) {
			return area != null ? area : "";
		}
		
		
		//get the MineCrates reward area associated with the area name found from the hook
		RewardArea rArea = RewardArea.getRewardArea(area);
		
		if (identifier.equalsIgnoreCase("in_reward_area")) {
			return rArea != null ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		}
		
		if (rArea == null) {
			return "";
		}
		
		if (identifier.equalsIgnoreCase("counter")) {
			return String.valueOf(rArea.getCounter());
		}
		
		if (identifier.equalsIgnoreCase("chance")) {
			return String.valueOf(rArea.getDropChance());
		}
		
		if (identifier.equalsIgnoreCase("total_needed")) {
			return String.valueOf(rArea.getThreshold());
		}
		
		if (identifier.equalsIgnoreCase("reward_list_name")) {
			return rArea.getRewardList(p) != null ? rArea.getRewardList(p).getName() : "none";
		}

		return null;
	}
}