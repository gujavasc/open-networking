package org.gujavasc.opennetworking.domain.repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Repository<T> {

	@PersistenceContext
	protected EntityManager entityManager;
	
	protected Class<T> typeClass;

	@SuppressWarnings("unchecked")
	public Repository() {
    	typeClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

	public T find(Class<T> clazz, Long id) {
		return entityManager.find(clazz, id);
	}

	public void update(T entity) {
		entity = entityManager.merge(entity);
		entityManager.persist(entity);
	}

	public void persist(T entity) {
		entityManager.persist(entity);
	}

	public void remove(T entity) {
		entity = entityManager.merge(entity);
		entityManager.remove(entity);
	}
	
	public List<T> findAll() {
		String sql = "FROM " + typeClass.getSimpleName();
		return entityManager.createQuery(sql, typeClass).getResultList();
	}

}
