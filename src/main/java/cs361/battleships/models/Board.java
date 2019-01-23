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
		// TODO Implement
		ships = new ArrayList<>();
		attacks = new ArrayList<>();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		// TODO Implement
		if(updateShip(ship,x,y,isVertical)){// Try to update the ships location if it works add it
			ships.add(ship);
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
				x++;
			}
		}
		else {
			if(y+ship.getLen() > 'j'){// Check to make sure we aren't off the board
				return false;
			}
			for(int i=0;i<ship.getLen();i++){// Add each square to the ship object
				ship.addOccupiedSquare(x,y);
				y++;
			}

		}
		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		//TODO Implement

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
				}
			}
		}

		//Check for miss
		if(output.getResult() == AtackStatus.INVALID){
			output.setResult(AtackStatus.MISS);
		}
		else{//Check for sunk
			int totalHits = 0;
			for(int i=0;i<this.ships.size();i++){// Go through each ship
				List<Square> testMe = ships.get(i).getOccupiedSquares();
				//TODO can be optmised by checking to see if a ship is sunk before doing square compair
				for(int j=0;j<attacks.size();j++){// Check to see if any of it's squares have been hit
					for(int k=0;k<testMe.size();k++){// Check the list of a ships squares vs all the attacks made
						if(testMe.get(k).getRow() == attacks.get(j).getLocation().getRow() && testMe.get(k).getColumn() == attacks.get(j).getLocation().getColumn()){
							totalHits++;
						}
					}
				}
				if(totalHits == this.ships.get(i).getLen()){
					output.setResult(AtackStatus.SUNK);// Change to sunk
					//TODO check to see if all ships are sunk
				}
				totalHits = 0;// Reset the # of hits
			}
		}
		attacks.add(output);
		return output;
	}

	public List<Ship> getShips() {
		//TODO implement
		return ships;
	}

	public void setShips(List<Ship> inShips) {
		//TODO implement
		ships = inShips;
	}

	public List<Result> getAttacks() {
		//TODO implement
		return attacks;
	}

	public void setAttacks(List<Result> inAttacks) {
		//TODO implement
		attacks = inAttacks;
	}
}
