package com.study.scheduling.app;

import com.study.scheduling.app.repository.DoctorRepository;
import com.study.scheduling.app.repository.RoomRepository;
import com.study.scheduling.app.utils.CommonUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	InitializingBean setupData() {
		return () -> {
			CommonUtils.setupDoctors(doctorRepository);
			CommonUtils.setupRooms(roomRepository);
		};
	}

}
