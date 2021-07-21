package cn.test.controller;

import cn.test.domain.User;
import cn.test.service.UserService;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author genez
 * @date Created at 2021/07/19
 * @description Sentinel功能测试服务模拟
 */
@Slf4j
@RestController
@RequestMapping({"/test"})
public class TestController {

	@Autowired
	private UserService userService;

	@GetMapping("/query-user/{uid}")
	public String queryUser(@PathVariable("uid") Long uid) {

		String user = null;
		try {
			user = userService.getUser(uid);
		} catch (Exception e) {
			if (e instanceof FlowException) {
				log.warn("controller被限流...");
			}
		}
		return user;
	}



}
