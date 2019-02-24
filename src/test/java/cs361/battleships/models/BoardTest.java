package cs361.battleships.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    private Board board;

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testInvalidPlacement() {
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', true));
    }

    @Test
    public void testPlaceMinesweeper() {
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 1, 'A', true));
    }

    @Test
    public void testAttackEmptySquare() {
        board.placeShip(new Ship("MINESWEEPER"), 1, 'A', true);
        Result result = board.attack(2, 'E',false);
        assertEquals(AtackStatus.MISS, result.getResult());
    }

    @Test
    public void testAttackShip() {
        Ship minesweeper = new Ship("MINESWEEPER");
        board.placeShip(minesweeper, 1, 'A', false);
        minesweeper = board.getShips().get(0);
        Result result = board.attack(1, 'B',false);
        assertEquals(AtackStatus.HIT, result.getResult());
        assertEquals(minesweeper, result.getShip());
    }

    @Test
    public void testAttackSameSquareMultipleTimes() {
        Ship minesweeper = new Ship("MINESWEEPER");
        board.placeShip(minesweeper, 1, 'A', true);
        board.attack(1, 'A',false);
        Result result = board.attack(1, 'A',false);
        assertEquals(AtackStatus.INVALID, result.getResult());
    }

    @Test
    public void testAttackSameEmptySquareMultipleTimes() {
        Result initialResult = board.attack(1, 'A',false);
        assertEquals(AtackStatus.MISS, initialResult.getResult());
        Result result = board.attack(1, 'A',false);
        assertEquals(AtackStatus.INVALID, result.getResult());
    }

    @Test
    public void testSurrender() {
        board.placeShip(new Ship("MINESWEEPER"), 1, 'A', false);
        //board.attack(1, 'A',false);
        var result = board.attack(1, 'B',false);
        assertEquals(AtackStatus.SURRENDER, result.getResult());
    }

    @Test
    public void testPlaceMultipleShipsOfSameType() {
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 1, 'A', true));
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 5, 'D', true));

    }

    @Test
    public void testCantPlaceMoreThan3Ships() {
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 1, 'A', true));
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), 5, 'D', true));
        assertTrue(board.placeShip(new Ship("DESTROYER"), 6, 'A', false));
        assertFalse(board.placeShip(new Ship(""), 8, 'A', false));

    }

    @Test
    public void testSonarOnlyAfter1Sink(){
        board.sonar(6,'J');
        assertEquals(0,board.getAtacks().size());// Make sure that no sonar attacks are made
        Square tmpS = new Square(6,'J');
        Result tmpR = new Result(tmpS);
        tmpR.setResult(AtackStatus.SUNK);
        board.addToAtacks(tmpR);
        board.sonar(1,'A');
        // Make sure that all the sonar attacks where made 1 sunk attack + 13 sonar attacks
        assertEquals(14,board.getAtacks().size());
    }

    @Test
    public void testSonarAttackHitAndMiss(){
        board.placeShip(new Ship("MINESWEEPER"), 1, 'A', false);
        board.sonarAtk(1,'A');
        board.sonarAtk(6,'J');
        assertEquals(2,board.getAtacks().size());// Make sure both attacks happen
        assertEquals(AtackStatus.SONARHIT,board.getAtacks().get(0).getResult());// Check that #1 was a hit
        assertEquals(AtackStatus.SONARMISS,board.getAtacks().get(1).getResult());// Check that #2 was a miss
    }

    @Test
    public void testOnlyTwoSonar(){
        board.sonar(6,'J');
        assertEquals(board.getAtacks().size(),0);// Make sure that no sonar attacks are made
        Square tmpS = new Square(6,'J');
        Result tmpR = new Result(tmpS);
        tmpR.setResult(AtackStatus.SUNK);
        board.addToAtacks(tmpR);
        board.sonar(1,'A');
        board.attack(1, 'A',false);
        board.sonar(1,'A');
        board.attack(1, 'J',false);
        board.sonar(1,'A');
        assertEquals(3,board.getAtacks().size());// Sunk and the two attacks and no sonar events as it shouldn't trigger
    }
}
