package me.clip.placeholderapi;

import java.util.Map;
import java.util.Map.Entry;

import me.clip.placeholderapi.events.PlaceholderHookUnloadEvent;
import me.clip.placeholderapi.expansion.Cacheable;
import me.clip.placeholderapi.expansion.Cleanable;
import me.clip.placeholderapi.expansion.ExpansionManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Taskable;
import me.clip.placeholderapi.external.EZPlaceholderHook;

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
	
	@EventHandler
	public void onEnable(PluginEnableEvent event) {
		ExpansionManager m = plugin.getExpansionManager();
		PlaceholderExpansion e = m.getWaitingExpansion(event.getPlugin().getName().toLowerCase());
		if (e != null && e.canRegister()) {
			if (e.isRegistered() || m.registerExpansion(e)) {
				m.removeExpansion(e.getPlugin());
			}
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
			
		if (PlaceholderAPI.unregisterPlaceholderHook(n.toLowerCase())) {
			plugin.getLogger().info("Unregistered placeholder hook to: "+n);
			return;
		}
			
		Map<String, PlaceholderHook> hooks = PlaceholderAPI.getPlaceholders();
			
		if (hooks == null) {
			return;
		}
			
		for (Entry<String, PlaceholderHook> hook : hooks.entrySet()) {
				
			PlaceholderHook i = hook.getValue();
				
			if (i instanceof EZPlaceholderHook) {
					
				EZPlaceholderHook h = (EZPlaceholderHook) i;
					
				if (h.getPluginName().equalsIgnoreCase(n)) {
					if (PlaceholderAPI.unregisterPlaceholderHook(hook.getKey())) {
						plugin.getLogger().info("Unregistered placeholder hook for placeholder identifier: "+n);
					}
				}
			} else if (i instanceof PlaceholderExpansion) {
				PlaceholderExpansion ex = (PlaceholderExpansion) i;
				if (ex.getPlugin() == null) {
					continue;
				}
				if (ex.getPlugin().equalsIgnoreCase(n)) {
					if (PlaceholderAPI.unregisterPlaceholderHook(hook.getKey())) {
						plugin.getLogger().info("Unregistered placeholder expansion for placeholder identifier: "+n);
					}
				}
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
