package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private int shipLen;

	public Ship() {
		this.occupiedSquares = new ArrayList<>();
	}
	
	public Ship(String kind) {
		this.occupiedSquares = new ArrayList<>();
		//FIXME remove debug
		System.out.print("Making a ship of kind: ");
		System.out.println(kind);
		switch(kind) {
			case "BATTLESHIP":
				this.shipLen = 4;
				break;
			case "DESTROYER":
				this.shipLen = 3;
				break;
			case "MINESWEEPER":
				this.shipLen = 2;
				break;
			default:
				this.shipLen = 0;
				break;
		}
		System.out.println(shipLen);
	}

	public int getLen(){
		return this.shipLen;
	}

	public void setLen(int x){this.shipLen = x; return;}

	public void addOccupiedSquare(int x, char y){
		this.occupiedSquares.add(new Square(x,y));
	}


	public List<Square> getOccupiedSquares() {
		return this.occupiedSquares;
	}

	public void cleanOccupiedSquares(){ this.occupiedSquares = new ArrayList<>();}

}
