package me.clip.placeholderapi.injector.inventory;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.comphenix.packetwrapper.WrapperPlayServerSetSlot;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;

/**
@author extended_clip, GGhost
*/
public class InventorySetSlotPacketListener extends PacketAdapter {

	public InventorySetSlotPacketListener(PlaceholderAPIPlugin i){
		super(i, ListenerPriority.HIGHEST, PacketType.Play.Server.SET_SLOT);
		ProtocolLibrary.getProtocolManager().addPacketListener(this);
	}
	
	@Override
	public void onPacketSending(PacketEvent e) {
		
		if (e.getPlayer() == null) {
			return;
		}
		
		WrapperPlayServerSetSlot packet = new WrapperPlayServerSetSlot(e.getPacket());
		
		if (packet.getWindowId() == 0) {	
			return;
		}
		
		Player p = e.getPlayer();

		ItemStack item = packet.getSlotData();
		
		if (item == null || item.getItemMeta() == null || !item.hasItemMeta())  {
			return;
		}

		ItemMeta meta = item.getItemMeta();
		
		boolean strip = false;
		
		if (p.getOpenInventory().getType() == InventoryType.ANVIL) {
			
			if (!p.hasPermission("placeholderapi.injector.anvil.bypass")) {
				strip = true;
			}
		}
		
		boolean contains = false;
		
		if (meta.hasDisplayName()) {
			
			if (PlaceholderAPI.getPlaceholderPattern().matcher(meta.getDisplayName()).find()) {
				
				contains = true;
				
				if (strip) {
					
					meta.setDisplayName(null);
					
				} else {
					
					meta.setDisplayName(PlaceholderAPI.setPlaceholders(p, meta.getDisplayName()));
				}
			}
		}
		
		if (meta.hasLore()) {
			
			ArrayList<String> updated = new ArrayList<String>();
			
			for (String line : meta.getLore()){
				
				if (PlaceholderAPI.getPlaceholderPattern().matcher(line).find()) {
					
					contains = true;
					
					if (strip) {
						continue;
					} else {
						
						updated.add(PlaceholderAPI.setPlaceholders(p, line));
					}
					
				} else {
					updated.add(line);
				}
				
			}
			
			meta.setLore(updated);
		}
		
		item.setItemMeta(meta);
		
		packet.setSlotData(item);
		
		if (strip && contains) {
			p.getOpenInventory().setItem(packet.getSlot(), item);
		}
	}
}
