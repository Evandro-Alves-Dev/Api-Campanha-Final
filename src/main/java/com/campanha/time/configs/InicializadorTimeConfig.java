package com.campanha.time.configs;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.campanha.time.entities.Time;
import com.campanha.time.repositories.TimeRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class InicializadorTimeConfig {	
		
	private final TimeRepository timerepository;
	
	@PostConstruct
	public void inicializar() {
		
		if (timerepository.count() > 0) {
			return;
		}
		
		Time time = new Time();
		time.setNome("Palmeiras");
		timerepository.save(time);
		
		Time time1 = new Time();
		time1.setNome("Santos");
		timerepository.save(time1);		
	}
}
	
	
	


