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
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {

	private PlaceholderAPIPlugin plugin;

	public VaultHook(PlaceholderAPIPlugin instance) {
		plugin = instance;
	}

	private static Permission perms = null;

	private static Economy econ = null;

	private static Chat chat = null;

	public void hook() {

		if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {

			if (setupEconomy()) {

				boolean ecoHooked = PlaceholderAPI.registerPlaceholderHook("Vaulteco", new PlaceholderHook() {

							@Override
							public String onPlaceholderRequest(Player p, String identifier) {

								if (identifier.equals("balance")) {
									return String.valueOf(getBalance(p));
								} else if (identifier.equals("balance_fixed")) {
									return rounded(getBalance(p));
								} else if (identifier.equals("balance_formatted")) {
									return formatted(getBalance(p));
								}

								return null;
							}

						}, true);

				if (ecoHooked) {
					plugin.log.info("Hooked into Vault for economy placeholders!");
				}
			}

			if (setupPerms()) {

				boolean hooked = PlaceholderAPI.registerPlaceholderHook("Vault",
						new PlaceholderHook() {	

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
						}, true);

				if (hooked) {
					plugin.log.info("Hooked into Vault for permissions placeholders!");
				}
			}
		}
	}
	
	private String rounded(double amt) {
		long send = (long) amt;
		return String.valueOf(send);
	}
	
	private String formatted(double amount) {

		if (amount >= 1000000000000000.0D) {
				
				String format = String.format("%.2f", new Object[] { Double.valueOf(amount / 1000000000000000.0D) });
				
				int p = format.indexOf(".");
				
				String p2 = format.substring(p+1);
					
				if (p2.endsWith("0")){
					if (p2.startsWith("0")) {
						format = format.substring(0, format.length()-3);
					}
					else {
						format = format.substring(0, format.length()-1);
					}
				}
				
				return format+" Quadrillion";
		}
		
		else if (amount >= 1000000000000.0D) {

				String format = String.format("%.2f", new Object[] { Double.valueOf(amount / 1000000000000.0D) });
				
				int p = format.indexOf(".");
				
				String p2 = format.substring(p+1);
					
				if (p2.endsWith("0")){
					
					if (p2.startsWith("0")) {
						format = format.substring(0, format.length()-3);
					}
					else {
						format = format.substring(0, format.length()-1);
					}
				}
				
				return format+" Trillion";
		}
		
		else if (amount >= 1000000000.0D) {

				String format = String.format("%.2f", new Object[] { Double.valueOf(amount / 1000000000.0D) });
				

				int p = format.indexOf(".");
				
				String p2 = format.substring(p+1);
					
				if (p2.endsWith("0")){
					
					if (p2.startsWith("0")) {
						format = format.substring(0, format.length()-3);
					}
					else {
						format = format.substring(0, format.length()-1);
					}
				}
				
				return format+" Billion";
		}
		
		else if (amount >= 1000000.0D) {

				String format = String.format("%.2f", new Object[] { Double.valueOf(amount / 1000000.0D) });
				
				int p = format.indexOf(".");
				
				String p2 = format.substring(p+1);
					
				if (p2.endsWith("0")){
					
					if (p2.startsWith("0")) {
						format = format.substring(0, format.length()-3);
					}
					else {
						format = format.substring(0, format.length()-1);
					}
				}
				
				return format+" Million";
		} 
		
		else if (amount >= 1000.0D) {

				String format = String.format("%.2f", new Object[] { Double.valueOf(amount / 1000.0D) });

				int p = format.indexOf(".");
				
				String p2 = format.substring(p+1);
					
				if (p2.endsWith("0")){
					
					if (p2.startsWith("0")) {
						format = format.substring(0, format.length()-3);
					}
					else {
						format = format.substring(0, format.length()-1);
					}
				
				return format+" Thousand";
			}
		}
		
		int amt = (int) amount;
		return String.valueOf(amt);
	}

	private boolean setupEconomy() {

		if (plugin.getConfig().getBoolean("hooks.vault_eco")) {

			RegisteredServiceProvider<Economy> rsp = this.plugin.getServer()
					.getServicesManager().getRegistration(Economy.class);

			if (rsp == null) {
				return false;
			}

			econ = rsp.getProvider();

			return econ != null;
		}

		return false;
	}

	private boolean setupPerms() {

		if (plugin.getConfig().getBoolean("hooks.vault_perms")) {

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
		return false;
	}

	public boolean useVaultChat() {
		if (chat != null) {
			return true;
		}
		return false;
	}

	public boolean useVaultEcon() {
		if (econ != null) {
			return true;
		}
		return false;
	}

	public boolean useVaultPerms() {
		if (perms != null) {
			return true;
		}
		return false;
	}

	public String getVaultVersion() {
		return Bukkit.getServer().getPluginManager().getPlugin("Vault")
				.getDescription().getVersion();
	}

	public String[] getGroups(Player p) {
		if (perms.getPlayerGroups(p) != null) {
			return perms.getPlayerGroups(p);
		}
		return new String[] { "" };
	}

	public double getBalance(OfflinePlayer p) {

		if (econ != null) {
			return econ.getBalance(p);
		}
		return 0;
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
			if (chat.getGroupPrefix(p.getWorld(), group) != null) {
				return String.valueOf(chat.getGroupPrefix(p.getWorld(), group).substring(0, 2));
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
