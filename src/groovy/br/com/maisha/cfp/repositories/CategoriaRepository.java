package br.com.maisha.cfp.repositories;

import java.util.List;

import br.com.maisha.cfp.model.Categoria;
import br.com.maisha.cfp.model.Orcamento;

/**
 * Repositorio de dados de {@link Categoria}
 * <p/>
 * Reune funcionalidades de persistencia e recuperacao dados.
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 * 
 */
public interface CategoriaRepository {

	List<Categoria> findByNome(String nome);
	
	List<Categoria> findByOrcamento(Orcamento o);
	
}
