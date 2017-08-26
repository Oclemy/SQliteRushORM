package com.tutorials.hp.sqliterushorm.mDB;

import co.uk.rushorm.core.RushObject;

/**
 * Created by Oclemy for ProgrammingWizards Channel and http://www.camposha.info.
 - Our model class.
 - Our data object.
 - Take note it derives from co.uk.rushorm.core.RushObject.
 - This makes it a special object that can be saved,retrieved,edited or deleted to and from sqlite database.
 - It will be compiled to a sqlite database table.
 - The instance fields will be mapped to database table rows.
 - However, apart from deriving from RushObject, it must also define an empty constructor. If you have to pass other things
 via constructor then define a separate constructor for those.
 - NB/= The package for the model class should be specified in AndroidManifest.xml. Have a look at the manifest to see how we defined.
 This is beacuse RushORM needs to know where to find the class that should be converted to a table.
 */
public class Spacecraft extends RushObject {

    private String name,propellant;

    /*
    AN EMPTY CONSTRUCTOR
     */
    public Spacecraft() {
    }

    /*
    AN EMPTY CONSTRUCTOR.
     */
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
