package me.clip.placeholderapi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import me.clip.placeholderapi.hooks.ASkyblockHook;
import me.clip.placeholderapi.hooks.AcidIslandHook;
import me.clip.placeholderapi.hooks.AutoRankHook;
import me.clip.placeholderapi.hooks.AutoSellHook;
import me.clip.placeholderapi.hooks.ChatReactionHook;
import me.clip.placeholderapi.hooks.CheckNameHistoryHook;
import me.clip.placeholderapi.hooks.EZBlocksHook;
import me.clip.placeholderapi.hooks.EZPrestigeHook;
import me.clip.placeholderapi.hooks.EZRanksLiteHook;
import me.clip.placeholderapi.hooks.EssentialsHook;
import me.clip.placeholderapi.hooks.FactionsHook;
import me.clip.placeholderapi.hooks.FactionsUUIDHook;
import me.clip.placeholderapi.hooks.GAListenerHook;
import me.clip.placeholderapi.hooks.GangsPlusHook;
import me.clip.placeholderapi.hooks.IslandWorldHook;
import me.clip.placeholderapi.hooks.JobsHook;
import me.clip.placeholderapi.hooks.KillStatsHook;
import me.clip.placeholderapi.hooks.LWCHook;
import me.clip.placeholderapi.hooks.MarriageMasterHook;
import me.clip.placeholderapi.hooks.McMMOHook;
import me.clip.placeholderapi.hooks.NickyHook;
import me.clip.placeholderapi.hooks.PlayerPlaceholders;
import me.clip.placeholderapi.hooks.PlayerPointsHook;
import me.clip.placeholderapi.hooks.PlotMeHook;
import me.clip.placeholderapi.hooks.PlotSquaredHook;
import me.clip.placeholderapi.hooks.PrisonGangsHook;
import me.clip.placeholderapi.hooks.PvPStatsHook;
import me.clip.placeholderapi.hooks.QuickSellHook;
import me.clip.placeholderapi.hooks.RoyalCommandsHook;
import me.clip.placeholderapi.hooks.SQLPermsHook;
import me.clip.placeholderapi.hooks.SQLTokensHook;
import me.clip.placeholderapi.hooks.SimpleClansHook;
import me.clip.placeholderapi.hooks.SimpleCoinsAPIHook;
import me.clip.placeholderapi.hooks.SimpleSuffixHook;
import me.clip.placeholderapi.hooks.SkyWarsReloadedHook;
import me.clip.placeholderapi.hooks.Statistic_1_7_10_Placeholders;
import me.clip.placeholderapi.hooks.Statistic_1_8_1_Placeholders;
import me.clip.placeholderapi.hooks.Statistic_1_8_3_Placeholders;
import me.clip.placeholderapi.hooks.TeamsHook;
import me.clip.placeholderapi.hooks.TokenEnchantHook;
import me.clip.placeholderapi.hooks.TownyHook;
import me.clip.placeholderapi.hooks.USkyblockHook;
import me.clip.placeholderapi.hooks.UltimateVotesHook;
import me.clip.placeholderapi.hooks.VaultHook;
import me.clip.placeholderapi.hooks.WickedSkywarsHook;
import me.clip.placeholderapi.metricslite.MetricsLite;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * PlaceholderAPI main class
 * @author Ryan McCarthy
 *
 */
public class PlaceholderAPIPlugin extends JavaPlugin {
	
	private PlaceholderAPI api;
	
	private PlaceholderAPIConfig config;
	
	public Logger log;
	
	private static SimpleDateFormat dateFormat;
	
	private static String booleanTrue;
	
	private static String booleanFalse;
	
