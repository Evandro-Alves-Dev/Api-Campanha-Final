package com.campanha.time.services.impl;

import com.campanha.time.dto.request.SocioTorcedorResquest;
import com.campanha.time.dto.response.SocioTorcedorResponse;
import com.campanha.time.entities.SocioTorcedor;
import com.campanha.time.entities.Time;
import com.campanha.time.exceptions.NomeDataNacimentoIgualException;
import com.campanha.time.exceptions.NomeTimeNaoExisteException;
import com.campanha.time.mappers.MapperSocioTorcedorToSocioTorcedorResponse;
import com.campanha.time.repositories.CampanhaRepository;
import com.campanha.time.repositories.SocioTorcedorRepository;
import com.campanha.time.repositories.TimeRepository;
import com.campanha.time.services.SocioTorcedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SocioTorcedorServiceImpl implements SocioTorcedorService {

	final SocioTorcedorRepository socioTorcedorRepository;

	final TimeRepository timeRepository;

	final CampanhaRepository campanhaRepository;

	private final MapperSocioTorcedorToSocioTorcedorResponse mapperSocioTorcedorToSocioTorcedorResponse;

	// Verifica se o cliente ja existe através do nome e data de nascimento
	@Override
	public SocioTorcedorResponse inserirSocio(SocioTorcedorResquest socioTorcedorResquest) {
		String message = "";

		SocioTorcedor socioTorcedor = new SocioTorcedor();

		verificaNomeDataNascimento(socioTorcedorResquest);

		Time time = timeRepository.findByNomeIgnoreCase(socioTorcedorResquest.getTime());

		if (Objects.isNull(time)) {
			throw new NomeTimeNaoExisteException("Nome do time não existe!");
		}

		//Campanha campanha = campanhaRepository.findByNomeIgnoreCase(socioTorcedorResquest.getCampanha());

		socioTorcedor.setNome(socioTorcedorResquest.getNome());
		socioTorcedor.setTime(time);
		socioTorcedor.setEmail(socioTorcedorResquest.getEmail());
		socioTorcedor.setDataNascimento(socioTorcedorResquest.getDataNascimento());
		//socioTorcedor.setCampanha(campanha);

		socioTorcedorRepository.save(socioTorcedor);

		message += " Sócio cadastrado com sucesso.";

		SocioTorcedorResponse socioTorcedorResponse = mapperSocioTorcedorToSocioTorcedorResponse.toDto(socioTorcedor);
		socioTorcedorResponse.setMessage(message);

		return socioTorcedorResponse;
	}

	@Override
	public void verificaNomeDataNascimento(SocioTorcedorResquest socioTorcedorRequest){
		List<SocioTorcedor> listaTodosSociosTorcedores = socioTorcedorRepository.findAll();
		for (SocioTorcedor socioLista : listaTodosSociosTorcedores) {
			if (socioLista.getDataNascimento().equals(socioTorcedorRequest.getDataNascimento())
					&& socioLista.getNome().equals(socioTorcedorRequest.getNome())) {
				throw new NomeDataNacimentoIgualException("Sócio existente. Tente outro nome ou verifique a data de nascimento!");
			}
		}
	}

	@Override
	public List<SocioTorcedorResponse> listarSocioTorcedor() {
		List<SocioTorcedorResponse> novaLista = new ArrayList<>();

		List<SocioTorcedor> listaTodosSociosTorcedores = socioTorcedorRepository.findAll();
		for (SocioTorcedor socioLista : listaTodosSociosTorcedores) {
			SocioTorcedor socioTorcedorsave = socioTorcedorRepository.save(socioLista);

			SocioTorcedorResponse socioTorcedorResponse = mapperSocioTorcedorToSocioTorcedorResponse.toDto(socioTorcedorsave);

			novaLista.add(socioTorcedorResponse);
		}
		return novaLista;
	}

	@Override
	public SocioTorcedorResponse listarQuantidade() {
		long contagem = socioTorcedorRepository.count();

		SocioTorcedorResponse socioTorcedorResponse = new SocioTorcedorResponse();

		socioTorcedorResponse.setMessage("Total de campanhas " + "" + contagem);

		return socioTorcedorResponse;
	}

	@Override
	public SocioTorcedorResponse excluiSocioTorcedor(Long id) {
		List<SocioTorcedor> listaTodosSociosTorcedores = socioTorcedorRepository.findAll();
		for (SocioTorcedor socioLista : listaTodosSociosTorcedores) {
			if (socioLista.getId().equals(id)) {
				SocioTorcedor socioTorcedor = socioTorcedorRepository.getOne(id);
				socioTorcedorRepository.deleteById(id);
				SocioTorcedorResponse socioTorcedorResponse = mapperSocioTorcedorToSocioTorcedorResponse.toDto(socioTorcedor);
				socioTorcedorResponse.setMessage("Sócio torcedor excluido com sucesso!");
				return socioTorcedorResponse;
			}
		}
		SocioTorcedorResponse socioTorcedorResponse = new SocioTorcedorResponse();
		socioTorcedorResponse.setMessage("ID invalido, favor informar um ID valido");
		return socioTorcedorResponse;
	}	
	
	@Override
	public SocioTorcedorResponse atualizaSocioTorcedor(SocioTorcedorResquest socioTorcedorResquest, Long id) {
		List<SocioTorcedor> listaTodosSociosTorcedores = socioTorcedorRepository.findAll();
		for (SocioTorcedor socioLista : listaTodosSociosTorcedores){
			if (socioLista.getId().equals(id)) {

				SocioTorcedor socioTorcedor = socioTorcedorRepository.getOne(id);

				verificaNomeDataNascimento(socioTorcedorResquest);

				Time time = timeRepository.findByNomeIgnoreCase(socioTorcedorResquest.getTime());

				if (Objects.isNull(time)) {
					throw new NomeTimeNaoExisteException("Nome do time não existe!");
				}

				//Campanha campanha = campanhaRepository.findByNomeIgnoreCase(socioTorcedorResquest.getCampanha());

				socioTorcedor.setNome(socioTorcedorResquest.getNome());
				socioTorcedor.setEmail(socioTorcedorResquest.getEmail());
				socioTorcedor.setDataNascimento(socioTorcedorResquest.getDataNascimento());
				socioTorcedor.setTime(time);
				//socioTorcedor.setId(id);
				//socioTorcedor.setCampanha(campanha);

				socioTorcedorRepository.save(socioTorcedor);

				SocioTorcedorResponse socioTorcedorResponse = mapperSocioTorcedorToSocioTorcedorResponse.toDto(socioTorcedor);
				socioTorcedorResponse.setMessage("Sócio Torcedor atualizado com sucesso");

				return socioTorcedorResponse;
			}
		}
		SocioTorcedorResponse socioTorcedorResponse = new SocioTorcedorResponse();
		socioTorcedorResponse.setMessage("ID inválido");
		return socioTorcedorResponse;
	}
}
