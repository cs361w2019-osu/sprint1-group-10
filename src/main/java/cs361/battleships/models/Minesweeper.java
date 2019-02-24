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
	
    public Minesweeper(){
        this.name = "MINESWEEPER";
        size = 2;
    }

    @Override
    public String getKind(){
        return this.name;
    }
}
