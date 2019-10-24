package com.example.s326197mappe2;

public class Friend {

    private int id;
    private String name;
    private String telephone;

    public Friend() {
    }

    public Friend(String name, String telephone) {
        this.name = name;
        this.telephone = telephone;
    }

    public Friend(int id, String name, String telephone) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
