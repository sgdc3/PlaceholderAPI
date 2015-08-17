package me.clip.placeholderapi.hooks;

import me.clip.ezprestige.PrestigeManager;
import me.clip.ezprestige.objects.Prestige;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

public class EZPrestigeHook extends IPlaceholderHook {

	public EZPrestigeHook(InternalHook hook) {
		super(hook);
	}

	private String getCurrentPrestigeTag(Player p) {
		

		Prestige pr = PrestigeManager.getCurrentPrestige(p);
		
		if (pr == null) {
			return "";
		}
		
		return pr.getDisplayTag();
	}

	private String getCurrentPrestige(Player p) {
		
		Prestige pr = PrestigeManager.getCurrentPrestige(p);
		
		if (pr == null) {
			return "0";
		}
		
		return String.valueOf(pr.getPrestige());
	}
	
	private String getNextPrestigeTag(Player p) {
		
		Prestige pr = PrestigeManager.getNextPrestige(p);
		
		if (pr == null) {
			return "";
		}
		
		return pr.getDisplayTag();
	}

	private String getNextPrestige(Player p) {
		
		Prestige pr = PrestigeManager.getNextPrestige(p);
		
		if (pr == null) {
			return "";
		}
		
		return String.valueOf(pr.getPrestige());
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (p == null) {
			return "";
		}
		
		switch (identifier) {
		
		case "prestige":
			return getCurrentPrestige(p);
		case "prestigetag":
			return getCurrentPrestigeTag(p);
		case "nextprestige":
			return getNextPrestige(p);
		case "nextprestigetag":
			return getNextPrestigeTag(p);
		}
		
		return null;
	}
}
