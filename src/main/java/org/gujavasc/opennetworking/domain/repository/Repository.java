package org.gujavasc.opennetworking.domain.repository;

import java.util.List;

import javax.persistence.EntityManager;

public abstract class Repository<T> {

	protected final Class<T> typeClass;

	public Repository(Class<T> typeClass) {
		this.typeClass = typeClass;
	}

	public T find(Class<T> clazz, Long id) {
		return getEntityManager().find(clazz, id);
	}

	public void update(T entity) {
		entity = getEntityManager().merge(entity);
		getEntityManager().persist(entity);
	}

	public void persist(T entity) {
		getEntityManager().persist(entity);
	}

	public void remove(T entity) {
		entity = getEntityManager().merge(entity);
		getEntityManager().remove(entity);
	}

	public List<T> findAll() {
		String sql = "FROM " + typeClass.getSimpleName();
		return getEntityManager().createQuery(sql, typeClass).getResultList();
	}
	
	protected abstract EntityManager getEntityManager();

}
