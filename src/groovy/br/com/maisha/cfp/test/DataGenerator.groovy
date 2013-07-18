package br.com.maisha.cfp.test

import br.com.maisha.cfp.model.Categoria
import br.com.maisha.cfp.model.Orcamento
import br.com.maisha.cfp.model.Recorrencia
import br.com.maisha.cfp.model.SubCategoria
import br.com.maisha.cfp.model.Orcamento.Fase
import br.com.maisha.cfp.model.Orcamento.MesBase
import br.com.maisha.cfp.model.Orcamento.Tipo
import br.com.maisha.cfp.model.Recorrencia.Granularidade
import br.com.maisha.cfp.repositories.CategoriaRepository
import br.com.maisha.cfp.repositories.OrcamentoRepository
import br.com.maisha.cfp.repositories.SubcategoriaRepository

/**
 * Gerador de massa de dados para os cenarios de teste.
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 *
 */
class DataGenerator {

	/** Repo de Orcamento */ 
	def OrcamentoRepository orcamentoRepository

	def CategoriaRepository categoriaRepository

	def SubcategoriaRepository subcategoriaRepository

	/**
	 * Gera a massa base.
	 */
	def void generateData(){
		dumpData()

		(1..10).each { x ->
			Orcamento o = new Orcamento()
			o.with{
				nome = "Orcamento $x"
				id = "$x" as Long
				fase = Orcamento.Fase.PLANEJADO
				tipo = Orcamento.Tipo.SAIDA
				mesBase = Orcamento.MesBase.getMesBase(x)

				Categoria c = new Categoria()
				c.with {
					id = ("$x" as Long) * 1000
					nome = "Categoria $id"

					SubCategoria sc = new SubCategoria()
					sc.with {
						id = ("$x" as Long) * 10000
						nome = "Subcategoria $id"
						gerarAlerta = true
						valor = 120 * id
					}

					c.addToSubcategorias sc
				}

				o.addToCategorias c
			}

			Orcamento.withTransaction {
				orcamentoRepository.save(o)
			}
			
			Orcamento.findAll().each{
				println ">>>> o cats" + it.categorias
			}
		}

		SubCategoria scRecorenteMensal = new SubCategoria()
		scRecorenteMensal.with {
			nome = "Subcategoria Mensal"
			gerarAlerta = true
			valor = 120 
			recorrencia = new Recorrencia()
			recorrencia.with {
				granularidade = Granularidade.MENSAL
				subcategoria = scRecorenteMensal
				vencimento = 10
			}
			categoria = categoriaRepository.findByNome("Categoria 2000").first()
		}
		subcategoriaRepository.save(scRecorenteMensal)

		Orcamento oPlanejado = new Orcamento()
		oPlanejado.with {
			nome = "Orcamento Paulo Freitas"
			fase = Fase.PLANEJADO
			mesBase = MesBase.MAIO
			tipo = Tipo.SAIDA
		}
		orcamentoRepository.save(oPlanejado)

		Orcamento corrente = new Orcamento()
		corrente.with {
			nome = "Orcamento Paulo Freitas"
			fase = Fase.CORRENTE
			mesBase = MesBase.ABRIL
			tipo = Tipo.SAIDA
			planejado = oPlanejado
		}

		orcamentoRepository.save(corrente)
	}

	/**
	 * Remove toda a massa inserida em {@link DataGenerator#generateData()}
	 */
	def void dumpData(){
		orcamentoRepository.deleteAll()
	}
}
