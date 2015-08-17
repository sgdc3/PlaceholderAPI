package me.clip.placeholderapi.hooks;

import java.util.List;

import me.clip.placeholderapi.internal.IPlaceholderHook;
import me.clip.placeholderapi.internal.InternalHook;

import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.object.TownyUniverse;

public class TownyHook extends IPlaceholderHook {
	
	public TownyHook(InternalHook hook) {
		super(hook);
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}

		switch (identifier) {
		
		case "town":
			return getPlayersTown(p);
		case "friends":
			return getPlayersFriends(p);
		case "nation":
			return getPlayersNation(p);
		case "title":
			return getPlayersTownyTitle(p);
		case "town_residents":
			return getTownResidents(p);
		case "town_size":
			return getTownSize(p);
		case "town_tag":
			return getTownTag(p);
		case "town_balance":
			return getTownBankBalance(p);
		case "town_mayor":
			return getTownMayor(p);
		case "surname":
			return getPlayersSurname(p);
		case "town_rank":
			return getTownRank(p);
		case "nation_rank":
			return getNationRank(p);
		}	
		
		return null;
	}

	private String getPlayersTown(Player p) {
		
		String town = "";
		
		try {
			
			town = String.valueOf(TownyUniverse.getDataSource().getResident(p.getName()).getTown().getName());
		
		} catch (Exception e) { }
		return town;
	}
	
	private String getPlayersFriends(Player p) {
		String res = "";
		try {
			res = String.valueOf(TownyUniverse.getDataSource()
					.getResident(p.getName()).getFriends().size());
		} catch (Exception e) {
			return "";
		}
		return res;
	}
	
	private String getPlayersNation(Player p) {
		String nation = "";
		 try
	      {
	        nation = String.valueOf(TownyUniverse.getDataSource().getResident(p.getName()).getTown().getNation().getName());
	      }
	      catch (Exception e)
	      {
	       return "";
	      }
		return nation;
	}
	
	private String getPlayersTownyTitle(Player p) {
		String title = "";
		 try
	      {
	        title = String.valueOf(TownyUniverse.getDataSource().getResident(p.getName()).getTitle());
	      }
	      catch (Exception e) {
	       return "";
	      }
		return title;
	}
	
	private String getPlayersSurname(Player p) {
		String title = "";
		 try {
	        title = String.valueOf(TownyUniverse.getDataSource().getResident(p.getName()).getSurname());
	      }
	      catch (Exception e) {
	       return "";
	      }
		return title;
	}
	
	private String getTownResidents(Player p) {
		String res = "";
		 try {
	        res = String.valueOf(TownyUniverse.getDataSource().getResident(p.getName()).getTown().getNumResidents());
	      }
	      catch (Exception e) {
	       return "";
	      }
		return res;
	}
	
	private String getTownBankBalance(Player p) {
		String bal = "";
		 try {
	        bal = String.valueOf(TownyUniverse.getDataSource().getResident(p.getName()).getTown().getHoldingBalance());
	      }
	      catch (Exception e) {
	       return "";
	      }
		return bal;
	}
	
	private String getTownMayor(Player p) {
		String mayor = "";
		 try
	      {
			 mayor = String.valueOf(TownyUniverse.getDataSource().getResident(p.getName()).getTown().getMayor().getName());
	      }
	      catch (Exception e)
	      {
	       return "";
	      }
		return mayor;
	}
	
	private String getTownRank(Player p) {
		
		String rank = "";
		
		 try {
			 
			 List<String> ranks = TownyUniverse.getDataSource().getResident(p.getName()).getTownRanks();
			 
			 if (ranks != null && !ranks.isEmpty()) {
				 
				 for (String r : ranks) {
					 
					 if (TownyUniverse.getDataSource().getResident(p.getName()).hasTownRank(r)) {
						 //return first rank they have
						 rank = r;
						 break;
					 }
				 }
			 }
			
	      }
	      catch (Exception e)
	      {
	       return "";
	      }
		return rank;
	}
	
	private String getNationRank(Player p) {
		
		String rank = "";
		
		 try {
			 
			 List<String> ranks = TownyUniverse.getDataSource().getResident(p.getName()).getNationRanks();
			 
			 if (ranks != null && !ranks.isEmpty()) {
				 
				 for (String r : ranks) {
					 
					 if (TownyUniverse.getDataSource().getResident(p.getName()).hasNationRank(r)) {
						 //return first rank they have
						 rank = r;
						 break;
					 }
				 }
			 }
			
	      }
	      catch (Exception e)
	      {
	       return "";
	      }
		return rank;
	}
	
	private String getTownSize(Player p) {
		String size = "";
		 try
	      {
			 size = String.valueOf(TownyUniverse.getDataSource().getResident(p.getName()).getTown().getTotalBlocks());
	      }
	      catch (Exception e)
	      {
	       return "";
	      }
		return size;
	}
	
	private String getTownTag(Player p) {
		String tag = "";
		 try
	      {
			 tag = String.valueOf(TownyUniverse.getDataSource().getResident(p.getName()).getTown().getTag());
	      }
	      catch (Exception e)
	      {
	       return "";
	      }
		return tag;
	}

}
