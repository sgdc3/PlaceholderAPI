package me.clip.placeholderapi.injector.entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.gmail.filoghost.holographicdisplays.HolographicDisplays;
import com.gmail.filoghost.holographicdisplays.bridge.protocollib.WrapperPlayServerEntityMetadata;
import com.gmail.filoghost.holographicdisplays.bridge.protocollib.WrapperPlayServerSpawnEntityLiving;
import com.gmail.filoghost.holographicdisplays.nms.interfaces.entity.NMSEntityBase;
import com.gmail.filoghost.holographicdisplays.object.CraftHologram;
import com.gmail.filoghost.holographicdisplays.util.VersionUtils;

/**
@author filoghost, extended_clip, GGhost
*/
public class HD_PRE_1_9_ProtocolLib implements HologramInjector {

	private PlaceholderAPIPlugin plugin;
	
	private final int customNameWatcherIndex;
	
	private final int refreshInterval;
	
	private final Set<CraftHologram> holograms = new HashSet<CraftHologram>();
	
	private BukkitTask updateTask;
	
	public HD_PRE_1_9_ProtocolLib(PlaceholderAPIPlugin i, int interval, int customNameWatcherIndex) {
		plugin = i;		
		
		this.customNameWatcherIndex = customNameWatcherIndex;
		
		if (interval <= 0) {
			refreshInterval = 30;
		} else {
			refreshInterval = interval;
		}
		
		inject();
		
		plugin.getLogger().info("Intercepting hologram packets for HolographicDisplays placeholders");
		
		startTask();
	}
	
	public void clear() {
		holograms.clear();
	}
	
	public void stopTask() {
		if (updateTask != null) {
			try {
			    updateTask.cancel();
			} catch (Exception ex) {}
			updateTask = null;
		}
	}
	
	public void startTask() {
		
		updateTask = new BukkitRunnable() {

			@Override
			public void run() {

				Iterator<CraftHologram> iterator = holograms.iterator();
				
				while (iterator.hasNext()) {
					
					CraftHologram h = iterator.next();
					
					if (h.isDeleted()) {
						iterator.remove();
						return;
					}
					
					h.despawnEntities();
					h.spawnEntities();
				}
			}
			
		}.runTaskTimer(plugin, refreshInterval * 20L, refreshInterval * 20L);		
	}
	
	public void inject() {
		
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.SPAWN_ENTITY_LIVING, PacketType.Play.Server.ENTITY_METADATA) {
			  
			@Override
			public void onPacketSending(PacketEvent event) {
				
				if (event.isCancelled()) {
					return;
				}
				
				if (event.getPlayer() == null) {
					return;
				}
				
				PacketContainer packet = event.getPacket();

				if (packet.getType() == PacketType.Play.Server.SPAWN_ENTITY_LIVING) {

					WrapperPlayServerSpawnEntityLiving spawnEntityPacket = new WrapperPlayServerSpawnEntityLiving(packet);
					
					Entity entity = spawnEntityPacket.getEntity(event);
					
					if (entity == null || !isHologramType(entity.getType())) {
						return;
					}
					
					CraftHologram hologram = getHologram(entity);
					
					if (hologram == null) {
						return;
					}
					
					Player player = event.getPlayer();
					
					WrappedDataWatcher dataWatcher = spawnEntityPacket.getMetadata();
					
					String customName = dataWatcher.getString(2);
					
					if (customName == null) {
						return;
					}
					
					if (PlaceholderAPI.getPlaceholderPattern().matcher(customName).find()) {
						
						if (!holograms.contains(hologram)) {
							holograms.add(hologram);
						}
						
						WrappedDataWatcher dataWatcherClone = dataWatcher.deepClone();
						dataWatcherClone.setObject(customNameWatcherIndex, PlaceholderAPI.setPlaceholders(player, customName));
						spawnEntityPacket.setMetadata(dataWatcherClone);
						event.setPacket(spawnEntityPacket.getHandle());
						
					}

				} else if (packet.getType() == PacketType.Play.Server.ENTITY_METADATA) {
					
					WrapperPlayServerEntityMetadata entityMetadataPacket = new WrapperPlayServerEntityMetadata(packet);
					Entity entity = entityMetadataPacket.getEntity(event);
					
					if (entity == null) {
						return;
					}

					if (entity.getType() != EntityType.HORSE && !VersionUtils.isArmorstand(entity.getType())) {
						return;
					}
					
					CraftHologram hologram = getHologram(entity);
					
					if (hologram == null) {
						return;
					}
					
					Player player = event.getPlayer();

					List<WrappedWatchableObject> dataWatcherValues = entityMetadataPacket.getEntityMetadata();
						
					for (int i = 0; i < dataWatcherValues.size(); i++) {
						
						if (dataWatcherValues.get(i).getIndex() == customNameWatcherIndex && dataWatcherValues.get(i).getValue() != null) {
								
							Object customNameObject = dataWatcherValues.get(i).deepClone().getValue();
							
							if (customNameObject == null || customNameObject instanceof String == false) {
								return;
							}
							
							String customName = (String) customNameObject;
								
							if (PlaceholderAPI.getPlaceholderPattern().matcher(customName).find()) {
								
								if (!holograms.contains(hologram)) {
									holograms.add(hologram);
								}
								
								entityMetadataPacket = new WrapperPlayServerEntityMetadata(packet.deepClone());
								List<WrappedWatchableObject> clonedList = entityMetadataPacket.getEntityMetadata();
								WrappedWatchableObject clonedElement = clonedList.get(i);
								clonedElement.setValue(PlaceholderAPI.setPlaceholders(player, customName));
								entityMetadataPacket.setEntityMetadata(clonedList);
								event.setPacket(entityMetadataPacket.getHandle());
								return;
							}
						}
					}
				}
			}
		});
	}
	
	private boolean isHologramType(EntityType type) {
		return type == EntityType.HORSE || type == EntityType.WITHER_SKULL || type == EntityType.DROPPED_ITEM || type == EntityType.SLIME || VersionUtils.isArmorstand(type); // To maintain backwards compatibility
	}
	
	private CraftHologram getHologram(Entity bukkitEntity) {
		NMSEntityBase entity = HolographicDisplays.getNMSManager().getNMSEntityBase(bukkitEntity);
		if (entity != null) {
			return entity.getHologramLine().getParent();
		}
		
		return null;
	}
	
}
