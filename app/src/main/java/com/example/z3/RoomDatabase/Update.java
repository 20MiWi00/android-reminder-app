package com.example.z3.RoomDatabase;

public class Update {
    Integer id;
    String typeOfUpdate;

    public Update(Integer id, String typeOfUpdate) {
        this.id = id;
        this.typeOfUpdate = typeOfUpdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeOfUpdate() {
        return typeOfUpdate;
    }

    public void setTypeOfUpdate(String typeOfUpdate) {
        this.typeOfUpdate = typeOfUpdate;
    }
}
