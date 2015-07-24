package me.clip.placeholderapi.placeholders;

import java.util.HashSet;
import java.util.Set;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;
import me.clip.placeholderapi.configuration.JavascriptPlaceholdersConfig;
import me.clip.placeholderapi.javascript.JavascriptPlaceholder;

import org.bukkit.entity.Player;

public class JavascriptPlaceholders {

	private PlaceholderAPIPlugin plugin;
	
	private JavascriptPlaceholdersConfig config;
	
	private static Set<JavascriptPlaceholder> scripts;

	public JavascriptPlaceholders(PlaceholderAPIPlugin i) {
		plugin = i;
		
		config = new JavascriptPlaceholdersConfig(plugin);
		
		config.loadPlaceholders();
	}
	
	public static boolean addJavascriptPlaceholder(JavascriptPlaceholder p) {
		
		if (p == null) {
			return false;
		}
		
		if (scripts == null) {
			scripts = new HashSet<JavascriptPlaceholder>();
		}
		
		if (scripts.isEmpty()) {
			scripts.add(p);
			return true;
		}
		
		for (JavascriptPlaceholder pl : scripts) {
			if (pl.getIdentifier().equalsIgnoreCase(p.getIdentifier())) {
				return false;
			}
		}
		scripts.add(p);
		return true;
	}
	
	public static int getJavascriptPlaceholdersAmount() {
		return scripts == null ? 0 : scripts.size();
	}
	
	public static void cleanup() {
		scripts = null;
	}

	public void hook() {

			boolean hooked = PlaceholderAPI.registerPlaceholderHook("javascript", new PlaceholderHook() {

				@Override
				public String onPlaceholderRequest(Player p, String identifier) {
					
					if (p == null) {
						return "";
					}
					
					if (scripts == null) {
						return null;
					}

					for (JavascriptPlaceholder script : scripts) {
						
						if (script.getIdentifier().equalsIgnoreCase(identifier)) {
							
							return script.evaluate(p);
						}
					}
					
					return null;
				}
				
			}, true);
			
			if (hooked) {
				plugin.log.info(getJavascriptPlaceholdersAmount() + " Javascript placeholders registered!");
			}
	}
}
