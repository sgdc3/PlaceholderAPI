package me.clip.placeholderapi.placeholders;

import java.util.HashSet;
import java.util.Set;

import me.clip.placeholderapi.configuration.JavascriptPlaceholdersConfig;
import me.clip.placeholderapi.internal.Cacheable;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import me.clip.placeholderapi.javascript.JavascriptPlaceholder;

import org.bukkit.entity.Player;

public class JavascriptPlaceholders extends IPlaceholderHook implements Cacheable {

	private JavascriptPlaceholdersConfig config;
	
	private static Set<JavascriptPlaceholder> scripts;

	public JavascriptPlaceholders(InternalHook hook) {
		
		super(hook);
		
		config = new JavascriptPlaceholdersConfig(getPlaceholderAPI());
		
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
	
	@Override
	public void clear() {
		scripts = null;
		JavascriptPlaceholder.cleanup();
	}

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
}
