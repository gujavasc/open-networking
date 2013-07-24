package org.gujavasc.opennetworking.domain.repository;

import java.io.Serializable;
import java.util.List;

public interface Repository<TYPE, KEY extends Serializable> {

	public abstract TYPE findById(KEY id);

	public abstract void update(TYPE entity);

	public abstract void persist(TYPE entity);

	public abstract void remove(TYPE entity);

	public abstract List<TYPE> findAll();

}
