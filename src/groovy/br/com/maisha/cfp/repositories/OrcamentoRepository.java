package br.com.maisha.cfp.repositories;

import java.util.List;

import br.com.maisha.cfp.model.Orcamento;
import br.com.maisha.cfp.model.Orcamento.Fase;

/**
 * Repositorio de dados de {@link Orcamento}
 * <p/>
 * Reune funcionalidades de persistencia e recuperacao dados.
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 * 
 */
public interface OrcamentoRepository extends CrudRepository<Orcamento, Long> {

	List<Orcamento> findByNome(String nome);

	List<Orcamento> findByNomeAndFase(String nome, Fase fase);

}
