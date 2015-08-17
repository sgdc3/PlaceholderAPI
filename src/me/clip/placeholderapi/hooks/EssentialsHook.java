package me.clip.placeholderapi.hooks;


import java.util.Date;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import me.clip.placeholderapi.util.TimeUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Kit;
import com.earth2me.essentials.User;

public class EssentialsHook extends IPlaceholderHook {
	
	private Essentials essentials = null;

	public EssentialsHook(InternalHook hook) {
		super(hook);
	}
	
	@Override
	public boolean hook() {
		
		if (!Bukkit.getPluginManager().isPluginEnabled(getPlugin())) {
			return false;
		}
		
		essentials = (Essentials) Bukkit.getPluginManager().getPlugin(getPlugin());
			
		boolean hooked = false;
		
		if (essentials != null) {
				
			hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
		}
		return hooked;
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		
		if (p == null) {
			return "";
		}
		
		if (identifier.startsWith("kit_last_use_")) {
			String kit = identifier.split("kit_last_use_")[1];
			
			Kit k = null;
			
			try {
				k = new Kit(kit, essentials);
			} catch (Exception e) {
				return "invalid kit";
			}
			
			long time = essentials.getUser(p).getKitTimestamp(k.getName());
			
			if (time == 01 || time <= 0) {
				return "01";
			}
			return PlaceholderAPIPlugin.getDateFormat().format(new Date(time));
		}
		
		if (identifier.startsWith("kit_is_available_")) {
			String kit = identifier.split("kit_is_available_")[1];
			
			Kit k = null;
			
			User u = essentials.getUser(p);
			
			try {
				k = new Kit(kit, essentials);
			} catch (Exception e) {
				return PlaceholderAPIPlugin.booleanFalse();
			}
			
			long time = -1;
			
			try {
				time = k.getNextUse(u);
			} catch (Exception e) {
				return PlaceholderAPIPlugin.booleanFalse();
			}
			
			if (time < 0 || time > 0) {
				return PlaceholderAPIPlugin.booleanFalse();
			}
			return PlaceholderAPIPlugin.booleanTrue();
		}
		
		if (identifier.startsWith("kit_time_until_available_")) {
			String kit = identifier.split("kit_time_until_available_")[1];
			
			Kit k = null;
			
			User u = essentials.getUser(p);
			
			try {
				k = new Kit(kit, essentials);
			} catch (Exception e) {
				return PlaceholderAPIPlugin.booleanFalse();
			}
			
			long time = -1;
			
			try {
				time = k.getNextUse(u);
			} catch (Exception e) {
				return "-1";
			}
			int seconds = (int)(time - System.currentTimeMillis())/1000;
			
			if (seconds <= 0) {
				return "0";
			}
			return TimeUtil.getTime(seconds);
		}
		
		if (identifier.startsWith("has_kit_")) {
			String kit = identifier.split("has_kit_")[1];
			
			return p.hasPermission("essentials.kits." + kit) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		}
		
		switch (identifier) {
		
		case "nickname":
			return essentials.getUser(p).getNickname() != null ? essentials.getUser(p).getNickname() : p.getName();
		case "godmode":
			return essentials.getUser(p).isGodModeEnabled() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
		case "jailed":
			return String.valueOf(essentials.getUser(p).isJailed());
		case "pm_recipient":
			User u = essentials.getUser(p);
			if (u.getReplyTo() != null) {
				if (!u.getReplyTo().isPlayer()) {
					return u.getReplyTo().getSender().getName() != null ? u.getReplyTo().getSender().getName() : "";
				}
				Player t = u.getReplyTo().getPlayer();
				
				if (t != null) {
					return t.getName();
				} 
				return "";
			} else {
				return "";
			}
			
		}
		
		return null;
	}	
}
