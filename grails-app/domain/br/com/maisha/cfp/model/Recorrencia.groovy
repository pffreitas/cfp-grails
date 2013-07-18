package br.com.maisha.cfp.model

/**
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 *
 */
class Recorrencia {

	def static enum Granularidade{
		MENSAL
	}

	def SubCategoria subcategoria
	def Granularidade granularidade
	def Integer vencimento


	def Recorrencia(){
	}

	def Recorrencia(Recorrencia other){
		this.granularidade = other.granularidade
		this.vencimento = other.vencimento
	}
}
