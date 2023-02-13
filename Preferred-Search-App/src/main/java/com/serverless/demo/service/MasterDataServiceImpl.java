package com.serverless.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.serverless.demo.model.lego.MasterData;
import com.serverless.demo.service.impl.MasterDataService;

public class MasterDataServiceImpl implements MasterDataService {
	List<MasterData>  masterDataStore;
	
	AmazonDynamoDB dynamoDb;
	public MasterDataServiceImpl(AmazonDynamoDB dynamoDb) {
		this.dynamoDb=dynamoDb;
	}
	public MasterDataServiceImpl(List<MasterData>  masterDataStore) {
		this.masterDataStore=masterDataStore;
		masterDataStore.add(new MasterData(1, MasterData.Status.NORMAL, 3.0));
		masterDataStore.add(new MasterData(2, MasterData.Status.OUTGOING, 3.0));
		masterDataStore.add(new MasterData(3, MasterData.Status.NORMAL, 3.0));
		
	}
	
	@Override
	public List<MasterData> getMasterDataList() {
		return masterDataStore;
	}

}
