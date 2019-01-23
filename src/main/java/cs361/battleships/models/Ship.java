package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty int shipLen;

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}
	
	public Ship(String kind) {
		//TODO implement
		if (kind == "Minesweeper"){
			shipLen = 2;
		} else if (kind == "Destroyer") {
			shipLen = 3;
		} else if (kind == "Battleship") {
			shipLen = 4;
		}
	}

	public int getLen(){
		return this.shipLen;
	}

	public void addOccupiedSquare(int x, char y){
		this.occupiedSquares.add(new Square(x,y));
	}


	public List<Square> getOccupiedSquares() {
		//TODO implement
		return occupiedSquares;
	}

}
