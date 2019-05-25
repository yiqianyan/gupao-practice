package spring.test.service.impl;


import spring.annotation.MyService;
import spring.test.service.IDemoService;

/**
 * 核心业务逻辑
 */
@MyService
public class DemoServiceImpl implements IDemoService {

	public String get(String name) {
		return String.valueOf(1/0);
	}

}
