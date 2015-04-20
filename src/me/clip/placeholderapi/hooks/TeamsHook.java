package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;
import me.coder_m.teams.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeamsHook {

	private PlaceholderAPIPlugin plugin;
	
	public TeamsHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	public void hook() {
		if (Bukkit.getPluginManager().isPluginEnabled("Teams")) {

			if (TeamManager.getTeamManager() != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook("Teams", new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {

						switch (identifier) {
						
						case "team":
							return getTeamName(p);
						case "is_leader":
							return isTeamLeader(p);
						case "leader":
							return getTeamLeader(p);
						case "size":
							return getTeamSize(p);
						case "leaders_size":
							return getLeaderSize(p);
						case "friendly_fire":
							return getFf(p);
						case "home_location":
							return getHomeLocation(p);
						case "home_x":
							return getHomeX(p);
						case "home_y":
							return getHomeY(p);
						case "home_z":
							return getHomeZ(p);
						case "home_world":
							return getHomeWorld(p);
						}

						return null;
					}
				});
				
				if (hooked) {
					plugin.log.info("Hooked into Teams for placeholders!");
				}
			}
		}
	}

	private String getTeamName(Player p) {
		if (TeamManager.getTeamManager().getInTeam(p) != null) {
			if (!TeamManager.getTeamManager().getInTeam(p).isEmpty()) {
				return TeamManager.getTeamManager().getInTeam(p).get(0);
			}
		}
		return "";
	}

	private String getTeamSize(Player p) {
		if (getTeamName(p) != "") {
			return String.valueOf(TeamManager.getTeamManager().getTeamSize(getTeamName(p)));
		}
		return "";
	}
	
	private String getLeaderSize(Player p) {
		if (getTeamName(p) != "") {
			return String.valueOf(TeamManager.getTeamManager().getTeamLeaderAmount((p)));
		}
		return "";
	}
	
	private String isTeamLeader(Player p) {
		if (getTeamName(p) != "") {
			if (TeamManager.getTeamManager().isTeamLeader(p)) {
			return PlaceholderAPIPlugin.booleanTrue();
			}
			return PlaceholderAPIPlugin.booleanFalse();
		}
		return "";
	}
	
	private String getFf(Player p) {
		if (getTeamName(p) != "") {
			if (TeamManager.getTeamManager().getFriendlyFire(getTeamName(p)) != null) {
			return TeamManager.getTeamManager().getFriendlyFire(getTeamName(p));
			}
		}
		return "";
	}
	
	private String getHomeLocation(Player p) {
		if (getTeamName(p) != "") {
			if (TeamManager.getTeamManager().getTeamHome(getTeamName(p)) != null) {
				Location l = TeamManager.getTeamManager().getTeamHome(getTeamName(p));
				String loc = l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ();
			return loc;
			}
		}
		return "";
	}
	
	private String getHomeWorld(Player p) {
		if (getTeamName(p) != "") {
			if (TeamManager.getTeamManager().getTeamHome(getTeamName(p)) != null) {
			return TeamManager.getTeamManager().getTeamHome(getTeamName(p)).getWorld().getName();
			}
		}
		return "";
	}
	
	private String getHomeX(Player p) {
		if (getTeamName(p) != "") {
			if (TeamManager.getTeamManager().getTeamHome(getTeamName(p)) != null) {
				Location l = TeamManager.getTeamManager().getTeamHome(getTeamName(p));
				return String.valueOf(l.getBlockX());
			}
		}
		return "";
	}
	
	private String getHomeY(Player p) {
		if (getTeamName(p) != "") {
			if (TeamManager.getTeamManager().getTeamHome(getTeamName(p)) != null) {
				Location l = TeamManager.getTeamManager().getTeamHome(getTeamName(p));
				return String.valueOf(l.getBlockY());
			}
		}
		return "";
	}
	
	private String getHomeZ(Player p) {
		if (getTeamName(p) != "") {
			if (TeamManager.getTeamManager().getTeamHome(getTeamName(p)) != null) {
				Location l = TeamManager.getTeamManager().getTeamHome(getTeamName(p));
				return String.valueOf(l.getBlockZ());
			}
		}
		return "";
	}
	
	private String getTeamLeader(Player p) {
		if (getTeamName(p) != "") {
			if (TeamManager.getTeamManager().getTeamLeader(getTeamName(p)) != null) {
				return TeamManager.getTeamManager().getTeamLeader(getTeamName(p));
			}
		}
		return "";
	}
	
}
