package br.com.maisha.cfp.repositories.gorm;

import br.com.maisha.cfp.model.Orcamento
import br.com.maisha.cfp.model.Orcamento.Fase
import br.com.maisha.cfp.repositories.OrcamentoRepository

/**
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 *
 */
public class OrcamentoGormRepository extends CrudGormRepository<Orcamento, Long> implements OrcamentoRepository {

	def OrcamentoGormRepository(){
		super(Orcamento)
	}

	/**
	 * 
	 * @see br.com.maisha.cfp.repositories.OrcamentoRepository#findByNome(java.lang.String)
	 */
	public List<Orcamento> findByNome(String nome) {
		Orcamento.findAllByNome(nome)
	}

	@Override
	public List<Orcamento> findByNomeAndFase(String nome, Fase fase) {
		Orcamento.findAllByNomeAndFase(nome, fase)
	}
}
