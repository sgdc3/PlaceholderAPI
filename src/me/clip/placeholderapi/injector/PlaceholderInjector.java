package me.clip.placeholderapi.injector;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.injector.chat.ChatListener;
import me.clip.placeholderapi.injector.chat.ChatPacketListener;
import me.clip.placeholderapi.injector.chat.SpigotChatPacketListener;
import me.clip.placeholderapi.injector.entity.HD_PRE_1_9_ProtocolLib;
import me.clip.placeholderapi.injector.entity.HologramInjector;
import me.clip.placeholderapi.injector.inventory.InventorySetSlotPacketListener;
import me.clip.placeholderapi.injector.inventory.InventoryWindowPacketListener;
import me.clip.placeholderapi.injector.sign.SignChangeListener;
import me.clip.placeholderapi.injector.sign.SignPacketListener;
import me.clip.placeholderapi.injector.tab.TabPacketListener;
import me.clip.placeholderapi.injector.title.TitlePacketListener;
import me.clip.placeholderapi.internal.NMSVersion;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import com.comphenix.protocol.ProtocolLibrary;

public class PlaceholderInjector {
	
	private PlaceholderAPIPlugin plugin;
	
	private boolean isEnabled;
	
	private HologramInjector hdInjector;
	
	private SignPacketListener signInjector;
	
	public PlaceholderInjector(PlaceholderAPIPlugin instance) {
		plugin = instance;
	}
	
	public void disable() {
		
		if (hdInjector != null) {
			hdInjector.stopTask();
			hdInjector.clear();
			hdInjector = null;
		}
		
		if (signInjector != null) {
			signInjector.stopTask();
			signInjector.clear();
			signInjector = null;
		}
		
		if (isEnabled) {
			ProtocolLibrary.getProtocolManager().removePacketListeners(plugin);
			isEnabled = false;
		}
	}
	
	public void setup() {
		
		if (PlaceholderAPIPlugin.getNMSVersion() == NMSVersion.UNKNOWN) {
			return;
		}
		
		FileConfiguration config = plugin.getConfig();
		
		if (config.getBoolean("injector.chat.enabled")) {
			
			isEnabled = true;
			
			plugin.getLogger().info("Intercepting chat packets for placeholders");
			
			new ChatListener(plugin);
			
			if (PlaceholderAPIPlugin.isSpigot()) {
				new SpigotChatPacketListener(plugin);
			} else {
				new ChatPacketListener(plugin);
			}	
		}
		
		if (config.getBoolean("injector.inventory.enabled")) {
			isEnabled = true;
			plugin.getLogger().info("Intercepting inventory packets for placeholders");
			new InventorySetSlotPacketListener(plugin);
			new InventoryWindowPacketListener(plugin);
			//new InventoryItemsPacketListener(this);
		}
		
		if (config.getBoolean("injector.title.enabled")) {
			
			isEnabled = true;
			plugin.getLogger().info("Intercepting title packets for placeholders");
			new TitlePacketListener(plugin);
		}
		
		if (config.getBoolean("injector.tab.enabled")) {
			
			isEnabled = true;
			plugin.getLogger().info("Intercepting tab header and footer packets for placeholders");
			new TabPacketListener(plugin);
		}
		
		if (config.getBoolean("injector.signs.enabled")) {
			
			isEnabled = true;
			plugin.getLogger().info("Intercepting sign packets for placeholders");
			signInjector = new SignPacketListener(plugin, config.getInt("injector.signs.update_interval", 30));
			new SignChangeListener(plugin);
		}
		
		if (config.getBoolean("injector.holographicdisplays.enabled")) {
			
			if (Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
				if (PlaceholderAPIPlugin.getNMSVersion().is1_9()) {
					plugin.getLogger().warning("Injector for HolographicDisplays does not support 1.9 yet!");
				} else if (PlaceholderAPIPlugin.getNMSVersion().is1_8()) {
					plugin.getLogger().info("Placeholder injector for HolographicDisplays has been enabled");
					isEnabled = true;
					hdInjector = new HD_PRE_1_9_ProtocolLib(plugin, config.getInt("injector.holographicdisplays.update_interval", 60), 2);
				} else {
					plugin.getLogger().info("Placeholder injector for HolographicDisplays has been enabled");
					isEnabled = true;
					hdInjector = new HD_PRE_1_9_ProtocolLib(plugin, config.getInt("injector.holographicdisplays.update_interval", 60), 10);
				}				
			}
		}
	}
}
