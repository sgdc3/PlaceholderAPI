package me.clip.placeholderapi;

import java.util.Map;
import java.util.Map.Entry;

import me.clip.placeholderapi.events.PlaceholderHookUnloadEvent;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import me.clip.placeholderapi.internal.Cacheable;
import me.clip.placeholderapi.internal.Cleanable;
import me.clip.placeholderapi.internal.InternalHook;
import me.clip.placeholderapi.internal.Taskable;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class PlaceholderListener implements Listener {
	
	private PlaceholderAPIPlugin plugin;
	
	public PlaceholderListener(PlaceholderAPIPlugin instance) {
		plugin = instance;
		Bukkit.getPluginManager().registerEvents(this, instance);
	}
	
	@EventHandler
	public void onInternalUnload(PlaceholderHookUnloadEvent event) {
		if (event.getHook() instanceof Taskable) {
			plugin.getLogger().info("Stopping task for " + event.getHookName() + " placeholders...");
			((Taskable) event.getHook()).stop();
		}
		
		if (event.getHook() instanceof Cacheable) {
			((Cacheable) event.getHook()).clear();
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPluginUnload(PluginDisableEvent e) {
		
		String n = e.getPlugin().getName();
		
		if (n == null) {
			return;
		}
		
		if (n.equals(plugin.getName())) {
			return;
		}
			
		if (PlaceholderAPI.unregisterPlaceholderHook(n)) {
			plugin.getLogger().info("Unregistered placeholder hook to: "+n);
			return;
		}
		
		InternalHook iHook = InternalHook.getHookByPluginName(n);

		if (iHook == null) {
			
			Map<String, PlaceholderHook> hooks = PlaceholderAPI.getPlaceholders();
			
			if (hooks == null) {
				return;
			}
			
			for (Entry<String, PlaceholderHook> hook : hooks.entrySet()) {
				if (hook.getValue() instanceof EZPlaceholderHook) {
					EZPlaceholderHook h = (EZPlaceholderHook) hook.getValue();
					if (h.getPluginName().equalsIgnoreCase(n)) {
						if (PlaceholderAPI.unregisterPlaceholderHook(hook.getKey())) {
							plugin.getLogger().info("Unregistered placeholder hook to: "+n);
						}
					}
				}
			}
			
			return;
		}
				
		if (PlaceholderAPI.unregisterPlaceholderHook(iHook.getIdentifier())) {
			plugin.getLogger().info("Unregistered placeholder hook to: "+n);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPluginload(PluginEnableEvent e) {
		
		String n = e.getPlugin().getName();
		
		if (n != null && !n.equals(plugin.getName())) {
			
			InternalHook hook = InternalHook.getHookByPluginName(n);
			
			if (hook == null) {
				return;
			}
			
			if (!hook.isRegistered()) {
				hook.register();
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		Map<String, PlaceholderHook> placeholders = PlaceholderAPI.getPlaceholders();
		
		if (placeholders == null || placeholders.isEmpty()) {
			return;
		}
		
		for (PlaceholderHook hooks : placeholders.values()) {
			if (hooks instanceof Cleanable) {
				((Cleanable) hooks).cleanup(e.getPlayer());
			}
		}
	}
}
