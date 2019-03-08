package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.mchange.v1.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Ship {

	@JsonProperty private String kind;
	@JsonProperty private Boolean underwater;
	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private int size;

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}
	
	public Ship(String kind) {
		this();
		this.kind = kind;
		switch(kind) {
			case "MINESWEEPER":
				this.underwater = false;
				size = 2;
				break;
			case "DESTROYER":
				this.underwater = false;
				size = 3;
				break;
			case "BATTLESHIP":
				this.underwater = false;
				size = 4;
				break;
			case "SUB":
				this.underwater = false;
				size = 5;
				break;
			case "SUBB":
				this.underwater = true;
				this.kind = "SUB";
				size = 5;
				break;
		}
	}

	public boolean isUnderwater(){
		return underwater;
	}
	public void goUnderwater() { this.underwater = true;}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public void place(char col, int row, boolean isVertical) {
		switch (this.kind) {
			case "SUB":
				for (int i = 0; i < size - 1; i++) {
					if (isVertical) {
						occupiedSquares.add(new Square(row + i, col));
					} else {
						occupiedSquares.add(new Square(row, (char) (col + i)));
					}
				}
				if (isVertical) {
					occupiedSquares.add(new Square(row + 1, (char) (col + 1)));
				} else {
					occupiedSquares.add(new Square(row - 1, (char) (col + 1)));
				}
				break;
			default:
				for (int i = 0; i < size; i++) {
					if (isVertical) {
						occupiedSquares.add(new Square(row + i, col));
					} else {
						occupiedSquares.add(new Square(row, (char) (col + i)));
					}
				}
				break;
		}
	}

	public boolean overlaps(Ship other) {
		Set<Square> thisSquares = Set.copyOf(getOccupiedSquares());
		Set<Square> otherSquares = Set.copyOf(other.getOccupiedSquares());
		Sets.SetView<Square> intersection = Sets.intersection(thisSquares, otherSquares);
		return intersection.size() != 0;
	}

	public boolean isAtLocation(Square location) {
		return getOccupiedSquares().stream().anyMatch(s -> s.equals(location));
	}

	public String getKind() {
		return kind;
	}

	public Result attack(int x, char y) {
		var attackedLocation = new Square(x, y);
		var square = getOccupiedSquares().stream().filter(s -> s.equals(attackedLocation)).findFirst();
		if (!square.isPresent()) {
			return new Result(attackedLocation);
		}
		var attackedSquare = square.get();
		if (attackedSquare.isHit()) {
			var result = new Result(attackedLocation);
			result.setResult(AtackStatus.INVALID);
			return result;
		}
		attackedSquare.hit();
		var result = new Result(attackedLocation);
		result.setShip(this);
		if (isSunk()) {
			result.setResult(AtackStatus.SUNK);
		} else {
			result.setResult(AtackStatus.HIT);
		}
		return result;
	}

	@JsonIgnore
	public Square getCCM(){
		return occupiedSquares.get(0);
	}

	@JsonIgnore
	public Square getCCD() { return occupiedSquares.get(1);}

	@JsonIgnore
	public Square getCCB() { return occupiedSquares.get(2);}

	@JsonIgnore
	public boolean isSunk() {
		return getOccupiedSquares().stream().allMatch(s -> s.isHit());
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Ship)) {
			return false;
		}
		var otherShip = (Ship) other;

		return this.kind.equals(otherShip.kind)
				&& this.size == otherShip.size
				&& this.occupiedSquares.equals(otherShip.occupiedSquares);
	}

	@Override
	public int hashCode() {
		return 33 * kind.hashCode() + 23 * size + 17 * occupiedSquares.hashCode();
	}

	@Override
	public String toString() {
		return kind + occupiedSquares.toString();
	}
}
