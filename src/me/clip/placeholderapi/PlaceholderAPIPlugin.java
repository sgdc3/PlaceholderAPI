package me.clip.placeholderapi;

import java.io.IOException;
import java.text.SimpleDateFormat;

import me.clip.placeholderapi.configuration.PlaceholderAPIConfig;
import me.clip.placeholderapi.injector.PlaceholderInjector;
import me.clip.placeholderapi.internal.InternalHook;
import me.clip.placeholderapi.internal.NMSVersion;
import me.clip.placeholderapi.metricslite.MetricsLite;
import me.clip.placeholderapi.updatechecker.UpdateChecker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * PlaceholderAPI main class
 * @author Ryan McCarthy
 *
 */
public class PlaceholderAPIPlugin extends JavaPlugin {
	
	private PlaceholderAPIConfig config;
	
	private PlaceholderInjector injector;
	
	private static SimpleDateFormat dateFormat;
	
	private static String booleanTrue;
	
	private static String booleanFalse;
	
	private static PlaceholderAPIPlugin instance;
	
	private static NMSVersion nmsVersion;
	
	private static boolean isSpigot;
	
	@Override
	public void onEnable() {

		instance = this;
		
		if (checkForSpigot()) {
			getLogger().info("This server is running Spigot :)");
		}
		
		setupNMS();
		
		config = new PlaceholderAPIConfig(this);
		
		config.loadDefConfig();
		
		setupOptions();
		
		getCommand("placeholderapi").setExecutor(new PlaceholderAPICommands(this));
		
		getLogger().info("Internal placeholder registration initializing...");
		
		new PlaceholderListener(this);
		
		InternalHook.registerHooks();
		
		if (config.checkUpdates()) {
			new UpdateChecker(this);
		}
		
		if (!startMetricsLite()) {
			getLogger().warning("Could not start MetricsLite");
		}
		
		if (config.injectorEnabled()) {
			if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
				injector = new PlaceholderInjector(this);
				injector.setup();
			} else {
				getLogger().warning("Could not hook into ProtocolLib!");
				getLogger().info("Placeholder injector requires ProtocolLib in order to be used!");
			}
		}
	}
	
	@Override
	public void onDisable() {
		if (injector != null) {
			injector.disable();
		}
		PlaceholderAPI.unregisterAll();
		Bukkit.getScheduler().cancelTasks(this);
		instance = null;
	}
	
	protected void reloadConf(CommandSender s) {
		reloadConfig();
		saveConfig();
		setupOptions();
		PlaceholderAPI.resetInternalPlaceholderHooks();
		InternalHook.registerHooks();
		s.sendMessage(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.getRegisteredPlaceholderPlugins().size()+" &aplaceholder hooks successfully registered!"));
	}
	
	private void setupOptions() {
		booleanTrue = config.booleanTrue();
		if (booleanTrue == null) {
			booleanTrue = "true";
		}
		booleanFalse = config.booleanFalse();
		if (booleanFalse == null) {
			booleanFalse = "false";
		}
		try {
			dateFormat = new SimpleDateFormat(config.dateFormat());
		} catch (Exception e) {
			dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		}
	}
	
	private boolean checkForSpigot() {
		try {
			Class.forName("org.spigotmc.SpigotConfig");
			isSpigot = true;
		} catch (Exception exception) {
			isSpigot = false;
		}
		return isSpigot;
	}
	
	private boolean startMetricsLite() {
		try {
			MetricsLite ml = new MetricsLite(this);
			ml.start();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	private void setupNMS() {
		String v = "unknown";
		try {
			v = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException ex) {
        }
		nmsVersion = NMSVersion.getVersion(v);
		if (nmsVersion == NMSVersion.UNKNOWN) {
			getLogger().info("Your " + (isSpigot ? "Spigot" : "Bukkit")+ " version is not supported by PlaceholderAPI.");
			getLogger().info("Placeholder hooks which use NMS are not compatible with version: +" + v);
		} else {
			getLogger().info("Detected NMS version: " + nmsVersion.getVersion());	
		}
	}
	
	/**
	 * Gets the static instance of the main class for PlaceholderAPI.
	 * This class is not the actual API class, this is the main class that extends JavaPlugin.
	 * For most API methods, use static methods available from the class: {@link PlaceholderAPI}
	 * @return
	 */
	public static PlaceholderAPIPlugin getInstance() {
		return instance;
	}
	
	/**
	 * Get the configurable {@linkplain SimpleDateFormat} object that is used to parse time for generic time based placeholders
	 * @return
	 */
	public static SimpleDateFormat getDateFormat() {
		return dateFormat != null ? dateFormat : new SimpleDateFormat("MM/dd/yy HH:mm:ss");
	}
	
	/**
	 * Get the configurable {@linkplain String} value that should be returned when a boolean is true
	 * @return
	 */
	public static String booleanTrue() {
		return booleanTrue != null ? booleanTrue : "true";
	}
	
	/**
	 * Get the configurable {@linkplain String} value that should be returned when a boolean is false
	 * @return
	 */
	public static String booleanFalse() {
		return booleanFalse != null ? booleanFalse : "false";
	}
	
	/**
	 * Get the net.minecraft.server version for this server
	 * @return {@link NMSVersion} that this server is running, {@link NMSVersion.UNKNOWN}
	 * If PlaceholderAPI does not recognize the net.minecraft.server version this server
	 * is running.
	 * 
	 */
	public static NMSVersion getNMSVersion() {
		return nmsVersion != null ? nmsVersion : NMSVersion.UNKNOWN;
	}
	
	/**
	 * Obtain the configuration class for PlaceholderAPI.
	 * This class contains special methods to load the default config, obtain certain config values, etc.
	 * This method should not be used by external plugins.
	 * @return PlaceholderAPIConfig instance
	 */
	public PlaceholderAPIConfig getPlaceholderAPIConfig() {
		return config;
	}

	/**
	 * Check if the server is running Spigot
	 * @return true if the server is running Spigot
	 */
	public static boolean isSpigot() {
		return isSpigot;
	}
}
