
public class ShipPlacement {
	ShipTypes shipName;
	int x, y; // coordinate, 0 based.
	Orientation orientation;
	
	public ShipPlacement(ShipTypes name, int x, int y, Orientation orient) {
		this.shipName = name;
		this.x = x;
		this.y =y;
		this.orientation = orient;
	}
}
