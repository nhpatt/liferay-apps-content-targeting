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

package com.liferay.content.targeting.service.impl;

import com.liferay.content.targeting.anonymous.users.model.AnonymousUser;
import com.liferay.content.targeting.anonymous.users.service.AnonymousUserLocalService;
import com.liferay.content.targeting.model.AnonymousUserUserSegment;
import com.liferay.content.targeting.model.UserSegment;
import com.liferay.content.targeting.service.AnonymousUserUserSegmentLocalService;
import com.liferay.content.targeting.service.UserSegmentLocalService;
import com.liferay.content.targeting.service.test.service.ServiceTestUtil;
import com.liferay.content.targeting.service.test.util.TestPropsValues;
import com.liferay.osgi.util.service.ServiceTrackerUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.ServiceContext;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

/**
 * @author Eduardo Garcia
 */
@RunWith(Arquillian.class)
public class AnonymousUserUserSegmentLocalServiceImplTest {

	@Before
	public void setUp() {
		try {
			_bundle.start();
		}
		catch (BundleException e) {
			e.printStackTrace();
		}

		_anonymousUserLocalService =
			ServiceTrackerUtil.getService(
				AnonymousUserLocalService.class, _bundle.getBundleContext());
		_anonymousUserUserSegmentLocalService =
			ServiceTrackerUtil.getService(
				AnonymousUserUserSegmentLocalService.class,
				_bundle.getBundleContext());
		_userSegmentLocalService =
			ServiceTrackerUtil.getService(
				UserSegmentLocalService.class, _bundle.getBundleContext());
	}

	@Test
	public void testAddAndDeleteAnonymousUserUserSegment() throws Exception {
		long anonymouseUserId = 1;
		long userSegmentId = 1;

		int initialCount =
			_anonymousUserUserSegmentLocalService.
				getAnonymousUserUserSegmentsCount();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		AnonymousUserUserSegment anonymousUserUserSegment =
			_anonymousUserUserSegmentLocalService.addAnonymousUserUserSegment(
				anonymouseUserId, userSegmentId, false, true, serviceContext);

		Assert.assertEquals(
			initialCount + 1,
			_anonymousUserUserSegmentLocalService.
				getAnonymousUserUserSegmentsCount());

		_anonymousUserUserSegmentLocalService.deleteAnonymousUserUserSegment(
			anonymousUserUserSegment.getAnonymousUserUserSegmentId());

		Assert.assertEquals(
			initialCount,
			_anonymousUserUserSegmentLocalService.
				getAnonymousUserUserSegmentsCount());
	}

	@Test
	public void testCheckAnonymousUserUserSegments() throws Exception {
		long anonymouseUserId = 1;
		long userSegmentId = 1;

		int initialCount =
			_anonymousUserUserSegmentLocalService.
				getAnonymousUserUserSegmentsCount();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		AnonymousUserUserSegment anonymousUserUserSegment =
			_anonymousUserUserSegmentLocalService.addAnonymousUserUserSegment(
				anonymouseUserId, userSegmentId, false, true, serviceContext);

		Calendar calendar = CalendarFactoryUtil.getCalendar();
		calendar.set(Calendar.DATE, -5);

		anonymousUserUserSegment.setModifiedDate(calendar.getTime());

		_anonymousUserUserSegmentLocalService.updateAnonymousUserUserSegment(
			anonymousUserUserSegment);

		Assert.assertTrue(anonymousUserUserSegment.isActive());

		Assert.assertEquals(
			initialCount + 1,
			_anonymousUserUserSegmentLocalService.
				getAnonymousUserUserSegmentsCount());

		_anonymousUserUserSegmentLocalService.checkAnonymousUserUserSegments();

		anonymousUserUserSegment =
			_anonymousUserUserSegmentLocalService.getAnonymousUserUserSegment(
				anonymousUserUserSegment.getAnonymousUserUserSegmentId());

		Assert.assertFalse(anonymousUserUserSegment.isActive());

		_anonymousUserUserSegmentLocalService.deleteAnonymousUserUserSegment(
			anonymousUserUserSegment.getAnonymousUserUserSegmentId());

		Assert.assertEquals(
			initialCount,
			_anonymousUserUserSegmentLocalService.
				getAnonymousUserUserSegmentsCount());
	}

	@Test
	public void testGetAnonymousUsersByUserSegment() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		AnonymousUser anonymousUser =
			_anonymousUserLocalService.addAnonymousUser(
				TestPropsValues.getUserId(), "127.0.0.1", "", serviceContext);

