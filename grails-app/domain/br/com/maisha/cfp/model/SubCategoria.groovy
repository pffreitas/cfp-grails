package br.com.maisha.cfp.model


class SubCategoria {

	def String nome
	def BigDecimal valor
	def Boolean gerarAlerta
	def TipoSubcategoria tipo = TipoSubcategoria.EVENTUAL
	def Recorrencia recorrencia

	static belongsTo = [categoria: Categoria]
	static hasMany = [lancamentos: Lancamento]

	static constraints = {
		recorrencia nullable: true 
	}
	
	def static enum TipoSubcategoria {
		EVENTUAL, RECORRENTE
	}

	def SubCategoria(){
	}

	def SubCategoria(SubCategoria other){
		this.nome = other.nome
		this.valor = other.valor
		this.gerarAlerta = other.gerarAlerta
		if(other.recorrencia)
			this.recorrencia = new Recorrencia(other.recorrencia)
	}

}
