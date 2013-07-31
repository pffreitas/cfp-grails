package br.com.maisha.cfp

import grails.converters.JSON
import br.com.maisha.cfp.business.OrcamentoBusiness
import br.com.maisha.cfp.model.Orcamento
import br.com.maisha.cfp.model.Orcamento.Tipo

/**
 * Responsavel pelo controle da tela de Planejamento, incluindo cadastro de Orcamento -> Categtoria -> Subcategoria.
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 *
 */
class PlanejamentoController {

	def OrcamentoBusiness orcamentoBusiness
	
	def index() {
	}
	
	def novoOrcamento(){
		def Orcamento o = new Orcamento(nome: params.nome, tipo: Tipo.valueOf(params.tipo))
		def saved = orcamentoBusiness.criarOrcamento(o)
		
		if(o.hasErrors()){
			response.status = 503
			render o.errors as JSON
		}
		
		render saved as JSON
	}
}
