package com.blk.testcolorchooser.scenarios;

import java.lang.reflect.Array;
import java.util.ArrayList;

public enum ScenarioNames {
    SNAKE("snake"),
    DEFAULT("default"),
    LTOR("ltor"),
    RTOL("rtol"),
    TOCENTER("toceneter");

    private String name;
    ScenarioNames(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

}
