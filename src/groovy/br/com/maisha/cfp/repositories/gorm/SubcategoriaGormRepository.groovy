package br.com.maisha.cfp.repositories.gorm

import br.com.maisha.cfp.model.Categoria
import br.com.maisha.cfp.model.SubCategoria
import br.com.maisha.cfp.repositories.SubcategoriaRepository

/**
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 *
 */
class SubcategoriaGormRepository extends CrudGormRepository<SubCategoria, Long> implements SubcategoriaRepository{

	def SubcategoriaGormRepository(){
		super(SubCategoria)
	}

	/**
	 * 
	 * @see br.com.maisha.cfp.repositories.SubcategoriaRepository#findByNome(java.lang.String)
	 */
	public List<SubCategoria> findByNome(String nome) {
		SubCategoria.findAllByNome(nome)
	}

	/**
	 * 
	 * @see br.com.maisha.cfp.repositories.SubcategoriaRepository#findByCategoria(br.com.maisha.cfp.model.Categoria)
	 */
	public List<SubCategoria> findByCategoria(Categoria c) {
		SubCategoria.findAllByCategoria(c)
	}
}
