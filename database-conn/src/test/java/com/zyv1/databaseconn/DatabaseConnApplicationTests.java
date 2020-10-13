package com.zyv1.databaseconn;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DatabaseConnApplicationTests {

	@Test
	void contextLoads() {
		List<String> stringList = new ArrayList<>();
		stringList.add("user");
		stringList.add("order");
		System.out.println(StringUtils.join(stringList.toArray(), ","));
	}

}
