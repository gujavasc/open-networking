package org.gujavasc.opennetworking.domain.repository;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.model.User;

@Stateless
@LocalBean
public class UserRepository implements Repository<User, String> {

	@Inject
	private IdentityManager manager;

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public User findById(String id) {
		return manager.getUser(id);
	}

	@Override
	public void update(User entity) {
		manager.update(entity);
	}

	@Override
	public void persist(User entity) {
		manager.add(entity);
	}

	@Override
	public void remove(User entity) {
		manager.remove(entity);
	}

	@Override
	public List<User> findAll() {
		return manager.createIdentityQuery(User.class).getResultList();
	}
}
