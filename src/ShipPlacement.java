
public class ShipPlacement {
	String shipName;
	int x, y; // coordinate, 0 based.
	Orientation orientation;
	
	public ShipPlacement(String name, int x, int y, Orientation orient) {
		this.shipName = name;
		this.x = x;
		this.y =y;
		this.orientation = orient;
	}
}
