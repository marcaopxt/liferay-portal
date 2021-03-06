/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.internal.activator;

import com.liferay.portal.upgrade.release.BaseUpgradeServiceModuleRelease;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Carlos Sierra Andrés
 */
public class SamlPersistenceServiceBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		SamlServiceModuleRelease samlServiceModuleRelease =
			new SamlServiceModuleRelease();

		samlServiceModuleRelease.upgrade();
	}

	@Override
	public void stop(BundleContext context) {
	}

	private static class SamlServiceModuleRelease
		extends BaseUpgradeServiceModuleRelease {

		@Override
		protected String getNamespace() {
			return "Saml";
		}

		@Override
		protected String getNewBundleSymbolicName() {
			return "com.liferay.saml.persistence.service";
		}

		@Override
		protected String getOldBundleSymbolicName() {
			return "saml-portlet";
		}

	}

}