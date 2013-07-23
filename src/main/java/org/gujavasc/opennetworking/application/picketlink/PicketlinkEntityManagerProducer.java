package org.gujavasc.opennetworking.application.picketlink;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.picketlink.annotations.PicketLink;

/**
 * Makes container-managed {@link EntityManager}s available to the CDI context
 * 
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 * 
 */
public class PicketlinkEntityManagerProducer {
	/**
	 * This {@link EntityManager} is used by Picketlink
	 */
	@Produces
	@PicketLink
	@PersistenceContext(unitName = "forge-default")
	private EntityManager picketLinkEntityManager;

}
