package com.campanha.time.services;

import com.campanha.time.dto.request.CampanhaRequest;
import com.campanha.time.dto.response.CampanhaResponse;
import com.campanha.time.entities.Campanha;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CampanhaService {	
	
	CampanhaResponse insereCampanha(CampanhaRequest campanhaRequest);
	void verificaNomeIgual(String nomeCampanha);
	boolean verificaPeriodoIgual(CampanhaRequest campanhaRequest);
	List<Campanha> alteraDataCampanhas();
	List<CampanhaResponse> campanhaVigente();
	CampanhaResponse listaId(@PathVariable Long id);
	CampanhaResponse listarQuantidade();
	CampanhaResponse excluirCampanha(Long id);
	CampanhaResponse atualizaCampanha(CampanhaRequest campanhaRequest, Long id);
	CampanhaResponse adicionarSocioTorcedor(Long idSocioTorcedor, Long idCampanha);

}
