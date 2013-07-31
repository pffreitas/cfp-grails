package br.com.maisha.cfp.model

/**
 * 
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 */
class Orcamento {

	def static enum Fase {
		FECHADO, CORRENTE, PLANEJADO
	}

	def static enum Tipo {
		ENTRADA, SAIDA
	}

	def static enum MesBase {
		JANEIRO(1), FEVEREIRO(2), MARCO(3), ABRIL(4), MAIO(5), JUNHO(6), JULHO(7), AGOSTO(8), SETEMBRO(9), OUTUBRO(10), NOVEMBRO(11), DEZEMBRO(12)

		private int mes

		def MesBase(int mes){
			this.mes = mes
		}

		def static MesBase getMesBase(int mes){
			MesBase.values().find { it.mes == mes }
		}
	}


	def String nome
	def Fase fase
	def Tipo tipo
	def MesBase mesBase
	def Orcamento planejado
	def Set categorias
	
	static hasMany = [categorias : Categoria]
	static constraints = {
		nome blank:false, nullable: false
	}
	
	def Orcamento(){
	}

	def Orcamento(Orcamento other){
		this.nome = other.nome
		this.tipo = other.tipo

		this.categorias = new HashSet<Categoria>()
		other.categorias?.each { otherCategoria ->
			addToCategorias new Categoria(otherCategoria)
		}
	}


}
