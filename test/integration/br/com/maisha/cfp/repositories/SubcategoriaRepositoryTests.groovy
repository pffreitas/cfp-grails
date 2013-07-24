package br.com.maisha.cfp.repositories;

import static org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

import br.com.maisha.cfp.model.SubCategoria
import br.com.maisha.cfp.test.DataGenerator



/**
 * Casos de teste para o repositorio {@link OrcamentoRepository}
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 *
 */
class SubcategoriaRepositoryTests {

	/** Objeto sob teste. */
	def SubcategoriaRepository subcategoriaRepository

	def CategoriaRepository categoriaRepository

	/** Gerador de massa de dados. */
	def DataGenerator dataGenerator

	@Before
	def void before(){
		dataGenerator.generateData()
	}

	@After
	def void after(){
		dataGenerator.dumpData()
	}

	/**
	 * Titulo: Validar a copia de Subcategorias.
	 *
	 * Dado que possuo uma Subcategoria "sc1"
	 * Quando instancio uma nova Subcategoria passando "sc1" como parametro
	 * Verifico que os dados de "sc1" sao copiados para a nova instancia
	 * E essa nova instancia pode ser persistida com sucesso.
	 */
	@Test
	def void test1(){
		def SubCategoria sc1 = subcategoriaRepository.findByNome("Subcategoria 10000").first()

		SubCategoria scNew = new SubCategoria(sc1)
		assertNull scNew.id
		assertEquals sc1.nome, scNew.nome
		assertEquals sc1.valor, scNew.valor
		assertEquals sc1.gerarAlerta, scNew.gerarAlerta
		assertNull scNew.categoria
		assertTrue scNew.lancamentos == null

		// associa uma categoria para poder gravar..
		scNew.categoria = sc1.categoria

		def saved = subcategoriaRepository.save(scNew)

		assertNotNull saved.id
		assertFalse saved.id == sc1.id
	}

	/**
	 * Titulo: Validar a copia de Subcategorias com recorrencia.
	 *
	 * Dado que possuo uma Subcategoria "sc1" que possui recorrencia mensal
	 * Quando instancio uma nova Subcategoria passando "sc1" como parametro
	 * Verifico que os dados de "sc1" sao copiados para a nova instancia
	 * E essa nova instancia pode ser persistida com sucesso.
	 */
	@Test
	def void test2(){
		def SubCategoria sc1 = subcategoriaRepository.findByNome("Subcategoria Mensal").first()

		SubCategoria scNew = new SubCategoria(sc1)
		assertNull scNew.id
		assertEquals sc1.nome, scNew.nome
		assertEquals sc1.valor, scNew.valor
		assertEquals sc1.gerarAlerta, scNew.gerarAlerta
		assertNull scNew.categoria
		assertTrue scNew.lancamentos == null

		assertNotNull scNew.recorrencia

		// associa uma categoria para poder gravar..
		scNew.categoria = sc1.categoria

		def saved = subcategoriaRepository.save(scNew)

		assertNotNull saved.id
		assertFalse saved.id == sc1.id
	}
}
