package com.campanha.time.services.impl;

import com.campanha.time.dto.request.CampanhaRequest;
import com.campanha.time.dto.response.CampanhaResponse;
import com.campanha.time.entities.Campanha;
import com.campanha.time.entities.SocioTorcedor;
import com.campanha.time.entities.Time;
import com.campanha.time.exceptions.CampanhaNaoExisteException;
import com.campanha.time.exceptions.NomeCampanhaIgualException;
import com.campanha.time.exceptions.NomeTimeNaoExisteException;
import com.campanha.time.exceptions.SocioNaoExisteException;
import com.campanha.time.mappers.MapperCampanhaToCampanhaResponse;
import com.campanha.time.repositories.CampanhaRepository;
import com.campanha.time.repositories.SocioTorcedorRepository;
import com.campanha.time.repositories.TimeRepository;
import com.campanha.time.services.CampanhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampanhaServiceImpl implements CampanhaService {

	private final CampanhaRepository campanhaRepository;

	private final TimeRepository timeRepository;

	private final SocioTorcedorRepository socioTorcedorRepository;

	private final MapperCampanhaToCampanhaResponse mapperCampanhaToCampanhaResponse;

	// Insere uma nova campanha e ja faz validações

	@Override
	public CampanhaResponse insereCampanha(CampanhaRequest campanhaRequest) {
		String message = "";

		Campanha campanha = new Campanha();

		verificaNomeIgual(campanhaRequest.getNome());

		if (verificaPeriodoIgual(campanhaRequest)) {
			message = "Periodo já existe. Será acrescentado um dia na data final"
					+ "de todas campanhas já existente e será mantido a data cadastrada agora!";

			alteraDataCampanhas();
		}

		Time time = timeRepository.findByNomeIgnoreCase(campanhaRequest.getTime());

		if (Objects.isNull(time)) {
			throw new NomeTimeNaoExisteException("Nome do time não existe!");
		}

		campanha.setNome(campanhaRequest.getNome());
		campanha.setDataInicioVigencia(campanhaRequest.getDataInicioVigencia());
		campanha.setDataFimVigencia(campanhaRequest.getDataFimVigencia());
		campanha.setTime(time);

		campanhaRepository.save(campanha);

		message += " Campanha inserida com sucesso.";

		CampanhaResponse campanhaResponse = mapperCampanhaToCampanhaResponse.toDto(campanha);
		campanhaResponse.setMessage(message);

		return campanhaResponse;
	}

	// Verifica se o nome da nova campanha ja existe
	@Override
	public void verificaNomeIgual(String nome) {
		Campanha campanha = campanhaRepository.findByNomeIgnoreCase(nome);

		if (Objects.nonNull(campanha)) {
			throw new NomeCampanhaIgualException("Campanha existente. Tente outro nome!");
		}
	}

	// Verifica se o periodo da nova campanha é igual a alguma ja cadastrada e
	// insere 1 dia nas campanhas ja cadastradas
	@Override
	public boolean verificaPeriodoIgual(CampanhaRequest campanhaRequest) {
		List<Campanha> listaTodasCampanhas = campanhaRepository.findAll();

		for (Campanha campanhaLista : listaTodasCampanhas) {
			if (campanhaLista.getDataFimVigencia().equals(campanhaRequest.getDataFimVigencia())
					&& campanhaLista.getDataInicioVigencia().equals(campanhaRequest.getDataInicioVigencia())) {
				return true;
			}
		}
		return false;
	}

	// Acrscenta 1 dia em todas as campanhas ja cadastradas
	@Override
	public List<Campanha> alteraDataCampanhas() {
		List<Campanha> listaGeral = campanhaRepository.findAll();
		List<Campanha> listaAtualizada = listaGeral.stream().map(n -> {
			Campanha campanha = new Campanha();
			n.setDataFimVigencia(n.getDataFimVigencia().plusDays(1));
			return campanha;
		}).collect(Collectors.toList());
		return listaAtualizada;
	}

	// Retorna apenas as datas que não estão vencidas de acordo com o dia em que o
	// relatório foi puxado
	@Override
	public List<CampanhaResponse> campanhaVigente() {
		List<CampanhaResponse> novaLista = new ArrayList<>();

		List<Campanha> listaTodasCampanhas = campanhaRepository.findAll();

		for (Campanha campanhaLista : listaTodasCampanhas) {
			if (campanhaLista.getDataFimVigencia().isAfter(LocalDate.now())) {
				Campanha campanhaSave = campanhaRepository.save(campanhaLista);

				CampanhaResponse campanhaResponse = mapperCampanhaToCampanhaResponse.toDto(campanhaSave);

				novaLista.add(campanhaResponse);
			}
		}
		return novaLista;
	}

	// Listar por id
	@Override
	public CampanhaResponse listaId(Long id) {
		List<Campanha> listaTodasCampanhas = campanhaRepository.findAll();
		for (Campanha campanhaLista : listaTodasCampanhas) {
			if (campanhaLista.getId().equals(id)) {
				Campanha campanha = campanhaRepository.getOne(id);
				CampanhaResponse campanhaResponse = mapperCampanhaToCampanhaResponse.toDto(campanha);
				return campanhaResponse;
			}
		}
		CampanhaResponse campanhaResponse = new CampanhaResponse();
		campanhaResponse.setMessage("ID inválido");
		return campanhaResponse;
	}

	// Lista a quantidadde de campanhas cadastradas
	@Override
	public CampanhaResponse listarQuantidade() {
		long contagem = campanhaRepository.count();

		CampanhaResponse CampanhaResponse = new CampanhaResponse();

		CampanhaResponse.setMessage("Total de campanhas " + "" + contagem);

		return CampanhaResponse;
	}

	// Exclui campanhas pelo ID
	@Override
	public CampanhaResponse excluirCampanha(Long id) {
		List<Campanha> listaTodasCampanhas = campanhaRepository.findAll();
		for (Campanha campanhaLista : listaTodasCampanhas) {
			if (campanhaLista.getId().equals(id)) {
				Campanha campanha = campanhaRepository.getOne(id);
				campanhaRepository.deleteById(id);
				CampanhaResponse campanhaResponse = mapperCampanhaToCampanhaResponse.toDto(campanha);
				campanhaResponse.setMessage("Campanha excluida com sucesso.");
				return campanhaResponse;
			}
		}
		CampanhaResponse campanhaResponse = new CampanhaResponse();
		campanhaResponse.setMessage("ID inválido");
		return campanhaResponse;
	}

	@Override
	public CampanhaResponse atualizaCampanha(CampanhaRequest campanhaRequest, Long id) {
		List<Campanha> listaTodasCampanhas = campanhaRepository.findAll();
		for (Campanha campanhaLista : listaTodasCampanhas) {
			if (campanhaLista.getId().equals(id)) {

				Campanha campanha = campanhaRepository.getOne(id);

				verificaNomeIgual(campanhaRequest.getNome());

				Time time = timeRepository.findByNomeIgnoreCase(campanhaRequest.getTime());

				if (Objects.isNull(time)) {
					throw new NomeTimeNaoExisteException("Nome do time não existe!");
				}

				campanha.setNome(campanhaRequest.getNome());
				campanha.setDataInicioVigencia(campanhaRequest.getDataInicioVigencia());
				campanha.setDataFimVigencia(campanhaRequest.getDataFimVigencia());
				campanha.setTime(time);
				campanha.setId(id);

				campanhaRepository.save(campanha);

				CampanhaResponse campanhaResponse = mapperCampanhaToCampanhaResponse.toDto(campanha);
				campanhaResponse.setMessage("Campanha atualizada com sucesso.");

				return campanhaResponse;
			}
		}
		CampanhaResponse campanhaResponse = new CampanhaResponse();
		campanhaResponse.setMessage("ID inválido");
		return campanhaResponse;
	}

	@Override
	public CampanhaResponse adicionarSocioTorcedor(Long idSocioTorcedor, Long idCampanha){

		Campanha campanha = campanhaRepository.findById(idCampanha).get();
		if (campanha == null) {
			throw new CampanhaNaoExisteException("Campanha não existe.");
		}
		SocioTorcedor socioTorcedor = socioTorcedorRepository.findById(idSocioTorcedor).get();

		if(socioTorcedor == null){
			throw new SocioNaoExisteException("Sócio torcedor não possui cadastro.");
		}

		campanha.getSocioTorcedor().add(socioTorcedor);
		campanhaRepository.save(campanha);
		CampanhaResponse campanhaResponse = mapperCampanhaToCampanhaResponse.toDto(campanha);
		campanhaResponse.setMessage("Cadastrado com sucesso");
		return campanhaResponse;
	}
}