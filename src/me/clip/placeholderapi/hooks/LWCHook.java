package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.griefcraft.lwc.LWC;
import com.griefcraft.lwc.LWCPlugin;
public class LWCHook extends IPlaceholderHook {

	public LWCHook(InternalHook hook) {
		super(hook);
	}
	
	private LWC lwc;

	@Override
	public boolean hook() {
		
		boolean hooked = false;

		LWCPlugin l = (LWCPlugin) Bukkit.getPluginManager().getPlugin(getPlugin());

		if (l != null) {
			lwc = l.getLWC();

			if (lwc != null) {
				hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
			}
		}
		return hooked;
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (lwc == null || p == null) {
			return "";
		}

		if (identifier.equals("locks")) {
			return String.valueOf(lwc.getPhysicalDatabase().getProtectionCount(p.getName()));
		}
		
		return null;
	}
}
