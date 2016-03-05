package me.clip.placeholderapi.internal;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.hooks.ASkyblockHook;
import me.clip.placeholderapi.hooks.AcidIslandHook;
import me.clip.placeholderapi.hooks.AutoRankHook;
import me.clip.placeholderapi.hooks.AutoSellHook;
import me.clip.placeholderapi.hooks.BungeeCordHook;
import me.clip.placeholderapi.hooks.ChatColorHook;
import me.clip.placeholderapi.hooks.ChatReactionHook;
import me.clip.placeholderapi.hooks.CheckNameHistoryHook;
import me.clip.placeholderapi.hooks.DeluxeTagsHook;
import me.clip.placeholderapi.hooks.EZBlocksHook;
import me.clip.placeholderapi.hooks.EZPrestigeHook;
import me.clip.placeholderapi.hooks.EZRanksProHook;
import me.clip.placeholderapi.hooks.EnjinMinecraftPluginHook;
import me.clip.placeholderapi.hooks.EssentialsHook;
import me.clip.placeholderapi.hooks.FactionsHook;
import me.clip.placeholderapi.hooks.FactionsUUIDHook;
import me.clip.placeholderapi.hooks.GAListenerHook;
import me.clip.placeholderapi.hooks.GangsPlusHook;
import me.clip.placeholderapi.hooks.HeroesHook;
import me.clip.placeholderapi.hooks.IslandWorldHook;
import me.clip.placeholderapi.hooks.JobsHook;
import me.clip.placeholderapi.hooks.KillStatsHook;
import me.clip.placeholderapi.hooks.LWCHook;
import me.clip.placeholderapi.hooks.LeaderheadsHook;
import me.clip.placeholderapi.hooks.MVdWPlaceholderHook;
import me.clip.placeholderapi.hooks.MarriageHook;
import me.clip.placeholderapi.hooks.MarriageMasterHook;
import me.clip.placeholderapi.hooks.McInfectedHook;
import me.clip.placeholderapi.hooks.McInfectedRanksHook;
import me.clip.placeholderapi.hooks.McMMOHook;
import me.clip.placeholderapi.hooks.MineCratesHook;
import me.clip.placeholderapi.hooks.MySQLTokensHook;
import me.clip.placeholderapi.hooks.NicknamerHook;
import me.clip.placeholderapi.hooks.NickyHook;
import me.clip.placeholderapi.hooks.OnTimeHook;
import me.clip.placeholderapi.hooks.PlayerPointsHook;
import me.clip.placeholderapi.hooks.PlotSquaredHook;
import me.clip.placeholderapi.hooks.PointsAPIHook;
import me.clip.placeholderapi.hooks.PrisonMinesHook;
import me.clip.placeholderapi.hooks.PvPStatsHook;
import me.clip.placeholderapi.hooks.QuickSellHook;
import me.clip.placeholderapi.hooks.RedisBungeeHook;
import me.clip.placeholderapi.hooks.RoyalCommandsHook;
import me.clip.placeholderapi.hooks.SQLPermsHook;
import me.clip.placeholderapi.hooks.SQLTokensHook;
import me.clip.placeholderapi.hooks.SellAllHook;
import me.clip.placeholderapi.hooks.SimpleClansHook;
import me.clip.placeholderapi.hooks.SimpleCoinsAPIHook;
import me.clip.placeholderapi.hooks.SimplePrefixHook;
import me.clip.placeholderapi.hooks.SimpleSuffixHook;
import me.clip.placeholderapi.hooks.SkillAPIHook;
import me.clip.placeholderapi.hooks.SkyWarsReloadedHook;
import me.clip.placeholderapi.hooks.StTitlesHook;
import me.clip.placeholderapi.hooks.SuperbVoteHook;
import me.clip.placeholderapi.hooks.TokenEnchantHook;
import me.clip.placeholderapi.hooks.TokenManagerHook;
import me.clip.placeholderapi.hooks.TownyHook;
import me.clip.placeholderapi.hooks.USkyblockHook;
import me.clip.placeholderapi.hooks.UltimateVotesHook;
import me.clip.placeholderapi.hooks.VaultEcoHook;
import me.clip.placeholderapi.hooks.VaultPermsHook;
import me.clip.placeholderapi.hooks.VotePartyHook;
import me.clip.placeholderapi.hooks.VoteRouletteHook;
import me.clip.placeholderapi.hooks.WickedSkywarsHook;
import me.clip.placeholderapi.placeholders.BetterPingerPlaceholders;
import me.clip.placeholderapi.placeholders.DatePlaceholders;
import me.clip.placeholderapi.placeholders.GlobalSoundPlaceholders;
import me.clip.placeholderapi.placeholders.InventoryPlaceholders;
import me.clip.placeholderapi.placeholders.JavascriptPlaceholders;
import me.clip.placeholderapi.placeholders.NMSPlayer_1_7_R4_Placeholders;
import me.clip.placeholderapi.placeholders.NMSPlayer_1_8_R1_Placeholders;
import me.clip.placeholderapi.placeholders.NMSPlayer_1_8_R2_Placeholders;
import me.clip.placeholderapi.placeholders.NMSPlayer_1_8_R3_Placeholders;
import me.clip.placeholderapi.placeholders.NMSPlayer_1_9_R1_Placeholders;
import me.clip.placeholderapi.placeholders.PlayerPlaceholders;
import me.clip.placeholderapi.placeholders.PlayerSoundPlaceholders;
import me.clip.placeholderapi.placeholders.MemoryPlaceholders;
import me.clip.placeholderapi.placeholders.ServerPlaceholders;
import me.clip.placeholderapi.placeholders.Statistic_1_7_R4_Placeholders;
import me.clip.placeholderapi.placeholders.Statistic_1_8_R1_Placeholders;
import me.clip.placeholderapi.placeholders.Statistic_1_8_R2_Placeholders;
import me.clip.placeholderapi.placeholders.Statistic_1_8_R3_Placeholders;
import me.clip.placeholderapi.placeholders.Statistic_1_9_R1_Placeholders;

