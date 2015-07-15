package me.clip.placeholderapi.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.util.PlotHelper;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

public class PlotSquaredHook {

	private PlaceholderAPIPlugin plugin;

	public PlotSquaredHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}

	private PlotAPI plotAPI = null;

	public void hook() {
		
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlotSquared")) {
			
			plotAPI = new PlotAPI(plugin);
			
			if (plotAPI != null) {
				
				boolean hooked = PlaceholderAPI.registerPlaceholderHook("PlotSquared", new PlaceholderHook() {

					@Override
					public String onPlaceholderRequest(Player p, String identifier) {
						
						if (p == null) {
							return "";
						}
						
						switch (identifier) {
						
						case "currentplot_alias":
							return getCurrentPlot(p);
						case "currentplot_owner":
							return getCurrentPlotOwner(p);
						case "currentplot_helpers_size":
							return getCurrentPlotHelpers(p);
						case "currentplot_x":
							return getCurrentPlotX(p);
						case "currentplot_y":
							return getCurrentPlotY(p);
						case "currentplot_world":
							return getCurrentPlotWorld(p);
						case "has_plot":
							return hasPlot(p);
						case "has_build_rights":
							return hasRights(p);
						case "plot_count":
							return getPlotCount(p);
						case "allowed_plot_count":
							return getAllowedPlotCount(p);						
						}
						return null;
					}
					
				}, true);
				
				if (hooked) {
					plugin.log.info("Hooked into PlotSquared for placeholders!");
				}
			}
		}
	}

	private String getCurrentPlotX(Player p) {
		if (plotAPI.isInPlot(p)) {
			Plot plot = plotAPI.getPlot(p);

			if (plot == null) {
				return "";
			}
			if (plot.id.x != null) {
				return String.valueOf(plot.id.x);
			}
		}
		return "";
	}

	private String getCurrentPlotY(Player p) {
		if (plotAPI.isInPlot(p)) {
			Plot plot = plotAPI.getPlot(p);

			if (plot == null) {
				return "";
			}
			if (plot.id.y != null) {
				return String.valueOf(plot.id.y);
			}
		}
		return "";
	}

	private String getCurrentPlotWorld(Player p) {
		if (plotAPI.isInPlot(p)) {
			Plot plot = plotAPI.getPlot(p);

			if (plot == null) {
				return "";
			}
			if (plot.getWorld() != null) {
				return plot.getWorld().getName();
			}
		}
		return "";
	}

	private String getCurrentPlotHelpers(Player p) {
		if (plotAPI.isInPlot(p)) {
			Plot plot = plotAPI.getPlot(p);

			if (plot == null) {
				return "";
			}
			if (plot.helpers != null) {
				return String.valueOf(plot.helpers.size());
			}
		}
		return "";
	}

	private String getCurrentPlot(Player p) {
		if (plotAPI.isInPlot(p)) {
			Plot plot = plotAPI.getPlot(p);

			if (plot == null) {
				return "";
			}
			if (plot.settings.getAlias() != null) {
				return plot.settings.getAlias();
			}
		}
		return "";
	}

	private String getPlotCount(Player p) {
		if (plotAPI != null) {
		
			return String.valueOf(plotAPI.getPlayerPlotCount(p.getWorld(), p));
		
		}
		return "";
	}
	private String getAllowedPlotCount(Player p) {
		if (plotAPI != null) {
		
			return String.valueOf(plotAPI.getAllowedPlots(p));
		
		}
		return "";
	}
	
	private String hasPlot(Player p) {
		if (plotAPI != null) {
			if (plotAPI.hasPlot(p.getWorld(), p)) {
				return PlaceholderAPIPlugin.booleanTrue();
			}
			return PlaceholderAPIPlugin.booleanFalse();
		}
		return "";
	}
	
	private String hasRights(Player p) {
		if (plotAPI != null) {
			if (plotAPI.getPlot(p) != null) {
				if (plotAPI.getPlot(p).hasRights(p)) {
					return PlaceholderAPIPlugin.booleanTrue();
				}
				return PlaceholderAPIPlugin.booleanFalse();
			}
		}
		return "";
	}
	
	private String getCurrentPlotOwner(Player p) {
		if (plotAPI != null) {
			if (plotAPI.getPlot(p) != null) {
				if (plotAPI.getPlot(p).getOwner() != null) {
					if (PlotHelper.getPlayerName(plotAPI.getPlot(p).getOwner()) != null) {
						return PlotHelper.getPlayerName(plotAPI.getPlot(p).getOwner());
					}	
				}
				return "";
			}
		}
		return "";
	}

}
