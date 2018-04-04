package com.imooc.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 通过MockQueue来模拟消息队列
 * @author longxn
 *
 */
@Component
public class MockQueue {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private String placeOrder;
	private String completeOrder;
	
	public String getPlaceOrder() {
		return placeOrder;
	}
	
	public void setPlaceOrder(String placeOrder) throws Exception {
		
		new Thread(() -> {
			logger.info("接到下单请求, "+placeOrder);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}//表示正在处理
			this.completeOrder = placeOrder;//表示订单完成
			logger.info("下单请求处理完毕, "+placeOrder);
		}).start();
	}
	
	public String getCompleteOrder() {
		return completeOrder;
	}
	
	public void setCompleteOrder(String completeOrder) {
		this.completeOrder = completeOrder;
	}
	
	
}
