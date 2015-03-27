package com.gt.foodflip;

/**
 * The User class will hold data for the current user of the app.
 */
public class User {
    private String id;
    private String karma;

    public User() {
    }

    public String getId() {
        return this.id;
    }

    public String getKarma() {
        return this.karma;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKarma(String karma) {
        this.karma = karma;
    }

    public boolean isSet() {
        return this.getId() != null && !this.getId().equals("") &&
                this.getKarma() != null && !this.getKarma().equals("");
    }
}
