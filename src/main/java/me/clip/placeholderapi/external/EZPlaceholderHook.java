package me.clip.placeholderapi.external;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;

public abstract class EZPlaceholderHook extends PlaceholderHook {

    private String identifier;

    private String plugin;

    public EZPlaceholderHook(Plugin plugin, String identifier) {
        Validate.notNull(plugin, "Plugin can not be null!");
        Validate.isTrue(identifier != null && !identifier.isEmpty(), "Placeholder name can not be null or empty!");
        this.identifier = identifier;
        this.plugin = plugin.getName();
    }

    public boolean isHooked() {
        return PlaceholderAPI.getRegisteredPlaceholderPlugins().contains(identifier);
    }

    public boolean hook() {
        return PlaceholderAPI.registerPlaceholderHook(identifier, this);
    }

    public String getPlaceholderName() {
        return identifier;
    }

    public String getPluginName() {
        return plugin;
    }
}
