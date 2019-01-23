package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import java.util.Random;

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
        //TODO add all your test functions here
        alexTest();

    }

    public void alexTest() {
        Random rand = new Random();
        int randomRow = rand.nextInt(10) + 1;
        if (randomRow >= 1 && randomRow <= 10)
            this.pass++;

        else
            this.fail++;

        int randomColNum = rand.nextInt(74) + 65;
        char randomCol = (char) randomColNum;
        if (randomCol < 'A' && randomCol > 'J')
            this.fail++;
        else
            this.fail++;

        if(rand.nextBoolean() == 0 || rand.nextBoolean() == 1)
            this.pass++;
        else
            this.fail++;
    }

}


