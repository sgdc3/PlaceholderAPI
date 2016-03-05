package me.clip.placeholderapi.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultEcoHook extends IPlaceholderHook {

	public VaultEcoHook(InternalHook hook) {
		super(hook);
	}

	private Economy econ = null;


	@Override
	public boolean hook() {

		boolean hooked = false;
		
		if (setupEconomy()) {

			hooked = PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
		}
		return hooked;
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (p == null) {
			return "";
		}

		if (identifier.equals("balance")) {
			return String.valueOf(getBalance(p));
		} else if (identifier.equals("balance_fixed")) {
			return rounded(getBalance(p));
		} else if (identifier.equals("balance_formatted")) {
			return formatted(getBalance(p));
		}

		return null;
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

		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer()
				.getServicesManager().getRegistration(Economy.class);

		if (rsp == null) {
			return false;
		}

		econ = rsp.getProvider();

		return econ != null;

	}



	public double getBalance(OfflinePlayer p) {

		if (econ != null) {
			return econ.getBalance(p);
		}
		return 0;
	}





}
