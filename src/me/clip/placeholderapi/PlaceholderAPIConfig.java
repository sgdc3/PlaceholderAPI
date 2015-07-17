package me.clip.placeholderapi;

import org.bukkit.configuration.file.FileConfiguration;

public class PlaceholderAPIConfig {

	private PlaceholderAPIPlugin plugin;
	
	public PlaceholderAPIConfig(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	public void loadDefConfig() {
		
		FileConfiguration c = plugin.getConfig();
		
		c.options().header("PlaceholderAPI version "+plugin.getDescription().getVersion()+""
				+ "\nCreated by extended_clip"
				+ "\n"
				+ "\n");
		c.addDefault("boolean.true", "yes");
		c.addDefault("boolean.false", "no");
		c.addDefault("date_format", "MM/dd/yy HH:mm:ss");
		c.addDefault("hooks.minecraft_statistics", false);
		c.addDefault("hooks.acidisland", false);
		c.addDefault("hooks.askyblock", false);
		c.addDefault("hooks.autorank", false);
		c.addDefault("hooks.autosell", false);
		c.addDefault("hooks.battlelevels", false);
		c.addDefault("hooks.chatreaction", false);
		c.addDefault("hooks.checknamehistory", false);
		c.addDefault("hooks.deluxetags", false);
		c.addDefault("hooks.essentials", false);
		c.addDefault("hooks.ezblocks", false);
		c.addDefault("hooks.ezprestige", false);
		c.addDefault("hooks.ezrankslite", false);
		c.addDefault("hooks.factions_mcore", false);
		c.addDefault("hooks.factions_uuid", false);
		c.addDefault("hooks.galistener", false);
		c.addDefault("hooks.gangsplus", false);
		c.addDefault("hooks.heroes", false);
		c.addDefault("hooks.islandworld", false);
		c.addDefault("hooks.jobs", false);
		c.addDefault("hooks.killstats", false);
		c.addDefault("hooks.marriagemaster", false);
		c.addDefault("hooks.mcinfected", false);
		c.addDefault("hooks.mcinfected-ranks", false);
		c.addDefault("hooks.mcmmo", false);
		c.addDefault("hooks.minecrates", false);
		c.addDefault("hooks.nicky", false);
		c.addDefault("hooks.ontime", false);
		c.addDefault("hooks.playerpoints", false);
		c.addDefault("hooks.plotme", false);
		c.addDefault("hooks.plotsquared", false);
		c.addDefault("hooks.prisongangs", false);
		c.addDefault("hooks.pvpstats", false);
		c.addDefault("hooks.quicksell", false);
		c.addDefault("hooks.royalcommands", false);
		c.addDefault("hooks.simpleclans", false);
		c.addDefault("hooks.simplecoinsapi", false);
		c.addDefault("hooks.simpleprefix", false);
		c.addDefault("hooks.simple_suffix", false);
		c.addDefault("hooks.skywarsreloaded", false);
		c.addDefault("hooks.sqlperms", false);
		c.addDefault("hooks.sqltokens", false);
		c.addDefault("hooks.teams", false);
		c.addDefault("hooks.tokenenchant", false);
		c.addDefault("hooks.towny", false);
		c.addDefault("hooks.uskyblock", false);
		c.addDefault("hooks.ultimatevotes", false);
		c.addDefault("hooks.vault_perms", true);
		c.addDefault("hooks.vault_eco", true);
		c.addDefault("hooks.voteparty", true);
		c.addDefault("hooks.wickedskywars", false);
		c.options().copyDefaults(true);
		plugin.saveConfig();
		plugin.reloadConfig();
	}
	
	public String booleanTrue() {
		return plugin.getConfig().getString("boolean.true");
	}
	
	public String booleanFalse() {
		return plugin.getConfig().getString("boolean.false");
	}
	
	public String dateFormat() {
		return plugin.getConfig().getString("date_format");
	}
	
}
