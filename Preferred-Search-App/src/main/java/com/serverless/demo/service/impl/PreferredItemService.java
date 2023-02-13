package com.serverless.demo.service.impl;

import java.util.List;

import com.serverless.demo.model.lego.Brick;
import com.serverless.demo.model.lego.Item;

public interface PreferredItemService {
	Item findPreferredItem(List<Brick> bricks);
}
