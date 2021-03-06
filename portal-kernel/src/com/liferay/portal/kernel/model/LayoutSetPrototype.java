/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the LayoutSetPrototype service. Represents a row in the &quot;LayoutSetPrototype&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetPrototypeModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.LayoutSetPrototypeImpl")
@ProviderType
public interface LayoutSetPrototype
	extends LayoutSetPrototypeModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.LayoutSetPrototypeImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<LayoutSetPrototype, Long>
		LAYOUT_SET_PROTOTYPE_ID_ACCESSOR =
			new Accessor<LayoutSetPrototype, Long>() {

				@Override
				public Long get(LayoutSetPrototype layoutSetPrototype) {
					return layoutSetPrototype.getLayoutSetPrototypeId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<LayoutSetPrototype> getTypeClass() {
					return LayoutSetPrototype.class;
				}

			};

	public Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public LayoutSet getLayoutSet()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.util.UnicodeProperties
		getSettingsProperties();

	public String getSettingsProperty(String key);

	public boolean hasSetModifiedDate();

	public void setSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			settingsUnicodeProperties);

}