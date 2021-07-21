package cn.test.config;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.redis.RedisDataSource;
import com.alibaba.csp.sentinel.datasource.redis.config.RedisConnectionConfig;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author genez
 * @date Created at 2021/07/21
 * @description TODO
 */
@Component
public class DataInit {

	@PostConstruct
	public void init() throws Exception{
		Converter<String, List<FlowRule>> parser = source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {});
		RedisConnectionConfig config = RedisConnectionConfig.builder()
				.withHost("127.0.0.1")
				.withPort(RedisConnectionConfig.DEFAULT_REDIS_PORT)
				.build();
		ReadableDataSource<String, List<FlowRule>> redisDataSource = new RedisDataSource<>(config, "test-service-init-rules", "test-service-channel", parser);
		FlowRuleManager.register2Property(redisDataSource.getProperty());
	}
}
