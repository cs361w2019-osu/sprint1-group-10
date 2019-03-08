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
        if (oppenentsSunk >= 2 && movesMade < 2) {
            if(playersBoard.moveShips(dir)){
                movesMade++;
                return true;
            }
        }
        return false;
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
