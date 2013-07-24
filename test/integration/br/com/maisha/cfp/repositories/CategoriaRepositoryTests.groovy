package br.com.maisha.cfp.repositories;

import static org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

import br.com.maisha.cfp.model.Categoria
import br.com.maisha.cfp.test.DataGenerator



/**
 * Casos de teste para o repositorio {@link OrcamentoRepository}
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 *
 */
class CategoriaRepositoryTests {

	/** Objeto sob teste. */
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
	 * Titulo: Validar a copia de Categorias.
	 *
	 * Dado que possuo uma Categoria "c1"
	 * Quando instancio uma nova Categoria passando "c1" como parametro
	 * Verifico que os dados de "c1" sao copiados para a nova instancia
	 * E essa nova instancia pode ser persistida com sucesso.
	 */
	@Test
	def void test1(){
		def Categoria c1 = categoriaRepository.findByNome("Categoria 1000").first()

		Categoria cNew = new Categoria(c1)
		assertNull cNew.id
		assertEquals "Categoria 1000", cNew.nome
		assertNull cNew.orcamento
		assertEquals 1, cNew.subcategorias.size()

		// associa um orcamento para poder gravar..
		cNew.orcamento = c1.orcamento
		
		def saved = categoriaRepository.save(cNew)

		assertNotNull saved.id
		assertFalse saved.id == c1.id
	}
}
