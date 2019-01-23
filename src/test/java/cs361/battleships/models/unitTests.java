package cs361.battleships.models;

import org.junit.Test;

import java.util.List;

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

        public void shipTest(){
            Ship testShip1 = new Ship();
            testShip1.shipLen = 1;
            testShip1.shipType = 'Minesweeper';
            if(testShip1.shipLen == 1 && testShip.shipType == 'Minesweeper'){
                this.pass++;
            }
            else{
                this.fail++;
            }
        }
        public void getShipTest(){
            Ship testShip2 = testResult.getShip();
            if(testShip2){
                this.pass++;
            }
            else{
                this.fail++;
            }

        }
        public void setShipTest(){
            Ship testShip3 = new Ship();
            testResult.setShip(testShip3);
            if(testShip3.shipLen && testShip3.shipType){
                this.pass++;
            }
            else{
                this.fail++;
            }

        }
        public void getShipsTest(){
            List<Ship> testList = testBoard.getShips();
            if(testList){
                this.pass++;
            }
            else{
                this.fail++;
            }

        }
        public void placeShipTest(){
            Ship testShip5 = new Ship();
            testShip5.shipLen = 3;
            testShip5.shipType = 'Minesweeper';
            boolean vertical = false;
            boolean shipPlaced = testBoard.placeShip(testShip5, 1, 'A', vertical);
            if(!shipPlaced){
                this.fail++;
                break;
            }
            shipPlaced = testBoard.placeShip(testShip5, 1,'A', vertical);
            if(shipPlaced){
                this.fail++;
                break;
            }

        }
    }

}
