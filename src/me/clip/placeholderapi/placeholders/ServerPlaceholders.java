package me.clip.placeholderapi.placeholders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import me.clip.placeholderapi.util.TimeUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerPlaceholders extends IPlaceholderHook {

	public ServerPlaceholders(InternalHook hook) {
		super(hook);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (identifier.startsWith("countdown_")) {
			String time = identifier.replace("countdown_", "");
			
			if (time.indexOf("_") == -1) {
				
				Date then = null;
				
				try {
					then = PlaceholderAPIPlugin.getDateFormat().parse(time);
				} catch(Exception e) {
					return null;
				}
				
				Date now = new Date();
				
				long between = then.getTime() - now.getTime();
				
				if (between <= 0) {
					return "0";
				}
				
				return TimeUtil.getTime((int)TimeUnit.MILLISECONDS.toSeconds(between));
				
			} else {
				
				String[] parts = time.split("_");
				
				if (parts.length != 2) {
					return "invalid format and time";
				}
				
				time = parts[1];
				
				String format = parts[0];
				
				SimpleDateFormat f = null;
				
				try {
					f = new SimpleDateFormat(format);
				} catch (Exception e) {
					return "invalid date format";
				}
				
				Date then = null;
				
				try {
					then = f.parse(time);
				} catch(Exception e) {
					return "invalid date";
				}
				
				long t = System.currentTimeMillis();
				
				long between = then.getTime() - t;
				
				if (between <= 0) {
					return "0";
				}
				
				return TimeUtil.getTime((int)TimeUnit.MILLISECONDS.toSeconds(between));
				
			}
		}
		
		switch(identifier) {
		
		case "online":
			return String.valueOf(Bukkit.getOnlinePlayers().size());
		case "unique_joins":
			return String.valueOf(Bukkit.getOfflinePlayers().length);
		
		}

		return null;
	
	}
}
