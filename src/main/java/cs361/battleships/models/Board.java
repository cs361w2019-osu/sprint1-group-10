package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Board {
	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Result> attacks;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		//FIXME remove debug
		System.out.println("Hello!");
		ships = new ArrayList<>();
		attacks = new ArrayList<>();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		// FIXME duplcate checking, overlap checking, and remvoe debug statement
		System.out.print("Creating a ship with length: ");
		System.out.println(ship.getLen());
		for(int i=0;i<this.ships.size();i++){// Dupe check
			if(this.ships.get(i).getLen() == ship.getLen()){
				return false;// Ship has already been placed
			}
		}
		if(updateShip(ship,x,y,isVertical)){// Try to update the ships location if it works add it
			ships.add(ship);
			System.out.println("Created a new ship");
			return true;
		}
		else{// Otherwise error
			return false;
		}
	}
	/*
	updateShip:
	The goal of this function is to add all the squares that a ship would occupied with the left most block being 0,0
	Returns false if the ship would be off the board
	 */
	private boolean updateShip(Ship ship, int x, char y,boolean isVertical){
		if(isVertical){
			if(x+ship.getLen() > 10){// Check to make sure we aren't off the board
				return false;
			}
			for(int i=0;i<ship.getLen();i++){// Add each square to the ship object
				ship.addOccupiedSquare(x,y);
				//FIXME remove debug
				System.out.print("Added square: ");
				System.out.print(x);
				System.out.println(y);
				x++;
			}
		}
		else {
			if(y+ship.getLen() > 'J'){// Check to make sure we aren't off the board
				return false;
			}
			for(int i=0;i<ship.getLen();i++){// Add each square to the ship object
				ship.addOccupiedSquare(x,y);
				//FIXME remove debug
				System.out.print("Added square: ");
				System.out.print(x);
				System.out.println(y);
				y++;
			}

		}
		// Overlap check
		for(int i=0;i<this.ships.size();i++){// Grab every ship
			for(int j=0;i<this.ships.get(i).getOccupiedSquares().size();j++){// Grab all their squares
				for(int k=0;k<ship.getOccupiedSquares().size();k++){// Grab all of our squares
					// Compare
					if(ship.getOccupiedSquares().get(k).getColumn() == this.ships.get(i).getOccupiedSquares().get(j).getColumn() && ship.getOccupiedSquares().get(k).getRow() == this.ships.get(i).getOccupiedSquares().get(j).getRow()){
						ship.cleanOccupiedSquares();
						return false;
					}
				}
			}
		}
		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		//TODO Implement
		int totalHits = 0;
		//Create a new result and initialise it to the default variables
		Result output = new Result();
		output.setLocation(new Square(x,y));
		output.setResult(AtackStatus.INVALID);

		//Check for a hit
		for(int i=0;i<this.ships.size();i++){// Go through each ship
			List<Square> testMe = ships.get(i).getOccupiedSquares();
			for(int j=0;j<testMe.size();j++){// Check to see if any of it's squares have been hit
				if(testMe.get(j).getColumn() == y && testMe.get(j).getRow() == x){//Hit!
					output.setShip(this.ships.get(i));
					output.setResult(AtackStatus.HIT);
					totalHits++;// The reason we need this is that we only count previous hits so we need to include this one
				}
			}
		}

		//Check for miss
		if(output.getResult() == AtackStatus.INVALID){
			output.setResult(AtackStatus.MISS);
		}
		else{//Check for sunk
			for(int i=0;i<this.ships.size();i++){// Go through each ship
				List<Square> testMe = ships.get(i).getOccupiedSquares();
				//TODO can be optimised by checking to see if a ship is sunk before doing square compare
				for(int j=0;j<attacks.size();j++){// Check to see if any of it's squares have been hit
					for(int k=0;k<testMe.size();k++){// Check the list of a ships squares vs all the attacks made
						if(testMe.get(k).getRow() == attacks.get(j).getLocation().getRow() && testMe.get(k).getColumn() == attacks.get(j).getLocation().getColumn()){
							totalHits++;
						}
					}
				}
				if(totalHits == this.ships.get(i).getLen()){
					output.setResult(AtackStatus.SUNK);// Change to sunk
					int numSunk = 1;
					for(int f=0;f<attacks.size();f++){
						if(attacks.get(f).getResult() == AtackStatus.SUNK){
							numSunk++;
						}
					}
					//If this hits 3 then all our ships are sunk and we return a SURRENDER event
					if(numSunk == 3){// FIXME: This needs to be changed if we add more ships
						output.setResult(AtackStatus.SURRENDER);
						attacks.add(output);
						return output;
					}
				}
				totalHits = 0;// Reset the # of hits
			}
		}
		attacks.add(output);
		return output;
	}

	public List<Ship> getShips() {
		return ships;
	}

	public void setShips(List<Ship> inShips) {
		ships = inShips;
	}

	public List<Result> getAttacks() {
		return attacks;
	}

	public void setAttacks(List<Result> inAttacks) {
		attacks = inAttacks;
	}
}
