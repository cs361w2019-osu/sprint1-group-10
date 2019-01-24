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
		//FIXME remove me
		System.out.print("Making a ship of kind: ");
		System.out.println(kind);
		if (kind == "MINESWEEPER"){
			this.shipLen = 2;
		} else if (kind == "DESTROYER") {
			this.shipLen = 3;
		} else if (kind == "BATTLESHIP") {
			this.shipLen = 4;
		}
		else{
			this.shipLen = 0;
		}
	}

	public int getLen(){
		return this.shipLen;
	}

	public void addOccupiedSquare(int x, char y){
		this.occupiedSquares.add(new Square(x,y));
	}


	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

}
