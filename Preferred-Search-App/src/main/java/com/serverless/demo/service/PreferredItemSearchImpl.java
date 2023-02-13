package com.serverless.demo.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.serverless.demo.model.lego.Brick;
import com.serverless.demo.model.lego.Item;
import com.serverless.demo.model.lego.MasterData;
import com.serverless.demo.service.impl.ItemService;
import com.serverless.demo.service.impl.MasterDataService;
import com.serverless.demo.service.impl.PreferredItemService;
/**
 * PreferredItemSearchImpl
 * @author Gireesh Gopinath
 *
 */
public class PreferredItemSearchImpl implements PreferredItemService {

	private MasterDataService masterDataService;
	private ItemService itemService;

	public PreferredItemSearchImpl(MasterDataService masterDataService, ItemService itemService) {
		this.masterDataService = masterDataService;
		this.itemService = itemService;
	}


	/**
	 * find the preferred item using map 
	 */
	public Item findPreferredItem(List<Brick> bricks) {

		List<Item> items =itemService.getItemList();

		//convert list of item to db structure
		Map<Brick, Set<Item>>brickToItemsMap= getBrickmappingDB(items);

		Map<Brick, Long> countMap = bricks.stream()
				.collect(Collectors.groupingBy(brick -> brick, Collectors.counting()));// group them by brick
		Set<Item> matchingItems = null;
		
		//search bricks exist in items
		for (Brick brick : bricks) {
			Set<Item> itemsForBrick = brickToItemsMap.get(brick);
			if (itemsForBrick == null) 
				return null;

			itemsForBrick=countfilter(brick.hashCode(),countMap.get(brick),itemsForBrick);// filter count of occurance of bricks in an item

			if(matchingItems==null) {

				matchingItems = new HashSet<>(itemsForBrick);
			}else {
				matchingItems.retainAll(itemsForBrick);
				if (matchingItems.isEmpty()) {
					return null;
				}
			}

		}

		// master data service and get the masterData list
		List<MasterData> masterDataList = masterDataService.getMasterDataList();



		return getpreferedItem(matchingItems,masterDataList);
	}

	//filter for bricks count should be same 
	public static Set<Item> countfilter(int brickId,Long count,Set<Item> matchedItems) {
		Set<Item> filteredItems=new HashSet<Item>();
		for(Item item:matchedItems) {
			if(item.getBricksMap().get(brickId)>=count)
				filteredItems.add(item);
		}
		return filteredItems;
	}


	// process we need to added when add new brick to item
	public Map<Brick, Set<Item>> getBrickmappingDB(List<Item> items){

		Map<Brick, Set<Item>> brickToItemsMap = new HashMap<>();
		for (Item item : items) {
			Map<Brick, Long> countMap = item.getBricks().stream()
					.collect(Collectors.groupingBy(brick -> brick, Collectors.counting()));

			for (Brick brick : item.getBricks()) {
				Set<Item> matchingItems = brickToItemsMap.get(brick);
				Long count=countMap.get(brick);
				if (matchingItems == null) {
					matchingItems = new HashSet<>();
					brickToItemsMap.put(brick, matchingItems);
				}


				item.addBrickCount(brick.hashCode(), count);
				matchingItems.add(item);
			}
		}
		return brickToItemsMap;
	}


// using matched item to fetch most prefer item 
	public Item getpreferedItem(Set<Item> items,List<MasterData>masterDataList) {

		Map<Integer, Item> itemMap = items.stream().collect(Collectors.toMap(Item::getId, Function.identity()));

		//filter master data based on match list
		Optional<MasterData> prefredMasterData=	masterDataList.stream().filter(data -> itemMap.containsKey(data.getId())).sorted().findFirst();

		//identify the preferred item
		if(prefredMasterData.isPresent())
			return  items.stream().filter(it -> prefredMasterData.get().getId()==it.getId()).findFirst().get();

		return null;
	}

}
