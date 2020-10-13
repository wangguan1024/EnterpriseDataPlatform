package com.zyv1.databasemanager;

import com.zyv1.databasemanager.dao.DbinfoDao;
import com.zyv1.databasemanager.service.DbinfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DbinfoApplicationTests {

	@Autowired
	private DbinfoService dbinfoService;
	private DbinfoDao dbinfoDao;

	@Test
	void contextLoads() {
	}

}
