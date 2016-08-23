package me.clip.placeholderapi.expansion;

/**
 * Placeholder expansions which use NMS code should be version specific.
 * Implementing this class allows you to set the nms compatible version for this specific
 * expansion. The version will be compared before the expansion attempts to register with PlaceholderAPI.
 * @author Ryan McCarthy
 *
 */
public interface VersionSpecific {

	/**
	 * Get the NMS version set for this placeholder expansion
	 * 
	 * @return {@link NMSVersion} specific to the implementing expansion
	 */
	public NMSVersion getVersion();
}
