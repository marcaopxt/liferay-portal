<%--
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
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<liferay-theme:defineObjects />

<%
String redirect = request.getParameter("redirect");

redirect = PortalUtil.escapeRedirect(redirect);
%>

<aui:script>
	var data = {
		id: '<portlet:namespace />editAsset',
		portletAjaxable: true,
	};

	<c:choose>
		<c:when test="<%= redirect != null %>">
			data.redirect = '<%= HtmlUtil.escapeJS(redirect) %>';
		</c:when>
		<c:otherwise>
			data.refresh = '<%= portletDisplay.getId() %>';
		</c:otherwise>
	</c:choose>

	Liferay.fire('closeWindow', data);
</aui:script>