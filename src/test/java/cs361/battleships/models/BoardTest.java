package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BoardTest {
        /*  ✔💢
      --Board.java--
        [💢]Board()
        [💢]placeShip()
        [💢]attack()
        [💢]getShips()
        [💢]setShips()
        [💢]getAttacks()
        [💢]setAttacks()
        */


    @Test
    public void testOutOfBounds() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', true));
    }
    @Test
    public void testOverlap(){
        Board board = new Board();
        board.placeShip(new Ship("BATTLESHIP"),0,'A',false);
        assertFalse(board.placeShip(new Ship("DESTROYER"),0,'A',true));
    }
    @Test
    public void testDuplicateShip(){// FIXME: This needs to be changed if we add more ships
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"),0,'A',false);
        assertFalse(board.placeShip(new Ship("MINESWEEPER"),1,'B',true));

        board = new Board();
        board.placeShip(new Ship("BATTLESHIP"),0,'A',false);
        assertFalse(board.placeShip(new Ship("BATTLESHIP"),5,'B',true));

        board = new Board();
        board.placeShip(new Ship("DESTROYER"),0,'A',false);
        assertFalse(board.placeShip(new Ship("DESTROYER"),1,'B',true));
    }
    @Test
    public void testAttack(){
        Board board = new Board();
        board.placeShip(new Ship("BATTLESHIP"),0,'A',false);
        board.attack(0,'A');
        assertEquals(AtackStatus.HIT,board.getAttacks().get(0).getResult());
    }
    @Test
    public void testSunk(){
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"),0,'A',true);
        board.attack(0,'A');
        board.attack(1,'A');
        //If a .HIT sinks a ship it turns into a .SUNK so the final hit is never recorded as a .HIT so it'll be the second attack @1
        assertEquals(AtackStatus.SUNK,board.getAttacks().get(1).getResult());
    }/*
    @Test
    public void testSurrender(){// FIXME: This needs to be changed if we add more ships
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"),0,'C',true);
        board.placeShip(new Ship("BATTLESHIP"),0,'B',true);
        board.placeShip(new Ship("DESTROYER"),0,'A',true);

        //Destroyer
        board.attack(0,'A');
        board.attack(1,'A');
        board.attack(2,'A');
        board.attack(3,'A');

        //Battleship
        board.attack(0,'B');
        board.attack(1,'B');
        board.attack(2,'B');

        //Minesweeper
        board.attack(0,'C');
        board.attack(1,'C');

        assertEquals(AtackStatus.SURRENDER,board.getAttacks().get(8).getResult());
    }*/
}
