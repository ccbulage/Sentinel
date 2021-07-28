package cn.test.config;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.redis.RedisDataSource;
import com.alibaba.csp.sentinel.datasource.redis.config.RedisConnectionConfig;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author genez
 * @date Created at 2021/07/21
 * @description 应用规则初始化
 */
@Component
public class DataInit {

	@PostConstruct
	public void init() throws Exception{
		RedisConnectionConfig config = RedisConnectionConfig.builder()
				.withHost("127.0.0.1")
				.withPort(RedisConnectionConfig.DEFAULT_REDIS_PORT)
				.build();

		// 流控规则
		ReadableDataSource<String, List<FlowRule>> flowDataSource = new RedisDataSource<>(config,
				"test-service-flow-rules", "test-service-flow-rules-channel",
				source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
		FlowRuleManager.register2Property(flowDataSource.getProperty());

		// 熔断规则
		ReadableDataSource<String, List<DegradeRule>> degradeDataSource = new RedisDataSource<>(config,
				"test-service-degrade-rules", "test-service-degrade-rules-channel",
				source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {}));
		DegradeRuleManager.register2Property(degradeDataSource.getProperty());

		// 热点规则
		ReadableDataSource<String, List<ParamFlowRule>> hotSpotDataSource = new RedisDataSource<>(config,
				"test-service-hotSpot-rules", "test-service-hotSpot-rules-channel",
				source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {}));
		ParamFlowRuleManager.register2Property(hotSpotDataSource.getProperty());

		// 系统规则
		ReadableDataSource<String, List<SystemRule>> systemDataSource = new RedisDataSource<>(config,
				"test-service-system-rules", "test-service-system-rules-channel",
				source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {}));
		SystemRuleManager.register2Property(systemDataSource.getProperty());

		// 授权规则
		ReadableDataSource<String, List<AuthorityRule>> authorityDataSource = new RedisDataSource<>(config,
				"test-service-authority-rules", "test-service-authority-rules-channel",
				source -> JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {}));
		AuthorityRuleManager.register2Property(authorityDataSource.getProperty());



	}
}
