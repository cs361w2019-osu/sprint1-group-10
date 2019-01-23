package cs361.battleships.models;

import controllers.AttackGameAction;

public class Result {
	private AtackStatus outcome;
	private Ship target;
	private Square targetLocation;

	public AtackStatus getResult() {
		//TODO implement
		return outcome;
	}

	public void setResult(AtackStatus result) {
		//TODO implement
		outcome = result;
	}

	public Ship getShip() {
		//TODO implement
		return target;
	}

	public void setShip(Ship ship) {
		//TODO implement
		target = ship;
	}

	public Square getLocation() {
		//TODO implement
		return targetLocation;
	}

	public void setLocation(Square square) {
		//TODO implement
		targetLocation = square;
	}
}
