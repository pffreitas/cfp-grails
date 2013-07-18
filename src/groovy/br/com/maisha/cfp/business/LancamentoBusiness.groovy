package br.com.maisha.cfp.business

import br.com.maisha.cfp.model.Lancamento
import br.com.maisha.cfp.repositories.LancamentoRepository

/**
 * Reune funcionalidades relacionadas ao gerenciamento de Lancamentos.
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 *
 */
class LancamentoBusiness {

	def LancamentoRepository lancamentoRepository

	/**
	 * TODO verificar se orcamento para o qual este lancamento esta sendo realizado e' o corrente,
	 * 
	 * @param l Lancamento a ser realizado.
	 */
	def void realizarLancamento(Lancamento l){
		lancamentoRepository.save(l)
	}
}
