package me.clip.placeholderapi.placeholders;

import java.util.ArrayList;
import java.util.List;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryPlaceholders extends IPlaceholderHook {

	public InventoryPlaceholders(InternalHook hook) {
		super(hook);
	}
	
	private boolean isInt(String s) {
		try {
			Integer.parseInt(s);
		} catch(Exception e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {


		if (p == null) {
			return "";
		}
		
		String[] parts = identifier.split("_");
		
		Material m = null;
		int amt = 1;
		short data = 0;
		String name = null;
		List<String> lore = null;
		
		for (String part : parts) {
			
			//material
			if (part.startsWith("m:")) {
				part = part.replace("m:", "");
				
				if (isInt(part)) {
					m = Material.getMaterial(Integer.parseInt(part));
				} else {
					m = Material.getMaterial(part.toUpperCase());
				}
				
				if (m == null) {
					return null;
				}
			} else if (part.startsWith("a:")) {
				
				part = part.replace("a:", "");
				
				if (isInt(part)) {
					amt = Integer.parseInt(part);
				} else {
					return null;
				}
			} else if (part.startsWith("d:")) {
				
				part = part.replace("d:", "");
				
				if (isInt(part)) {
					data = (short) Integer.parseInt(part);
				}
			} else if (part.startsWith("n:")) {
				
				part = part.replace("n:", "");
				
				if (part.contains("-")) {
					part = part.replace("-", " ");
				}
				
				part = ChatColor.translateAlternateColorCodes('&', part);
				name = part;
			} else if (part.startsWith("l:")) {
				
				part = part.replace("l:", "");
				part = ChatColor.translateAlternateColorCodes('&', part);
				if (part.contains("-")) {
					part = part.replace("-", " ");
				}
				lore = new ArrayList<String>();
				
				if (part.contains(",")) {
					String[] loreParts = part.split(",");
					for (String l : loreParts) {
						lore.add(l);
					}
				}
			}
		}
		
		ItemStack i = new ItemStack(m, amt);
		
		if (data > 0) {
			i.setDurability(data);
		}
		
		ItemMeta meta = i.getItemMeta();
		
		if (name != null) {
			meta.setDisplayName(name);
		}
		
		if (lore != null) {
			meta.setLore(lore);
		}
		
		i.setItemMeta(meta);
		
		return p.getInventory().containsAtLeast(i, amt) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
	
	}
}