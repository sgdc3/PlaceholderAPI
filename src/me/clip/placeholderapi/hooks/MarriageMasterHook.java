package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import at.pcgamingfreaks.MarriageMaster.Bukkit.MarriageMaster;

public class MarriageMasterHook extends IPlaceholderHook {
	
	private MarriageMaster marriage;
	
	public MarriageMasterHook(InternalHook hook) {
		super(hook);
	}

	@Override
	public boolean hook() {
		
		boolean hooked = false;
		
		marriage = (MarriageMaster) Bukkit.getPluginManager().getPlugin(getPlugin());

			if (marriage != null) {
				
				hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
			}
		return hooked;
	}


	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (p == null) {
			return "";
		}

		switch (identifier) {

		case "married":
			return married(p);
		case "partner":
			return getPartner(p);

		}
		return null;
	}
	
	private boolean isMarried(Player p) {
		return marriage.HasPartner(p);
	}
	
	private String married(Player p) {
		return isMarried(p) ? "&c\u2665" : "&8\u2665";
	}
	
	private String getPartner(Player p) {
		if (!isMarried(p)) {
			return "";
		}
		String partner = marriage.DB.GetPartner(p);
	   
		if (partner == null) {
			return "";
		}
		return partner;
	}
	
}
