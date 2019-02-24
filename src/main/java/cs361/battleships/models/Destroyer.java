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
	private String kind;
	private int size;
    public Destroyer(){
        this.kind = "DESTROYER";
        size = 3;
    }
}
