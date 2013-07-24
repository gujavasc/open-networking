package org.gujavasc.opennetworking.domain.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractJPARepository<TYPE> implements Repository<TYPE, Long> {

	@PersistenceContext
	protected EntityManager entityManager;

	protected final Class<TYPE> typeClass;

	public AbstractJPARepository(Class<TYPE> typeClass) {
		this.typeClass = typeClass;
	}

	@Override
	public TYPE findById(Long id) {
		return entityManager.find(typeClass, id);
	}

	@Override
	public void update(TYPE entity) {
		entity = entityManager.merge(entity);
		entityManager.persist(entity);
	}

	@Override
	public void persist(TYPE entity) {
		entityManager.persist(entity);
	}

	@Override
	public void remove(TYPE entity) {
		entity = entityManager.merge(entity);
		entityManager.remove(entity);
	}

	@Override
	public List<TYPE> findAll() {
		String sql = "FROM " + typeClass.getSimpleName();
		return entityManager.createQuery(sql, typeClass).getResultList();
	}
}