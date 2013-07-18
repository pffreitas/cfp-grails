package br.com.maisha.cfp.repositories.gorm

import br.com.maisha.cfp.model.Categoria
import br.com.maisha.cfp.model.Orcamento
import br.com.maisha.cfp.repositories.CategoriaRepository

/**
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 *
 */
class CategoriaGormRepository extends CrudGormRepository<Categoria, Long> implements CategoriaRepository{

	def CategoriaGormRepository(){
		super(Categoria)
	}

	/**
	 * 
	 * @see br.com.maisha.cfp.repositories.CategoriaRepository#findByNome(java.lang.String)
	 */
	public List<Categoria> findByNome(String nome) {
		println ">>>>> $nome" + Categoria.list()
		Categoria.findAllByNome(nome)
	}

	/**
	 * 
	 * @see br.com.maisha.cfp.repositories.CategoriaRepository#findByOrcamento(br.com.maisha.cfp.model.Orcamento)
	 */
	public List<Categoria> findByOrcamento(Orcamento o) {
		Categoria.findAllByOrcamento(o)
	}
}
