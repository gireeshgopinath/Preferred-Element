package com.serverless.demo.model.lego;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Item {
    private int id;
    private List<Brick> bricks;
    
    private Map<Integer,Long> bricksMap;
    
   
    public void addBricks(Brick brick) {
    	bricks.add(brick);
			
	}

	public Item(int id, List<Brick> bricks) {
        this.id = id;
        this.bricks = bricks;
    }

    public int getId() {
        return id;
    }

    public List<Brick> getBricks() {
        return bricks;
    }
    

    public Map<Integer, Long> getBricksMap() {
		return bricksMap;
	}

	public void addBrickCount(int brickId,Long count) {
		if(this.bricksMap==null) {
			this.bricksMap=new HashMap<Integer, Long>();
		}
		this.bricksMap.put(brickId, count);
		
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bricks);
    }
}
