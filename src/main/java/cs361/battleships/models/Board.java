package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Result> attacks;
	@JsonProperty private Weapon spaceLaser;
	@JsonProperty private int numSonar;
	//@JsonProperty private int capNumD = 0;
	@JsonProperty private int capNumB = 0;
	@JsonProperty private int capNumS = 0;
	private Minesweeper M = new Minesweeper();
	private Destroyer D = new Destroyer();

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
		if (ships.size() >= 4) {
			return false;
		}
		if (ships.stream().anyMatch(s -> s.getKind().equals(ship.getKind()))) {
			return false;
		}
		final var placedShip = new Ship(ship.getKind());
		if(ship.isUnderwater()){
			placedShip.goUnderwater();
		}
		placedShip.place(y, x, isVertical);
		for(int i=0;i<ships.size();i++){// Rewritten to check underwater status.
			if(ships.get(i).overlaps(placedShip)){// If there is an overlap
				if(ships.get(i).isUnderwater() == placedShip.isUnderwater()) {// If they are both under/above water error out
					return false;
				}//Otherwise we are good
			}
		}
		if (placedShip.getOccupiedSquares().stream().anyMatch(s -> s.isOutOfBounds())) {
			return false;
		}
		ships.add(placedShip);
		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y, boolean useSonar) {
		Result attackResult = attack(new Square(x, y),useSonar);
		attacks.add(attackResult);
		return attackResult;
	}

	public void sonarAtk(int x,char y){
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

	public void sonar(int x, char y){
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
				attacks = M.hitCC(attacks, hitShip, attackResult);
				attackResult = M.getRe();
			}
			else if (attackResult.getResult() == AtackStatus.HIT && hitShip.getCCD().getRow() == s.getRow() && hitShip.getCCD().getColumn() == s.getColumn() && hitShip.getKind().equals("DESTROYER")){
				if(D.getCapNum() == 1) {
					attacks = D.hitCC1(attacks, hitShip, attackResult);
					attackResult = D.getRe();
				}
				else if(D.getCapNum() == 0) {
					hitShip = D.hitCC2(attacks, hitShip, attackResult);
					attackResult = D.getRe();
				}
				//attacks = D.hitCC(attacks, hitShip, attackResult);
				//attackResult = D.getRe();
				//hitShip.equals(D.getSh());
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
			else if (attackResult.getResult() == AtackStatus.HIT && hitShip.getCCM().getRow() == s.getRow() && hitShip.getCCM().getColumn() == s.getColumn() && hitShip.getKind().equals("SUB")){
				if (capNumS == 1) {
					Square tmpS = hitShip.getOccupiedSquares().get(1);
					attacks.add(attackResult);
					attackResult = hitShip.attack(tmpS.getRow(), tmpS.getColumn());
					attacks.add(attackResult);
					tmpS = hitShip.getOccupiedSquares().get(2);
					attackResult = hitShip.attack(tmpS.getRow(), tmpS.getColumn());
					attacks.add(attackResult);
					tmpS = hitShip.getOccupiedSquares().get(3);
					attackResult = hitShip.attack(tmpS.getRow(), tmpS.getColumn());
					attacks.add(attackResult);
					tmpS = hitShip.getOccupiedSquares().get(4);
					attackResult = hitShip.attack(tmpS.getRow(), tmpS.getColumn());
				}
				else if (capNumS == 0){
					capNumS++;
					attackResult.setResult(AtackStatus.CAPHIT);
					hitShip.getCCM().nohit();
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
