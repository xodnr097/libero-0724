
package com.libero.service.buy;

import com.libero.service.domain.Pay;

public interface BuyService {
	
	//public void removeBuy(); -> addCancelation
	
	public void getBuyStatus();
	
	public void updateBuyStatus();
	
	public void getDeliveryStatus();
	
	public void updateDeliveryStatus();
	
	public void addCancelation();
	
	public Pay addBuy(Pay pay);
	
	public Pay getUserBuy(String userId, String payNo);
	
	public void getUserBuyList();
	
	public void getFactoryBuy();
	
	public void getFactoryBuyList();
	
	//public void getPayStatus();
	
	//public void updatePayStatus();
}
