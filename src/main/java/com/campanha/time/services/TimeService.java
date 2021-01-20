package com.campanha.time.services;

import com.campanha.time.dto.request.TimeResquest;
import com.campanha.time.dto.response.TimeResponse;

import java.util.List;

public interface TimeService {

	TimeResponse incluiTime(TimeResquest timeResquest);
	void verificaNomeIgual(String nome);
	List<TimeResponse> listarTimes();
	TimeResponse listarQuantidade();
	TimeResponse deletarTime(Long id);
	TimeResponse atualizaTime(Long id, TimeResquest timeResquest);
}
