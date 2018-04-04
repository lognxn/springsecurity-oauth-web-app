package com.imooc.web.async;

import java.util.concurrent.Callable;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * 测试异步访问
 * @author longxn
 *
 */
@RestController
public class AsyncController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MockQueue mockQueue;
	@Autowired
	private DeferredResultHolder deferredResultHolder;
	
	/**
	 * 测试多线程来处理请求, 主线程会立即返回, 然后重新接受别的请求
	 * 由副线程运行结束后返回给客户端
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/order")
	public DeferredResult<String> order() throws Exception{
		logger.info("主线程开始");
		
		String orderNum = RandomStringUtils.randomNumeric(8);//生成8位订单号
		mockQueue.setPlaceOrder(orderNum);//模拟放入到消息队列
		
		DeferredResult<String> result = new DeferredResult<>();
		deferredResultHolder.getMap().put(orderNum, result);
		
//		Callable<String> result = () -> {
//			logger.info("副线程开始");
//			Thread.sleep(1000);
//			logger.info("副线程结束");
//			return "success";};
			
		logger.info("主线程结束");
		return result;
	}
}
