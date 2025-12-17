package com.muhardin.endy.belajar.htmx;

import com.muhardin.endy.belajar.htmx.config.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestContainersConfig.class)
class BelajarHtmxApplicationTests {

	@Test
	void contextLoads() {
	}

}
