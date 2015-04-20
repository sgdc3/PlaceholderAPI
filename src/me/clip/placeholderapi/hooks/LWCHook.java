package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.griefcraft.lwc.LWC;
import com.griefcraft.lwc.LWCPlugin;
public class LWCHook {
	
	private PlaceholderAPIPlugin plugin;
	
	public LWCHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	private LWC lwc;

	public void hook() {
	   if (Bukkit.getPluginManager().isPluginEnabled("LWC")) {
		  
		   LWCPlugin l = (LWCPlugin) Bukkit.getPluginManager().getPlugin("LWC");
		   
		   if (l != null) {
			   
			   lwc = l.getLWC();
			   
			   if (lwc == null) {
				   return;
			   }
			   
			  boolean hooked = PlaceholderAPI.registerPlaceholderHook("lwc", new PlaceholderHook() {

				@Override
				public String onPlaceholderRequest(Player p, String identifier) {

					if (identifier.equals("locks")) {
						return String.valueOf(lwc.getPhysicalDatabase().getProtectionCount(p.getName()));
					}
					
					return null;
				}
				   
			   });
			  
			  if (hooked) {
				  plugin.log.info("Hooked into LWC for placeholders!");
			  } 
		   }
	   }
	}
}
