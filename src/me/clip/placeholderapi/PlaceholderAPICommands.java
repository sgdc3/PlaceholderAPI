package me.clip.placeholderapi;

import java.util.Set;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaceholderAPICommands implements CommandExecutor {

	private PlaceholderAPIPlugin plugin;
	
	public PlaceholderAPICommands(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	private void sms(CommandSender s, String msg) {
		s.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String label, String[] args) {


		if (args.length == 0) {
			
			sms(s, "&8&m-----------------------------------------------------");
			sms(s, "&cClips &fPlaceholder&7API &f&o"+plugin.getDescription().getVersion());
			sms(s, "&7Created by &fextended_clip");
			sms(s, "&8&m-----------------------------------------------------");
			return true;
			
		} else {

			if (s instanceof Player) {

				Player p = (Player) s;

				if (!p.hasPermission("placeholderapi.admin")) {
					sms(s, "&cYou don't have permission to do that!");
					return true;
				}
			}
			
			if (args[0].equalsIgnoreCase("help")) {
				sms(s, "&8&m-----------------------------------------------------");
				sms(s, "&cClips &fPlaceholder&7API &eHelp");
				sms(s, "&c/placeholderapi");
				sms(s, "&fView plugin info/version info");
				sms(s, "&c/placeholderapi list");
				sms(s, "&fList all placeholder hook plugins that are currently active");
				sms(s, "&c/placeholderapi reload");
				sms(s, "&fReload the config settings");
				sms(s, "&8&m-----------------------------------------------------");
				
			} else if (args[0].equalsIgnoreCase("reload")) {

				plugin.reloadConf();
				sms(s, "&8&m-----------------------------------------------------");
				sms(s, "&cClips &fPlaceholder&7API &bconfiguration reloaded!");
				sms(s, "&7Keep in mind this does not update placeholder hook settings!");
				sms(s, "&7You must restart for placeholder hook changes to take effect!");
				sms(s, "&8&m-----------------------------------------------------");

			} else if (args[0].equalsIgnoreCase("list")) {
				sms(s, "&8&m-----------------------------------------------------");
				
				Set<String> registered = PlaceholderAPI.getRegisteredPlaceholderPlugins();
				
				if (registered == null || registered.isEmpty()) {
					sms(s, "&7There are no placeholder hooks currently registered!");
					sms(s, "&8&m-----------------------------------------------------");
					return true;
				}
				sms(s, registered.size()+" &7Placeholder hooks registered:");
				sms(s, registered.toString());
				sms(s, "&8&m-----------------------------------------------------");
			} else {
				sms(s, "&8&m-----------------------------------------------------");
				sms(s, "&cIncorrect usage! &7/placeholderhook help");
				sms(s, "&8&m-----------------------------------------------------");
			}
		}
		
		return true;
	}

}
