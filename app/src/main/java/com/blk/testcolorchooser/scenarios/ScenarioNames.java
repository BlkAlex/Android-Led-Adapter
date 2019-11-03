package com.blk.testcolorchooser.scenarios;

import java.lang.reflect.Array;
import java.util.ArrayList;

public enum ScenarioNames {
    NOEFFECT("noeffect","Без эффектов"),
    SNAKE("snake", "Змейка"),
    LTOR("ltor", "Слева направо"),
    RTOL("rtol","Справа налево"),
    TOCENTER("toceneter","От краев к центру");

    private String name;
    private String stringName;
    ScenarioNames(String name,String stringName){
        this.name = name;
        this.stringName = stringName;
    }
    public String getName(){
        return name;
    }
    public String getStringName(){
        return stringName;
    }

}
