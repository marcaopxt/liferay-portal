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

package com.liferay.portal.template.xsl.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;

import org.apache.xml.utils.SAXSourceLocator;
import org.apache.xml.utils.WrappedRuntimeException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author Raymond Augé
 */
public class XSLErrorListener implements ErrorListener {

	public XSLErrorListener(Locale locale) {
		_locale = locale;
	}

	@Override
	public void error(TransformerException transformerException)
		throws TransformerException {

		setLocation(transformerException);

		throw transformerException;
	}

	@Override
	public void fatalError(TransformerException transformerException)
		throws TransformerException {

		setLocation(transformerException);

		throw transformerException;
	}

	public int getColumnNumber() {
		return _columnNumber;
	}

	public int getLineNumber() {
		return _lineNumber;
	}

	public String getLocation() {
		return _location;
	}

	public String getMessage() {
		return _message;
	}

	public String getMessageAndLocation() {
		return _message + " " + _location;
	}

	public void setLocation(Throwable exception) {
		SourceLocator locator = null;
		Throwable cause = exception;
		Throwable rootCause = null;

		while (cause != null) {
			if (cause instanceof SAXParseException) {
				locator = new SAXSourceLocator((SAXParseException)cause);
				rootCause = cause;
			}
			else if (cause instanceof TransformerException) {
				TransformerException transformerException =
					(TransformerException)cause;

				SourceLocator causeLocator = transformerException.getLocator();

				if (causeLocator != null) {
					locator = causeLocator;
					rootCause = cause;
				}
			}

			if (cause instanceof TransformerException) {
				TransformerException transformerException =
					(TransformerException)cause;

				cause = transformerException.getCause();
			}
			else if (cause instanceof WrappedRuntimeException) {
				WrappedRuntimeException wrappedRuntimeException =
					(WrappedRuntimeException)cause;

				cause = wrappedRuntimeException.getException();
			}
			else if (cause instanceof SAXException) {
				SAXException saxException = (SAXException)cause;

				cause = saxException.getException();
			}
			else {
				cause = null;
			}
		}

		_message = rootCause.getMessage();

		if (locator != null) {
			_lineNumber = locator.getLineNumber();
			_columnNumber = locator.getColumnNumber();

			StringBundler sb = new StringBundler(8);

			sb.append(LanguageUtil.get(_locale, "line"));
			sb.append(" #");
			sb.append(locator.getLineNumber());
			sb.append("; ");
			sb.append(LanguageUtil.get(_locale, "column"));
			sb.append(" #");
			sb.append(locator.getColumnNumber());
			sb.append("; ");

			_location = sb.toString();
		}
		else {
			_location = StringPool.BLANK;
		}
	}

	@Override
	public void warning(TransformerException transformerException)
		throws TransformerException {

		setLocation(transformerException);

		throw transformerException;
	}

	private int _columnNumber;
	private int _lineNumber;
	private final Locale _locale;
	private String _location;
	private String _message;

}