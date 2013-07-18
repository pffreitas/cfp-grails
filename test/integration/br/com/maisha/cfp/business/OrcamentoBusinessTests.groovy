package br.com.maisha.cfp.business

import static org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

import br.com.maisha.cfp.model.Orcamento
import br.com.maisha.cfp.model.Orcamento.Fase
import br.com.maisha.cfp.model.Orcamento.MesBase
import br.com.maisha.cfp.model.Orcamento.Tipo
import br.com.maisha.cfp.repositories.OrcamentoRepository
import br.com.maisha.cfp.test.DataGenerator

/**
 * Casos de teste relacionados a {@link OrcamentoBusiness}
 * 
 * @author Paulo Freitas (pfreitas1@gmail.com)
 *
 */
class OrcamentoBusinessTests {

	/** Repositorio de orcamento. */
	def OrcamentoBusiness bean

	/** Repositorio de orcamentos */
	def OrcamentoRepository orcamentoRepository

	/** Gerador de massa de dados. */
	def DataGenerator dataGenerator

	@Before
	def void before(){
		println ">>>>>> $orcamentoRepository, $dataGenerator"
		dataGenerator.generateData()
	}

	@After
	def void after(){
		dataGenerator.dumpData()
	}



	/**
	 * Titulo: Criar um orcamento para o mes corrente
	 * 
	 * Dado que desejo criar um novo orcamento.
	 * Quando aciono o metodo {@link OrcamentoBusiness#criarOrcamento(br.com.maisha.cfp.model.Orcamento)} passando o orcamento a ser criado.
	 * Enao verifico que o orcamento foi devidamente persistido seguindo as regras de negocio.
	 */
	@Test
	def void test1(){

		Orcamento o = new Orcamento()
		o.with{
			nome = "Familia"
			tipo = Tipo.SAIDA
		}

		def saved = bean.criarOrcamento(o)

		assertNotNull saved
		assertNotNull saved.id
		assertEquals Fase.PLANEJADO, saved.fase
		assertNull saved.planejado
		assertEquals MesBase.getMesBase(Calendar.getInstance().get(Calendar.MONTH) + 2), saved.mesBase
	}

	/**
	 * Titulo: validar fechamento de orcamento
	 * 
	 * Dado que possuo um orcamento corrente e desejo realizar seu fechamento.
	 * Quando aciono o metodo {@link OrcamentoBusiness#fecharOrcamento(Orcamento)} passando o orcamento corrente e solicitando a abertura de um novo orcamento.
	 * Entao verifico que o orcamento corrente e' fechado, o planejado associado e' o novo corrente e um novo planejado e' criado
	 */
	@Test
	def void test2(){
		def oName = "Orcamento Paulo Freitas"
		def Orcamento oCorrente = orcamentoRepository.findByNomeAndFase(oName, Fase.CORRENTE).first()
		def Long oPlanejadoId = oCorrente.planejado.id

		bean.fecharOrcamento(oCorrente, true)

		// verifica o estado do orcamento corrente que passou a ser fechado.
		assertEquals Fase.FECHADO, oCorrente.fase
		assertEquals MesBase.ABRIL, oCorrente.mesBase
		assertNull oCorrente.planejado

		// verifica o estado do orcamento planejado que estava associado ao orcamento corrente
		def novoCorrente = orcamentoRepository.findOne(oPlanejadoId)
		assertEquals Fase.CORRENTE, novoCorrente.fase
		assertEquals MesBase.getMesBase(Calendar.getInstance().get(Calendar.MONTH) + 1), novoCorrente.mesBase
		assertNotNull novoCorrente.planejado

		// verifica que existe um novo orcamento planejado associado ao novo orcamento corrente
		def novoPlanejado = novoCorrente.planejado
		assertEquals Fase.PLANEJADO, novoPlanejado.fase
		assertEquals MesBase.getMesBase(Calendar.getInstance().get(Calendar.MONTH) + 2), novoPlanejado.mesBase
		assertNull novoPlanejado.planejado
	}


	/**
	 * Titulo: validar fechamento de orcamento sem abrir o proximo planejado
	 *
	 * Dado que possuo um orcamento corrente e desejo realizar seu fechamento.
	 * Quando aciono o metodo {@link OrcamentoBusiness#fecharOrcamento(Orcamento)} passando o orcamento corrente sem abrir o proximo planejado
	 * Entao verifico que o orcamento corrente e' fechado, o planejado associado continua sendo planejado, porem sem associacao com o orcamento fechado.
	 */
	@Test
	def void test3(){
		def oName = "Orcamento Paulo Freitas"
		def Orcamento oCorrente = orcamentoRepository.findByNomeAndFase(oName, Fase.CORRENTE).first()
		def Long oPlanejadoId = oCorrente.planejado.id

		bean.fecharOrcamento(oCorrente, false)

		// verifica o estado do orcamento corrente que passou a ser fechado.
		assertEquals Fase.FECHADO, oCorrente.fase
		assertEquals MesBase.ABRIL, oCorrente.mesBase
		assertNull oCorrente.planejado

		// verifica o estado do orcamento planejado que estava associado ao orcamento corrente continua o mesmo e nao ha criacao de outro orcamento
		def oPlanejado = orcamentoRepository.findOne(oPlanejadoId)
		assertEquals Fase.PLANEJADO, oPlanejado.fase
		assertEquals MesBase.MAIO, oPlanejado.mesBase
		assertNull oPlanejado.planejado
	}

	/**
	 * Titulo: Verificar tratamento de fase ao abrir orcamento
	 * 
	 *  Dado que possuo um orcamento Fechado
	 *  Quando aciono o metodo {@link OrcamentoBusiness#abrirOrcamento(Orcamento)} passando este orcamento
	 *  Entao verifico uma exception informando que o estado do orcamento e' illegal
	 */
	@Test(expected = IllegalStateException)
	def void test4(){
		bean.abrirOrcamento(new Orcamento(fase: Fase.FECHADO))
	}

	/**
	 * Titulo: Verificar tratamento de fase ao fechar orcamento
	 *
	 *  Dado que possuo um orcamento Fechado
	 *  Quando aciono o metodo {@link OrcamentoBusiness#fecharOrcamento(Orcamento)} passando este orcamento
	 *  Entao verifico uma exception informando que o estado do orcamento e' illegal
	 */
	@Test(expected = IllegalStateException)
	def void test5(){
		bean.fecharOrcamento(new Orcamento(fase: Fase.FECHADO))
	}

	/**
	 * Titulo: Validar abertura de orcamento
	 * 
	 * Dado que possuo um orcamento planejado.
	 * Quando aciono o metodo {@link OrcamentoBusiness#abrirOrcamento(Orcamento)} passando este orcamento
	 * Entao verifico que o orcamento dado e' convertido para corrente e um novo orcamento planejado e' criado copiando o novo orcamento corrente
	 */
	@Test
	def void test6(){
		def Orcamento oPlanejado = orcamentoRepository.findByNomeAndFase("Orcamento Paulo Freitas", Fase.PLANEJADO).first()

		bean.abrirOrcamento(oPlanejado)

		assertEquals Fase.CORRENTE, oPlanejado.fase
		assertEquals MesBase.getMesBase(Calendar.getInstance().get(Calendar.MONTH) + 1), oPlanejado.mesBase
		assertNotNull oPlanejado.planejado
		assertEquals oPlanejado.nome, oPlanejado.planejado.nome
	}
}
