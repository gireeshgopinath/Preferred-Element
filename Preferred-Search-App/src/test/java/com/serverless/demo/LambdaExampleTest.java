package com.serverless.demo;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.serverless.demo.model.lego.Brick;
import com.serverless.demo.model.lego.Item;
import com.serverless.demo.model.lego.MasterData;
import com.serverless.demo.service.ItemServiceImpl;
import com.serverless.demo.service.MasterDataServiceImpl;
import com.serverless.demo.service.PreferredItemSearchImpl;
import com.serverless.demo.service.impl.ItemService;
import com.serverless.demo.service.impl.MasterDataService;
import com.serverless.demo.service.impl.PreferredItemService;
/**
 * test case for solution
 * @author Gireesh Gopinath
 *
 */
public class LambdaExampleTest {
	PreferredItemService preferedItemService;
	Item  currentPrefferedItem;
	@Before
    public void setUp() throws Exception {
		
		
		List<Item> items=constructItem();
		currentPrefferedItem=items.get(0);
		List<MasterData> masterDataStore=new ArrayList<MasterData>();
		masterDataStore.add(new MasterData(1, MasterData.Status.NORMAL, 3.0));
		masterDataStore.add(new MasterData(2, MasterData.Status.OUTGOING, 3.0));
		masterDataStore.add(new MasterData(3, MasterData.Status.NORMAL, 3.0));
		MasterDataService masterdata =new MasterDataServiceImpl(masterDataStore);
		
		ItemService itemService=new ItemServiceImpl(items);
		
		preferedItemService=	new PreferredItemSearchImpl(masterdata,itemService);
		
    }

	@Test
    public void findPreferredItem() throws Exception {
		Brick b1=new Brick(0,Arrays.asList(1,2));
		Brick b2=new Brick(2, Arrays.asList(2,2));
		Brick b3=new Brick(2, Arrays.asList(2,2,3));
		List<Brick> bricks=new ArrayList<Brick>();
		bricks.add(b1);
		bricks.add(b2);
		
        assertTrue("Prefred  Item Found is correct",new Item(1,bricks).equals(preferedItemService.findPreferredItem(bricks)));
    }
	
	@Test
    public void findPreferredNotFount() throws Exception {
		Brick b1=new Brick(0,Arrays.asList(1,2));
		Brick b2=new Brick(2, Arrays.asList(2,2));
		List<Brick> bricks=new ArrayList<Brick>();
		bricks.add(b1);
		bricks.add(b2);
		bricks.add(b2);
		
        assertTrue("Prefred not Item Found",null==preferedItemService.findPreferredItem(bricks));
    }
	
	
	public static List<Item> constructItem() {
		Brick b1=new Brick(0,Arrays.asList(1,2));
		Brick b2=new Brick(2, Arrays.asList(2,2));
		Brick b3=new Brick(2, Arrays.asList(2,2,3));
		
		List<Item> items=new ArrayList<Item>();
		
		items.add(new Item(1,Arrays.asList(b1,b2,b3)));
		items.add(new Item(2,Arrays.asList(b1,b2)));
		items.add(new Item(3,Arrays.asList(b1,b2)));
		
		return items;
		
	}
}
