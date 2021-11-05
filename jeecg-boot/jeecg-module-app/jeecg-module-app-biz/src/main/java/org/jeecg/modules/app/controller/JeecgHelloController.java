package org.jeecg.modules.app.controller;

import org.jeecg.modules.app.entity.JeecgHelloEntity;
import org.jeecg.modules.app.service.IJeecgHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/test/jeecg")
@Slf4j
public class JeecgHelloController {

	@Autowired
	private IJeecgHelloService jeecgHelloService;

	@GetMapping(value = "/hello")
	public String queryPageList() {
		return jeecgHelloService.hello();
	}

}
