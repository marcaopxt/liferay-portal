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

package com.liferay.saml.persistence.model.impl;

import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalServiceUtil;

/**
 * The extended model base implementation for the SamlSpIdpConnection service. Represents a row in the &quot;SamlSpIdpConnection&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SamlSpIdpConnectionImpl}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpIdpConnectionImpl
 * @see SamlSpIdpConnection
 * @generated
 */
public abstract class SamlSpIdpConnectionBaseImpl
	extends SamlSpIdpConnectionModelImpl implements SamlSpIdpConnection {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a saml sp idp connection model instance should use the <code>SamlSpIdpConnection</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			SamlSpIdpConnectionLocalServiceUtil.addSamlSpIdpConnection(this);
		}
		else {
			SamlSpIdpConnectionLocalServiceUtil.updateSamlSpIdpConnection(this);
		}
	}

}