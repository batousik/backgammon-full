package gui;

/**
 * 
 * @author 130017964
 * @version 4.20(release)
 *
 * Class allows to store zones for dropping objects for GUI Backgammon RUS 4.20
 */
public class Zone {
	public final int LEFT;
	public final int RIGHT;
	public final int TOP;
	public final int BOTTOM;
	
	/**
	 * 
	 * @param left set left boundary of the zone 
	 * @param right set right boundary of the zone
	 * @param top set top boundary of the zone
	 * @param bottom set bottom boundary of the zone
	 */
	public Zone(int left,int right,int top,int bottom) {
		LEFT = left;
		RIGHT = right;
		TOP = top;
		BOTTOM = bottom;
	}
	
	/**
	 * 
	 * @param topLeftX top left corner X coordinate of an object
	 * @param topLeftY top left corner Y coordinate of an object
	 * @return true if object is in the zone, false otherwise
	 */
	public boolean isInZone(int topLeftX, int topLeftY) {
		if (LEFT <= topLeftX && topLeftX <= RIGHT && 
				TOP <=  topLeftY && topLeftY <= BOTTOM)
			return true;
		return false;
	}
}
