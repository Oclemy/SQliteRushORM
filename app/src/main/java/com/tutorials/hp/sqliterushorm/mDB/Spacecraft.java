





package com.tutorials.hp.sqliterushorm.mDB;

import co.uk.rushorm.core.RushObject;

/**
 * Created by Oclemy on 12/8/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class Spacecraft extends RushObject {

    private String name,propellant;

    //AN EMPTY CONSTRUCTOR
    public Spacecraft() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPropellant() {
        return propellant;
    }

    public void setPropellant(String propellant) {
        this.propellant = propellant;
    }
}