		int initialCount =
			_anonymousUserUserSegmentLocalService.
				getAnonymousUserUserSegmentsCount();

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.getDefault(), StringUtil.randomString());

		UserSegment userSegment = _userSegmentLocalService.addUserSegment(
			TestPropsValues.getUserId(), nameMap, null, serviceContext);

		AnonymousUserUserSegment anonymousUserUserSegment =
			_anonymousUserUserSegmentLocalService.addAnonymousUserUserSegment(
				anonymousUser.getAnonymousUserId(),
				userSegment.getUserSegmentId(), false, true, serviceContext);

		List<AnonymousUser> activeUsers =
			_anonymousUserUserSegmentLocalService.
				getAnonymousUsersByUserSegmentId(
					userSegment.getUserSegmentId(), true);

		Assert.assertEquals(1, activeUsers.size());

		List<AnonymousUser> inactiveUsers =
			_anonymousUserUserSegmentLocalService.
				getAnonymousUsersByUserSegmentId(
					userSegment.getUserSegmentId(), false);

		Assert.assertEquals(0, inactiveUsers.size());

		_anonymousUserUserSegmentLocalService.deleteAnonymousUserUserSegment(
			anonymousUserUserSegment.getAnonymousUserUserSegmentId());

		Assert.assertEquals(
			initialCount,
			_anonymousUserUserSegmentLocalService.
				getAnonymousUserUserSegmentsCount());

		_userSegmentLocalService.deleteUserSegment(userSegment);
	}

	@Test
	public void testGetUserSegmentsByAnonymousUser() throws Exception {
		long anonymousUserId = 1;

		int initialCount =
			_anonymousUserUserSegmentLocalService.
				getAnonymousUserUserSegmentsCount();

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.getDefault(), StringUtil.randomString());

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		UserSegment userSegment = _userSegmentLocalService.addUserSegment(
			TestPropsValues.getUserId(), nameMap, null, serviceContext);

		AnonymousUserUserSegment anonymousUserUserSegment =
			_anonymousUserUserSegmentLocalService.addAnonymousUserUserSegment(
				anonymousUserId, userSegment.getUserSegmentId(), false, true,
				serviceContext);

		Assert.assertEquals(
			initialCount + 1,
			_anonymousUserUserSegmentLocalService.
				getAnonymousUserUserSegmentsCount());

		List<UserSegment> activeUserSegments =
			_anonymousUserUserSegmentLocalService.
				getUserSegmentsByAnonymousUserId(anonymousUserId, true);

		Assert.assertEquals(1, activeUserSegments.size());

		List<UserSegment> inactiveUserSegments =
			_anonymousUserUserSegmentLocalService.
				getUserSegmentsByAnonymousUserId(anonymousUserId, false);

		Assert.assertEquals(0, inactiveUserSegments.size());

		_anonymousUserUserSegmentLocalService.deleteAnonymousUserUserSegment(
			anonymousUserUserSegment.getAnonymousUserUserSegmentId());

		Assert.assertEquals(
			initialCount,
			_anonymousUserUserSegmentLocalService.
				getAnonymousUserUserSegmentsCount());

		_userSegmentLocalService.deleteUserSegment(userSegment);
	}

	@Test
	public void testUpdateAnonymousUserUserSegment() throws Exception {
		long anonymouseUserId = 1;
		long userSegmentId = 1;

		int initialCount =
			_anonymousUserUserSegmentLocalService.
				getAnonymousUserUserSegmentsCount();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		AnonymousUserUserSegment anonymousUserUserSegment =
			_anonymousUserUserSegmentLocalService.addAnonymousUserUserSegment(
				anonymouseUserId, userSegmentId, false, true, serviceContext);

		Assert.assertEquals(
			initialCount + 1,
			_anonymousUserUserSegmentLocalService.
				getAnonymousUserUserSegmentsCount());

		Date modifiedDate = anonymousUserUserSegment.getModifiedDate();

		anonymousUserUserSegment =
			_anonymousUserUserSegmentLocalService.
				updateAnonymousUserUserSegment(
					anonymousUserUserSegment.getAnonymousUserUserSegmentId(),
					serviceContext);

		Assert.assertTrue(
			anonymousUserUserSegment.getModifiedDate().after(modifiedDate));

		_anonymousUserUserSegmentLocalService.deleteAnonymousUserUserSegment(
			anonymousUserUserSegment.getAnonymousUserUserSegmentId());

		Assert.assertEquals(
			initialCount,
			_anonymousUserUserSegmentLocalService.
				getAnonymousUserUserSegmentsCount());
	}

	private AnonymousUserLocalService _anonymousUserLocalService;
	private AnonymousUserUserSegmentLocalService
		_anonymousUserUserSegmentLocalService;

	@ArquillianResource
	private Bundle _bundle;

	private UserSegmentLocalService _userSegmentLocalService;

}