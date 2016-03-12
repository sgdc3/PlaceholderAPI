package me.clip.placeholderapi.expansion;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.apache.commons.lang.Validate;

/**
 * Classes which extend this class are recognized as placeholder expansions
 * when they are located inside of the /plugins/placeholderapi/expansions/ folder.
 * @author Ryan McCarthy
 *
 */
public abstract class PlaceholderExpansion extends PlaceholderHook {

	/**
	 * Check if a placeholder has already been registered with this identifier
	 * @return
	 */
	public boolean isRegistered() {
		Validate.notNull(getIdentifier() != null, "Placeholder identifier can not be null!");
		return PlaceholderAPI.getRegisteredPlaceholderPlugins().contains(getIdentifier());
	}
	
	/**
	 * Attempt to register this PlaceholderExpansion with PlaceholderAPI
	 * @return true if this class and identifier have been successfully registered with PlaceholderAPI
	 */
	public boolean register() {
		Validate.notNull(getIdentifier() != null, "Placeholder identifier can not be null!");
		return PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
	}
	
	/**
	 * Quick getter for the {@link PlaceholderAPIPlugin} instance
	 * @return {@link PlaceholderAPIPlugin} instance
	 */
	public PlaceholderAPIPlugin getPlaceholderAPI() {
		return PlaceholderAPIPlugin.getInstance();
	}
	
	/**
	 * If any requirements are required to be checked before this hook can register, add them here
	 * @return true if this hook meets all the requirements to register
	 */
	public abstract boolean canRegister();
	
	/**
	 * Get the identifier that this placeholder expansion uses to be passed placeholder requests
	 * @return placeholder identifier that is associated with this class
	 */
	public abstract String getIdentifier();
	
	/**
	 * Get the plugin that this expansion hooks into. 
	 * This will ensure the expansion is added to a cache if canRegister() returns false
	 * get. 
	 * The expansion will be removed from the cache
	 * once a plugin loads with the name that is here and the expansion is registered
	 * @return placeholder identifier that is associated with this class
	 */
	public abstract String getPlugin();
	
	/**
	 * Get the author of this PlaceholderExpansion
	 * @return name of the author for this expansion
	 */
	public abstract String getAuthor();
	
	/**
	 * Get the version of this PlaceholderExpansion
	 * @return current version of this expansion
	 */
	public abstract String getVersion();
}