	@Override
	public void onEnable() {
		
		log = getLogger();
		
		config = new PlaceholderAPIConfig(this);
		
		config.loadDefConfig();
		
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

		api = new PlaceholderAPI(this);
		
		getCommand("placeholderapi").setExecutor(new PlaceholderAPICommands(this));
		
		initializeHooks();
		
		if (!startMetricsLite()) {
			log.warning("Could not start MetricsLite");
		}
		
		log.info(PlaceholderAPI.getRegisteredPlaceholderPlugins().size()+" placeholder hooks successfully registered!");
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
	
	protected void reloadConf() {
		reloadConfig();
		saveConfig();
		
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

	@Override
	public void onDisable() {
		
		api.unregisterAll();
	}
	
	public void initializeHooks() {
		
		new PlayerPlaceholders(this).hook();
		
		String version = Bukkit.getBukkitVersion();
		
		if (getConfig().getBoolean("hooks.minecraft_statistics")) {
			if (version.startsWith("1.8.3")) {
				new Statistic_1_8_3_Placeholders(this).hook();
			} else if (version.startsWith("1.8")) {
				new Statistic_1_8_1_Placeholders(this).hook();
			}  else if (version.startsWith("1.7")) {
				new Statistic_1_7_10_Placeholders(this).hook();
			} else {
				getLogger().warning("Statistic placeholders are not available for your server version!");
			}
		}
		
		if (getConfig().getBoolean("hooks.acidisland")) {
			new AcidIslandHook(this).hook();
		} else if (getConfig().getBoolean("hooks.askyblock")) {
			new ASkyblockHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.autorank")) {
			new AutoRankHook(this).hook();
		}
	
		if (getConfig().getBoolean("hooks.autosell")) {
			new AutoSellHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.chatreaction")) {
			new ChatReactionHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.checknamehistory")) {
			new CheckNameHistoryHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.essentials")) {
			new EssentialsHook(this).hook();
		}

		if (getConfig().getBoolean("hooks.ezblocks")) {
			new EZBlocksHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.ezprestige")) {
			new EZPrestigeHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.ezrankslite")) {
			new EZRanksLiteHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.factions_mcore")) {
			
			new FactionsHook(this).hook();
			
		} else if (getConfig().getBoolean("hooks.factions_uuid")) {
			
			new FactionsUUIDHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.galistener")) {
			new GAListenerHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.gangsplus")) {
			new GangsPlusHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.islandworld")) {
			new IslandWorldHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.jobs")) {
			new JobsHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.killstats")) {
			new KillStatsHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.lwc")) {
			new LWCHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.marriagemaster")) {
			new MarriageMasterHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.mcmmo")) {
			new McMMOHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.nicky")) {
			new NickyHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.playerpoints")) {
			new PlayerPointsHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.plotme")) {
			new PlotMeHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.plotsquared")) {
			new PlotSquaredHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.prisongangs")) {
			new PrisonGangsHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.pvpstats")) {
			new PvPStatsHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.quicksell")) {
			new QuickSellHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.royalcommands")) {
			new RoyalCommandsHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.simpleclans")) {
			new SimpleClansHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.simplecoinsapi")) {
			new SimpleCoinsAPIHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.simple_suffix")) {
			new SimpleSuffixHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.skywarsreloaded")) {
			new SkyWarsReloadedHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.sqlperms")) {
			new SQLPermsHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.sqltokens")) {
			new SQLTokensHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.teams")) {
			new TeamsHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.tokenenchant")) {
			new TokenEnchantHook(this).hook();
		}	

		if (getConfig().getBoolean("hooks.towny")) {
			new TownyHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.uskyblock")) {
			new USkyblockHook(this).hook();
		}
		
		if (getConfig().getBoolean("hooks.ultimatevotes")) {
			new UltimateVotesHook(this).hook();
		}
		
		new VaultHook(this).hook();

		if (getConfig().getBoolean("hooks.wickedskywars")) {
			new WickedSkywarsHook(this).hook();
		}
	}
	
	public static SimpleDateFormat getDateFormat() {
		return dateFormat;
	}
	
	public static String booleanTrue() {
		return booleanTrue;
	}
	
	public static String booleanFalse() {
		return booleanFalse;
	}
	
}
