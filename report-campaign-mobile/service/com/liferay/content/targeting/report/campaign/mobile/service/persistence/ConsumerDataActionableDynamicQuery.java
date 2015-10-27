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

package com.liferay.content.targeting.report.campaign.mobile.service.persistence;

import com.liferay.content.targeting.report.campaign.mobile.model.ConsumerData;
import com.liferay.content.targeting.report.campaign.mobile.service.ConsumerDataLocalServiceUtil;

import com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class ConsumerDataActionableDynamicQuery
	extends BaseActionableDynamicQuery {
	public ConsumerDataActionableDynamicQuery() throws SystemException {
		setBaseLocalService(ConsumerDataLocalServiceUtil.getService());
		setClass(ConsumerData.class);

		setClassLoader(com.liferay.content.targeting.report.campaign.mobile.service.ClpSerializer.class.getClassLoader());

		setPrimaryKeyPropertyName("consumerDataId");
	}
}