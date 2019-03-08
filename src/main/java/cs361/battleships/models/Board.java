package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Result> attacks;
	@JsonProperty private int numSonar;
	@JsonProperty private int capNumD = 0;
	@JsonProperty private int capNumB = 0;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		ships = new ArrayList<>();
		attacks = new ArrayList<>();
		numSonar = 0;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		if (ships.size() >= 3) {
			return false;
		}
		if (ships.stream().anyMatch(s -> s.getKind().equals(ship.getKind()))) {
			return false;
		}
		final var placedShip = new Ship(ship.getKind());
		placedShip.place(y, x, isVertical);
		if (isVertical){
			placedShip.makeVertical();
		}
		if (ships.stream().anyMatch(s -> s.overlaps(placedShip))) {
			return false;
		}
		if (placedShip.getOccupiedSquares().stream().anyMatch(s -> s.isOutOfBounds())) {
			return false;
		}
		ships.add(placedShip);

		return true;
	}

	public boolean isPlaceShipValid(Ship ship, int x, char y, boolean isVertical){
		if (ships.size() >= 3) {
			return false;
		}
		if (ships.stream().anyMatch(s -> s.getKind().equals(ship.getKind()))) {
			return false;
		}
		final var placedShip = new Ship(ship.getKind());
		if (ships.stream().anyMatch(s -> s.overlaps(placedShip))) {
			return false;
		}
		if (placedShip.getOccupiedSquares().stream().anyMatch(s -> s.isOutOfBounds())) {
			return false;
		}
		return true;
	}

	public boolean moveShips(char dir){
		if(dir == 'n'){
			for(int i = 0; i < ships.size(); i++) { //for each ship
				if (isPlaceShipValid(ships.get(i),ships.get(i).getOccupiedSquares().get(0).getRow()-1, ships.get(i).getOccupiedSquares().get(0).getColumn(), ships.get(i).getVertical())) { // if move is valid
					for (int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++) { // for each square in ship
						ships.get(i).getOccupiedSquares().get(j).setRow(ships.get(i).getOccupiedSquares().get(j).getRow()-1); //move square north by 1
					}
				}
			}
			return true;
		}
		else if(dir == 's'){
			for(int i = 0; i < ships.size(); i++) { //for each ship
				if (isPlaceShipValid(ships.get(i),ships.get(i).getOccupiedSquares().get(0).getRow()+1, ships.get(i).getOccupiedSquares().get(0).getColumn(), ships.get(i).getVertical())) { // if move is valid
					for (int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++) { // for each square in ship
						ships.get(i).getOccupiedSquares().get(j).setRow(ships.get(i).getOccupiedSquares().get(j).getRow()+1); //move square south by 1
					}
				}
			}
			return true;
		}
		else if(dir == 'e'){
			for(int i = 0; i < ships.size(); i++) { //for each ship
				if (isPlaceShipValid(ships.get(i), ships.get(i).getOccupiedSquares().get(0).getRow(), (char)(ships.get(i).getOccupiedSquares().get(0).getColumn()+1), ships.get(i).getVertical())) { // if move is valid
					for (int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++) { // for each square in ship
						ships.get(i).getOccupiedSquares().get(j).setColumn((char)(ships.get(i).getOccupiedSquares().get(j).getColumn()+1)); //move square east by 1
					}
				}
			}
			return true;
		}
		else if(dir == 'w'){
			for(int i = 0; i < ships.size(); i++) { //for each ship
				if (isPlaceShipValid(ships.get(i), ships.get(i).getOccupiedSquares().get(0).getRow(), (char)(ships.get(i).getOccupiedSquares().get(0).getColumn()-1), ships.get(i).getVertical())) { // if move is valid
					for (int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++) { // for each square in ship
						ships.get(i).getOccupiedSquares().get(j).setColumn((char)(ships.get(i).getOccupiedSquares().get(j).getColumn()-1)); //move square east by 1
					}
				}
			}
			return true;
		}
		return false;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y, boolean useSonar) {
		Result attackResult = attack(new Square(x, y),useSonar);
		attacks.add(attackResult);
		return attackResult;
	}

	public void sonarAtk(int x,char y){//FIXME: Write test functionss
		Square tmpS = new Square(x,y);
		Result tmpR = attack(tmpS,true);
		if(tmpR.getResult() == AtackStatus.HIT){// Center of the sonar
			tmpR.getLocation().nohit();
			tmpR.setShip(null);
			tmpR.setResult(AtackStatus.SONARHIT);
			attacks.add(tmpR);
		}
		else if(tmpR.getResult() == AtackStatus.MISS){
			tmpR.setResult(AtackStatus.SONARMISS);
			attacks.add(tmpR);
		}

		//If it's not hit or miss then it's invalid and out of bounds in witch case we don't care
	}

	public void sonar(int x, char y){//FIXME: Write test functions
		if(numSonar < 2){
			for(int i = 0; i < attacks.size(); i++){// Search for the first sunk ship
				if(attacks.get(i).getResult() == AtackStatus.SUNK){
					int tmp = (int) y;
					sonarAtk(x,y);// Center
					sonarAtk(x+1,y);// Up one
					sonarAtk(x+2,y);// Up two
					sonarAtk(x-1,y);// Down one
					sonarAtk(x-2,y);// Down two
					sonarAtk(x,(char)(tmp+1));// Right one
					sonarAtk(x,(char)(tmp+2));// Right two
					sonarAtk(x,(char)(tmp-1));// Left one
					sonarAtk(x,(char)(tmp-2));// Left two
					sonarAtk(x+1,(char)(tmp+1));// Top right
					sonarAtk(x+1,(char)(tmp-1));// Top left
					sonarAtk(x-1,(char)(tmp+1));// Bottom right
					sonarAtk(x-1,(char)(tmp-1));// Bottom left
					numSonar++;
					return;
				}
			}
		}
	}

	private Result attack(Square s,boolean useSonar) {
		if(!useSonar){
			int numItems = attacks.size();
			for(int i = 0; i < numItems; i++){
				if(attacks.get(i).getResult() == AtackStatus.SONARHIT || attacks.get(i).getResult() == AtackStatus.SONARMISS){
					attacks.remove(i);
					i--;
					numItems--;
				}
			}
		}
		//Already attacked
		/*if (attacks.stream().anyMatch(r -> r.getLocation().equals(s))) {
			var attackResult = new Result(s);
			attackResult.setResult(AtackStatus.INVALID);
			return attackResult;
		}*/
		//Miss
		var shipsAtLocation = ships.stream().filter(ship -> ship.isAtLocation(s)).collect(Collectors.toList());
		if (shipsAtLocation.size() == 0) {
			var attackResult = new Result(s);
			return attackResult;
		}
		if(!useSonar){
			var hitShip = shipsAtLocation.get(0);
			var attackResult = hitShip.attack(s.getRow(), s.getColumn());
			if(attackResult.getResult() == AtackStatus.HIT && hitShip.getCCM().getRow() == s.getRow() && hitShip.getCCM().getColumn() == s.getColumn() && hitShip.getKind().equals("MINESWEEPER")){
				Square tmpS = hitShip.getOccupiedSquares().get(1);
				attacks.add(attackResult);
				attackResult = hitShip.attack(tmpS.getRow(),tmpS.getColumn());
				//attackResult.setResult(AtackStatus.CAPHIT); This changes the final hit form SUNK to cap hit witch won't trigger the surrender
			}
			else if (attackResult.getResult() == AtackStatus.HIT && hitShip.getCCD().getRow() == s.getRow() && hitShip.getCCD().getColumn() == s.getColumn() && hitShip.getKind().equals("DESTROYER")){
				if (capNumD == 1) {
					Square tmpS = hitShip.getOccupiedSquares().get(0);
					attacks.add(attackResult);
					attackResult = hitShip.attack(tmpS.getRow(), tmpS.getColumn());
					attacks.add(attackResult);
					tmpS = hitShip.getOccupiedSquares().get(2);
					attackResult = hitShip.attack(tmpS.getRow(), tmpS.getColumn());
				}
				else if (capNumD == 0){
					capNumD++;
					attackResult.setResult(AtackStatus.CAPHIT);
					hitShip.getCCD().nohit();
				}
			}
			else if (attackResult.getResult() == AtackStatus.HIT && hitShip.getCCB().getRow() == s.getRow() && hitShip.getCCB().getColumn() == s.getColumn() && hitShip.getKind().equals("BATTLESHIP")){
				if (capNumB == 1) {
					Square tmpS = hitShip.getOccupiedSquares().get(0);
					attacks.add(attackResult);
					attackResult = hitShip.attack(tmpS.getRow(), tmpS.getColumn());
					attacks.add(attackResult);
					tmpS = hitShip.getOccupiedSquares().get(1);
					attackResult = hitShip.attack(tmpS.getRow(), tmpS.getColumn());
					attacks.add(attackResult);
					tmpS = hitShip.getOccupiedSquares().get(3);
					attackResult = hitShip.attack(tmpS.getRow(), tmpS.getColumn());
				}
				else if (capNumB == 0){
					capNumB++;
					attackResult.setResult(AtackStatus.CAPHIT);
					hitShip.getCCB().nohit();
				}
			}
			if (attackResult.getResult() == AtackStatus.SUNK) {
				if (ships.stream().allMatch(ship -> ship.isSunk())) {
					attackResult.setResult(AtackStatus.SURRENDER);
				}
			}
			return attackResult;
		}
		else {
			var attackResult = new Result(s);
			attackResult.setResult(AtackStatus.HIT);
			return attackResult;
		}

	}

	List<Ship> getShips() {
		return ships;
	}

	List<Result> getAtacks(){//Test function
		return attacks;
	}

	void addToAtacks(Result tmp){//Test helper function
		attacks.add(tmp);
	}
}
