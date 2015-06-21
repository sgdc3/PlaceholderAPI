package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.vk2gpz.tokenenchant.TokenEnchant;

public class TokenEnchantHook {

	private PlaceholderAPIPlugin plugin;

	public TokenEnchantHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	


	private TokenEnchant te;
	
	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("TokenEnchant")) {
			
			te = (TokenEnchant) Bukkit.getPluginManager().getPlugin("TokenEnchant");
			
			if (te != null) {

				boolean hooked = PlaceholderAPI.registerPlaceholderHook(te, new PlaceholderHook() {

							@Override
							public String onPlaceholderRequest(Player p, String identifier) {

								if (identifier.equals("tokens")) {
									return String.valueOf(te.getTokens(p));
								}
								return null;
							}

						}, true);

				if (hooked) {
					plugin.log.info("Hooked into TokenEnchant for placeholders!");
				}
			}
		}
	}
}
