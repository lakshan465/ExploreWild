package com.example.demo3;

public class Animal {
    private int animalID;
    private int cageID;
    private String animalType;
    private String sex;

    public Animal(int animalID, int cageID, String animalType, String sex) {
        this.animalID = animalID;
        this.cageID = cageID;
        this.animalType = animalType;
        this.sex = sex;
    }

    public int getAnimalID() {
        return animalID;
    }

    public int getCageID() {
        return cageID;
    }

    public String getAnimalType() {
        return animalType;
    }

    public String getSex() {
        return sex;
    }
}
