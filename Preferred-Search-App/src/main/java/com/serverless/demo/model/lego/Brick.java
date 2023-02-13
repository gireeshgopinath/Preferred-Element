package com.serverless.demo.model.lego;

import java.util.List;

public class Brick {
    private int designId;
   
    
    private List<Integer> colorCodes;

    public Brick(int designId, List<Integer> colorCodes) {
        this.designId = designId;
        this.colorCodes = colorCodes;
    }

    public int getDesignId() {
        return designId;
    }

    public List<Integer> getColorCodes() {
        return colorCodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brick brick = (Brick) o;

        if (designId != brick.designId) return false;
        return colorCodes.equals(brick.colorCodes);
    }

    @Override
    public int hashCode() {
        int result = designId;
        result = 31 * result + colorCodes.hashCode();
        return result;
    }
}
