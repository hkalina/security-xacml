/*
  * JBoss, Home of Professional Open Source
  * Copyright 2007, JBoss Inc., and individual contributors as indicated
  * by the @authors tag. See the copyright.txt in the distribution for a
  * full listing of individual contributors.
  *
  * This is free software; you can redistribute it and/or modify it
  * under the terms of the GNU Lesser General Public License as
  * published by the Free Software Foundation; either version 2.1 of
  * the License, or (at your option) any later version.
  *
  * This software is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * Lesser General Public License for more details.
  *
  * You should have received a copy of the GNU Lesser General Public
  * License along with this software; if not, write to the Free
  * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
  * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
  */
package org.jboss.test.security.xacml.saml;

import java.util.List;

import org.jboss.security.xacml.saml.integration.opensaml.impl.XACMLAuthzDecisionQueryTypeImplBuilder;
import org.jboss.security.xacml.saml.integration.opensaml.impl.XACMLAuthzDecisionQueryTypeMarshaller;
import org.jboss.security.xacml.saml.integration.opensaml.impl.XACMLAuthzDecisionQueryTypeUnMarshaller;
import org.jboss.security.xacml.saml.integration.opensaml.impl.XACMLAuthzDecisionStatementTypeImplBuilder;
import org.jboss.security.xacml.saml.integration.opensaml.impl.XACMLAuthzDecisionStatementTypeMarshaller;
import org.jboss.security.xacml.saml.integration.opensaml.impl.XACMLAuthzDecisionStatementTypeUnMarshaller;
import org.jboss.security.xacml.saml.integration.opensaml.request.JBossSAMLResponse;
import org.jboss.security.xacml.saml.integration.opensaml.types.XACMLAuthzDecisionQueryType;
import org.jboss.security.xacml.saml.integration.opensaml.types.XACMLAuthzDecisionStatementType;
import org.opensaml.Configuration;
import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Statement;

import junit.framework.TestCase;
 
/**
 *  Test reading of a saml response containing
 *  xacml response
 *  @author Anil.Saldhana@redhat.com
 *  @since  Apr 2, 2008 
 *  @version $Revision$
 */
public class SAMLXACMLResponseUnitTestCase extends TestCase
{
   protected void setUp() throws Exception
   {
     org.opensaml.DefaultBootstrap.bootstrap(); 
     Configuration.registerObjectProvider(XACMLAuthzDecisionQueryType.DEFAULT_ELEMENT_NAME_XACML20, 
           new XACMLAuthzDecisionQueryTypeImplBuilder(), 
           new XACMLAuthzDecisionQueryTypeMarshaller(), 
           new XACMLAuthzDecisionQueryTypeUnMarshaller(), 
           null);
     Configuration.registerObjectProvider(XACMLAuthzDecisionStatementType.DEFAULT_ELEMENT_NAME_XACML20, 
           new XACMLAuthzDecisionStatementTypeImplBuilder(), 
           new XACMLAuthzDecisionStatementTypeMarshaller(), 
           new XACMLAuthzDecisionStatementTypeUnMarshaller(), 
           null);
   }
   
   public void testSAMLXACMLResponseRead() throws Exception
   {
      JBossSAMLResponse response = new JBossSAMLResponse();
      SAMLObject samlObject = response.getSAMLResponse("src/tests/resources/saml/samlxacmlresponse.xml");
      assertNotNull(samlObject); 
      
      //Verify that the xacml response does exist
      Response samlResponse = (Response) samlObject;
      Assertion assertion = samlResponse.getAssertions().get(0);
      List<Statement> statements = assertion.getStatements();
      assertTrue("statements > 0 ", statements.size() > 0);
      Statement statement = statements.get(0);
      assertNotNull("Statement != null", statement);
   } 
}