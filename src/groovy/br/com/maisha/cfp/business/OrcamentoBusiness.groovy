package br.com.maisha.cfp.business

import br.com.maisha.cfp.model.Categoria
import br.com.maisha.cfp.model.Orcamento
import br.com.maisha.cfp.model.SubCategoria
import br.com.maisha.cfp.model.Orcamento.Fase
import br.com.maisha.cfp.model.Orcamento.MesBase
import br.com.maisha.cfp.repositories.CategoriaRepository
import br.com.maisha.cfp.repositories.OrcamentoRepository
import br.com.maisha.cfp.repositories.SubcategoriaRepository

/**
 * Reune a inteligencia associada ao gerenciamento de Orcamentos, Categorias e Subcategorias.
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 */
class OrcamentoBusiness {

	/** Repositorio de Orcamentos */
	def OrcamentoRepository orcamentoRepository

	/** Repositorio de Categorias */
	def CategoriaRepository categoriaRepository

	/** Repositorio de Subcategorias. */
	def SubcategoriaRepository subcategoriaRepository

	/**
	 * Realiza o fechamento do orcamento dado.
	 * <p/>
	 * Isso consiste em mudar o orcamento corrente para fechado, e o planejado para corrente.
	 * Um novo orcamento planejado e' criado copiando o novo orcamento corrente.
	 * O mes base do novo orcamento corrente sera' o mes atual.
	 *  
	 * <p/>
	 * E' importante notar que esta operacao afeta as Categorias e Subcategorias em cascata.
	 *  
	 * @param oCorrente Orcamento Corrente.
	 */
	def void fecharOrcamento(Orcamento oCorrente, boolean abrirProximoPlanejado = false){
		checkFaseOrcamento(oCorrente, Fase.CORRENTE)

		def oPlanejado = oCorrente.planejado

		//muda o orcamento corrente para fechado.
		oCorrente.fase = Fase.FECHADO
		oCorrente.planejado = null
		orcamentoRepository.save(oCorrente)

		if(abrirProximoPlanejado)
			abrirOrcamento(oPlanejado)
	}

	/**
	 * Abre um orcamento planejado transformando-o em orcamento corrente para o mes atual.
	 * <p/>
	 * Um novo orcamento planejado e criado copiando o novo orcamento corrente.
	 * 
	 * @param oPlanejado Orcamento planejado a entrar em vigencia para o mes atual.
	 */
	def void abrirOrcamento(final Orcamento oPlanejado){
		checkFaseOrcamento(oPlanejado, Fase.PLANEJADO)

		//muda o planejado para corrente.
		Orcamento oCorrente = oPlanejado
		oCorrente.fase = Fase.CORRENTE
		oCorrente.mesBase = MesBase.getMesBase(Calendar.getInstance().get(Calendar.MONTH) + 1)

		//cria um novo planejado e associa ao novo corrente.
		Orcamento novoPlanejado = new Orcamento(oCorrente)
		criarOrcamento(novoPlanejado)
		oCorrente.planejado = novoPlanejado

		orcamentoRepository.save(oCorrente)
	}

	/**
	 * Cria um novo Orcamento planejado para o proximo mes.
	 * 
	 * @param o Orcamento configurado com nome, tipo e opcionalmente categorias. 
	 */
	def Orcamento criarOrcamento(Orcamento o){
		if(o) {
			//configura mes base sendo o proximo mes em relacao ao mes atual.
			Calendar c = Calendar.getInstance()
			o.mesBase = MesBase.getMesBase(c.get(Calendar.MONTH ) + 2)

			//obriga a ser um orcamento planejado
			o.fase = Fase.PLANEJADO
			o.planejado = null

			def saved = orcamentoRepository.save(o)

			return saved
		}
	}

	/**
	 * TODO javadoc-me
	 * @param c
	 * @return
	 */
	def Categoria criarCategoria(Categoria c){
		if(c && c.orcamento){
			checkFaseOrcamento(c.orcamento, Fase.PLANEJADO)
			return categoriaRepository.save(c)
		}
	}

	/**
	 * TODO javadoc-me
	 * @param sc
	 * @return
	 */
	def SubCategoria criarSubCategoria(SubCategoria sc){
		if(sc && sc.categoria && sc.categoria.orcamento){
			checkFaseOrcamento(sc.categoria.orcamento, Fase.PLANEJADO)
			return subcategoriaRepository.save(sc)
		}
	}

	/**
	 * Verifica se o orcamento recebido em <code>o</code> se encontra na fase esperada.
	 * 
	 * @param o Orcamento a ser verificado.
	 * @param faseEsperada Fase esperada
	 */
	private void checkFaseOrcamento(Orcamento o, Fase faseEsperada){
		if(!o || o.fase != faseEsperada)
			throw new IllegalStateException("Orcamento nao esta na fase esperada: $faseEsperada")
	}
}
