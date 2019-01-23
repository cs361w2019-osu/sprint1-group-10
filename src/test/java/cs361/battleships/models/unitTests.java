package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class unitTests {
    Board testBoard = new Board();
    Game testGame = new Game();
    Result testResult = new Result();
    Ship testShip = new Ship();
    Square testSquare = new Square();
    int pass = 0;
    int fail = 0;

    public unitTests(){
        testAll();
        System.out.println("Number passed: ");
        System.out.println(pass);
        System.out.println("Number failed");
        System.out.println(fail);
    }


    public void testAll(){
        //TODO add all your test fucntions here

    }

}
