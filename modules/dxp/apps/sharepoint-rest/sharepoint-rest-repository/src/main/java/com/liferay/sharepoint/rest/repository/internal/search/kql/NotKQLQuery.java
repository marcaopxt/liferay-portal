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

package com.liferay.sharepoint.rest.repository.internal.search.kql;

/**
 * @author Adolfo Pérez
 */
public class NotKQLQuery implements KQLQuery {

	public NotKQLQuery(KQLQuery kqlQuery) {
		_kqlQuery = kqlQuery;
	}

	@Override
	public String toString() {
		return String.format("(NOT %s)", _kqlQuery.toString());
	}

	private final KQLQuery _kqlQuery;

}