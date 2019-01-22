package cs361.battleships.models;

public class Result {
	private Ship ships;
	private Square square;
	private AttackStatus status;

	public AtackStatus getResult() {
		//TODO implement
		return status;
	}

	public void setResult(AtackStatus result) {
		//TODO implement
		this.status = result
	}

	public Ship getShip() {
		//TODO implement
		return ships;
	}

	public void setShip(Ship ship) {
		//TODO implement
		this.ships=ship
	}

	public Square getLocation() {
		//TODO implement
		return square;
	}

	public void setLocation(Square square) {
		//TODO implement
		this.square=square
	}
}
