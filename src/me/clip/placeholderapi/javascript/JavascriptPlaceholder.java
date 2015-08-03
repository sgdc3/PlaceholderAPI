package me.clip.placeholderapi.javascript;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;

public class JavascriptPlaceholder {

	private String identifier;
	
	private String expression;
	
	private String trueResult;
	
	private String falseResult;
	
	private JavascriptReturnType type;
	
	private static ScriptEngine engine = null;
	
	public JavascriptPlaceholder(String identifier, JavascriptReturnType type, String expression, String trueResult, String falseResult) {
		
		if (type == null) {
			throw new IllegalArgumentException("Javascript placeholder type must either be 'boolean' or 'javascript'!");
		}
		
		this.type = type;
		
		if (identifier == null) {
			throw new IllegalArgumentException("Javascript placeholder identifier must not be null!");
		} else if (expression == null) {
			throw new IllegalArgumentException("Javascript placeholder expression must not be null!");
		}

		this.identifier = identifier;
		
		this.expression = expression;
		
		if (type == JavascriptReturnType.BOOLEAN) {
			
			if (trueResult == null) {
				throw new IllegalArgumentException("Javascript boolean placeholder must contain a true_result!");
			} else if (falseResult == null) {
				throw new IllegalArgumentException("Javascript boolean placeholder must contain a false_result!");
			}
			
			this.trueResult = trueResult;
			
			this.falseResult = falseResult;
			
		}
	}
	
	public static void cleanup() {
		engine = null;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public String getExpression() {
		return expression;
	}
	
	public String getTrueResult() {
		return trueResult;
	}
	
	public String getFalseResult() {
		return falseResult;
	}
	
	public JavascriptReturnType getType() {
		return type;
	}
	
	public String evaluate(Player p) {
		
		if (engine == null) {
			engine = new ScriptEngineManager().getEngineByName("javascript");
        	engine.put("BukkitServer", Bukkit.getServer());
		}
		
		String exp = PlaceholderAPI.setPlaceholders(p, expression);

        try {
        	
        	engine.put("BukkitPlayer", p);
        	
            Object result = engine.eval(exp);

            if (type == JavascriptReturnType.BOOLEAN) {
            	
            	if (!(result instanceof Boolean)) {
                 	return "invalid javascript";
                 }
                 
                 if ((boolean) result) {
                 	return PlaceholderAPI.setPlaceholders(p, trueResult);
                 } else {
                 	return PlaceholderAPI.setPlaceholders(p, falseResult);
                 }
            	
            } else {
            	            	
            	return result.toString();
            }
                        
        } catch (Exception ex) {
        	PlaceholderAPIPlugin.getInstance().log.severe("Error in javascript placeholder - " + this.identifier);
        	ex.printStackTrace();
        	return null;
        }
	}
}
