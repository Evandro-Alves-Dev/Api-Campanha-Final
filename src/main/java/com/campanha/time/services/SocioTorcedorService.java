package com.campanha.time.services;

import com.campanha.time.dto.request.SocioTorcedorResquest;
import com.campanha.time.dto.response.SocioTorcedorResponse;

import java.util.List;

public interface SocioTorcedorService {

	SocioTorcedorResponse inserirSocio(SocioTorcedorResquest socioTorcedorResquest);
	void verificaNomeDataNascimento(SocioTorcedorResquest socioTorcedorRequest);
	List<SocioTorcedorResponse> listarSocioTorcedor();
	SocioTorcedorResponse listarQuantidade();
	SocioTorcedorResponse excluiSocioTorcedor(Long id);
	SocioTorcedorResponse atualizaSocioTorcedor(SocioTorcedorResquest socioTorcedorResquest, Long id);
}
