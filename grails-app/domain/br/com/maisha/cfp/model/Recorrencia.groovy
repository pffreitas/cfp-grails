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

	def Granularidade granularidade
	def Integer vencimento
	static belongsTo = [subcategoria: SubCategoria]
	

	def Recorrencia(){
	}

	def Recorrencia(Recorrencia other){
		this.granularidade = other.granularidade
		this.vencimento = other.vencimento
	}
}
