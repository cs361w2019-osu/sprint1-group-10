package cs361.battleships.models;

import controllers.AttackGameAction;

public class Result {
	private AtackStatus outcome;
	private Ship target;
	private Square targetLocation;

	public AtackStatus getResult() {
		return outcome;
	}

	public void setResult(AtackStatus result) {
		outcome = result;
	}

	public Ship getShip() {
		return target;
	}

	public void setShip(Ship ship) {
		target = ship;
	}

	public Square getLocation() {
		return targetLocation;
	}

	public void setLocation(Square square) {
		targetLocation = square;
	}
}
