/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.gujavasc.opennetworking.picketlink;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.picketlink.IdentityConfigurationEvent;
import org.picketlink.idm.config.IdentityConfigurationBuilder;
import org.picketlink.idm.jpa.schema.CredentialObject;
import org.picketlink.idm.jpa.schema.CredentialObjectAttribute;
import org.picketlink.idm.jpa.schema.IdentityObject;
import org.picketlink.idm.jpa.schema.IdentityObjectAttribute;
import org.picketlink.idm.jpa.schema.PartitionObject;
import org.picketlink.idm.jpa.schema.RelationshipIdentityObject;
import org.picketlink.idm.jpa.schema.RelationshipObject;
import org.picketlink.idm.jpa.schema.RelationshipObjectAttribute;

@ApplicationScoped
public class PicketlinkIDMConfiguration
{
   /**
    * This method uses the IdentityConfigurationBuilder to create an IdentityConfiguration, which defines how PicketLink
    * stores identity-related data. In this particular example, a JPAIdentityStore is configured to allow the identity
    * data to be stored in a relational database using JPA.
    */
   public void configure(@Observes IdentityConfigurationEvent event)
   {
      IdentityConfigurationBuilder builder = event.getConfig();
      builder
               .stores()
               .jpa()

               // Specify the entity bean class used to hold user, group and role records
               .identityClass(IdentityObject.class)

               // Specify the entity bean class used to hold credential values
               .credentialClass(CredentialObject.class)

               // Specify the entity bean class used to hold credential attributes
               .credentialAttributeClass(CredentialObjectAttribute.class)

               // Specify the entity bean class used to hold ad-hoc identity attribute values
               .attributeClass(IdentityObjectAttribute.class)

               // Specify the entity bean class used to define inter-identity relationships
               .relationshipClass(RelationshipObject.class)

               // Specify the entity bean class used to hold references to the identities that
               // take part in a relationship
               .relationshipIdentityClass(RelationshipIdentityObject.class)

               // Specify the entity bean class used to hold relationship attribute values
               .relationshipAttributeClass(RelationshipObjectAttribute.class)
               
               // Specify the entity bean class used to hold partition (i.e. realm and tier) related
               // data
               .partitionClass(PartitionObject.class)
               
               // Specify that this identity store configuration supports all features
               .supportAllFeatures();
   }
}
