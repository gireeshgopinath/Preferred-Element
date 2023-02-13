package com.serverless.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.serverless.demo.model.lego.Brick;
import com.serverless.demo.model.lego.Item;
import com.serverless.demo.service.impl.ItemService;

public class ItemServiceImpl implements ItemService {

	List<Item> items;
	
	AmazonDynamoDB dynamoDb;
	
	public ItemServiceImpl(AmazonDynamoDB dynamoDb){
		this.dynamoDb=dynamoDb;
	}
	
	public ItemServiceImpl(List<Item> items){
		this.items=items;
	}
	
	
	 
	 
     
	@Override
	public List<Item> getItemList() {
		return items;
	}

	
	
}
