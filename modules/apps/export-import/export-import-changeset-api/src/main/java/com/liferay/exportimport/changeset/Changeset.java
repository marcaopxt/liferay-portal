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

package com.liferay.exportimport.changeset;

import com.liferay.exportimport.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Máté Thurzó
 */
public class Changeset implements Serializable {

	public static Builder create() {
		return new Builder(new Changeset());
	}

	public static RawBuilder createRaw() {
		return new RawBuilder(new Changeset());
	}

	public String getUuid() {
		return _uuid;
	}

	public Stream<StagedModel> stream() {
		return _stagedModels.stream();
	}

	public static class Builder {

		public Builder(Changeset changeset) {
			_changeset = changeset;

			_changeset._stagedModels = new ArrayList<>();
		}

		public Builder addModel(
			Supplier<ClassedModel> supplier,
			Function<ClassedModel, StagedModel> adapterFunction) {

			Supplier<StagedModel> stagedModelSupplier = () -> {
				ClassedModel classedModel = supplier.get();

				return adapterFunction.apply(classedModel);
			};

			_changeset._stagedModels.add(stagedModelSupplier.get());

			return this;
		}

		public Builder addMultipleStagedModel(
			Supplier<Collection<? extends StagedModel>> supplier) {

			Collection<? extends StagedModel> stagedModels = supplier.get();

			Stream<? extends StagedModel> stream = stagedModels.stream();

			stream.filter(
				Objects::nonNull
			).forEach(
				stagedModel -> _changeset._stagedModels.add(stagedModel)
			);

			return this;
		}

		public Builder addStagedModel(Supplier<StagedModel> supplier) {
			_changeset._stagedModels.add(supplier.get());

			return this;
		}

		@SuppressWarnings("unchecked")
		public <T extends StagedModel> Builder addStagedModelHierarchy(
			Supplier<T> supplier,
			Function<T, Collection<?>> hierarchyFunction) {

			Function<StagedModel, Collection<?>> function =
				(Function<StagedModel, Collection<?>>)hierarchyFunction;

			StagedModel stagedModel = supplier.get();

			String stagedModelClassName =
				ExportImportClassedModelUtil.getClassName(stagedModel);

			_changeset._stagedModels.addAll(
				_getChildrenStagedModels(
					stagedModel, stagedModelClassName, function));

			return this;
		}

		public Changeset build() {
			return _changeset;
		}

		private final Changeset _changeset;

	}

	public static class RawBuilder {

		public RawBuilder(Changeset changeset) {
			_changeset = changeset;

			_changeset._stagedModels = new ArrayList<>();
		}

		public RawBuilder addMultipleStagedModel(
			Collection<? extends StagedModel> stagedModels) {

			Stream<? extends StagedModel> stream = stagedModels.stream();

			stream.filter(
				Objects::nonNull
			).forEach(
				stagedModel -> _changeset._stagedModels.add(stagedModel)
			);

			return this;
		}

		public RawBuilder addStagedModel(StagedModel stagedModel) {
			_changeset._stagedModels.add(stagedModel);

			return this;
		}

		public Changeset build() {
			return _changeset;
		}

		private final Changeset _changeset;

	}

	private static List<StagedModel> _getChildrenStagedModels(
		final StagedModel parentStagedModel, final String parentClassName,
		Function<StagedModel, Collection<?>> hierarchyFunction) {

		List<StagedModel> stagedModels = new ArrayList<>();

		for (Object object : hierarchyFunction.apply(parentStagedModel)) {
			StagedModel stagedModel = (StagedModel)object;

			String stagedModelClassName = stagedModel.getModelClassName();

			if (stagedModelClassName.equals(parentClassName)) {
				stagedModels.addAll(
					_getChildrenStagedModels(
						stagedModel, parentClassName, hierarchyFunction));
			}

			stagedModels.add(stagedModel);
		}

		return stagedModels;
	}

	private Changeset() {
	}

	private List<StagedModel> _stagedModels;
	private String _uuid = PortalUUIDUtil.generate();

}