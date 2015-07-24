package me.clip.placeholderapi.hooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.enjin.officialplugin.points.ErrorConnectingToEnjinException;
import com.enjin.officialplugin.points.PlayerDoesNotExistException;
import com.enjin.officialplugin.points.PointsAPI;

public class EnjinMinecraftPluginHook implements Listener {

	private PlaceholderAPIPlugin plugin;
	
	private final Map<String, Integer> points = new ConcurrentHashMap<String, Integer>();

	private final Set<String> retrieve = new HashSet<String>();
	
	private static int taskId = -1;
	
	private static boolean registered;
	
	public EnjinMinecraftPluginHook(PlaceholderAPIPlugin i) {
		plugin = i;
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		String name = e.getPlayer().getName();

		if (points != null && points.containsKey(name)) {
			points.remove(name);
		}
	}
	
	public void hook() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("EnjinMinecraftPlugin")) {
			
				boolean hooked = PlaceholderAPI.registerPlaceholderHook("EnjinMinecraftPlugin", new PlaceholderHook() {

					@Override
						public String onPlaceholderRequest(Player p, String identifier) {

							if (p == null) {
								return "";
							}

							if (identifier.equals("points")) {
								if (points == null) {
									return "0";
								}

								if (points.containsKey(p.getName())) {
									return String.valueOf(points.get(p.getName()));
								} else {
									if (!retrieve.contains(p.getName())) {
										retrieve.add(p.getName());
									}
									return "0";
								}

							}

							return null;
						}
					
				}, true);
				
				if (hooked) {
					
					if (!registered) {
						Bukkit.getPluginManager().registerEvents(this, plugin);
						registered = true;
					}
					
					if (taskId == -1) {
						
						taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {

							@Override
							public void run() {
								
								List<String> just = new ArrayList<String>();
								
									if (retrieve != null && !retrieve.isEmpty()) {

										Iterator<String> it = retrieve.iterator();

										while (it.hasNext()) {

											String p = it.next();

											just.add(p);

											try {
												points.put(p, PointsAPI.getPointsForPlayer(p));
											} catch (PlayerDoesNotExistException | ErrorConnectingToEnjinException e) {
												points.put(p, 0);
											}

											it.remove();
										}

									}
								
									if (points != null && !points.isEmpty()) {

										Iterator<String> cached = points.keySet().iterator();

										Map<String, Integer> updated = new HashMap<String, Integer>();

										while (cached.hasNext()) {
											String p = cached.next();

											if (!just.contains(p)) {

												try {
													updated.put(p, PointsAPI.getPointsForPlayer(p));
												} catch (PlayerDoesNotExistException | ErrorConnectingToEnjinException e) {
												}
											}
										}

										if (!updated.isEmpty()) {
											for (Entry<String, Integer> p : updated.entrySet()) {
												points.put(p.getKey(), p.getValue());
											}
											updated = null;
										}
									}
									
									just = null;
								}
								
							
						}, 20L, 20L * 60).getTaskId();
						
					}
					plugin.log.info("Hooked into EnjinMinecraftPlugin for placeholders!");
				}
			}
	}
}
