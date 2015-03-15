package com.gt.foodflip;

/*
 * This class is used to save each food entries data.
 */

public class ListModel {

    private String building = "";
    private String location = "";
    private String category = "";
    private String type = "";
    private String description = "";

    /*********** Set Methods ******************/

    public void setBuilding(String building)
    {
        this.building = building;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*********** Get Methods ****************/

    public String getBuilding()
    {
        return this.building;
    }

    public String getLocation()
    {
        return this.location;
    }

    public String getCategory()
    {
        return this.category;
    }

    public String getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }
}