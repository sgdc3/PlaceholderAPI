package me.clip.placeholderapi.hooks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.clip.placeholderapi.internal.Cacheable;
import me.clip.placeholderapi.internal.Cleanable;
import me.clip.placeholderapi.internal.Configurable;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import me.clip.placeholderapi.internal.Taskable;
import me.realized.tm.api.TMAPI;

public class TokenManagerHook extends IPlaceholderHook implements Cacheable, Taskable, Cleanable, Configurable {

	private final Map<UUID, Long> tokens = new ConcurrentHashMap<UUID, Long>();
	
	private BukkitTask task;
	
	private int fetchInterval;
	
	public TokenManagerHook(InternalHook hook) {
		super(hook);
		
		int interval = getPlaceholderAPI().getConfig().getInt(getIdentifier() + ".check_interval", 60);
		
		if (interval > 0) {
			fetchInterval = interval;
		} else {
			fetchInterval = 60;
		}
	}
	
	@Override
	public Map<String, Object> getDefaults() {
		final Map<String, Object> defaults = new HashMap<String, Object>();
		defaults.put("check_interval", 30);
		return defaults;
	}

	@Override
	public void start() {
		task = new BukkitRunnable() {

			@Override
			public void run() {
				
				if (tokens.isEmpty()) {
					return;
				}
				
				for (UUID u : tokens.keySet()) {
					tokens.put(u, TMAPI.getTokens(u));
				}
			}
			
		}.runTaskTimerAsynchronously(getPlaceholderAPI(), 100L, 20L*fetchInterval);
	}

	@Override
	public void stop() {
		if (task != null) {
			try {
				task.cancel();
			} catch(IllegalStateException ex) {
				//uh oh
			}
			task = null;
		}
	}

	@Override
	public void clear() {
		tokens.clear();
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		if (identifier.equals("tokens")) {
			if (tokens.containsKey(p.getUniqueId())) {
				return String.valueOf(tokens.get(p.getUniqueId()));
			}
			tokens.put(p.getUniqueId(), 0l);
			return "0";
		}
		return null;
	}

	@Override
	public void cleanup(Player p) {
		if (tokens.containsKey(p.getUniqueId())) {
			tokens.remove(p.getUniqueId());
		}
	}
}
