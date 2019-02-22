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

package com.liferay.user.associated.data.web.internal.search;

import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.display.UADEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Pei-Jung Lan
 */
public class UADHierarchyResultRowSplitter implements ResultRowSplitter {

	public UADHierarchyResultRowSplitter(
		Locale locale, UADDisplay<?>[] uadDisplays) {

		_locale = locale;
		_uadDisplays = uadDisplays;
	}

	public UADHierarchyResultRowSplitter(
		Locale locale, UADDisplay<?>[] containerUADDisplays,
		UADDisplay<?>[] noncontainerUADDisplays) {

		List<UADDisplay> uadDisplayList = Stream.concat(
			Arrays.stream(containerUADDisplays),
			Arrays.stream(noncontainerUADDisplays)
		).collect(
			Collectors.toList()
		);

		_locale = locale;
		_uadDisplays = (UADDisplay<?>[])uadDisplayList.toArray();
	}

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		Map<Class, List<ResultRow>> classResultRowsMap = new HashMap<>();

		for (UADDisplay uadDisplay : _uadDisplays) {
			classResultRowsMap.put(
				uadDisplay.getTypeClass(), new ArrayList<>());
		}

		for (ResultRow resultRow : resultRows) {
			Object object = resultRow.getObject();

			UADEntity uadEntity = (UADEntity)object;

			for (UADDisplay uadDisplay : _uadDisplays) {
				Class<?> typeClass = uadDisplay.getTypeClass();

				if (typeClass.isInstance(uadEntity.getEntity())) {
					List<ResultRow> classResultRows = classResultRowsMap.get(
						typeClass);

					classResultRows.add(resultRow);
				}
			}
		}

		for (UADDisplay uadDisplay : _uadDisplays) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					uadDisplay.getTypeName(_locale),
					classResultRowsMap.get(uadDisplay.getTypeClass())));
		}

		return resultRowSplitterEntries;
	}

	private final Locale _locale;
	private final UADDisplay<?>[] _uadDisplays;

}