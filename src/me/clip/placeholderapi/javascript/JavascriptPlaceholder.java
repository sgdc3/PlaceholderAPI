package me.clip.placeholderapi.javascript;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class JavascriptPlaceholder {

	private String identifier;
	
	private String expression;
	
	private String trueResult;
	
	private String falseResult;
	
	private static ScriptEngine engine = null;
	
	public JavascriptPlaceholder(String identifier, String expression, String trueResult, String falseResult) {
		
		if (identifier == null) {
			throw new IllegalArgumentException("Javascript placeholder identifier must not be null!");
		} else if (expression == null) {
			throw new IllegalArgumentException("Javascript placeholder expression must not be null!");
		} else if (trueResult == null) {
			throw new IllegalArgumentException("Javascript placeholder true result must not be null!");
		} else if (falseResult == null) {
			throw new IllegalArgumentException("Javascript placeholder false result must not be null!");
		}
		
		this.identifier = identifier;
		
		this.expression = expression;
		
		this.trueResult = trueResult;
		
		this.falseResult = falseResult;
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
	
	public String evaluate(Player p) {
		
		if (engine == null) {
			engine = new ScriptEngineManager().getEngineByName("javascript");
		}
		
		String exp = PlaceholderAPI.setPlaceholders(p, expression);

        try {
        	
            Object result = engine.eval(exp);

            if (!(result instanceof Boolean)) {
            	return "invalid javascript";
            }
            
            if ((boolean) result) {
            	return PlaceholderAPI.setPlaceholders(p, trueResult);
            } else {
            	return PlaceholderAPI.setPlaceholders(p, falseResult);
            }
            
        } catch (Exception ex) {
           return null;
        }
	}
}
