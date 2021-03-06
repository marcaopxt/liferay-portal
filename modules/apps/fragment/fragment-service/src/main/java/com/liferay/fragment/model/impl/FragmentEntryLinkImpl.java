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

package com.liferay.fragment.model.impl;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Date;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryLinkImpl extends FragmentEntryLinkBaseImpl {

	@Override
	public boolean isCacheable() {
		if (getFragmentEntryId() == 0) {
			return false;
		}

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				getFragmentEntryId());

		if (fragmentEntry != null) {
			return fragmentEntry.isCacheable();
		}

		return false;
	}

	@Override
	public boolean isLatestVersion() throws PortalException {
		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.getFragmentEntry(
				getFragmentEntryId());

		Date fragmentEntryModifiedDate = fragmentEntry.getModifiedDate();

		return fragmentEntryModifiedDate.before(getLastPropagationDate());
	}

}