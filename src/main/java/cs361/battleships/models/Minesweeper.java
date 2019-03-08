package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.mchange.v1.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Minesweeper extends Ship {
	private String name;
	private int size;
	private Result re = new Result();
	
    public Minesweeper(){
        this.name = "MINESWEEPER";
        size = 2;
    }

    @Override
    public String getKind(){
        return this.name;
    }

    public List<Result> hitCC(List<Result> a, Ship h, Result ar) {
        Square tmpS = h.getOccupiedSquares().get(1);
        a.add(ar);
        re = h.attack(tmpS.getRow(),tmpS.getColumn());
        return a;
    }

    public Result getRe() {
        return re;
    }

}
