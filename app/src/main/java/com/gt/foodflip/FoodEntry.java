package com.gt.foodflip;

/**
 * This class is used to save each food entries data.
 */
public class FoodEntry {
    private String building = "";
    private String location = "";
    private String category = "";
    private String type = "";
    private String description = "";
    private int votes;

    /* Setters */
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

    public void setVotes(int votes) {
        this.votes = votes;
    }

    /* Getters */
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

    public int getVotes() {
        return this.votes;
    }
}