package com.github.tabreubr.musiclass;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class MusiclassApplicationTests {

	@Test
	void contextLoads() {
	}

}