public enum InternalHook {

	//no dependency
	BUNGEECORD("bungeecord", null, BungeeCordHook.class, null),
	REDISBUNGEE("redisbungee", null, RedisBungeeHook.class, null),
	INVENTORY_CHECK("invcheck", null, InventoryPlaceholders.class, null),
	JAVASCRIPT("javascript", null, JavascriptPlaceholders.class, null),
	PLAYER("player", null, PlayerPlaceholders.class, null),
	NMSPLAYER_1_7_R4("nmsplayer", null, NMSPlayer_1_7_R4_Placeholders.class, null),
	NMSPLAYER_1_8_R1("nmsplayer", null, NMSPlayer_1_8_R1_Placeholders.class, null),
	NMSPLAYER_1_8_R2("nmsplayer", null, NMSPlayer_1_8_R2_Placeholders.class, null),
	NMSPLAYER_1_8_R3("nmsplayer", null, NMSPlayer_1_8_R3_Placeholders.class, null),
	NMSPLAYER_1_9_R1("nmsplayer", null, NMSPlayer_1_9_R1_Placeholders.class, null),
	SERVER("server", null, ServerPlaceholders.class, null),
	STATISTIC_1_7_R4("statistic", null, Statistic_1_7_R4_Placeholders.class, null),
	STATISTIC_1_8_R1("statistic", null, Statistic_1_8_R1_Placeholders.class, null),
	STATISTIC_1_8_R2("statistic", null, Statistic_1_8_R2_Placeholders.class, null),
	STATISTIC_1_8_R3("statistic", null, Statistic_1_8_R3_Placeholders.class, null),
	STATISTIC_1_9_R1("statistic", null, Statistic_1_9_R1_Placeholders.class, null),
	PINGER("pinger", null, BetterPingerPlaceholders.class, null),
	DATE("date", null, DatePlaceholders.class, null),
	GLOBALSOUND("globalsound", null, GlobalSoundPlaceholders.class, null),
	PLAYERSOUND("playersound", null, PlayerSoundPlaceholders.class, null),
	MEMORY("memory", null, MemoryPlaceholders.class, null),
	//require dependency
	ACID_ISLAND("acidisland", "AcidIsland", AcidIslandHook.class, "https://www.spigotmc.org/resources/581/"),
	ASKYBLOCK("askyblock", "ASkyBlock", ASkyblockHook.class, "https://www.spigotmc.org/resources/1220/"),
	AUTORANK("autorank", "Autorank", AutoRankHook.class, "https://www.spigotmc.org/resources/3239/"),
	AUTOSELL("autosell", "AutoSell", AutoSellHook.class, "https://www.spigotmc.org/resources/2157/"),
	CHATCOLOR("chatcolor", "ChatColor", ChatColorHook.class, "https://www.spigotmc.org/resources/1546/"),
	CHATREACTION("chatreaction", "ChatReaction", ChatReactionHook.class, "https://www.spigotmc.org/resources/3748/"),
	CHECKNAMEHISTORY("checknamehistory", "CheckNameHistory", CheckNameHistoryHook.class, "https://www.spigotmc.org/resources/3768/"),
	DELUXETAGS("deluxetags", "DeluxeTags", DeluxeTagsHook.class, "https://www.spigotmc.org/resources/4390/"),
	ENJIN("enjin", "EnjinMinecraftPlugin", EnjinMinecraftPluginHook.class, "https://www.spigotmc.org/resources/17209/"),
	ESSENTIALS("essentials", "Essentials", EssentialsHook.class, "https://hub.spigotmc.org/jenkins/job/Spigot-Essentials/"), 
	EZBLOCKS("ezblocks", "EZBlocks", EZBlocksHook.class, "https://www.spigotmc.org/resources/1499/"), 
	EZPRESTIGE("ezprestige", "EZPrestige", EZPrestigeHook.class, "https://www.spigotmc.org/resources/1794/"),  
	EZRANKSPRO("ezrankspro", "EZRanksPro", EZRanksProHook.class, "https://www.spigotmc.org/resources/10731/"), 
	FACTIONS_MCORE("factions", "Factions", FactionsHook.class, "https://www.spigotmc.org/resources/1900/"), 
	FACTIONS_UUID("factionsuuid", "Factions", FactionsUUIDHook.class, "https://www.spigotmc.org/resources/1035/"), 
	GALISTENER("galistener", "GAListener", GAListenerHook.class, "https://www.spigotmc.org/resources/4501/"), 
	GANGSPLUS("gangsplus", "GangsPlus", GangsPlusHook.class, "https://www.spigotmc.org/resources/2604/"), 
	HEROES("heroes", "Heroes", HeroesHook.class, "https://www.spigotmc.org/resources/305/"), 
	ISLANDWORLD("islandworld", "IslandWorld", IslandWorldHook.class, "https://www.spigotmc.org/resources/2757/"),
	JOBS("jobs", "Jobs", JobsHook.class, "https://www.spigotmc.org/resources/4216/"), 
	KILLSTATS("killstats", "killStats", KillStatsHook.class, "http://dev.bukkit.org/bukkit-plugins/killstats-v1-0/"),
	LEADERHEADS("leaderheads", "LeaderHeads", LeaderheadsHook.class, "https://www.spigotmc.org/resources/2079/"),
	LWC("lwc", "LWC", LWCHook.class, "https://www.spigotmc.org/resources/2150/"), 
	MARRIAGE("marriage", "Marriage", MarriageHook.class, "https://www.spigotmc.org/resources/18998/"),
	MARRIAGEMASTER("marriagemaster", "MarriageMaster", MarriageMasterHook.class, "http://dev.bukkit.org/bukkit-plugins/marriage-master/"),
	MCINFECTED("mcinfected", "McInfected", McInfectedHook.class, "https://www.spigotmc.org/resources/2133/"), 
	MCINFECTED_RANKS("mcinfected-ranks", "McInfected-Ranks", McInfectedRanksHook.class, "https://www.spigotmc.org/resources/2826/"), 
	MCMMO("mcmmo", "mcMMO", McMMOHook.class, "https://www.spigotmc.org/resources/2445/"), 
	MINECRATES("minecrates", "MineCrates", MineCratesHook.class, "https://www.spigotmc.org/resources/4685/"), 
	MVDWPLACEHOLDERS("mvdw", "MVdWPlaceholderAPI", MVdWPlaceholderHook.class, "https://www.spigotmc.org/resources/11182/"), 
	MYSQLTOKENS("mysqltokens", "MySQLTokens", MySQLTokensHook.class, "https://www.spigotmc.org/resources/15541/"),
	NICKNAMER("nicknamer", "NickNamer", NicknamerHook.class, "https://www.spigotmc.org/resources/5341/"), 
	NICKY("nicky", "Nicky", NickyHook.class, "https://www.spigotmc.org/resources/590/"), 
	ONTIME("ontime", "OnTime", OnTimeHook.class, "http://dev.bukkit.org/bukkit-plugins/ontime/"), 
	PLAYERPOINTS("playerpoints", "PlayerPoints", PlayerPointsHook.class, "http://dev.bukkit.org/bukkit-plugins/playerpoints/"), 
	PLOTSQUARED("plotsquared", "PlotSquared", PlotSquaredHook.class, "https://www.spigotmc.org/resources/1177/"),
	POINTSAPI("pointsapi", "PointsAPI", PointsAPIHook.class, "https://www.spigotmc.org/resources/13957/"),
	PRISONMINES("prisonmines", "PrisonMines", PrisonMinesHook.class, "https://www.spigotmc.org/resources/4046/"),
	PVPSTATS("pvpstats", "pvpstats", PvPStatsHook.class, "http://dev.bukkit.org/bukkit-plugins/pvp-stats/"), 
	QUICKSELL("quicksell", "QuickSell", QuickSellHook.class, "https://www.spigotmc.org/resources/6107/"), 
	ROYALCOMMANDS("royalcommands", "RoyalCommands", RoyalCommandsHook.class, "http://dev.bukkit.org/bukkit-plugins/royalcommands/"), 
	SELLALL("sellall", "SellAll", SellAllHook.class, "https://www.spigotmc.org/resources/1221/"),
	SIMPLECLANS("simpleclans", "SimpleClans", SimpleClansHook.class, "http://dev.bukkit.org/bukkit-plugins/simpleclans/"), 
	SIMPLECOINS("simplecoinsapi", "SimpleCoinsAPI", SimpleCoinsAPIHook.class, "https://www.spigotmc.org/resources/1432/"), 
	SIMPLEPREFIX("simpleprefix", "SimplePrefix", SimplePrefixHook.class, "http://dev.bukkit.org/bukkit-plugins/simple-prefix/"), 
	SIMPLESUFFIX("simplesuffix", "Simple Suffix", SimpleSuffixHook.class, "http://dev.bukkit.org/bukkit-plugins/simple-suffix/"), 
	SKILLAPI("skillapi", "SkillAPI", SkillAPIHook.class, "https://www.spigotmc.org/resources/4824/"), 
	SKYWARSRELOADED("skywarsreloaded", "SkyWarsReloaded", SkyWarsReloadedHook.class, "https://www.spigotmc.org/resources/15464/"), 
	SQLPERMS("sqlperms", "SQLPerms", SQLPermsHook.class, "https://www.spigotmc.org/resources/1462/"), 
	SQLTOKENS("sqltokens", "SQLTokens", SQLTokensHook.class, "https://www.spigotmc.org/resources/3482/"), 
	STTITLES("sttitles", "stTitles", StTitlesHook.class, "https://www.spigotmc.org/resources/8310/"),
	SUPERBVOTE("superbvote", "SuperbVote", SuperbVoteHook.class, "https://www.spigotmc.org/resources/11626/"), 
	TOKENENCHANT("tokenenchant", "TokenEnchant", TokenEnchantHook.class, "https://www.spigotmc.org/resources/2287/"), 
	TOKENMANAGER("tokenmanager", "TokenManager", TokenManagerHook.class, "https://www.spigotmc.org/resources/8610/"),
	TOWNY("towny", "Towny", TownyHook.class, "http://palmergames.com/towny/"),
	ULTIMATEVOTES("ultimatevotes", "UltimateVotes", UltimateVotesHook.class, "https://www.spigotmc.org/resources/516/"), 
	USKYBLOCK("uskyblock", "uSkyBlock", USkyblockHook.class, "https://www.spigotmc.org/resources/2280/"), 
	VAULTECO("vaulteco", "Vault", VaultEcoHook.class, "http://dev.bukkit.org/bukkit-plugins/vault/"), 
	VAULTPERMS("vault", "Vault", VaultPermsHook.class, "http://dev.bukkit.org/bukkit-plugins/vault/"),
	VOTEPARTY("voteparty", "VoteParty", VotePartyHook.class, "https://www.spigotmc.org/resources/987/"),
	VOTEROULETTE("voteroulette", "VoteRoulette", VoteRouletteHook.class, "https://www.spigotmc.org/resources/8126/"), 
	WICKEDSKYWARS("wickedskywars", "WickedSkyWars", WickedSkywarsHook.class, "https://www.spigotmc.org/resources/556/")
	;
	
