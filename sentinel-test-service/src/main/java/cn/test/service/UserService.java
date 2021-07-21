package cn.test.service;


import cn.test.domain.User;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author genez
 * @date Created at 2021/07/19
 * @description 模拟限流：定义资源
 */
@Service
@Slf4j
public class UserService {

	/**
	 * after limiting
	 */
	public static final String USER_RES = "getUser";

	/*static {
		initFlowRule(USER_RES, 3d);
	}*/

	public static final String REQUEST_PASS = "pass";
	public static final String REQUEST_BLOCK = "block";

	@SentinelResource(value = USER_RES, /*blockHandler = "blockHandler",*/
			entryType = EntryType.IN, fallback = "fallBackHandler")
	public String getUser(Long uid) {
		User user = new User();
		user.setUid(uid);
		user.setName("user-"+uid);
		return JSON.toJSONString(user);
	}

	/**
	 * 限流阻断处理
	 * @param uid
	 * @param ex
	 * @return
	 */
	public String blockHandler(Long uid, BlockException ex) {
		log.warn("被限流: ", ex);
		return "Oops, block occurred at " + uid;
	}

	/**
	 * 回退处理（当@SentinelResource中blockHandler属性没有配置时才生效）
	 * @param uid
	 * @param e
	 * @return
	 */
	public String fallBackHandler(Long uid, Throwable e) {
		log.warn("限流之后，fallBack处理... ... ");
		return "fallback occurred at " + uid;
	}

	private static void initFlowRule(String resourceName, Double limitCount) {
		FlowRule flowRule = new FlowRule();
		flowRule.setResource(resourceName);
//		flowRule.setGrade();  // 默认是QPS
		flowRule.setCount(limitCount);
		flowRule.setLimitApp(RuleConstant.LIMIT_APP_DEFAULT);
		flowRule.setStrategy(RuleConstant.STRATEGY_DIRECT);
//		flowRule.setControlBehavior();  // 默认立即拒绝，拒绝方式为抛出FlowException
		FlowRuleManager.loadRules(Lists.newArrayList(flowRule));
	}

}
