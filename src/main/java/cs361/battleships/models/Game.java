package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static cs361.battleships.models.AtackStatus.*;

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();
    @JsonProperty private int oppenentsSunk = 0;
    @JsonProperty private int movesMade = 0;
    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
        if (!successful)
            return false;

        boolean opponentPlacedSuccessfully;
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            opponentPlacedSuccessfully = opponentsBoard.placeShip(ship, randRow(), randCol(), randVertical());
        } while (!opponentPlacedSuccessfully);

        return true;
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean attack(int x, char  y, boolean useSonar) {
        if(useSonar){
            opponentsBoard.sonar(x,y);
            return true;
        }
        else{
            Result playerAttack = opponentsBoard.attack(x, y, useSonar);
            if (playerAttack.getResult() == INVALID) {
                return false;
            }
            else if(playerAttack.getResult() == SUNK){
                addOpponentSunk();
            }

            Result opponentAttackResult;
            do {
                // AI does random attacks, so it might attack the same spot twice
                // let it try until it gets it right
                opponentAttackResult = playersBoard.attack(randRow(), randCol(),false);
            } while(opponentAttackResult.getResult() == INVALID);

            return true;
        }
    }

    public boolean move(char dir) {
        Board movedBoard = playersBoard;
        System.out.print("Made it here!\n");
        if (oppenentsSunk >= 2 && movesMade < 2) {
            movedBoard = moveShips(movedBoard, dir);
            playersBoard = movedBoard;
            return true;
        }
        System.out.print("moveShips returned false\n");
        return false;
    }

    public Board moveShips(Board movedBoard, char dir){
        Ship offsetShip;
        Result tempAttack;
        boolean shipMoved;
        if(dir == 'n'){
            for(int i = 0; i < playersBoard.getShips().size(); i++){
                offsetShip = playersBoard.getShips().get(i);
                shipMoved = movedBoard.placeShip(offsetShip, offsetShip.getOccupiedSquares().get(0).getRow()-1, offsetShip.getOccupiedSquares().get(0).getColumn(), offsetShip.getVertical());
                if(!shipMoved) {
                    movedBoard.placeShip(offsetShip, offsetShip.getOccupiedSquares().get(0).getRow(), offsetShip.getOccupiedSquares().get(0).getColumn(), offsetShip.getVertical());
                }
                else {
                    for(int j = 0; j < playersBoard.getAtacks().size(); j++) {
                        tempAttack = playersBoard.getAtacks().get(j);
                        tempAttack.getLocation().setRow(tempAttack.getLocation().getRow()-1);
                        playersBoard.getAtacks().remove(j);
                        playersBoard.addToAtacks(tempAttack);
                    }
                }
            }
            return movedBoard;
        }
        else if(dir == 's'){
            for(int i = 0; i < playersBoard.getShips().size(); i++){
                offsetShip = playersBoard.getShips().get(i);
                shipMoved = movedBoard.placeShip(offsetShip, offsetShip.getOccupiedSquares().get(0).getRow()+1, offsetShip.getOccupiedSquares().get(0).getColumn(), offsetShip.getVertical());
                if(!shipMoved) {
                    movedBoard.placeShip(offsetShip, offsetShip.getOccupiedSquares().get(0).getRow(), offsetShip.getOccupiedSquares().get(0).getColumn(), offsetShip.getVertical());
                }
                else {
                    for(int j = 0; j < playersBoard.getAtacks().size(); j++) {
                        tempAttack = playersBoard.getAtacks().get(j);
                        tempAttack.getLocation().setRow(tempAttack.getLocation().getRow()+1);
                        playersBoard.getAtacks().remove(j);
                        playersBoard.addToAtacks(tempAttack);
                    }
                }
            }
            return movedBoard;
        }
        else if(dir == 'e'){
            for(int i = 0; i < playersBoard.getShips().size(); i++){
                offsetShip = playersBoard.getShips().get(i);
                shipMoved = movedBoard.placeShip(offsetShip, offsetShip.getOccupiedSquares().get(0).getRow(), (char)(offsetShip.getOccupiedSquares().get(0).getColumn()+1), offsetShip.getVertical());
                if(!shipMoved) {
                    movedBoard.placeShip(offsetShip, offsetShip.getOccupiedSquares().get(0).getRow(), offsetShip.getOccupiedSquares().get(0).getColumn(), offsetShip.getVertical());
                }
                else {
                    for(int j = 0; j < playersBoard.getAtacks().size(); j++) {
                        tempAttack = playersBoard.getAtacks().get(j);
                        tempAttack.getLocation().setColumn((char)(tempAttack.getLocation().getColumn()+1));
                        playersBoard.getAtacks().remove(j);
                        playersBoard.addToAtacks(tempAttack);
                    }
                }
            }
            return movedBoard;
        }
        else if(dir == 'w'){
            for(int i = 0; i < playersBoard.getShips().size(); i++){
                offsetShip = playersBoard.getShips().get(i);
                shipMoved = movedBoard.placeShip(offsetShip, offsetShip.getOccupiedSquares().get(0).getRow(), (char)(offsetShip.getOccupiedSquares().get(0).getColumn()-1), offsetShip.getVertical());
                if(!shipMoved) {
                    movedBoard.placeShip(offsetShip, offsetShip.getOccupiedSquares().get(0).getRow(), offsetShip.getOccupiedSquares().get(0).getColumn(), offsetShip.getVertical());
                }
                else {
                    for(int j = 0; j < playersBoard.getAtacks().size(); j++) {
                        tempAttack = playersBoard.getAtacks().get(j);
                        tempAttack.getLocation().setColumn((char)(tempAttack.getLocation().getColumn()-1));
                        playersBoard.getAtacks().remove(j);
                        playersBoard.addToAtacks(tempAttack);
                    }
                }
            }
            return movedBoard;
        }
        return playersBoard;
    }

    public void addOpponentSunk() {
        oppenentsSunk++;
    }

    private char randCol() {
        int random = new Random().nextInt(10);
        return (char) ('A' + random);
    }

    private int randRow() {
        return  new Random().nextInt(10) + 1;
    }

    private boolean randVertical() {
        return new Random().nextBoolean();
    }
}
