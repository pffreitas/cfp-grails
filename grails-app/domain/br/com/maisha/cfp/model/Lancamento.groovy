package br.com.maisha.cfp.model




/**
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 */
class Lancamento {

	def String descricao
	def Date data
	def BigDecimal valor

	static belongsTo = [
		subcategoria: SubCategoria,
		contaPagamento: Conta,
		estabelecimento: Estabelecimento
	]
}
