package com.alibaba.csp.sentinel.metric.extension.test;

import com.alibaba.csp.sentinel.metric.extension.callback.MetricEntryCallback;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author genez
 * @date Created at 2021/07/22
 * @description 测试
 */
@Slf4j
public class MetricCallbackInitTest {

	@Test
	public void testGetCanonicalName() {
//		com.alibaba.csp.sentinel.metric.extension.callback.MetricEntryCallback
		log.info("WHAT: {}", MetricEntryCallback.class.getCanonicalName());
	}
}
