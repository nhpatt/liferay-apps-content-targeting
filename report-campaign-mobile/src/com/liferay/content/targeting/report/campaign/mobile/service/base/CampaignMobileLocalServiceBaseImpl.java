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

package com.liferay.content.targeting.report.campaign.mobile.service.base;

import com.liferay.content.targeting.report.campaign.mobile.model.CampaignMobile;
import com.liferay.content.targeting.report.campaign.mobile.service.CampaignMobileLocalService;
import com.liferay.content.targeting.report.campaign.mobile.service.persistence.CampaignMobileFinder;
import com.liferay.content.targeting.report.campaign.mobile.service.persistence.CampaignMobilePersistence;
import com.liferay.content.targeting.report.campaign.mobile.service.persistence.ConsumerDataFinder;
import com.liferay.content.targeting.report.campaign.mobile.service.persistence.ConsumerDataPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.persistence.UserPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the campaign mobile local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.content.targeting.report.campaign.mobile.service.impl.CampaignMobileLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.content.targeting.report.campaign.mobile.service.impl.CampaignMobileLocalServiceImpl
 * @see com.liferay.content.targeting.report.campaign.mobile.service.CampaignMobileLocalServiceUtil
 * @generated
 */
public abstract class CampaignMobileLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements CampaignMobileLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.content.targeting.report.campaign.mobile.service.CampaignMobileLocalServiceUtil} to access the campaign mobile local service.
	 */

	/**
	 * Adds the campaign mobile to the database. Also notifies the appropriate model listeners.
	 *
	 * @param campaignMobile the campaign mobile
	 * @return the campaign mobile that was added
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CampaignMobile addCampaignMobile(CampaignMobile campaignMobile)
		throws SystemException {
		campaignMobile.setNew(true);

		return campaignMobilePersistence.update(campaignMobile);
	}

	/**
	 * Creates a new campaign mobile with the primary key. Does not add the campaign mobile to the database.
	 *
	 * @param campaignMobileId the primary key for the new campaign mobile
	 * @return the new campaign mobile
	 */
	@Override
	public CampaignMobile createCampaignMobile(long campaignMobileId) {
		return campaignMobilePersistence.create(campaignMobileId);
	}

	/**
	 * Deletes the campaign mobile with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param campaignMobileId the primary key of the campaign mobile
	 * @return the campaign mobile that was removed
	 * @throws PortalException if a campaign mobile with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CampaignMobile deleteCampaignMobile(long campaignMobileId)
		throws PortalException, SystemException {
		return campaignMobilePersistence.remove(campaignMobileId);
	}

	/**
	 * Deletes the campaign mobile from the database. Also notifies the appropriate model listeners.
	 *
	 * @param campaignMobile the campaign mobile
	 * @return the campaign mobile that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CampaignMobile deleteCampaignMobile(CampaignMobile campaignMobile)
		throws SystemException {
		return campaignMobilePersistence.remove(campaignMobile);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(CampaignMobile.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return campaignMobilePersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.content.targeting.report.campaign.mobile.model.impl.CampaignMobileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return campaignMobilePersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.content.targeting.report.campaign.mobile.model.impl.CampaignMobileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return campaignMobilePersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return campaignMobilePersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) throws SystemException {
		return campaignMobilePersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public CampaignMobile fetchCampaignMobile(long campaignMobileId)
		throws SystemException {
		return campaignMobilePersistence.fetchByPrimaryKey(campaignMobileId);
	}

	/**
	 * Returns the campaign mobile with the primary key.
	 *
	 * @param campaignMobileId the primary key of the campaign mobile
	 * @return the campaign mobile
	 * @throws PortalException if a campaign mobile with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CampaignMobile getCampaignMobile(long campaignMobileId)
		throws PortalException, SystemException {
		return campaignMobilePersistence.findByPrimaryKey(campaignMobileId);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException, SystemException {
		return campaignMobilePersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the campaign mobiles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.content.targeting.report.campaign.mobile.model.impl.CampaignMobileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of campaign mobiles
	 * @param end the upper bound of the range of campaign mobiles (not inclusive)
	 * @return the range of campaign mobiles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CampaignMobile> getCampaignMobiles(int start, int end)
		throws SystemException {
		return campaignMobilePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of campaign mobiles.
	 *
	 * @return the number of campaign mobiles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getCampaignMobilesCount() throws SystemException {
		return campaignMobilePersistence.countAll();
	}

	/**
	 * Updates the campaign mobile in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param campaignMobile the campaign mobile
	 * @return the campaign mobile that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CampaignMobile updateCampaignMobile(CampaignMobile campaignMobile)
		throws SystemException {
		return campaignMobilePersistence.update(campaignMobile);
	}

	/**
	 * Returns the campaign mobile local service.
	 *
	 * @return the campaign mobile local service
	 */
	public com.liferay.content.targeting.report.campaign.mobile.service.CampaignMobileLocalService getCampaignMobileLocalService() {
		return campaignMobileLocalService;
	}

	/**
	 * Sets the campaign mobile local service.
	 *
	 * @param campaignMobileLocalService the campaign mobile local service
	 */
	public void setCampaignMobileLocalService(
		com.liferay.content.targeting.report.campaign.mobile.service.CampaignMobileLocalService campaignMobileLocalService) {
		this.campaignMobileLocalService = campaignMobileLocalService;
	}

	/**
	 * Returns the campaign mobile remote service.
	 *
	 * @return the campaign mobile remote service
	 */
	public com.liferay.content.targeting.report.campaign.mobile.service.CampaignMobileService getCampaignMobileService() {
		return campaignMobileService;
	}

	/**
	 * Sets the campaign mobile remote service.
	 *
	 * @param campaignMobileService the campaign mobile remote service
	 */
	public void setCampaignMobileService(
		com.liferay.content.targeting.report.campaign.mobile.service.CampaignMobileService campaignMobileService) {
		this.campaignMobileService = campaignMobileService;
	}

	/**
	 * Returns the campaign mobile persistence.
	 *
	 * @return the campaign mobile persistence
	 */
	public CampaignMobilePersistence getCampaignMobilePersistence() {
		return campaignMobilePersistence;
	}

	/**
	 * Sets the campaign mobile persistence.
	 *
	 * @param campaignMobilePersistence the campaign mobile persistence
	 */
	public void setCampaignMobilePersistence(
		CampaignMobilePersistence campaignMobilePersistence) {
		this.campaignMobilePersistence = campaignMobilePersistence;
	}

	/**
	 * Returns the campaign mobile finder.
	 *
	 * @return the campaign mobile finder
	 */
	public CampaignMobileFinder getCampaignMobileFinder() {
		return campaignMobileFinder;
	}

	/**
	 * Sets the campaign mobile finder.
	 *
	 * @param campaignMobileFinder the campaign mobile finder
	 */
	public void setCampaignMobileFinder(
		CampaignMobileFinder campaignMobileFinder) {
		this.campaignMobileFinder = campaignMobileFinder;
	}

	/**
	 * Returns the consumer data local service.
	 *
	 * @return the consumer data local service
	 */
	public com.liferay.content.targeting.report.campaign.mobile.service.ConsumerDataLocalService getConsumerDataLocalService() {
		return consumerDataLocalService;
	}

	/**
	 * Sets the consumer data local service.
	 *
	 * @param consumerDataLocalService the consumer data local service
	 */
	public void setConsumerDataLocalService(
		com.liferay.content.targeting.report.campaign.mobile.service.ConsumerDataLocalService consumerDataLocalService) {
		this.consumerDataLocalService = consumerDataLocalService;
	}

	/**
	 * Returns the consumer data remote service.
	 *
	 * @return the consumer data remote service
	 */
	public com.liferay.content.targeting.report.campaign.mobile.service.ConsumerDataService getConsumerDataService() {
		return consumerDataService;
	}

	/**
	 * Sets the consumer data remote service.
	 *
	 * @param consumerDataService the consumer data remote service
	 */
	public void setConsumerDataService(
		com.liferay.content.targeting.report.campaign.mobile.service.ConsumerDataService consumerDataService) {
		this.consumerDataService = consumerDataService;
	}

	/**
	 * Returns the consumer data persistence.
	 *
	 * @return the consumer data persistence
	 */
	public ConsumerDataPersistence getConsumerDataPersistence() {
		return consumerDataPersistence;
	}

	/**
	 * Sets the consumer data persistence.
	 *
	 * @param consumerDataPersistence the consumer data persistence
	 */
	public void setConsumerDataPersistence(
		ConsumerDataPersistence consumerDataPersistence) {
		this.consumerDataPersistence = consumerDataPersistence;
	}

	/**
	 * Returns the consumer data finder.
	 *
	 * @return the consumer data finder
	 */
	public ConsumerDataFinder getConsumerDataFinder() {
		return consumerDataFinder;
	}

	/**
	 * Sets the consumer data finder.
	 *
	 * @param consumerDataFinder the consumer data finder
	 */
	public void setConsumerDataFinder(ConsumerDataFinder consumerDataFinder) {
		this.consumerDataFinder = consumerDataFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		Class<?> clazz = getClass();

		_classLoader = clazz.getClassLoader();

		PersistedModelLocalServiceRegistryUtil.register("com.liferay.content.targeting.report.campaign.mobile.model.CampaignMobile",
			campaignMobileLocalService);
	}

	public void destroy() {
		PersistedModelLocalServiceRegistryUtil.unregister(
			"com.liferay.content.targeting.report.campaign.mobile.model.CampaignMobile");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	@Override
	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (contextClassLoader != _classLoader) {
			currentThread.setContextClassLoader(_classLoader);
		}

		try {
			return _clpInvoker.invokeMethod(name, parameterTypes, arguments);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	protected Class<?> getModelClass() {
		return CampaignMobile.class;
	}

	protected String getModelClassName() {
		return CampaignMobile.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = campaignMobilePersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.content.targeting.report.campaign.mobile.service.CampaignMobileLocalService.class)
	protected com.liferay.content.targeting.report.campaign.mobile.service.CampaignMobileLocalService campaignMobileLocalService;
	@BeanReference(type = com.liferay.content.targeting.report.campaign.mobile.service.CampaignMobileService.class)
	protected com.liferay.content.targeting.report.campaign.mobile.service.CampaignMobileService campaignMobileService;
	@BeanReference(type = CampaignMobilePersistence.class)
	protected CampaignMobilePersistence campaignMobilePersistence;
	@BeanReference(type = CampaignMobileFinder.class)
	protected CampaignMobileFinder campaignMobileFinder;
	@BeanReference(type = com.liferay.content.targeting.report.campaign.mobile.service.ConsumerDataLocalService.class)
	protected com.liferay.content.targeting.report.campaign.mobile.service.ConsumerDataLocalService consumerDataLocalService;
	@BeanReference(type = com.liferay.content.targeting.report.campaign.mobile.service.ConsumerDataService.class)
	protected com.liferay.content.targeting.report.campaign.mobile.service.ConsumerDataService consumerDataService;
	@BeanReference(type = ConsumerDataPersistence.class)
	protected ConsumerDataPersistence consumerDataPersistence;
	@BeanReference(type = ConsumerDataFinder.class)
	protected ConsumerDataFinder consumerDataFinder;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private String _beanIdentifier;
	private ClassLoader _classLoader;
	private CampaignMobileLocalServiceClpInvoker _clpInvoker = new CampaignMobileLocalServiceClpInvoker();
}