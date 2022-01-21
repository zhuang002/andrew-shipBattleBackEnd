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
	
	ArrayList<ArrayList<ShipPlacement>> rivalShipPlacementOptions = new ArrayList<>();
	
	public BattleShipLogic() {
		
		shipLengths.put(ShipTypes.Carrier, 5);
		shipLengths.put(ShipTypes.BattleShip, 4);
		shipLengths.put(ShipTypes.Cruiser, 3);
		shipLengths.put(ShipTypes.Submarine, 3);
		shipLengths.put(ShipTypes.Destroyer, 2);
		
		initializeRivalShipPlacement();
		
		
		
		clear();
	}
	
	private void initializeRivalShipPlacement() {
		// TODO Auto-generated method stub
		ArrayList<ShipPlacement> option = new ArrayList<>();
		option.add(new ShipPlacement(ShipTypes.Carrier, 0, 0, Orientation.Horizontal));
		option.add(new ShipPlacement(ShipTypes.BattleShip, 2, 1, Orientation.Horizontal));
		option.add(new ShipPlacement(ShipTypes.Cruiser, 4, 2, Orientation.Horizontal));
		option.add(new ShipPlacement(ShipTypes.Submarine, 6, 2, Orientation.Horizontal));
		option.add(new ShipPlacement(ShipTypes.Destroyer, 8, 3, Orientation.Horizontal));
		rivalShipPlacementOptions.add(option);
		
		option = new ArrayList<>();
		option.add(new ShipPlacement(ShipTypes.Carrier, 1, 2, Orientation.Vertical));
		option.add(new ShipPlacement(ShipTypes.BattleShip, 5, 1, Orientation.Vertical));
		option.add(new ShipPlacement(ShipTypes.Cruiser, 4, 0, Orientation.Vertical));
		option.add(new ShipPlacement(ShipTypes.Submarine, 6, 2, Orientation.Vertical));
		option.add(new ShipPlacement(ShipTypes.Destroyer, 1, 1, Orientation.Vertical));
		rivalShipPlacementOptions.add(option);
		
		option = new ArrayList<>();
		option.add(new ShipPlacement(ShipTypes.Carrier, 0, 2, Orientation.Horizontal));
		option.add(new ShipPlacement(ShipTypes.BattleShip, 2, 0, Orientation.Vertical));
		option.add(new ShipPlacement(ShipTypes.Cruiser, 4, 2, Orientation.Horizontal));
		option.add(new ShipPlacement(ShipTypes.Submarine, 4, 6, Orientation.Vertical));
		option.add(new ShipPlacement(ShipTypes.Destroyer, 2, 7, Orientation.Horizontal));
		rivalShipPlacementOptions.add(option);
		
		option = new ArrayList<>();
		option.add(new ShipPlacement(ShipTypes.Carrier, 0, 2, Orientation.Vertical));
		option.add(new ShipPlacement(ShipTypes.BattleShip, 6, 2, Orientation.Vertical));
		option.add(new ShipPlacement(ShipTypes.Cruiser, 6, 4, Orientation.Vertical));
		option.add(new ShipPlacement(ShipTypes.Submarine, 1, 6, Orientation.Vertical));
		option.add(new ShipPlacement(ShipTypes.Destroyer, 6, 7, Orientation.Horizontal));
		rivalShipPlacementOptions.add(option);
		
		option = new ArrayList<>();
		option.add(new ShipPlacement(ShipTypes.Carrier, 2, 2, Orientation.Horizontal));
		option.add(new ShipPlacement(ShipTypes.BattleShip, 6, 6, Orientation.Horizontal));
		option.add(new ShipPlacement(ShipTypes.Cruiser, 1, 7, Orientation.Vertical));
		option.add(new ShipPlacement(ShipTypes.Submarine, 6, 4, Orientation.Vertical));
		option.add(new ShipPlacement(ShipTypes.Destroyer, 0, 4, Orientation.Horizontal));
		rivalShipPlacementOptions.add(option);
		
		option = new ArrayList<>();
		option.add(new ShipPlacement(ShipTypes.Carrier, 0, 7, Orientation.Vertical));
		option.add(new ShipPlacement(ShipTypes.BattleShip, 7, 3, Orientation.Horizontal));
		option.add(new ShipPlacement(ShipTypes.Cruiser, 3, 4, Orientation.Vertical));
		option.add(new ShipPlacement(ShipTypes.Submarine, 6, 7, Orientation.Vertical));
		option.add(new ShipPlacement(ShipTypes.Destroyer, 8, 3, Orientation.Horizontal));
		rivalShipPlacementOptions.add(option);
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
		
		boolean gameOver = fire(myGrid, myShips, myShipHits, myShipCoordinates, x, y);
		controller.updateMyField(myGrid);
		
		if (gameOver) {
			controller.gameFinished(1);
		}
	}


	private boolean fire(GridStatus[][] grid, HashMap<Coordinate, ShipTypes> ships,
			HashMap<ShipTypes, Integer> shipHits, HashMap<ShipTypes, List<Coordinate>> shipCoordinates, int x,  int y) {
		// TODO Auto-generated method stub
		
		if (grid[x][y] == GridStatus.Default) {
			grid[x][y] = GridStatus.Miss;
		} else {
			grid[x][y] = GridStatus.Hit;
			ShipTypes ship = ships.get(new Coordinate(x,y));
			int hits = shipHits.get(ship);
			hits++;
			shipHits.put(ship, hits);
			if (hits>=shipLengths.get(ship)) { // this ship should be sunk
				List<Coordinate> coords = shipCoordinates.get(ship);
				for (Coordinate coordinate: coords) {
					grid[coordinate.x][coordinate.y] = GridStatus.Sunk;
				}
				// check if all ships are sunk
				
				for (ShipTypes st:shipHits.keySet()) {
					if (shipHits.get(st)<shipLengths.get(st)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
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
		
		ShipTypes ship = shipPlace.shipName;
		int shipLength = shipLengths.get(ship);
		int x=0;
		int y=0;
		
		for (int i=0;i<shipLength;i++) {
			if (shipPlace.orientation == Orientation.Horizontal) {
				x = shipPlace.x;
				y = shipPlace.y+i;
			} else {
				x = shipPlace.x +i;
				y = shipPlace.y;
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
		boolean gameOver = fire(rivalGrid, rivalShips, rivalShipHits, rivalShipCoordinates, x, y);
		controller.updateRivalField(rivalGrid);
		if (gameOver) {
			controller.gameFinished(0);
		} else {
			rivalFire();
		}
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
		int i = rand.nextInt(this.rivalShipPlacementOptions.size());
		return this.rivalShipPlacementOptions.get(i);
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
