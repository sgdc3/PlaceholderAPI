package me.clip.placeholderapi.expansion;

/**
 * Placeholder expansions can implement this class to perform tasks when the expansion
 * is enabled or disabled
 * @author Ryan McCarthy
 *
 */
public interface Taskable {
	
	/**
	 * Called when the implementing class has successfully registered an expansion
	 * Tasks that need to be performed when this expansion is registered should go here
	 */
	public void start();
	
	/**
	 * Called when the implementing class has been unregistered from PlaceholderAPI
	 * Tasks that need to be performed when this expansion has unregistered should go here
	 */
	public void stop();
}
