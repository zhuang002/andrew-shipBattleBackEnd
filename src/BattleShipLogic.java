import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/***
 * The backend class
 * @author zhuan
 *
 */
public class BattleShipLogic {

	public static BattleShipLogic instance= null;
	private BattleShipController controller;
	private Random rand = new Random();
	
	GridStatus[][] myGrid = new GridStatus[10][10];
	GridStatus[][] rivalGrid = new GridStatus[10][10];
	
	HashMap<ShipTypes, Integer> rivalShipHits = new HashMap<>();
	HashMap<ShipTypes, Integer> myShipHits = new HashMap<>();
	
	HashMap<Coordinate, ShipTypes> rivalShips = new HashMap<>();
	HashMap<ShipTypes, List<Coordinate>> rivalShipCoordinates = new HashMap<>();
	
	HashMap<Coordinate, ShipTypes> myShips = new HashMap<>();
	HashMap<ShipTypes, List<Coordinate>> myShipCoordinates = new HashMap<>();
	
	HashMap<ShipTypes, Integer> shipLengths = new HashMap<>();
	
	
	public BattleShipLogic() {
		
		shipLengths.put(ShipTypes.Carrier, 5);
		shipLengths.put(ShipTypes.BattleShip, 4);
		shipLengths.put(ShipTypes.Cruiser, 3);
		shipLengths.put(ShipTypes.Submarine, 3);
		shipLengths.put(ShipTypes.Destroyer, 2);
		clear();
	}
	
	/***
	 * Clear all internal data
	 */
	private void clear() {
		// TODO Auto-generated method stub
		for (int i=0;i<10;i++) {
			for (int j=0;j<10;j++) {
				myGrid[i][j] = GridStatus.Default;
				rivalGrid[i][j] = GridStatus.Default;
			}
		}
		
		rivalShipHits.put(ShipTypes.Carrier,0);
		rivalShipHits.put(ShipTypes.BattleShip,0);
		rivalShipHits.put(ShipTypes.Cruiser,0);
		rivalShipHits.put(ShipTypes.Submarine,0);
		rivalShipHits.put(ShipTypes.Destroyer,0);
		
		myShipHits.put(ShipTypes.Carrier,0);
		myShipHits.put(ShipTypes.BattleShip,0);
		myShipHits.put(ShipTypes.Cruiser,0);
		myShipHits.put(ShipTypes.Submarine,0);
		myShipHits.put(ShipTypes.Destroyer,0);
		
		rivalShips.clear();
		myShips.clear();
		rivalShipCoordinates.clear();
		myShipCoordinates.clear();
		
	}

	/***
	 * get the only instance of this class
	 * @return the only instance of backend.
	 */
	public static BattleShipLogic getInstance() {
		// TODO Auto-generated method stub
		if (instance == null) {
			instance = new BattleShipLogic();
		}
		return instance;
	}
	
	/***
	 * get the only instance of this class and set the controller
	 * @param controller the GUI controller
	 * @return the only instance.
	 */
	public static BattleShipLogic getInstance(BattleShipController controller) {
		// TODO Auto-generated method stub
		if (instance == null) {
			instance = new BattleShipLogic();
			instance.controller = controller;
		}
		return instance;
	}
	
	
/***
 * the backend action to be called by GUI when toss button is clicked.
 */
	public void toss() { // 1 or raval play first. 0 for me to play first
		// TODO Auto-generated method stub
		if (rand.nextInt(2) == 1) {
			rivalFire();
			controller.updateRivalField(myGrid);
		}
	}


	private void rivalFire() {
		// TODO Auto-generated method stub
		int index = rand.nextInt(100);
		int x = index / 10;
		int y = index % 10;
		while (myGrid[x][y] != GridStatus.Default && myGrid[x][y]!=GridStatus.PartOfShip) {
			index = rand.nextInt(100);
			x = index / 10;
			y = index % 10;
		} 
		
		fire(myGrid, myShips, myShipHits, myShipCoordinates, x, y);
		
		
		
	}


	private void fire(GridStatus[][] grid, HashMap<Coordinate, ShipTypes> ships,
			HashMap<ShipTypes, Integer> shipHits, HashMap<ShipTypes, List<Coordinate>> shipCoordinates, int x,  int y) {
		// TODO Auto-generated method stub
		if (grid[x][y] == GridStatus.Default) {
			grid[x][y] = GridStatus.Miss;
		} else {
			grid[x][y] = GridStatus.Hit;
			ShipTypes ship = ships.get(new Coordinate(x,y));
			int hits = shipHits.get(ship);
			hits++;
			myShipHits.put(ship, hits);
			if (hits>=shipLengths.get(ship)) { // this ship should be sunk
				List<Coordinate> coords = shipCoordinates.get(ship);
				for (Coordinate coordinate: coords) {
					myGrid[coordinate.x][coordinate.y] = GridStatus.Sunk;
				}
			}
		}
	}


