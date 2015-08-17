package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.vk2gpz.tokenenchant.TokenEnchant;

public class TokenEnchantHook extends IPlaceholderHook {

	public TokenEnchantHook(InternalHook hook) {
		super(hook);
	}
	
	private TokenEnchant te;
	
	@Override
	public boolean hook() {
		
		boolean hooked = false;
		
		te = (TokenEnchant) Bukkit.getPluginManager().getPlugin(getPlugin());
			
		if (te != null) {

			hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
		}
		return hooked;
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (te == null || p == null) {
			return "";
		}

		if (identifier.equals("tokens")) {
			return String.valueOf(te.getTokens(p));
		}
		return null;
	}
}
