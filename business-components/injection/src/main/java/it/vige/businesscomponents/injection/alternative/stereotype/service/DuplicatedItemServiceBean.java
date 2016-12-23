package it.vige.businesscomponents.injection.alternative.stereotype.service;

import java.util.LinkedList;
import java.util.List;

import it.vige.businesscomponents.injection.alternative.stereotype.ServiceStereotype;
import it.vige.businesscomponents.injection.interceptor.service.Item;
import it.vige.businesscomponents.injection.interceptor.service.ItemService;

@ServiceStereotype
public class DuplicatedItemServiceBean implements ItemService {
	
	List<Item> items = new LinkedList<Item>();

	@Override
	public void create(Item item) {
		items.add(item);
		Item duplicatedItem = new Item();
		duplicatedItem.setName(item.getName());
		items.add(duplicatedItem);
	}

	@Override
	public List<Item> getList() {
		return items;
	}

}
