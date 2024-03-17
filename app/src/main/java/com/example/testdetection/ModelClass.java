package com.example.testdetection;

public class ModelClass {

    private String id;
    private String name;
    private boolean isSelected;

    public ModelClass(String id, String name, boolean isSelected) {
        this.id = id;
        this.name = name;
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "ModelClass{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
