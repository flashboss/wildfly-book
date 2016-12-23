/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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

package it.vige.businesscomponents.injection;

import static it.vige.businesscomponents.injection.common.CommonBean.HELLO_GREETING_PREFIX;
import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.vige.businesscomponents.injection.common.CommonBean;
import it.vige.businesscomponents.injection.common.CommonManagedBean;
import it.vige.businesscomponents.injection.common.NamedBean;

/**
 * Tests that the Resource injection as specified by Java EE spec works as
 * expected
 * <p/>
 * User: Luca Stancapiano
 */
@RunWith(Arquillian.class)
public class ContainerInjectionTestCase {

	private static final Logger logger = getLogger(ContainerInjectionTestCase.class.getName());

	final String user = "Charlie Sheen";

	@Inject
	private CommonBean cb;

	@Inject
	private CommonManagedBean cmb;

	@Inject
	private NamedBean nb;

	@Deployment
	public static JavaArchive createWebDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "resource-injection-test.jar");
		jar.addPackage(CommonBean.class.getPackage());
		jar.addAsManifestResource(new FileAsset(new File("src/main/resources/META-INF/beans-empty.xml")), "beans.xml");
		return jar;
	}

	/**
	 * Tests simple resource injection in a jar archive
	 */
	@Test
	public void testResourceInjection() {
		logger.info("starting a weld engine in container mode");
		final String greeting = cb.sayHello(user);
		assertEquals("Unexpected greeting received from bean", HELLO_GREETING_PREFIX + user, greeting);
	}

	/**
	 * Tests simple resource injection in a jar archive
	 */
	@Test
	public void testBeanManager() {
		logger.info("starting a weld engine in container mode");
		BeanManager beanManager = cb.getBeanManager();
		assertNotNull("verify there is ever a bean manager", beanManager);
		Set<Bean<?>> beans = beanManager.getBeans(CommonBean.class);
		assertEquals("One injected common bean", 1, beans.size());
		@SuppressWarnings("unchecked")
		Bean<CommonBean> bean = (Bean<CommonBean>) beanManager.resolve(beans);
		CommonBean cb = beanManager.getContext(bean.getScope()).get(bean, beanManager.createCreationalContext(bean));
		final String greeting = cb.sayHello(user);
		assertEquals("Unepxected greeting received from bean", HELLO_GREETING_PREFIX + user, greeting);
	}

	@Test
	public void testManagedInjection() {
		logger.info("starting a weld engine with a managed bean");
		final String greeting = cmb.sayHello(user);
		assertEquals("Unexpected greeting received from bean", HELLO_GREETING_PREFIX + user, greeting);
	}

	@Test
	public void testNamedInjection() {
		logger.info("starting a weld engine with a managed bean");
		final double prize = nb.giveMeThePrize();
		assertEquals("Unexpected greeting received from bean", 5.6, prize, 0.0);
	}
}
