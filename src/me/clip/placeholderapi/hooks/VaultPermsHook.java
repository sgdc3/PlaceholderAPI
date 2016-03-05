/* This file is a class of EZRanksLite
 * @author extended_clip
 * 
 * 
 * EZRanksLite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 
 * EZRanksLite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultPermsHook extends IPlaceholderHook {

	public VaultPermsHook(InternalHook hook) {
		super(hook);
	}

	private Permission perms = null;
	
	private Chat chat = null;

	@Override
	public boolean hook() {

		boolean hooked = false;
		
		if (setupPerms()) {

			hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
		}
		return hooked;
	}

@Override
public String onPlaceholderRequest(Player p, String identifier) {
	
	if (identifier.startsWith("rankprefix_")) {
		
		int i = 1;
		
		try {
			i = Integer.parseInt(identifier.split("rankprefix_")[1]);
		} catch (Exception e) {
			
		}
		
		return getGroupPrefix(p, i);
	} else if (identifier.startsWith("ranksuffix_")) {
		
		int i = 1;
		
		try {
			i = Integer.parseInt(identifier.split("ranksuffix_")[1]);
		} catch (Exception e) {
			
		}
		
		return getGroupSuffix(p, i);
	} else if (identifier.startsWith("groupprefix_")) {
		
		int i = 1;
		
		try {
			i = Integer.parseInt(identifier.split("groupprefix_")[1]);
		} catch (Exception e) {
			
		}
		
		return getGroupPrefix(p, i);
	} else if (identifier.startsWith("groupsuffix_")) {
		
		int i = 1;
		
		try {
			i = Integer.parseInt(identifier.split("groupsuffix_")[1]);
		} catch (Exception e) {
			
		}
		
		return getGroupSuffix(p, i);
	}
	
	switch (identifier) {
	
	case "group":
	case "rank":
		return getMainGroup(p) != null ? getMainGroup(p) : "";
	case "prefix":
		return getPlayerPrefix(p) != null ? getPlayerPrefix(p) : "";
	case "groupprefix":
	case "rankprefix":
		return getGroupPrefix(p) != null ? getGroupPrefix(p) : "";
	case "suffix":
		return getPlayerSuffix(p) != null ? getPlayerSuffix(p) : "";
	case "groupsuffix":
	case "ranksuffix":
		return getGroupSuffix(p) != null ? getGroupSuffix(p) : "";
	case "prefix_color":
		return getGroupPrefixColor(p) != null ? getGroupPrefixColor(p) : "";
	}
	return null;
}


	private boolean setupPerms() {

		RegisteredServiceProvider<Permission> permsProvider = Bukkit
				.getServer().getServicesManager()
				.getRegistration(Permission.class);

		if (permsProvider != null && permsProvider.getPlugin() != null) {
			perms = permsProvider.getProvider();
		}

		RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer()
				.getServicesManager().getRegistration(Chat.class);

		if (chatProvider != null && chatProvider.getPlugin() != null) {
			chat = chatProvider.getProvider();
		}

		return perms != null && chat != null;
	}

	public String[] getGroups(Player p) {
		if (perms.getPlayerGroups(p) != null) {
			return perms.getPlayerGroups(p);
		}
		return new String[] { "" };
	}

	public String getMainGroup(Player p) {
		if (perms.getPrimaryGroup(p) != null) {
			return String.valueOf(perms.getPrimaryGroup(p));
		}
		return "";
	}

	public boolean opHasPermission(Player p, String perm) {
		if (perms.getPrimaryGroup(p) != null) {

			return perms.groupHas(p.getWorld(), perms.getPrimaryGroup(p), perm);
		}
		return false;
	}

	public boolean addPerm(Player p, String perm) {
		return perms.playerAdd(p, perm);
	}

	public String getPlayerPrefix(Player p) {
		if (chat.getPlayerPrefix(p) != null) {
			return String.valueOf(chat.getPlayerPrefix(p));
		}
		return "";
	}

	public String getPlayerSuffix(Player p) {
		if (chat.getPlayerSuffix(p) != null) {
			return String.valueOf(chat.getPlayerSuffix(p));
		}
		return "";
	}
	
	public String getGroupSuffix(Player p) {
		if (perms.getPrimaryGroup(p) == null) {
			return "";
		}
		
		if (chat.getGroupSuffix(p.getWorld(), perms.getPrimaryGroup(p)) != null) {
			return String.valueOf(chat.getGroupSuffix(p.getWorld(), perms.getPrimaryGroup(p)));
		}
		return "";
	}
	
	public String getGroupPrefix(Player p) {
		if (perms.getPrimaryGroup(p) == null) {
			return "";
		}
		
		if (chat.getGroupPrefix(p.getWorld(), perms.getPrimaryGroup(p)) != null) {
			return String.valueOf(chat.getGroupPrefix(p.getWorld(), perms.getPrimaryGroup(p)));
		}

		return "";
	}
	
	public String getGroupSuffix(Player p, int i) {
		
		if (perms.getPlayerGroups(p) == null) {
			return "";
		}
		
		String[] groups = perms.getPlayerGroups(p);
		if (i > groups.length) {
			return "";
		}
		
		int count = 1;
		
		for (String group : groups) {
			
			if (count < i) {
				count++;
				continue;
			}
			
			if (chat.getGroupSuffix(p.getWorld(), group) != null) {
				return String.valueOf(chat.getGroupSuffix(p.getWorld(), group));
			}
		}
		
		return "";
	}
	
	public String getGroupPrefix(Player p, int i) {
		
		if (perms.getPlayerGroups(p) == null) {
			return "";
		}
		
		String[] groups = perms.getPlayerGroups(p);
		if (i > groups.length) {
			return "";
		}
		
		int count = 1;
		for (String group : groups) {
			
			if (count < i) {
				count++;
				continue;
			}
			
			if (chat.getGroupPrefix(p.getWorld(), group) != null) {
				return String.valueOf(chat.getGroupPrefix(p.getWorld(), group));
			}
		}
		
		return "";
	}
	
	public String getGroupPrefixColor(Player p) {
		if (perms.getPlayerGroups(p) == null) {
			return "";
		}
		
		for (String group : perms.getPlayerGroups(p)) {
			String pre = chat.getGroupPrefix(p.getWorld(), group);
			if (pre != null && pre.length() >= 2) {
				pre = ChatColor.translateAlternateColorCodes('&', pre);
				if (pre.charAt(0) == ChatColor.COLOR_CHAR) {
						return pre.substring(0, 1);
				}
			}
		}
		return "";
	}

	public boolean hasPerm(Player p, String perm) {
		if (perms != null) {
			return perms.has(p, perm);
		}
		return p.hasPermission(perm);
	}

	public String[] getServerGroups() {
		if (perms.getGroups() != null) {
			return perms.getGroups();
		}
		return new String[] { "" };

	}

}
