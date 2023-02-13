package com.serverless.demo.function;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.demo.model.ServerlessInput;
import com.serverless.demo.model.ServerlessOutput;
import com.serverless.demo.model.lego.Brick;
import com.serverless.demo.model.lego.Item;
import com.serverless.demo.service.ItemServiceImpl;
import com.serverless.demo.service.MasterDataServiceImpl;
import com.serverless.demo.service.PreferredItemSearchImpl;
import com.serverless.demo.service.impl.ItemService;
import com.serverless.demo.service.impl.MasterDataService;
import com.serverless.demo.service.impl.PreferredItemService;

/**
 * Lambda function that triggered by the API Gateway event "GET /". It reads query parameter "id" for the article id and retrieves
 * the content of that preferred item returns the content as the payload of the HTTP Response.
 */
public class PreferredSearchlambda implements RequestHandler<ServerlessInput, ServerlessOutput> {
    // DynamoDB table name for storing item
    private static final String ITEM_TABLE_NAME = System.getenv("ITEM_TABLE_NAME");
    
  
    @Override
    public ServerlessOutput handleRequest(ServerlessInput serverlessInput, Context context) {
        // Using builder to create the clients could allow us to dynamically load the region from the AWS_REGION environment
        // variable. Therefore we can deploy the Lambda functions to different regions without code change.    
        AmazonDynamoDB dynamoDb = AmazonDynamoDBClientBuilder.standard().build();
        ServerlessOutput output = new ServerlessOutput();
        
        try {
            if (serverlessInput.getQueryStringParameters() == null || serverlessInput.getQueryStringParameters() == null) {
                    throw new Exception("Parameter bricks is null.  in query must be provided!");
            }
            List<Brick> queryData= serverlessInput.getQueryStringParameters();
           	MasterDataService masterdata =new MasterDataServiceImpl(dynamoDb);
   			ItemService itemService=new ItemServiceImpl(dynamoDb);
   		
   			PreferredItemService preferedItemService=	new PreferredItemSearchImpl(masterdata,itemService);
   			Item preferedItem= preferedItemService.findPreferredItem(queryData);
            
            output.setStatusCode(200);
            output.setBody(preferedItem);
        } catch (Exception e) {
            output.setStatusCode(500);
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            output.setBody(null);
        }

        return output;
    }
}