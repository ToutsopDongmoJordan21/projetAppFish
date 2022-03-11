package com.example.demo;

import com.example.demo.files.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

@SpringBootApplication
@Slf4j
@EnableSwagger2
public class ProjetAppFishApplication implements CommandLineRunner {

	@Resource
	FileService storageService;

	public static void main(String[] args) {
		SpringApplication.run(ProjetAppFishApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Initialize folder uploading file");
	}

}