	private String identifier;
	
	private String plugin;
	
	private String link;
	
	private Class<? extends IPlaceholderHook> hook;
	
	private InternalHook(String identifier, String plugin, Class<? extends IPlaceholderHook> hook, String link) {
		this.identifier = identifier;
		this.plugin = plugin;
		this.hook = hook;
		this.link = link;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public String getPluginName() {
		return plugin;
	}
	
	public String getLink() {
		return link;
	}
	
	public Class<? extends IPlaceholderHook> getHookClass() {
		return hook;
	}
	
	public boolean isRegistered() {
		return PlaceholderAPI.isRegistered(getIdentifier());
	}
	
	public static InternalHook getHookByIdentifier(String identifier) {
		for (InternalHook pl : values()) {
			if (pl.getIdentifier().equalsIgnoreCase(identifier)) {
				return pl;
			}
		}
		return null;
	}
	
	public static InternalHook getHookByPluginName(String plugin) {
		for (InternalHook pl : values()) {
			if (pl.getPluginName() == null) {
				continue;
			}
			if (pl.getPluginName().equalsIgnoreCase(plugin)) {
				return pl;
			}
		}
		return null;
	}
	
	public boolean shouldRegister() {
		return PlaceholderAPIPlugin.getInstance().getConfig().getBoolean("placeholder_hooks." + getIdentifier());
	}
	
	public boolean register() {
		
		if (PlaceholderAPIPlugin.getInstance() == null) {
			return false;
		}

		boolean hooked = false;
		
		if (!shouldRegister()) {
			return hooked;
		}
		
		String hooking = getPluginName() != null ? getPluginName() : getIdentifier();

		IPlaceholderHook c;
		
		try {
			c = getHookClass().getConstructor(InternalHook.class).newInstance(this);
		} catch (Exception e) {
			e.printStackTrace();
			return hooked;
		}
		
		if (!c.canHook()) {
			return hooked;
		}
			
		if (c instanceof VersionSpecific) {
			VersionSpecific nms = (VersionSpecific) c;
			if (nms.getVersion() != PlaceholderAPIPlugin.getNMSVersion()) {
				return hooked;
			}
		} 
			
		hooked = c.hook();
			
		if (hooked) {
			
			if (c instanceof Configurable) {
				
				Map<String, Object> defaults = ((Configurable)c).getDefaults();
				
				if (defaults != null && !defaults.isEmpty()) {
					
					String pre = c.getIdentifier() + ".";
					
					FileConfiguration cfg = PlaceholderAPIPlugin.getInstance().getConfig();
					
					boolean save = false;
					
					for (Entry<String, Object> entries : defaults.entrySet()) {
						if (entries.getKey() == null || entries.getKey().isEmpty()) {
							continue;
						}
						
						if (entries.getValue() == null) {
							if (cfg.contains(pre + entries.getKey())) {
								save = true;
								cfg.set(pre + entries.getKey(), null);
							}
						} else {
							if (!cfg.contains(pre + entries.getKey())) {
								save = true;
								cfg.set(pre + entries.getKey(), entries.getValue());
							}
						}
					}
					
					if (save) {
						PlaceholderAPIPlugin.getInstance().saveConfig();
						PlaceholderAPIPlugin.getInstance().reloadConfig();
						PlaceholderAPIPlugin.getInstance().getLogger().info("Configuration section has been updated for hook: " + c.getIdentifier());
					}
				}
			}
				
			PlaceholderAPIPlugin.getInstance().getLogger().info("Successfully registered " + hooking + " placeholders!");
				
			if (c instanceof Taskable) {
				((Taskable) c).start();
				PlaceholderAPIPlugin.getInstance().getLogger().info("Started task for " + hooking + " placeholders!");
			}
		}
			
		return hooked;
	}
	
	public static int registerHooks() {
		for (InternalHook pl : values()) {
			pl.register();
		}
		return PlaceholderAPI.getRegisteredPlaceholderPlugins().size();
	}
	
	public static int getHookAmount() {
		return values().length;
	}
	
	public static int getHookAmountWithDependency() {
		int i = 0;
		for (InternalHook hook : values()) {
			if (hook.getPluginName() != null) {
				i = i+1;
			}
		}
		return i;
	}
}
