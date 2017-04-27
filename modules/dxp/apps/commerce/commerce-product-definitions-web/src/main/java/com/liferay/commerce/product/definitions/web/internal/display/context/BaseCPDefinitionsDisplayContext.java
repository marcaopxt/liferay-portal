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

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.commerce.product.definitions.web.internal.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.definitions.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public abstract class BaseCPDefinitionsDisplayContext<T> {

	public BaseCPDefinitionsDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest,
		String rowCheckerIds, String portalPreferenceNamespace) {

		this.actionHelper = actionHelper;
		this.httpServletRequest = httpServletRequest;
		_rowCheckerIds = rowCheckerIds;
		_portalPreferenceNamespace = portalPreferenceNamespace;

		portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			this.httpServletRequest);

		cpRequestHelper = new CPRequestHelper(httpServletRequest);

		liferayPortletRequest = cpRequestHelper.getLiferayPortletRequest();
		liferayPortletResponse = cpRequestHelper.getLiferayPortletResponse();

		_defaultOrderByCol = "create-date";
		_defaultOrderByType = "asc";
	}

	public List<Locale> getAvailableLocales() throws PortalException {
		CPDefinition cpDefinition = getCPDefinition();

		if (cpDefinition == null) {
			return Collections.emptyList();
		}

		List<Locale> availableLocales = new ArrayList<>();

		for (String languageId : cpDefinition.getAvailableLanguageIds()) {
			availableLocales.add(LocaleUtil.fromLanguageId(languageId));
		}

		return availableLocales;
	}

	public CPDefinition getCPDefinition() throws PortalException {
		if (_cpDefinition != null) {
			return _cpDefinition;
		}

		_cpDefinition = actionHelper.getCPDefinition(
			cpRequestHelper.getRenderRequest());

		return _cpDefinition;
	}

	public long getCPDefinitionId() throws PortalException {
		CPDefinition cpDefinition = getCPDefinition();

		if (cpDefinition == null) {
			return 0;
		}

		return cpDefinition.getCPDefinitionId();
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = getDisplayStyle(
				httpServletRequest, portalPreferences);
		}

		return _displayStyle;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(httpServletRequest, "keywords");

		return _keywords;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(httpServletRequest, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = portalPreferences.getValue(
				_portalPreferenceNamespace, "order-by-col", _defaultOrderByCol);
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				portalPreferences.setValue(
					_portalPreferenceNamespace, "order-by-col", _orderByCol);
			}
		}

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(httpServletRequest, "orderByType");

		if (Validator.isNull(_orderByType)) {
			_orderByType = portalPreferences.getValue(
				_portalPreferenceNamespace, "order-by-type",
				_defaultOrderByType);
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				portalPreferences.setValue(
					_portalPreferenceNamespace, "order-by-type", _orderByType);
			}
		}

		return _orderByType;
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String delta = ParamUtil.getString(httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String displayStyle = ParamUtil.getString(
			httpServletRequest, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		String keywords = ParamUtil.getString(httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		CPDefinition cpDefinition = getCPDefinition();

		if (cpDefinition != null) {
			portletURL.setParameter(
				"cpDefinitionId", String.valueOf(getCPDefinitionId()));
		}

		return portletURL;
	}

	public RowChecker getRowChecker() {
		if (_rowChecker != null) {
			return _rowChecker;
		}

		RowChecker rowChecker = new EmptyOnClickRowChecker(
			liferayPortletResponse);

		rowChecker.setRowIds(_rowCheckerIds);

		_rowChecker = rowChecker;

		return _rowChecker;
	}

	public long getScopeGroupId() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getScopeGroupId();
	}

	public abstract SearchContainer<T> getSearchContainer()
		throws PortalException;

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	public boolean isShowInfoPanel() {
		if (isSearch()) {
			return false;
		}

		return true;
	}

	public void setDefaultOrderByCol(String defaultOrderByCol) {
		_defaultOrderByCol = defaultOrderByCol;
	}

	public void setDefaultOrderByType(String defaultOrderByType) {
		_defaultOrderByType = defaultOrderByType;
	}

	protected String getDisplayStyle(
		HttpServletRequest request, PortalPreferences portalPreferences) {

		String displayStyle = ParamUtil.getString(request, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			displayStyle = portalPreferences.getValue(
				_portalPreferenceNamespace, "display-style", "list");
		}
		else {
			portalPreferences.setValue(
				_portalPreferenceNamespace, "display-style", displayStyle);

			request.setAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
		}

		return displayStyle;
	}

	protected final ActionHelper actionHelper;
	protected final CPRequestHelper cpRequestHelper;
	protected final HttpServletRequest httpServletRequest;
	protected final LiferayPortletRequest liferayPortletRequest;
	protected final LiferayPortletResponse liferayPortletResponse;
	protected final PortalPreferences portalPreferences;
	protected SearchContainer<T> searchContainer;

	private CPDefinition _cpDefinition;
	private String _defaultOrderByCol;
	private String _defaultOrderByType;
	private String _displayStyle;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final String _portalPreferenceNamespace;
	private RowChecker _rowChecker;
	private final String _rowCheckerIds;

}