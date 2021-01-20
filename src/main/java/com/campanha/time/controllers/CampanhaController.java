package com.campanha.time.controllers;

import com.campanha.time.dto.request.CampanhaRequest;
import com.campanha.time.dto.response.CampanhaResponse;
import com.campanha.time.entities.Time;
import com.campanha.time.services.impl.CampanhaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/campanha")
public class CampanhaController {
	
	final CampanhaServiceImpl campanhaServiceImpl;	

	// Incluir uma nova campanha
	@PostMapping	
	public ResponseEntity<CampanhaResponse> criar(@RequestBody CampanhaRequest campanhaResquest, Time time) {
		CampanhaResponse response = campanhaServiceImpl.insereCampanha(campanhaResquest);	
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();

		return ResponseEntity.created(location).body(response);
	}
		
	// Consultar todas as campanhas
	@GetMapping	
	public ResponseEntity<List<CampanhaResponse>> listar() {
		List<CampanhaResponse> campanhaResponse = campanhaServiceImpl.campanhaVigente();
		
		return ResponseEntity.ok(campanhaResponse);
	}
	
	// Consultar por id
	@GetMapping("/{id}")
	public ResponseEntity<CampanhaResponse> listaId(@PathVariable Long id){
		CampanhaResponse campanhaResponse = campanhaServiceImpl.listaId(id);
		
		return ResponseEntity.ok(campanhaResponse);
	}
	
	// Consultar a quantidade de campanhas
	@GetMapping("/quantidade")	
	public ResponseEntity<CampanhaResponse> total(){
		CampanhaResponse totalCampanhas = campanhaServiceImpl.listarQuantidade();

		return ResponseEntity.status(HttpStatus.OK).body(totalCampanhas);		
	}
	
	// Deletar uma campanha
	@DeleteMapping(path = "/{id}")	
	public ResponseEntity<CampanhaResponse> deletar(@PathVariable Long id) {
		CampanhaResponse idExcluido = campanhaServiceImpl.excluirCampanha(id);

		return ResponseEntity.status(HttpStatus.OK).body(idExcluido);
	}

	// Atualizar uma campanha
	@PutMapping(path = "/{id}")
	public ResponseEntity<CampanhaResponse> atualizar(@PathVariable Long id, @RequestBody CampanhaRequest campanhaRequest) {
		CampanhaResponse campanhaResponse = campanhaServiceImpl.atualizaCampanha(campanhaRequest, id);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.buildAndExpand(campanhaResponse.getId()).toUri();

		return ResponseEntity.created(location).body(campanhaResponse);
	}

	@PostMapping("/{idCampanha}/add/{idSocioTorcedor}")
	public ResponseEntity<CampanhaResponse> adicionaSocioTorcedor(@PathVariable Long idSocioTorcedor, Long idCampanha){
		CampanhaResponse campanhaResponse = campanhaServiceImpl.adicionarSocioTorcedor(idSocioTorcedor, idCampanha);
		return ResponseEntity.ok(campanhaResponse);
	}

}
