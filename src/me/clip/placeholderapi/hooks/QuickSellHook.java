package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import me.mrCookieSlime.QuickSell.boosters.Booster;

import org.bukkit.entity.Player;

public class QuickSellHook extends IPlaceholderHook {

	public QuickSellHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}

		if (identifier.equals("booster")) {
			return getBooster(p);
		}
		if (identifier.equals("booster_time")) {
			return getBoosterTimeLeft(p);
		}
		return null;
	}


	public String getBooster(Player p) {
		if (Booster.getBoosters(p.getName()) != null && !Booster.getBoosters(p.getName()).isEmpty()) {
			double m = 0;
			for (Booster b : Booster.getBoosters(p.getName())) {
				m = m+b.getMultiplier();
			}
			return String.valueOf(m);
		}
		return "0";
	}
	
	public String getBoosterTimeLeft(Player p) {
		if (Booster.getBoosters(p.getName()) != null && !Booster.getBoosters(p.getName()).isEmpty()) {
			for (Booster b : Booster.getBoosters(p.getName())) {
				return String.valueOf(b.formatTime());
			}
		}
		return "";
	}
}
