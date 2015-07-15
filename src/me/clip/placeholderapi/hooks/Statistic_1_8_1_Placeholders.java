package me.clip.placeholderapi.hooks;

import java.util.EnumSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.craftbukkit.v1_8_R1.CraftStatistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;
import me.clip.placeholderapi.util.TimeFormat;
import me.clip.placeholderapi.util.TimeUtil;

public class Statistic_1_8_1_Placeholders {
	
	private PlaceholderAPIPlugin plugin;
	
	public Statistic_1_8_1_Placeholders(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	private final Set<Material> mine_block = EnumSet.noneOf(Material.class);
	private final Set<Material> use_item = EnumSet.noneOf(Material.class);
	private final Set<Material> break_item = EnumSet.noneOf(Material.class);
	private final Set<Material> craft_item = EnumSet.noneOf(Material.class);

	public void hook() {
		
		for (Material m : Material.values()) {
			if (CraftStatistic.getMaterialStatistic(Statistic.MINE_BLOCK, m) != null) {
				mine_block.add(m);
			}
			if (CraftStatistic.getMaterialStatistic(Statistic.USE_ITEM, m) != null) {
				use_item.add(m);
			}
			if (CraftStatistic.getMaterialStatistic(Statistic.BREAK_ITEM, m) != null) {
				break_item.add(m);
			}
			if (CraftStatistic.getMaterialStatistic(Statistic.CRAFT_ITEM, m) != null) {
				craft_item.add(m);
			}
		}

		boolean hooked = PlaceholderAPI.registerPlaceholderHook("statistic", new PlaceholderHook() {

			@Override
			public String onPlaceholderRequest(Player p, String identifier) {
				
				if (p == null) {
					return "";
				}

				if (identifier.contains("mine_block_")) {
					
					try {
						String type = identifier.split("mine_block_")[1];
						
						long mine = p.getStatistic(Statistic.MINE_BLOCK, Material.valueOf(type));
						
						return String.valueOf(mine);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return "0";
					
				} else if (identifier.contains("use_item_")) {
					
					
					try {
						
						String type = identifier.split("use_item_")[1];
						
						long use = p.getStatistic(Statistic.USE_ITEM, Material.valueOf(type));
						
						return String.valueOf(use);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return "0";
					
				} else if (identifier.contains("break_item_")) {

					try {
						String type = identifier.split("break_item_")[1];
						
						long b = p.getStatistic(Statistic.BREAK_ITEM, Material.valueOf(type));
						
						return String.valueOf(b);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return "0";
					
				} else if (identifier.contains("craft_item_")) {
					
					try {
						String type = identifier.split("craft_item_")[1];
						
						long c = p.getStatistic(Statistic.CRAFT_ITEM, Material.valueOf(type));
						
						return String.valueOf(c);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return "0";
					
				} else if (identifier.contains("kill_entity_")) {
						
					try {
						
						String type = identifier.split("kill_entity_")[1];
						
						long kills = p.getStatistic(Statistic.KILL_ENTITY, EntityType.valueOf(type));
						
						return String.valueOf(kills);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return "0";
						
				} else if (identifier.contains("entity_killed_by_")) {
					
					try {
						String type = identifier.split("entity_killed_by_")[1];
						
						long kills = p.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.valueOf(type));
						
						return String.valueOf(kills);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return "0";
				}
				
				switch (identifier) {

				case "mine_block":
					
					long mine = 0;
					
					if (mine_block == null) {
						return "0";
					}
						
					for (Material m : mine_block) {
						mine += p.getStatistic(Statistic.MINE_BLOCK, m);
					}
				
					return String.valueOf(mine);
					
				case "use_item":
					
					long use = 0;
					
					if (use_item == null) {
						return "0";
					}
						
					for (Material m : use_item) {
						use += p.getStatistic(Statistic.USE_ITEM, m);
					}
				
					return String.valueOf(use);
					
				case "break_item":
					
					long br = 0;
					
					if (break_item == null) {
						return "0";
					}
						
					for (Material m : break_item) {
						br += p.getStatistic(Statistic.BREAK_ITEM, m);
					}
				
					return String.valueOf(br);
					
				case "craft_item":
					
					long cr = 0;
					
					if (craft_item == null) {
						return "0";
					}
						
					for (Material m : craft_item) {
						cr += p.getStatistic(Statistic.CRAFT_ITEM, m);
					}
				
					return String.valueOf(cr);
					
				case "ticks_played":
					return String.valueOf(p.getStatistic(Statistic.PLAY_ONE_TICK));
				case "seconds_played":
					return String.valueOf(p.getStatistic(Statistic.PLAY_ONE_TICK) / 20);
				case "minutes_played":
					return String.valueOf((p.getStatistic(Statistic.PLAY_ONE_TICK) / 20) / 60);
				case "hours_played":
					return String.valueOf(((p.getStatistic(Statistic.PLAY_ONE_TICK) / 20) / 60) / 60);
				case "days_played":
					return String.valueOf((((p.getStatistic(Statistic.PLAY_ONE_TICK) / 20) / 60) / 60) / 24);
				case "time_played":
					return TimeUtil.getTime((int) p.getStatistic(Statistic.PLAY_ONE_TICK) / 20);
				case "days_played_remaining":
					return TimeUtil.getRemaining((int) p.getStatistic(Statistic.PLAY_ONE_TICK) / 20, TimeFormat.DAYS);
				case "hours_played_remaining":
					return TimeUtil.getRemaining((int) p.getStatistic(Statistic.PLAY_ONE_TICK) / 20, TimeFormat.HOURS);
				case "minutes_played_remaining":
					return TimeUtil.getRemaining((int) p.getStatistic(Statistic.PLAY_ONE_TICK) / 20, TimeFormat.MINUTES);
				case "seconds_played_remaining":
					return TimeUtil.getRemaining((int) p.getStatistic(Statistic.PLAY_ONE_TICK) / 20, TimeFormat.SECONDS);
				case "animals_bred":
					return String.valueOf(p.getStatistic(Statistic.ANIMALS_BRED));
				case "boat_one_cm":
					return String.valueOf(p.getStatistic(Statistic.BOAT_ONE_CM));
				case "climb_one_cm":
					return String.valueOf(p.getStatistic(Statistic.CLIMB_ONE_CM));
				case "crouch_one_cm":
					return String.valueOf(p.getStatistic(Statistic.CROUCH_ONE_CM));
				case "damage_dealt":
					return String.valueOf(p.getStatistic(Statistic.DAMAGE_DEALT));
				case "damage_taken":
					return String.valueOf(p.getStatistic(Statistic.DAMAGE_TAKEN));
				case "deaths":
					return String.valueOf(p.getStatistic(Statistic.DEATHS));
				case "dive_one_cm":
					return String.valueOf(p.getStatistic(Statistic.DIVE_ONE_CM));
				case "drop":
					return String.valueOf(p.getStatistic(Statistic.DROP));
				case "fall_one_cm":
					return String.valueOf(p.getStatistic(Statistic.FALL_ONE_CM));
				case "fish_caught":
					return String.valueOf(p.getStatistic(Statistic.FISH_CAUGHT));
				case "fly_one_cm":
					return String.valueOf(p.getStatistic(Statistic.FLY_ONE_CM));
				case "horse_one_cm":
					return String.valueOf(p.getStatistic(Statistic.HORSE_ONE_CM));
				case "jump":
					return String.valueOf(p.getStatistic(Statistic.JUMP));
				case "junk_fished":
					return String.valueOf(p.getStatistic(Statistic.JUNK_FISHED));
				case "leave_game":
					return String.valueOf(p.getStatistic(Statistic.LEAVE_GAME));
				case "join_game":
					return String.valueOf(p.getStatistic(Statistic.LEAVE_GAME)+1);
				case "minecart_one_cm":
					return String.valueOf(p.getStatistic(Statistic.MINECART_ONE_CM));
				case "pig_one_cm":
					return String.valueOf(p.getStatistic(Statistic.PIG_ONE_CM));
				case "player_kills":
					return String.valueOf(p.getStatistic(Statistic.PLAYER_KILLS));
				case "sprint_one_cm":
					return String.valueOf(p.getStatistic(Statistic.SPRINT_ONE_CM));
				case "swim_one_cm":
					return String.valueOf(p.getStatistic(Statistic.SWIM_ONE_CM));
				case "talked_to_villager":
					return String.valueOf(p.getStatistic(Statistic.TALKED_TO_VILLAGER));
				case "time_since_death":
					return TimeUtil.getTime((int) p.getStatistic(Statistic.TIME_SINCE_DEATH) / 20);
				case "ticks_since_death":
					return String.valueOf(p.getStatistic(Statistic.TIME_SINCE_DEATH));
				case "seconds_since_death":
					return String.valueOf(p.getStatistic(Statistic.TIME_SINCE_DEATH) / 20L);
				case "minutes_since_death":
					return String.valueOf((p.getStatistic(Statistic.TIME_SINCE_DEATH) / 20L) / 60L);
				case "hours_since_death":
					return String.valueOf(((p.getStatistic(Statistic.TIME_SINCE_DEATH) / 20L) / 60L) / 60L);
				case "days_since_death":
					return String.valueOf((((p.getStatistic(Statistic.TIME_SINCE_DEATH) / 20L) / 60L) / 60L) / 24L);
				case "traded_with_villager":
					return String.valueOf(p.getStatistic(Statistic.TRADED_WITH_VILLAGER));
				case "treasure_fished":
					return String.valueOf(p.getStatistic(Statistic.TREASURE_FISHED));
				case "walk_one_cm":
					return String.valueOf(p.getStatistic(Statistic.WALK_ONE_CM));
				}
				
				return null;
				}

				}, true);
		
		if (hooked) {
			plugin.log.info("Spigot 1.8.1 Player statistic placeholders enabled! Note that some statistics might not be available!");
		}
	}
	
}
