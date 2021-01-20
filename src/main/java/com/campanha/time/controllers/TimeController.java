package com.campanha.time.controllers;

import com.campanha.time.dto.request.TimeResquest;
import com.campanha.time.dto.response.TimeResponse;
import com.campanha.time.services.impl.TimeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/time")
public class TimeController {

	final TimeServiceImpl timeServiceImpl;

	// Inclui um novo time
	@PostMapping
	public ResponseEntity<TimeResponse> incluir(@RequestBody TimeResquest timeResquest) {
		TimeResponse cadastrar = timeServiceImpl.incluiTime(timeResquest);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cadastrar.getId()).toUri();

		return ResponseEntity.created(location).body(cadastrar);
	}

	// Consultar todos os times
	@GetMapping
	public ResponseEntity<List<TimeResponse>> listar() {
		List<TimeResponse> listaTodosTimes = timeServiceImpl.listarTimes();
		return ResponseEntity.ok(listaTodosTimes);
	}
	
	//Consultar a quantidade de times
	@GetMapping("/quantidade")
	public ResponseEntity<TimeResponse> listarQuantidade() {
		TimeResponse totalTimes = timeServiceImpl.listarQuantidade();
		return ResponseEntity.ok(totalTimes);
	}

	// Exclui um time
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<TimeResponse> excluir(@PathVariable Long id) {
		TimeResponse idExcluido = timeServiceImpl.deletarTime(id);

		return ResponseEntity.status(HttpStatus.OK).body(idExcluido);
	}

	// Atualiza um time
	@PutMapping(path = "/{id}")
	public ResponseEntity<TimeResponse> atualiza(@PathVariable Long id, @RequestBody TimeResquest timeResquest) {
		 TimeResponse timeResponse = timeServiceImpl.atualizaTime(id, timeResquest);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.buildAndExpand(timeResponse.getId()).toUri();

		return ResponseEntity.created(location).body(timeResponse);
	}
}
