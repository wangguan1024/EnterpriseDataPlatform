package com.example.dbinfo;

import com.example.dbinfo.entity.Dbinfo;
import com.example.dbinfo.service.DbinfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DbinfoApplicationTests {

	@Autowired
	private DbinfoService dbinfoService;

	@Test
	void contextLoads() {
		System.out.println(dbinfoService.DeleteByUrl("zzzzurl"));
	}

}
