package com.puppies.puppiesrescue;


import javax.persistence.*;

@Entity
@Table(name = "puppies")
public class Puppy {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private String color;
    private Status status;
    private boolean s_n = false;
    private Tail tail;
    private String breed;

    enum Status {
        TOO_YOUNG, SICK, READY, ADOPTED
    }

    enum Tail {
        STRAIGHT, CURLS, NONE
    }

    public Puppy() {}

    public Puppy(String name, String color, Status status, boolean s_n, Tail tail, String breed) {
        this.name = name;
        this.color = color;
        this.status = status;
        this.s_n = s_n;
        this.tail = tail;
        this.breed = breed;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isS_n() {
        return s_n;
    }

    public void setS_n(boolean s_n) {
        this.s_n = s_n;
    }

    public Tail getTail() {
        return tail;
    }

    public void setTail(Tail tail) {
        this.tail = tail;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
