package org.gujavasc.opennetworking.application.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.model.SimpleUser;
import org.picketlink.idm.model.User;
import org.picketlink.idm.query.IdentityQuery;

/**
 * Backing bean for User entities.
 * <p>
 * This class provides CRUD functionality for all User entities. It focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for state management,
 * <tt>PersistenceContext</tt> for persistence, <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or custom base class.
 */
@Named
@Stateful
@ConversationScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IdentityManager identityManager;

	@Inject
	private Identity identity;

	@Inject
	private Conversation conversation;

	private String id;

	private User user;

	private User example = new SimpleUser();

	public String create() {
		this.conversation.begin();
		return "create?faces-redirect=true";
	}

	public void retrieve() {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}

		if (this.conversation.isTransient()) {
			this.conversation.begin();
		}

		if (this.id == null) {
			this.user = this.example;
		} else {
			this.user = findById(getId());
		}
	}

	public User findById(String id) {
		IdentityQuery<User> query = identityManager.createIdentityQuery(User.class);
		query.setParameter(User.ID, id);
		List<User> resultList = query.getResultList();
		return (resultList.isEmpty()) ? null : resultList.get(0);
	}

	/*
	 * Support updating and deleting User entities
	 */
	public String update() {
		this.conversation.end();
		try {
			if (this.id == null) {
				identityManager.add(this.user);
				return "search?faces-redirect=true";
			} else {
				identityManager.update(this.user);
				return "view?faces-redirect=true&id=" + this.user.getId();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
	}

	public String delete() {
		this.conversation.end();

		try {
			identityManager.remove(user);
			return "search?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
	}

	public List<User> getAll() {
		return identityManager.createIdentityQuery(User.class).getResultList();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getExample() {
		return example;
	}

	public void setExample(User example) {
		this.example = example;
	}

	public User getUser() {
		return this.user;
	}

	public String getCurrentUserPicture() {
		if (identity.isLoggedIn()) {
			return (String) identity.getAgent().getAttribute("image").getValue();
		} else {
			return null;
		}
	}

}