package com.buddycash.ironbank;

import io.zonky.test.db.postgres.embedded.FlywayPreparer;
import io.zonky.test.db.postgres.junit.EmbeddedPostgresRules;
import io.zonky.test.db.postgres.junit.PreparedDbRule;
import io.zonky.test.db.postgres.junit.SingleInstancePostgresRule;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IronBankApplicationTests {

	@Rule
	public SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	@Rule
	public PreparedDbRule db =
			EmbeddedPostgresRules.preparedDatabase(
					FlywayPreparer.forClasspathLocation("db/migration"));
	@Test
	void contextLoads() {
	}

}
