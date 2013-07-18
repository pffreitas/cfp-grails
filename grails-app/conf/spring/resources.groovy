import br.com.maisha.cfp.business.LancamentoBusiness
import br.com.maisha.cfp.business.OrcamentoBusiness
import br.com.maisha.cfp.repositories.gorm.CategoriaGormRepository
import br.com.maisha.cfp.repositories.gorm.LancamentoGormRepository
import br.com.maisha.cfp.repositories.gorm.OrcamentoGormRepository
import br.com.maisha.cfp.repositories.gorm.SubcategoriaGormRepository
import br.com.maisha.cfp.test.DataGenerator

beans = {
	//business
	orcamentoBusiness(OrcamentoBusiness) { b ->
		b.autowire = "byName"
	}

	lancamentoBusiness(LancamentoBusiness) { b ->
		b.autowire = "byName"
	}

	// repos
	categoriaRepository(CategoriaGormRepository)
	lancamentoRepository(LancamentoGormRepository)
	orcamentoRepository(OrcamentoGormRepository)
	subcategoriaRepository(SubcategoriaGormRepository)

	//stuff
	dataGenerator (DataGenerator) { b ->
		b.autowire = "byName"
	}	
}
