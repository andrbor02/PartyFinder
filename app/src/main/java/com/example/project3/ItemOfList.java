package com.example.project3;

public class ItemOfList {
    private String name;
    private String value;

    public ItemOfList(String name, String count) {
        this.name = name;
        this.value = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String count) {
        this.value = count;
    }
}
