package com.tz.note;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tz
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ServerProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerProviderApplication.class, args);
	}
	@Slf4j
	@RestController
	static class TestController {
		@GetMapping("/hello/{str}")
		public String hello(@PathVariable String str) {
			log.info("invoked name = " + str);
			return "hello " + str;
		}
		@GetMapping("/test")
		public String test() {
			return "hello sentinel";
		}
	}

}
