package me.clip.placeholderapi.hooks;

import me.clip.autosell.AutoSell;
import me.clip.autosell.SellHandler;
import me.clip.autosell.multipliers.Multipliers;
import me.clip.autosell.objects.Multiplier;
import me.clip.autosell.objects.PermissionMultiplier;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

public class AutoSellHook extends IPlaceholderHook {
	
	public AutoSellHook(InternalHook hook) {
		super(hook);
	}
	
	private String getPermMulti(Player p) {

		PermissionMultiplier m = Multipliers.getPermissionMultiplier(p);

		if (m == null) {
			return "0.0";
		}

		return String.valueOf(m.getMultiplier());
	}
	
	private String getPermMultiName(Player p) {

		PermissionMultiplier m = Multipliers.getPermissionMultiplier(p);
			
		if (m == null) {
			return "";
		}
		return String.valueOf(m.getIdentifier());
	}
	
	private String getTimeMulti(Player p) {

		Multiplier m = Multipliers.getMultiplier(p.getName());
			
		if (m == null) {
			return "0";
		}
			
		return String.valueOf(m.getMultiplier());
	}
	
	private String getTimeMultiTime(Player p) {
		
		Multiplier m = Multipliers.getMultiplier(p.getName());
			
		if (m == null) {
			return "0";
		}
		
		return String.valueOf(m.getTimeLeft());
	}
	
	private String getTimeMultiMins(Player p) {
		
		Multiplier m = Multipliers.getMultiplier(p.getName());
			
		if (m == null) {
			return "0";
		}
		
		return String.valueOf(m.getMinutesLeft());
	}
	
	private String getTotalMulti(Player p) {
	
		double multi = 0;
			
		if (Multipliers.getGlobalMultiplier() != null) {
			multi = Multipliers.getGlobalMultiplier().getMultiplier();
		}
			
		if (Multipliers.getPermissionMultiplier(p) != null) {
			multi = multi+Multipliers.getPermissionMultiplier(p).getMultiplier();
		}
			
		if (Multipliers.getMultiplier(p.getName()) != null) {
			multi = multi+Multipliers.getMultiplier(p.getName()).getMultiplier();
		}
			
		return String.valueOf(multi);
	}
	
	private String getCurrentShop(Player p) {
			
		if (SellHandler.getShop(p) != null) {
			return SellHandler.getShop(p).getName();
		}
		
		if (SellHandler.getPermShop(p) != null) {
			return SellHandler.getPermShop(p).getName();
		}
		return "";
	}
	
	private String inSmelt(Player p) {
		return AutoSell.inSmeltMode(p) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
	}
	
	private String inSell(Player p) {
		return AutoSell.inSellMode(p) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
	}
	
	private String inBlocks(Player p) {
		return AutoSell.inAutoBlockMode(p) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {


		if (p == null) {
			return "";
		}
		
		// %autosell_current_shop%
		switch (identifier) {

		case "in_autosell_mode":
			return inSell(p);
		case "in_autoblocks_mode":
			return inBlocks(p);
		case "in_automelt_mode":
			return inSmelt(p);
		case "current_shop":
			return getCurrentShop(p);
		case "total_multiplier":
			return getTotalMulti(p);
		case "time_multiplier_minsleft":
			return getTimeMultiMins(p);
		case "time_multiplier_timeleft":
			return getTimeMultiTime(p);
		case "time_multiplier":
			return getTimeMulti(p);
		case "perm_multiplier_name":
			return getPermMultiName(p);
		case "perm_multiplier":
			return getPermMulti(p);
		}
		
		return null;
	
	}
}
