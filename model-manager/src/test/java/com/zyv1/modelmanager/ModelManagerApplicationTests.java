package com.zyv1.modelmanager;

import com.zyv1.modelmanager.entity.ModelInfo;
import com.zyv1.modelmanager.service.ModelManagerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ModelManagerApplicationTests {

	@Autowired
	private ModelManagerService modelManagerService;
	@Test
	void contextLoads() {
		System.out.println(modelManagerService.judgeRepeat("model_name", "LSTM"));
	}

}
