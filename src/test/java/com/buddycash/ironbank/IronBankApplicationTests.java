package com.buddycash.ironbank;

import com.buddycash.ironbank.configuration.BaseApplicationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
class IronBankApplicationTests extends BaseApplicationTest {

	@Test
	void contextLoads() {
	}

}
