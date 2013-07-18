package br.com.maisha.cfp.repositories;

import java.util.List;

import br.com.maisha.cfp.model.Categoria;
import br.com.maisha.cfp.model.SubCategoria;

/**
 * Repositorio de dados de {@link SubCategoria}
 * <p/>
 * Reune funcionalidades de persistencia e recuperacao dados.
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 * 
 */
public interface SubcategoriaRepository {

	List<SubCategoria> findByNome(String nome);

	List<SubCategoria> findByCategoria(Categoria c);
}
