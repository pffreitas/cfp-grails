package br.com.maisha.cfp.repositories.gorm

import br.com.maisha.cfp.repositories.CrudRepository

class CrudGormRepository<T, ID extends Serializable> implements CrudRepository<T, ID>{

	def Class<? extends T> type
	
	public CrudGormRepository(Class<? extends T> type) {
		this.type = type;
	}

	
	/**
	 * 
	 * @see br.com.maisha.cfp.repositories.CrudRepository#save(java.lang.Object)
	 */
	def <S extends T> S save(S entity) {
		entity.save(flush: true)
		println entity.errors
	}

	/**
	 * 
	 * @see br.com.maisha.cfp.repositories.CrudRepository#save(java.lang.Iterable)
	 */
	def <S extends T> Iterable<S> save(Iterable<S> entities) {
		entities.each { save(it) }
		return entities
	}

	/**
	 * 
	 * @see br.com.maisha.cfp.repositories.CrudRepository#delete(java.lang.Iterable)
	 */
	def void delete(Iterable<? extends T> entities) {
		entities.each {
			it.delete()
		}
	}

	/**
	 * 
	 * @see br.com.maisha.cfp.repositories.CrudRepository#deleteAll()
	 */
	public void deleteAll() {
		delete(type.list())
	}
}
