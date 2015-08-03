package me.clip.placeholderapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

public class PlaceholderAPI implements Listener {
	
	private PlaceholderAPIPlugin plugin;
	
	private final static Pattern PLACEHOLDER_PATTERN = Pattern.compile("[%]([a-zA-Z0-9_.-]+)[%]");
	
	private final static Pattern BRACKET_PLACEHOLDER_PATTERN = Pattern.compile("[{]([a-zA-Z0-9_.-]+)[}]");

	private static Map<String, PlaceholderHook> placeholders = new HashMap<String, PlaceholderHook>();

	private static Set<String> externalHooks = new HashSet<String>();
	
	public PlaceholderAPI(PlaceholderAPIPlugin i) {
		plugin = i;
		Bukkit.getPluginManager().registerEvents(this, i);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPluginUnload(PluginDisableEvent e) {
		
		String n = e.getPlugin().getName();
		
		if (n != null && !n.equals(plugin.getName())) {
			
			if (unregisterPlaceholderHook(n)) {
				plugin.getLogger().info("Unregistered placeholder hook to: "+n);
			}
		}
	}
	

	
	protected void resetInternalPlaceholderHooks() {
		
		Set<String> registered = getRegisteredPlaceholderPlugins();
		
		Set<String> external = getExternalPlaceholderPlugins();
		
		for (String pl : registered) {
			
			if (!external.contains(pl)) {
				unregisterPlaceholderHook(pl);
			}
		}
		
		plugin.initializeHooks();
	}
	
	protected void unregisterAll() {
		placeholders = null;
		externalHooks = null;
	}
	
	/**
	 * registers a placeholder hook specific to the plugin specified. Any time a placeholder that matches the plugin name, 
	 * the method inside of the PlaceholderHook will be called to retrieve a value
	 * placeholders always follow a specific format - %<plugin>_<identifier>%
	 * The identifier is passed to the method which will provide the value. It is up to the registering plugin to determine what
	 * a valid identifier is. If an identifier is unknown, you may return null which specifies the placeholder is invalid.
	 * @param plugin Plugin registering the placeholder hook
	 * @param placeholderHook PlaceholderHook class that contains the override method which is called when a value is needed for the specific plugins placeholder
	 * @return true if the hook was successfully registered, false if there was already a hook registered for the specified plugin
	 */
	public static boolean registerPlaceholderHook(Plugin plugin, PlaceholderHook placeholderHook) {
		
		if (plugin == null) {
			return false;
		}

		return registerPlaceholderHook(plugin.getName(), placeholderHook, false);
	}
	
	/**
	 * registers a placeholder hook specific to the plugin name specified. Any time a placeholder that matches the plugin name, 
	 * the method inside of the PlaceholderHook will be called to retrieve a value
	 * placeholders always follow a specific format - %<plugin>_<identifier>%
	 * The identifier is passed to the method which will provide the value. It is up to the registering plugin to determine what
	 * a valid identifier is. If an identifier is unknown, you may return null which specifies the placeholder is invalid.
	 * @param plugin Plugin name registering the placeholder hook
	 * @param placeholderHook PlaceholderHook class that contains the override method which is called when a value is needed for the specific plugins placeholder
	 * @return true if the hook was successfully registered, false if there was already a hook registered for the specified plugin
	 */
	public static boolean registerPlaceholderHook(String plugin, PlaceholderHook placeholderHook) {
		return registerPlaceholderHook(plugin, placeholderHook, false);
	}
	
	/**
	 * registers a placeholder hook specific to the plugin specified. Any time a placeholder that matches the plugin name, 
	 * the method inside of the PlaceholderHook will be called to retrieve a value
	 * placeholders always follow a specific format - %<plugin>_<identifier>%
	 * The identifier is passed to the method which will provide the value. It is up to the registering plugin to determine what
	 * a valid identifier is. If an identifier is unknown, you may return null which specifies the placeholder is invalid.
	 * @param plugin Plugin registering the placeholder hook
	 * @param placeholderHook PlaceholderHook class that contains the override method which is called when a value is needed for the specific plugins placeholder
	 * @param isInternalHook true if hook was registered by PlaceholderAPI internally, false if an external plugin is registering the placeholder hook
	 * this value should only be true if PlaceholderAPI is registering the PlaceholderHook
	 * @return true if the hook was successfully registered, false if there was already a hook registered for the specified plugin
	 */
	public static boolean registerPlaceholderHook(Plugin plugin, PlaceholderHook placeholderHook, boolean isInternalHook) {
		
		if (plugin == null) {
			return false;
		}
		
		return registerPlaceholderHook(plugin.getName(), placeholderHook, isInternalHook);
	}
	
	/**
	 * registers a placeholder hook specific to the plugin name specified. Any time a placeholder that matches the plugin name, 
	 * the method inside of the PlaceholderHook will be called to retrieve a value
	 * placeholders always follow a specific format - %<plugin>_<identifier>%
	 * The identifier is passed to the method which will provide the value. It is up to the registering plugin to determine what
	 * a valid identifier is. If an identifier is unknown, you may return null which specifies the placeholder is invalid.
	 * @param plugin Plugin name registering the placeholder hook
	 * @param placeholderHook PlaceholderHook class that contains the override method which is called when a value is needed for the specific plugins placeholder
	 * 	 * @param isInternalHook true if hook was registered by PlaceholderAPI internally, false if an external plugin is registering the placeholder hook
	 * this value should only be true if PlaceholderAPI is registering the PlaceholderHook
	 * @return true if the hook was successfully registered, false if there was already a hook registered for the specified plugin
	 */
	public static boolean registerPlaceholderHook(String plugin, PlaceholderHook placeholderHook, boolean isInternalHook) {
		
		if (placeholders == null ) {
			placeholders = new HashMap<String, PlaceholderHook>();
		}
		
		if (plugin == null 
				|| placeholderHook == null 
				|| placeholders.containsKey(plugin)) {
			return false;
		}

		placeholders.put(plugin, placeholderHook);
		
		if (!isInternalHook) {
			if (externalHooks == null) {
				externalHooks = new HashSet<String>();
			}
		
			if (!externalHooks.contains(plugin)) {
				externalHooks.add(plugin);
			}
		}
		
		return true;
	}

	/**
	 * unregister a placeholder hook for a specific plugin
	 * @param plugin Plugin to unregister
	 * @return true if the placeholder hook was successfully unregistered, false if there was no placeholder hook registered for the plugin specified
	 */
	public static boolean unregisterPlaceholderHook(Plugin plugin) {
		
		if (plugin == null) {
			return false;
		}
		
		return unregisterPlaceholderHook(plugin.getName());
	}
	
	/**
	 * unregister a placeholder hook for a specific plugin
	 * @param plugin Plugin name to unregister
	 * @return true if the placeholder hook was successfully unregistered, false if there was no placeholder hook registered for the plugin specified
	 */
	public static boolean unregisterPlaceholderHook(String plugin) {
		
		if (plugin == null) {
			return false;
		}
		
		if (placeholders == null || placeholders.isEmpty()) {
			return false;
		}
		
		if (externalHooks != null && externalHooks.contains(plugin)) {
			externalHooks.remove(plugin);
		}
		
		return placeholders.remove(plugin) != null;
	}
	
	/**
	 * obtain the names of every plugin that has a placeholder hook registered
	 * @return Set of plugin names currently registered
	 */
	public static Set<String> getRegisteredPlaceholderPlugins() {
		
		if (placeholders == null || placeholders.isEmpty()) {
			return new HashSet<String>();
		}
		
		return new HashSet<String>(placeholders.keySet());
	}
	
	/**
	 * obtain the names of every external hook that was not registered by PlaceholderAPI
	 * @return Set of plugin names that PlaceholderAPI did not register placeholders for
	 */
	public static Set<String> getExternalPlaceholderPlugins() {
		
		if (externalHooks == null || externalHooks.isEmpty()) {
			return new HashSet<String>();
		}
		
		return new HashSet<String>(externalHooks);
	}
	
	/**
	 * obtain the map of registered placeholder hook plugins and the corresponding PlaceholderHooks registered
	 * @return
	 */
	public static Map<String, PlaceholderHook> getPlaceholders() {
		return new HashMap<String, PlaceholderHook>(placeholders);
	}
	

	/**
	 * check if a String contains placeholders which do not require a Player object to be passed to the setPlaceholders method
	 * @param text String to check
	 * @return true if text contains server related placeholders which do not require a Player object
	 */
	public static boolean containsServerPlaceholders(String text) {
		
		if (text == null || placeholders == null || placeholders.isEmpty()) {
			return false;
		}
		
		Matcher placeholderMatcher = PLACEHOLDER_PATTERN.matcher(text);
		
		boolean containsServer = false;
		
		while (placeholderMatcher.find()) {
			
			String format = placeholderMatcher.group(1);
			
		    StringBuilder pluginBuilder = new StringBuilder();		    
		    
		    char[] formatArray = format.toCharArray();

		    int i;
		    
		    for (i=0;i<formatArray.length;i++) {
		    	
				if (formatArray[i] == '_') {
					break;
				} else {   
		        	
		        	pluginBuilder.append(formatArray[i]);
		        }
		    }
		    
		    String pl = pluginBuilder.toString();
			
			containsServer = pl.equalsIgnoreCase("server");
			
			if (containsServer) {
				break;
			}
		}

		return containsServer;
	}
	
	/**
	 * check if a String contains any valid PlaceholderAPI placeholders
	 * @param text String to check 
	 * @return true if String passed contains any valid PlaceholderAPI placeholder identifiers, false otherwise
	 */
	public static boolean containsPlaceholders(String text) {

		if (text == null || placeholders == null || placeholders.isEmpty()) {
			return false;
		}
		
		Matcher placeholderMatcher = PLACEHOLDER_PATTERN.matcher(text);
		
		while (placeholderMatcher.find()) {
			
			String format = placeholderMatcher.group(1);
			
		    StringBuilder pluginBuilder = new StringBuilder();		    
		    
		    char[] formatArray = format.toCharArray();

		    int i;
		    
		    for (i=0;i<formatArray.length;i++) {
		    	
				if (formatArray[i] == '_') {
					break;
				} else {   
		        	
		        	pluginBuilder.append(formatArray[i]);
		        }
		    }
		    
		    String pl = pluginBuilder.toString();
		    
		    StringBuilder identifierBuilder = new StringBuilder();
		    
			for (int b = i+1;b<formatArray.length;b++) {
				identifierBuilder.append(formatArray[b]);
			}
			
			String identifier = identifierBuilder.toString();
			
			if (identifier.isEmpty()) {
				identifier = pl;
			}
			
			for (String registered : getRegisteredPlaceholderPlugins()) {
				
				if (pl.equalsIgnoreCase(registered)) {
					
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * set placeholders in the list<String> text provided
	 * placeholders are matched with the pattern {<placeholder>} when set with this method
	 * @param p Player to set the placeholders for
	 * @param text text to set the placeholder values in
	 * @return original list with all valid placeholders set to the correct values if the list contains any valid placeholders
	 */
	public static List<String> setBracketPlaceholders(Player p, List<String> text) {
		if (text == null) {
			return text;
		}
		List<String> temp = new ArrayList<String>();
		for (String line : text) {
			temp.add(setBracketPlaceholders(p, line));
		}
		return temp;
	}
	
	/**
	 * set placeholders in the text specified
	 * placeholders are matched with the pattern {<placeholder>} when set with this method
	 * @param player Player to set the placeholders for
	 * @param text text to set the placeholder values to
	 * @return original text with all valid placeholders set to the correct values if the String contains valid placeholders
	 */
	public static String setBracketPlaceholders(Player player, String text) {

		if (text == null || placeholders == null || placeholders.isEmpty()) {
			return text;
		}
		
		Matcher placeholderMatcher = BRACKET_PLACEHOLDER_PATTERN.matcher(text);
		
		if (player == null) {			
			
			while (placeholderMatcher.find()) {
				
				String format = placeholderMatcher.group(1);
				
			    StringBuilder pluginBuilder = new StringBuilder();		    
			    
			    char[] formatArray = format.toCharArray();

			    int i;
			    
			    for (i=0;i<formatArray.length;i++) {
			    	
					if (formatArray[i] == '_') {
						break;
					} else {   
			        	
			        	pluginBuilder.append(formatArray[i]);
			        }
			    }
			    
			    String pl = pluginBuilder.toString();
			    
			    StringBuilder identifierBuilder = new StringBuilder();
			    
				for (int b = i+1;b<formatArray.length;b++) {
					identifierBuilder.append(formatArray[b]);
				}
				
				String identifier = identifierBuilder.toString();
				
				if (identifier.isEmpty()) {
					identifier = pl;
				}
				
				for (String registered : getRegisteredPlaceholderPlugins()) {
					
					if (pl.equalsIgnoreCase("server")) {
						
						String value = getPlaceholders().get(registered).onPlaceholderRequest(player, identifier);
						
						if (value != null) {
							text = text.replaceAll("\\{"+format+"\\}", Matcher.quoteReplacement(value));
						}
					}
				}
			}
			
			return ChatColor.translateAlternateColorCodes('&', text);
		}
		
		while (placeholderMatcher.find()) {
			
			String format = placeholderMatcher.group(1);
			
		    StringBuilder pluginBuilder = new StringBuilder();		    
		    
		    char[] formatArray = format.toCharArray();

		    int i;
		    
		    for (i=0;i<formatArray.length;i++) {
		    	
				if (formatArray[i] == '_') {
					break;
				} else {   
		        	
		        	pluginBuilder.append(formatArray[i]);
		        }
		    }
		    
		    String pl = pluginBuilder.toString();
		    
		    StringBuilder identifierBuilder = new StringBuilder();
		    
			for (int b = i+1;b<formatArray.length;b++) {
				identifierBuilder.append(formatArray[b]);
			}
			
			String identifier = identifierBuilder.toString();
			
			if (identifier.isEmpty()) {
				identifier = pl;
			}
			
			for (String registered : getRegisteredPlaceholderPlugins()) {
				
				if (pl.equalsIgnoreCase(registered)) {
					
					String value = getPlaceholders().get(registered).onPlaceholderRequest(player, identifier);
					
					if (value != null) {
						text = text.replaceAll("\\{"+format+"\\}", Matcher.quoteReplacement(value));
					}
				}
			}
		}

		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
	/**
	 * set placeholders in the list<String> text provided
	 * placeholders are matched with the pattern %<placeholder>% when set with this method
	 * @param p Player to set the placeholders for
	 * @param text text to set the placeholder values in
	 * @return original list with all valid placeholders set to the correct values if the list contains any valid placeholders
	 */
	public static List<String> setPlaceholders(Player p, List<String> text) {
		if (text == null) {
			return text;
		}
		List<String> temp = new ArrayList<String>();
		for (String line : text) {
			temp.add(setPlaceholders(p, line));
		}
		return temp;
	}
	

	/**
	 * set placeholders in the text specified
	 * placeholders are matched with the pattern %<placeholder>% when set with this method
	 * @param player Player to set the placeholders for
	 * @param text text to set the placeholder values to
	 * @return original text with all valid placeholders set to the correct values if the String contains valid placeholders
	 */
	public static String setPlaceholders(Player player, String text) {

		if (text == null || placeholders == null || placeholders.isEmpty()) {
			return text;
		}
		
		Matcher placeholderMatcher = PLACEHOLDER_PATTERN.matcher(text);
		
		if (player == null) {			
			
			while (placeholderMatcher.find()) {
				
				String format = placeholderMatcher.group(1);
				
			    StringBuilder pluginBuilder = new StringBuilder();		    
			    
			    char[] formatArray = format.toCharArray();

			    int i;
			    
			    for (i=0;i<formatArray.length;i++) {
			    	
					if (formatArray[i] == '_') {
						break;
					} else {   
			        	
			        	pluginBuilder.append(formatArray[i]);
			        }
			    }
			    
			    String pl = pluginBuilder.toString();
			    
			    StringBuilder identifierBuilder = new StringBuilder();
			    
				for (int b = i+1;b<formatArray.length;b++) {
					identifierBuilder.append(formatArray[b]);
				}
				
				String identifier = identifierBuilder.toString();
				
				if (identifier.isEmpty()) {
					identifier = pl;
				}
				
				for (String registered : getRegisteredPlaceholderPlugins()) {
					
					if (pl.equalsIgnoreCase("server")) {
						
						String value = getPlaceholders().get(registered).onPlaceholderRequest(player, identifier);
						
						if (value != null) {
							text = text.replaceAll("%"+format+"%", Matcher.quoteReplacement(value));
						}
					}
				}
			}
			
			return ChatColor.translateAlternateColorCodes('&', text);
		}
		
		while (placeholderMatcher.find()) {
			
			String format = placeholderMatcher.group(1);
			
		    StringBuilder pluginBuilder = new StringBuilder();		    
		    
		    char[] formatArray = format.toCharArray();

		    int i;
		    
		    for (i=0;i<formatArray.length;i++) {
		    	
				if (formatArray[i] == '_') {
					break;
				} else {   
		        	
		        	pluginBuilder.append(formatArray[i]);
		        }
		    }
		    
		    String pl = pluginBuilder.toString();
		    
		    StringBuilder identifierBuilder = new StringBuilder();
		    
			for (int b = i+1;b<formatArray.length;b++) {
				identifierBuilder.append(formatArray[b]);
			}
			
			String identifier = identifierBuilder.toString();
			
			if (identifier.isEmpty()) {
				identifier = pl;
			}
			
			for (String registered : getRegisteredPlaceholderPlugins()) {
				
				if (pl.equalsIgnoreCase(registered)) {
					
					String value = getPlaceholders().get(registered).onPlaceholderRequest(player, identifier);
					
					if (value != null) {
						text = text.replaceAll("%"+format+"%", Matcher.quoteReplacement(value));
					}
				}
			}
		}

		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
	public static Pattern getPlaceholderPattern() {
		return PLACEHOLDER_PATTERN;
	}
	
	public static Pattern getBracketPlaceholderPattern() {
		return BRACKET_PLACEHOLDER_PATTERN;
	}
}
