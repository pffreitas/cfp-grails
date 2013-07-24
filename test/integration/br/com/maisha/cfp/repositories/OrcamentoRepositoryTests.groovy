package br.com.maisha.cfp.repositories;

import static org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

import br.com.maisha.cfp.model.Categoria
import br.com.maisha.cfp.model.Orcamento
import br.com.maisha.cfp.model.Orcamento.Fase
import br.com.maisha.cfp.model.Orcamento.MesBase
import br.com.maisha.cfp.test.DataGenerator



/**
 * Casos de teste para o repositorio {@link OrcamentoRepository}
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 *
 */
class OrcamentoRepositoryTests {

	/** Objeto sob teste. */
	def OrcamentoRepository orcamentoRepository

	/** Gerador de massa de dados. */
	def DataGenerator dataGenerator

	@Before
	def void before(){
		dataGenerator.generateData()
		
		Orcamento o = new Orcamento()
		o.with{
			nome = "Carro"
			id = 10000L
			fase = Orcamento.Fase.PLANEJADO
			mesBase = MesBase.getMesBase(1)
			tipo = Orcamento.Tipo.SAIDA
		}
		orcamentoRepository.save(o)
	}

	@After
	def void after(){
		dataGenerator.dumpData()
	}

	/**
	 * Titulo: Teste basico de persistencia de um Orcamento
	 * 
	 * Dado que possuo uma instancia comum de Orcamento com todos os atributos not-null preenchidos.
	 * Quando aciono o metodo "save" do repositorio.
	 * Entao verifico que o orcamento foi persistido com sucesso.
	 */
	@Test
	def void test1(){
		Orcamento o = new Orcamento()
		o.with {
			nome = "Receita"
			id = 1000000L
			fase = Orcamento.Fase.CORRENTE
			mesBase = MesBase.getMesBase(1)
			tipo = Orcamento.Tipo.ENTRADA
		}

		def saved = orcamentoRepository.save(o)
		

		assertNotNull saved
		assertNotNull saved.id
	}

	/**
	 * Titulo: Validar busca por nome
	 * 
	 * Dado que possuo um registro de Orcamento com nome "Carro" em minha base de dados.
	 * Quando aciono o metodo findByNome passando "Carro"
	 * Entao verifico que o registro foi retornado
	 */
	@Test
	def void test2(){
		def lst = orcamentoRepository.findByNome("Carro")
		assertNotNull lst
		assertFalse lst.isEmpty()
	}

	/**
	 * Titulo: Validar cascade de orcamento -> categoria
	 * 
	 * Dado que possuo uma instancia de orcamento que possui uma categoria associada.
	 * Quando realizo a persistencia deste orcamento.
	 * Entao verifico que o orcamento foi persistido com sucesso bem como a categoria associada.
	 */
	@Test
	def void test3(){
		Categoria c1 = new Categoria()
		c1.with {
			id = 21L
			nome = "Salario"
		}

		Orcamento o = new Orcamento()
		o.with {
			nome = "Receita"
			id = 1000000L
			fase = Orcamento.Fase.CORRENTE
			tipo = Orcamento.Tipo.ENTRADA
			mesBase = MesBase.getMesBase(1)
			addToCategorias(c1)
		}

		def saved = orcamentoRepository.save(o)

		assertNotNull saved
		assertNotNull saved.id
		assertNotNull saved.categorias
		assertEquals 1, saved.categorias.size()
		
		saved.categorias.each {
			assertNotNull it.id
		}
	}



	/**
	 * Titulo: Validar a copia de orcamento em cascata.
	 *
	 * Dado que possuo um orcamento "o1"
	 * Quando instancio um novo orcamento passando "o1" como parametro
	 * Verifico que os dados de "o1" sao copiados para a nova instancia
	 * E essa nova instancia pode ser persistida com sucesso.
	 */
	@Test
	def void test4(){
		def o1 = orcamentoRepository.findByNome("Orcamento 1")[0]
		
		//Cria um novo orcamento baseado em o1
		def oNew = new Orcamento(o1)
		oNew.nome = "NEW ORCAMENTO"
		oNew.fase = Fase.FECHADO
		oNew.mesBase = MesBase.getMesBase(2)

		//os IDs estao null
		assertNull oNew.id
		assertNull oNew.categorias.first().id
		assertNull oNew.categorias.first().subcategorias.first().id
		
		//persistencia
		orcamentoRepository.save(oNew)

		//IDs nao esto mais null
		oNew = orcamentoRepository.findByNome("NEW ORCAMENTO")[0]
		assertNotNull oNew.id
		assertFalse oNew.id == o1.id

		assertEquals "Categoria 1000", oNew.categorias.first().nome
		assertNotNull oNew.categorias.first().id

		assertEquals "Subcategoria 10000", oNew.categorias.first().subcategorias.first().nome
		assertNotNull oNew.categorias.first().subcategorias.first().id
	}
}
