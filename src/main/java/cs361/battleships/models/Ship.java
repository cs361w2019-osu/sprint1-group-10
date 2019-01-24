package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private int shipLen;

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}
	
	public Ship(String kind) {
		occupiedSquares = new ArrayList<>();
		//FIXME remove me
		System.out.print("Making a ship of kind: ");
		System.out.println(kind);
		switch(kind) {
			case "BATTLESHIP":
				shipLen = 4;
				break;
			case "DESTROYER":
				shipLen = 3;
				break;
			case "MINESWEEPER":
				shipLen = 2;
				break;
			default:
				shipLen = 0;
				break;
		}
		System.out.println(shipLen);
	}

	public int getLen(){
		return shipLen;
	}

	public void addOccupiedSquare(int x, char y){
		occupiedSquares.add(new Square(x,y));
	}


	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

}
