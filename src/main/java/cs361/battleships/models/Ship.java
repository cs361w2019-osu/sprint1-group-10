package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	int shipLen;
	String shipType;

	@JsonProperty private List<Square> occupiedSquares;

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}
	
	public Ship(String kind) {
		//takes string input kind, assigns corresponding shipLen value.
		shipType = kind;
		if (shipType == "Minesweeper"){
			shipLen = 2;
		} else if (shipType == "Destroyer") {
			shipLen = 3;
		} else if (shipType == "Battleship") {
			shipLen = 4;
		}
	}

	public List<Square> getOccupiedSquares() {
		//TODO implement
		return Null;
	}
}