	// This is to set myGrid which is called by GUI
	/***
	 * Set the ship information for the player.
	 * @param ships the shipPlacements set by the player
	 * @throws InvalidShipPlacementException throws this exception when the user input data is not valid.
	 */
	public void setShipPlacements(List<ShipPlacement> ships) throws InvalidShipPlacementException {
		// TODO Auto-generated method stub
		setShipPlacements(ships, myGrid, myShips, myShipCoordinates);
		controller.updateMyField(myGrid);
		
	}

	
	// this is general 
	private void setShipPlacements(List<ShipPlacement> shipPlacements, GridStatus[][] grid,
			HashMap<Coordinate, ShipTypes> ships, HashMap<ShipTypes, List<Coordinate>> shipCoordinates) throws InvalidShipPlacementException {
		// TODO Auto-generated method stub
		
		for (ShipPlacement shipPlace:shipPlacements) {
			placeShip(shipPlace, grid, ships, shipCoordinates);
		}
		
	}

	

	private void placeShip(ShipPlacement shipPlace, GridStatus[][] grid, HashMap<Coordinate, ShipTypes> ships,
			HashMap<ShipTypes, List<Coordinate>> shipCoordinates) throws InvalidShipPlacementException {
		// TODO Auto-generated method stub
		
		ShipTypes ship = getShipTypeFromSName(shipPlace.shipName);
		int shipLength = shipLengths.get(ship);
		int x=0;
		int y=0;
		if (shipPlace.orientation == Orientation.Horizontal) {
			
			for (int i=0;i<shipLength;i++) {
				x = shipPlace.x;
				y = shipPlace.y+i;
				
			}
		} else {
			for (int i=0;i<shipLength;i++) {
				x = shipPlace.x +i;
				y = shipPlace.y;
			}
		}
		
		if (x>=0 && y>=0 && x<10 && y<10 && grid[x][y] == GridStatus.Default) {
			grid[x][y] = GridStatus.PartOfShip;
			Coordinate coord =new Coordinate(x,y);
			ships.put(coord , ship);
			List<Coordinate> coordinates = null;
			if (!shipCoordinates.containsKey(ship)) {
				coordinates = new ArrayList<Coordinate>();
				shipCoordinates.put(ship, coordinates);
			}
			
			coordinates = shipCoordinates.get(ship);
			if (coordinates == null) {
				coordinates = new ArrayList<Coordinate>();
				shipCoordinates.put(ship, coordinates);
			}
			
			coordinates.add(coord);
		} else {
			throw new InvalidShipPlacementException();
		}
	}

	private ShipTypes getShipTypeFromSName(String shipName) {
		// TODO Auto-generated method stub
		if (shipName.equals("Carrier")) return ShipTypes.Carrier;
		if (shipName.equals("BattleShip")) return ShipTypes.BattleShip;
		if (shipName.equals("Cruiser")) return ShipTypes.Cruiser;
		if (shipName.equals("Submarine")) return ShipTypes.Submarine;
		if (shipName.equals("Destroyer")) return ShipTypes.Destroyer;
		return null;
	}

	/*public GridStatus[][] getGridData(String string) {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	/***
	 * return the GUI controller.
	 * @return the GUI controller.
	 */
	public BattleShipController getGuiController() {
		return this.controller;
	}

	// fire the rival. This is called by GUI.
	/***
	 * This is the back end function called by GUI
	 * @param x the player fired rival coordinate x
	 * @param y the player fired rival coordinate y
	 */
	public void fire(int x, int y) {
		// TODO Auto-generated method stub
		fire(rivalGrid, rivalShips, rivalShipHits, rivalShipCoordinates, x, y);
	}

	/***
	 * This is the AI part to place the computer ships 
	 * @throws InvalidShipPlacementException
	 */
	public void placeRivalShips() throws InvalidShipPlacementException {
		// TODO Auto-generated method stub
		List<ShipPlacement> placements = randomShipPlacementForRival();
		setShipPlacements(placements, rivalGrid,rivalShips,rivalShipCoordinates);
		this.controller.updateRivalField(rivalGrid);
	}

	
	// auto placement for rival ships. make sure the placement is valid.
	private List<ShipPlacement> randomShipPlacementForRival() {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * the action called by GUI when reset button is called.
	 */
	public void reset() {
		// TODO Auto-generated method stub
		clear();
		controller.updateMyField(myGrid);
		controller.updateRivalField(rivalGrid);
	}

}
