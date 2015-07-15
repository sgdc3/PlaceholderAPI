package me.clip.placeholderapi.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.worldcretornica.plotme_core.Plot;
import com.worldcretornica.plotme_core.PlotId;
import com.worldcretornica.plotme_core.PlotMeCoreManager;
import com.worldcretornica.plotme_core.api.Location;
import com.worldcretornica.plotme_core.bukkit.BukkitUtil;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

public class PlotMeHook {

	private PlaceholderAPIPlugin plugin;
	
	private PlotMeCoreManager api;
	
	public PlotMeHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	public void hook() {
	
		if (Bukkit.getPluginManager().isPluginEnabled("PlotMe")) {
			
			api = PlotMeCoreManager.getInstance();
			
			if (api != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook("plotme", new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {
						
						if (p == null) {
							return "";
						}
						
						Location location = BukkitUtil.adapt(p.getLocation());
						
						if (location == null || !api.isPlotWorld(location)) {
							switch (identifier){
							
							case "in_plot":
							case "in_plot_world":
								return PlaceholderAPIPlugin.booleanFalse();
							}
							return "";
						} else {
							if (identifier.equals("in_plot_world")) {
								return PlaceholderAPIPlugin.booleanTrue();
							}
						}
						
						PlotId id = api.getPlotId(location);
						
						if (id == null) {
							if (identifier.equals("in_plot")) {
								return PlaceholderAPIPlugin.booleanFalse();
							} else {
								return "";
							}
						} else {
							if (identifier.equals("in_plot")) {
								return PlaceholderAPIPlugin.booleanTrue();
							}
						}
						
						Plot plot = api.getPlot(location);
						
						if (plot == null) {
							return "";
						}
						
						switch (identifier){
						
						case "current_plot_id":
							return plot.getId().getID();
						case "current_plot_owner":
							return plot.getOwner();	
						case "current_plot_owner_uuid":
							return plot.getOwnerId().toString();	
						case "current_plot_allowed":
						case "current_plot_is_allowed":
							return !plot.isDenied(p.getUniqueId()) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
						case "current_plot_is_denied":
						case "current_plot_denied":
							return plot.isDenied(p.getUniqueId()) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
						case "current_plot_finished_date":
							return plot.getFinishedDate();
						case "current_plot_world":
							return p.getWorld().getName();
						case "current_plot_biome":
							return plot.getBiome().toString();
						case "current_plot_likes":
							return String.valueOf(plot.getLikes());
						case "current_plot_price":
							return String.valueOf(plot.getPrice());
						case "current_plot_expired_date":
							return plot.getExpiredDate() != null ? 
									PlaceholderAPIPlugin.getDateFormat().format(plot.getExpiredDate()) : "";
						case "current_plot_is_member":
							return plot.isMember(p.getUniqueId()).orNull() != null ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
						case "current_get_role":
							if (plot.isMember(p.getUniqueId()).orNull() == null) {
								return "";
							} else {
								int level = plot.isMember(p.getUniqueId()).get().getLevel();
								
								if (level == 0) {
									return "Member";
								} else {
									return "Trusted";
								}
							}
						case "current_plot_is_finished":
							return plot.isFinished() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
						case "current_plot_is_for_sale":
							return plot.isForSale() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
						case "current_plot_is_protected":
							return plot.isProtected() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
						}
						return null;
					}
					
				}, true);
				
				if (hooked) {
					plugin.log.info("Successfully hooked into PlotMe for placeholders!");
				}
			}
		}
	}

}
