package com.campanha.time.controllers;

import com.campanha.time.dto.request.SocioTorcedorResquest;
import com.campanha.time.dto.response.SocioTorcedorResponse;
import com.campanha.time.services.impl.SocioTorcedorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sociotorcedor")
public class SocioTorcedorController {
	
	final SocioTorcedorServiceImpl socioTorcedorServiceImpl;

	// Incluir um novo sócio torcedor
	@PostMapping
	public ResponseEntity<SocioTorcedorResponse> criar(@RequestBody SocioTorcedorResquest socioTorcedorResquest) {
		SocioTorcedorResponse response = socioTorcedorServiceImpl.inserirSocio(socioTorcedorResquest);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();

		return ResponseEntity.created(location).body(response);
	}

	// Consulta todos os sócios torcedores
	@GetMapping
	public ResponseEntity<List<SocioTorcedorResponse>> listar() {
		List<SocioTorcedorResponse> socioTorcedorResponse = socioTorcedorServiceImpl.listarSocioTorcedor();

		return ResponseEntity.ok(socioTorcedorResponse);
	}
	
	//Listar a quantidade de socio torcedores
	@GetMapping("/quantidade")
	public ResponseEntity<SocioTorcedorResponse> total() {
		SocioTorcedorResponse totalSocioTorcedor = socioTorcedorServiceImpl.listarQuantidade();

		return ResponseEntity.status(HttpStatus.CREATED).body(totalSocioTorcedor);
	}

	// Excluir um sócio torcedor
	@DeleteMapping(path = "/{id}")	
	public ResponseEntity<SocioTorcedorResponse> excluir(@PathVariable Long id) {
		SocioTorcedorResponse idExcluido = socioTorcedorServiceImpl.excluiSocioTorcedor(id);

		return ResponseEntity.status(HttpStatus.OK).body(idExcluido);
	}

	// Atualiza um sócio torcedor
	@PutMapping(path = "/{id}")	
	public ResponseEntity<SocioTorcedorResponse> atualiza(@PathVariable Long id, @RequestBody SocioTorcedorResquest socioTorcedorResquest ) {
		SocioTorcedorResponse socioTorcedorResponse = socioTorcedorServiceImpl.atualizaSocioTorcedor(socioTorcedorResquest, id);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.buildAndExpand(socioTorcedorResponse.getId()).toUri();

		return ResponseEntity.created(location).body(socioTorcedorResponse);
	}
}
