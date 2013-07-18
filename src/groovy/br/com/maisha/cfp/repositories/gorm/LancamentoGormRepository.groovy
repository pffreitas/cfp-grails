package br.com.maisha.cfp.repositories.gorm

import br.com.maisha.cfp.model.Lancamento
import br.com.maisha.cfp.repositories.LancamentoRepository

/**
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 *
 */
class LancamentoGormRepository extends CrudGormRepository<Lancamento, Long> implements LancamentoRepository {


	public LancamentoGormRepository() {
		super(Lancamento);
	}
}
