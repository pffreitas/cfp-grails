package br.com.maisha.cfp.model

/**
 * TODO javadoc-me
 * 
 * @author pfreitas
 *
 */
class Categoria {

	def String nome
	
	static belongsTo = [orcamento: Orcamento]
	static hasMany = [subcategorias: SubCategoria]
	
	def Categoria(){
	}

	def Categoria(Categoria other){
		this.nome = other.nome

		this.subcategorias = new HashSet<SubCategoria>()
		other.subcategorias?.each { otherSc ->
			addToSubcategorias new SubCategoria(otherSc)
		}
	}
	
	def String toString(){
		nome
	}

}
