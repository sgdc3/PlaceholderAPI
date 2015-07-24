package me.clip.placeholderapi.placeholders;

import java.util.Date;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerPlaceholders {
	
	private PlaceholderAPIPlugin plugin;
	
	public PlayerPlaceholders(PlaceholderAPIPlugin i) {
		plugin = i;	
	}

 
	public void hook() {

		boolean hooked = PlaceholderAPI.registerPlaceholderHook("player", new PlaceholderHook() {

			@Override
			public String onPlaceholderRequest(Player p, String identifier) {
				
				if (p == null) {
					return "";
				}
				
				if (identifier.startsWith("has_permission_")) {
					
					String perm = identifier.split("has_permission_")[1];
					
					if (perm == null || perm.isEmpty()) {
						return "";
					}
					
					return p.hasPermission(perm) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
				}
				
				
				switch (identifier) {
				
				case "server":
				case "servername":
					return Bukkit.getServerName();
				case "name":
					return p.getName();
				case "displayname":
					return p.getDisplayName();
				case "uuid":
					return p.getUniqueId().toString();
				case "gamemode":
					return p.getGameMode().name();
				case "world":
					return p.getWorld().getName();
				case "x":
					return String.valueOf(p.getLocation().getBlockX());
				case "y":
					return String.valueOf(p.getLocation().getBlockY());
				case "z":
					return String.valueOf(p.getLocation().getBlockZ());		
				case "is_op":
					return p.isOp() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
				case "bed_x":
					return p.getBedSpawnLocation() != null ? 
							String.valueOf(p.getBedSpawnLocation().getBlockX()) : "";	
				case "bed_y":
					return p.getBedSpawnLocation() != null ? 
							String.valueOf(p.getBedSpawnLocation().getBlockY()) : "";
				case "bed_z":
					return p.getBedSpawnLocation() != null ? 
							String.valueOf(p.getBedSpawnLocation().getBlockZ()) : "";
				case "bed_world":
					return p.getBedSpawnLocation() != null ? 
							p.getBedSpawnLocation().getWorld().getName() : "";
				case "ip":
					return p.getAddress().getHostName();	
				case "allow_flight":	
					return p.getAllowFlight() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
				case "can_pickup_items":	
					return p.getCanPickupItems() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
				case "compass_x":	
					return p.getCompassTarget() != null ? 
							String.valueOf(p.getCompassTarget().getBlockX()) : "";
				case "compass_y":	
					return p.getCompassTarget() != null ? 
							String.valueOf(p.getCompassTarget().getBlockY()) : "";
				case "compass_z":	
					return p.getCompassTarget() != null ? 
							String.valueOf(p.getCompassTarget().getBlockZ()) : "";
				case "compass_world":	
					return p.getCompassTarget() != null ? 
							p.getCompassTarget().getWorld().getName() : "";
				
				case "custom_name":	
					return p.getCustomName() != null ? p.getCustomName() : p.getName();
				case "exp":
					return String.valueOf(p.getExp());
				case "exp_to_level":
					return String.valueOf(p.getExpToLevel());
				case "level":
					return String.valueOf(p.getLevel());
				case "first_join_date":
					Date d = new Date(p.getFirstPlayed());
					return PlaceholderAPIPlugin.getDateFormat().format(d);
				case "fly_speed":
					return String.valueOf(p.getFlySpeed());
				case "food_level":
					return String.valueOf(p.getFoodLevel());
				case "health":
					return String.valueOf(p.getHealth());
				case "health_scale":
					return String.valueOf(p.getHealthScale());
				case "item_in_hand":
					return p.getItemInHand() != null ? p.getItemInHand().getType().name() : "";
				case "last_damage":
					return String.valueOf(p.getLastDamage());
				case "max_health":
					return String.valueOf(p.getMaxHealth());
				case "max_air":
					return String.valueOf(p.getMaximumAir());
				case "max_no_damage_ticks":
					return String.valueOf(p.getMaximumNoDamageTicks());
				case "no_damage_ticks":
					return String.valueOf(p.getNoDamageTicks());
				case "time":
					return String.valueOf(p.getPlayerTime());
				case "time_offset":
					return String.valueOf(p.getPlayerTimeOffset());
				case "remaining_air":
					return String.valueOf(p.getRemainingAir());
				case "saturation":
					return String.valueOf(p.getSaturation());
				case "sleep_ticks":
					return String.valueOf(p.getSleepTicks());
				case "ticks_lived":
					return String.valueOf(p.getTicksLived());
				case "seconds_lived":
					return String.valueOf(p.getTicksLived() * 20);
				case "minutes_lived":
					return String.valueOf((p.getTicksLived() * 20) / 60);
					
				case "total_exp":
					return String.valueOf(p.getTotalExperience());	
				case "walk_speed":
					return String.valueOf(p.getWalkSpeed());
				}
				return p.getName();
			}	
		}, true);	
		
		if (hooked) {
			plugin.log.info("Player placeholders registered!");
		}
	}
}
