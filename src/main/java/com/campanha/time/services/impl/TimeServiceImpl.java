package com.campanha.time.services.impl;

import com.campanha.time.dto.request.TimeResquest;
import com.campanha.time.dto.response.TimeResponse;
import com.campanha.time.entities.Time;
import com.campanha.time.exceptions.NomeTimeNaoExisteException;
import com.campanha.time.mappers.MapperTimeToTimeResponse;
import com.campanha.time.repositories.TimeRepository;
import com.campanha.time.services.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TimeServiceImpl implements TimeService {
	
	final TimeRepository timeRepository;

	private final MapperTimeToTimeResponse mapperTimeToTimeResponse;

	@Override
	public TimeResponse incluiTime(TimeResquest timeResquest) {
		String message = "";

		Time time = new Time();

		verificaNomeIgual(timeResquest.getNome());

		time.setNome(timeResquest.getNome());

		timeRepository.save(time);

		message += " Time cadastrado com sucesso.";

		TimeResponse timeResponse = mapperTimeToTimeResponse.toDto(time);
		timeResponse.setMessage(message);
		return timeResponse;

	}

	@Override
	public void verificaNomeIgual(String nome){
		Time time = timeRepository.findByNomeIgnoreCase(nome);

		if (Objects.nonNull(time)) {
			throw new NomeTimeNaoExisteException("Nome do time ja existe!");
		}
	}

	@Override
	public List<TimeResponse> listarTimes() {
		List<TimeResponse> listaNova = new ArrayList<>();

		 List<Time> listaTime = timeRepository.findAll();

		for (Time timeLista : listaTime){
			Time timeSave = timeRepository.save(timeLista);
			TimeResponse timeResponse = mapperTimeToTimeResponse.toDto(timeSave);

			listaNova.add(timeResponse);
		}
		 return listaNova;
	}

	@Override
	public TimeResponse listarQuantidade() {
		long contagem = timeRepository.count();

		TimeResponse timeResponse = new TimeResponse();

		timeResponse.setMessage("Total de times " + "" + contagem);

		return timeResponse;
	}

	@Override
	public TimeResponse deletarTime(Long id) {
		List<Time> listaTime = timeRepository.findAll();

		for (Time timeLista : listaTime){
			if (timeLista.getId().equals(id)){
				Time time = timeRepository.getOne(id);
				timeRepository.deleteById(id);
				TimeResponse timeResponse = mapperTimeToTimeResponse.toDto(time);
				timeResponse.setMessage("Time excluido com sucesso");
				return timeResponse;
			}
		}
		TimeResponse timeResponse = new TimeResponse();
		timeResponse.setMessage("ID inválido");
		return timeResponse;
	}

	@Override
	public TimeResponse atualizaTime(Long id, TimeResquest timeResquest) {
		List<Time> listaTime = timeRepository.findAll();

		for (Time timeLista : listaTime) {
			if (timeLista.getId().equals(id)) {
				Time time = timeRepository.getOne(id);
				verificaNomeIgual(timeResquest.getNome());

				time.setNome(timeResquest.getNome());

				timeRepository.save(time);

				TimeResponse timeResponse = mapperTimeToTimeResponse.toDto(time);
				timeResponse.setMessage("Time atualizado com sucesso.");
				return timeResponse;
			}
		}
		TimeResponse timeResponse = new TimeResponse();
		timeResponse.setMessage("ID inválido");
		return timeResponse;
	}

}
