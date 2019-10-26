package com.example.s326197mappe2;

public class Friend {

    private long id;
    private String name;
    private String telephone;

    public Friend() {
    }

    public Friend(String name, String telephone) {
        this.name = name;
        this.telephone = telephone;
    }

    public Friend(long id, String name, String telephone) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getFirstName(){
        String fullName = this.getName();
        String[] parts = fullName.split(" ", 2);
        return parts[0];
    }

    public String getLastName(){
        String fullName = this.getName();
        String[] parts = fullName.split(" ", 2);
        return parts[1];
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
