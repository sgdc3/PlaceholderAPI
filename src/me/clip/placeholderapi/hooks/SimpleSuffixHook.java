package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import me.staartvin.simplesuffix.SimpleSuffix;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SimpleSuffixHook extends IPlaceholderHook {

	private SimpleSuffix ss;
	
	public SimpleSuffixHook(InternalHook hook) {
		super(hook);
	}
	
	@Override
	public boolean hook() {
		
		boolean hooked = false;
		
		ss = (SimpleSuffix) Bukkit.getPluginManager().getPlugin(getPlugin());
			
		if (ss != null) {

			hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
		}
		
		return hooked;
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		if (identifier.equals("prefix")) {
			return getPrefix(p);
		} else if (identifier.equals("suffix")) {
			return getSuffix(p);
		}
		return null;
	}
	
	private String getPrefix(Player p) {
		String pre = ss.getPermHandler().getPrefix(p);
		if (pre == null) {
			return "";
		}
		return pre;
	}
	
	private String getSuffix(Player p) {
		String su = ss.getPermHandler().getSuffix(p);
		if (su == null) {
			return "";
		}
		return su;
	}
}
