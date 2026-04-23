package com.example.demo.servlet;

public class Player {
    private int id;
    private String name;
    private int age;
    private String indexName;
    private float value;

    public Player(int id, String name, int age, String indexName, float value) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.indexName = indexName;
        this.value = value;
    }

    // Getters and setters for each field
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}