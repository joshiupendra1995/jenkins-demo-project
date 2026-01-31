package com.jenkins_demo.demo;

import com.jenkins_demo.demo.controller.DemoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
class DemoJenkinsApplicationTests {

	@Autowired
	private DemoController demoController;

	@Test
	void testHello(){
        StepVerifier.create(demoController.hello()).expectNext("hello").verifyComplete();
	}

}
