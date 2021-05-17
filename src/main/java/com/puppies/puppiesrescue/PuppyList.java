package com.puppies.puppiesrescue;

import java.util.List;

public class PuppyList {
    private List<Puppy> puppies;
    private int totalSize;
    private int numAvailable;

    public PuppyList() {}

    public PuppyList(List<Puppy> puppies) {
        this.puppies = puppies;
    }

    public List<Puppy> getPuppies() {
        return puppies;
    }

    public boolean isEmpty () {
        return puppies == null || puppies.isEmpty();
    }

    public int getTotalSize () {
        return puppies.size();
    }

    public int getNumAvailable () {
        int count = 0;
        for (Puppy puppy: puppies) {
            if (puppy.getStatus() == Puppy.Status.READY) {
                count++;
            }
        }
        return count;
    }
}
