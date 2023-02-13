package com.serverless.demo.model.lego;

import java.util.Objects;

public class MasterData implements Comparable<MasterData> {
    private int itemId;
    private Status status;
    private double price;

    public enum Status {
        NORMAL, NOVELTY, OUTGOING, DISCONTINUED
    }

    public MasterData(int id, Status status, double price) {
        this.itemId = id;
        this.status = status;
        this.price = price;
    }

    public int getId() {
        return itemId;
    }

    public Status getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }
    
    @Override
	public int compareTo(MasterData o) {

        int statusComparison = Integer.compare(this.getStatus().ordinal(), o.getStatus().ordinal());
        if (statusComparison != 0) {
            return statusComparison;
        } else {
            return Double.compare(this.getPrice(), o.getPrice());
        }
	}
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MasterData masterData = (MasterData) o;
        return this.getId() == masterData.getId() &&
               this.getStatus().equals(masterData.getStatus()) && this.getPrice()== masterData.getPrice();
    }

}
