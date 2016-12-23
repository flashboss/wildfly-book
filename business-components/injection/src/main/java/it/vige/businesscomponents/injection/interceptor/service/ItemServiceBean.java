package it.vige.businesscomponents.injection.interceptor.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import it.vige.businesscomponents.injection.interceptor.Audit;
import it.vige.businesscomponents.injection.interceptor.Logging;

@Named
public class ItemServiceBean implements ItemService {
	
	List<Item> items = new ArrayList<Item>();


    @Audit
    @Logging
    @Override
    public void create(Item item) {
    	items.add(item);
    }

    @Audit
    @Logging
    @Override
    public List<Item> getList() {
        return items;
    }

}
