package me.clip.placeholderapi;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
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
			sms(s, "&cClips &fPlaceholder&7API &f&o"+plugin.getDescription().getVersion());
			sms(s, "&7Created by &fextended_clip");
			return true;
			
		} else {

			if (args[0].equalsIgnoreCase("help")) {
				sms(s, "&cClips &fPlaceholder&7API &eHelp &c(&f" + plugin.getDescription().getVersion() + "&c)");
				sms(s, "&c/papi");
				sms(s, "&fView plugin info/version info");
				sms(s, "&c/papi list");
				sms(s, "&fList all placeholder hook plugins that are currently active");
				sms(s, "&c/papi parse <...args>");
				sms(s, "&fParse a String with placeholders");
				sms(s, "&c/papi reload");
				sms(s, "&fReload the config settings");
				
			} else if (args.length > 1 && args[0].equalsIgnoreCase("parse")) {

				if (!(s instanceof Player)) {
					sms(s, "&cThis command can only be used in game!");
					return true;
				} else {
					if (!s.hasPermission("placeholderapi.parse")) {
						sms(s, "&cYou don't have permission to do that!");
						return true;
					}
				}
				
				Player p = (Player) s;
				
				String parse = StringUtils.join(args, " ", 1, args.length);
				
				sms(s, "&r" + PlaceholderAPI.setPlaceholders(p, parse));
				
				return true;
			} else if (args[0].equalsIgnoreCase("reload")) {

				if (s instanceof Player) {
					if (!s.hasPermission("placeholderapi.reload")) {
						sms(s, "&cYou don't have permission to do that!");
						return true;
					}
				}
				
				sms(s, "&cClips &fPlaceholder&7API &bconfiguration reloaded!");
				plugin.reloadConf(s);
				
				//new CheckTask(plugin).runTaskLaterAsynchronously(plugin, 100L);

			} else if (args[0].equalsIgnoreCase("list")) {
				
				if (s instanceof Player) {
					if (!s.hasPermission("placeholderapi.list")) {
						sms(s, "&cYou don't have permission to do that!");
						return true;
					}
				}
				
				Set<String> registered = PlaceholderAPI.getRegisteredPlaceholderPlugins();
				
				if (registered == null || registered.isEmpty()) {
					sms(s, "&7There are no placeholder hooks currently registered!");
					return true;
				}
				sms(s, registered.size()+" &7Placeholder hooks registered:");
				sms(s, registered.toString());
			} else {
				sms(s, "&cIncorrect usage! &7/papi help");
			}
		}
		
		return true;
	}

}
