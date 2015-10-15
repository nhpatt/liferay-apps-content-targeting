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

package com.liferay.content.targeting.report.user.segment.users;

import com.liferay.content.targeting.anonymous.users.model.AnonymousUser;
import com.liferay.content.targeting.api.model.BaseReport;
import com.liferay.content.targeting.api.model.Report;
import com.liferay.content.targeting.model.ReportInstance;
import com.liferay.content.targeting.model.UserSegment;
import com.liferay.content.targeting.service.AnonymousUserUserSegmentLocalService;
import com.liferay.content.targeting.service.ReportInstanceLocalService;
import com.liferay.content.targeting.service.UserSegmentLocalService;
import com.liferay.content.targeting.util.SearchContainerIterator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Chan
 */
@Component(
	immediate = true, service = Report.class)
public class UserSegmentUsersReport extends BaseReport {

	@Activate
	@Override
	public void activate() {
		super.activate();
	}

	@Deactivate
	@Override
	public void deActivate() {
		super.deActivate();
	}

	@Override
	public String getIcon() {
		return "icon-user";
	}

	@Override
	public String getReportType() {
		return UserSegment.class.getName();
	}

	@Override
	public boolean isInstantiable() {
		return false;
	}

	@Reference
	public void setAnonymousUserUserSegmentLocalService(
		AnonymousUserUserSegmentLocalService
			anonymousUserUserSegmentLocalService) {

		_anonymousUserUserSegmentLocalService =
			anonymousUserUserSegmentLocalService;
	}

	@Reference
	public void setReportInstanceLocalService(
		ReportInstanceLocalService reportInstanceLocalService) {

		_reportInstanceLocalService = reportInstanceLocalService;
	}

	@Reference
	public void setUserSegmentLocalService(
		UserSegmentLocalService userSegmentLocalService) {

		_userSegmentLocalService = userSegmentLocalService;
	}

	@Override
	public void updateReport(ReportInstance reportInstance) {
		try {
			if (reportInstance != null) {
				reportInstance.setModifiedDate(new Date());

				_reportInstanceLocalService.updateReportInstance(
					reportInstance);
			}
		}
		catch (Exception e) {
			_log.error("Cannot update report", e);
		}
	}

	@Override
	protected void populateContext(
		ReportInstance reportInstance, Map<String, Object> context) {

		final long userSegmentId = reportInstance.getClassPK();
		int totalUsersCount = 0;
		int anonymousUsersCount = 0;

		try {
			List<AnonymousUser> anonymousUsers =
				_anonymousUserUserSegmentLocalService.
					getAnonymousUsersByUserSegmentId(userSegmentId, true);

			for (AnonymousUser anonymousUser : anonymousUsers) {
				if (anonymousUser.getUser() == null) {
					anonymousUsersCount++;
				}
			}

			totalUsersCount = anonymousUsers.size();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		context.put("anonymousUsersCount", anonymousUsersCount);
		context.put(
			"searchContainerIterator",
			new SearchContainerIterator<User>() {

				@Override
				public List<User> getResults(int start, int end)
					throws PortalException, SystemException {

					List<AnonymousUser> anonymousUsers =
						_anonymousUserUserSegmentLocalService.
							getAnonymousUsersByUserSegmentId(
								userSegmentId, true);

					List<User> users = new ArrayList<User>();

					for (AnonymousUser anonymousUser : anonymousUsers) {
						if (anonymousUser.getUser() != null) {
							users.add(anonymousUser.getUser());
						}
					}

					return users;
				}

				@Override
				public int getTotal() throws PortalException, SystemException {
					return getResults(QueryUtil.ALL_POS, QueryUtil.ALL_POS).
						size();
				}

			}
		);
		context.put("totalUsersCount", totalUsersCount);
	}

	private static Log _log = LogFactoryUtil.getLog(
UserSegmentUsersReport.class);

	private AnonymousUserUserSegmentLocalService
		_anonymousUserUserSegmentLocalService;
	private ReportInstanceLocalService _reportInstanceLocalService;
	private UserSegmentLocalService _userSegmentLocalService;

}