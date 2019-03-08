package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.mchange.v1.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Destroyer extends Ship {
	private String name;
	private int size;
	private int capNum;
    private Result re = new Result();
    private Ship sh = new Ship();

    public Destroyer(){
        this.name = "DESTROYER";
        this.size = 3;
        capNum = 0;
    }

    @Override
    public String getKind(){
        return this.name;
    }

    public List<Result> hitCC1(List<Result> a, Ship h, Result ar) {
        System.out.println(capNum);
        System.out.println("WhatMid");
        Square tmpS = h.getOccupiedSquares().get(0);
        a.add(ar);
        ar = h.attack(tmpS.getRow(), tmpS.getColumn());
        a.add(ar);
        tmpS = h.getOccupiedSquares().get(2);
        re = h.attack(tmpS.getRow(), tmpS.getColumn());
        return a;
    }

    public Ship hitCC2(List<Result> a, Ship h, Result ar) {
        System.out.println("HI");
        capNum++;
        re.setResult(AtackStatus.CAPHIT);
        h.getCCD().nohit();
        return h;
    }

    public Result getRe() {
        return re;
    }

    public Ship getSh() {
        return sh;
    }

    public int getCapNum() {
        return capNum;
    }

}
