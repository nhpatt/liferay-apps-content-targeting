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

package com.liferay.content.targeting.anonymous.users.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.content.targeting.anonymous.users.model.AnonymousUser;
import com.liferay.content.targeting.anonymous.users.service.AnonymousUserLocalService;
import com.liferay.content.targeting.anonymous.users.service.persistence.AnonymousUserPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.persistence.ClassNamePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.exportimport.lar.ExportImportHelperUtil;
import com.liferay.portlet.exportimport.lar.ManifestSummary;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the anonymous user local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.content.targeting.anonymous.users.service.impl.AnonymousUserLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.content.targeting.anonymous.users.service.impl.AnonymousUserLocalServiceImpl
 * @see com.liferay.content.targeting.anonymous.users.service.AnonymousUserLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class AnonymousUserLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements AnonymousUserLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.content.targeting.anonymous.users.service.AnonymousUserLocalServiceUtil} to access the anonymous user local service.
	 */

	/**
	 * Adds the anonymous user to the database. Also notifies the appropriate model listeners.
	 *
	 * @param anonymousUser the anonymous user
	 * @return the anonymous user that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AnonymousUser addAnonymousUser(AnonymousUser anonymousUser) {
		anonymousUser.setNew(true);

		return anonymousUserPersistence.update(anonymousUser);
	}

	/**
	 * Creates a new anonymous user with the primary key. Does not add the anonymous user to the database.
	 *
	 * @param anonymousUserId the primary key for the new anonymous user
	 * @return the new anonymous user
	 */
	@Override
	public AnonymousUser createAnonymousUser(long anonymousUserId) {
		return anonymousUserPersistence.create(anonymousUserId);
	}

	/**
	 * Deletes the anonymous user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param anonymousUserId the primary key of the anonymous user
	 * @return the anonymous user that was removed
	 * @throws PortalException if a anonymous user with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public AnonymousUser deleteAnonymousUser(long anonymousUserId)
		throws PortalException {
		return anonymousUserPersistence.remove(anonymousUserId);
	}

	/**
	 * Deletes the anonymous user from the database. Also notifies the appropriate model listeners.
	 *
	 * @param anonymousUser the anonymous user
	 * @return the anonymous user that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public AnonymousUser deleteAnonymousUser(AnonymousUser anonymousUser) {
		return anonymousUserPersistence.remove(anonymousUser);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(AnonymousUser.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return anonymousUserPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.content.targeting.anonymous.users.model.impl.AnonymousUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return anonymousUserPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.content.targeting.anonymous.users.model.impl.AnonymousUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return anonymousUserPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return anonymousUserPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return anonymousUserPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public AnonymousUser fetchAnonymousUser(long anonymousUserId) {
		return anonymousUserPersistence.fetchByPrimaryKey(anonymousUserId);
	}

	/**
	 * Returns the anonymous user with the matching UUID and company.
	 *
	 * @param uuid the anonymous user's UUID
	 * @param companyId the primary key of the company
	 * @return the matching anonymous user, or <code>null</code> if a matching anonymous user could not be found
	 */
	@Override
	public AnonymousUser fetchAnonymousUserByUuidAndCompanyId(String uuid,
		long companyId) {
		return anonymousUserPersistence.fetchByUuid_C_First(uuid, companyId,
			null);
	}

	/**
	 * Returns the anonymous user with the primary key.
	 *
	 * @param anonymousUserId the primary key of the anonymous user
	 * @return the anonymous user
	 * @throws PortalException if a anonymous user with the primary key could not be found
	 */
	@Override
	public AnonymousUser getAnonymousUser(long anonymousUserId)
		throws PortalException {
		return anonymousUserPersistence.findByPrimaryKey(anonymousUserId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.content.targeting.anonymous.users.service.AnonymousUserLocalServiceUtil.getService());
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(AnonymousUser.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("anonymousUserId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(com.liferay.content.targeting.anonymous.users.service.AnonymousUserLocalServiceUtil.getService());
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(AnonymousUser.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"anonymousUserId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.content.targeting.anonymous.users.service.AnonymousUserLocalServiceUtil.getService());
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(AnonymousUser.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("anonymousUserId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {
		final ExportActionableDynamicQuery exportActionableDynamicQuery = new ExportActionableDynamicQuery() {
				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(stagedModelType,
						modelAdditionCount);

					long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(portletDataContext,
							stagedModelType);

					manifestSummary.addModelDeletionCount(stagedModelType,
						modelDeletionCount);

					return modelAdditionCount;
				}
			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(new ActionableDynamicQuery.AddCriteriaMethod() {
				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(dynamicQuery,
						"modifiedDate");
				}
			});

		exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<AnonymousUser>() {
				@Override
				public void performAction(AnonymousUser anonymousUser)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						anonymousUser);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(AnonymousUser.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return anonymousUserLocalService.deleteAnonymousUser((AnonymousUser)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return anonymousUserPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns the anonymous user with the matching UUID and company.
	 *
	 * @param uuid the anonymous user's UUID
	 * @param companyId the primary key of the company
	 * @return the matching anonymous user
	 * @throws PortalException if a matching anonymous user could not be found
	 */
	@Override
	public AnonymousUser getAnonymousUserByUuidAndCompanyId(String uuid,
		long companyId) throws PortalException {
		return anonymousUserPersistence.findByUuid_C_First(uuid, companyId, null);
	}

	/**
	 * Returns a range of all the anonymous users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.content.targeting.anonymous.users.model.impl.AnonymousUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of anonymous users
	 * @param end the upper bound of the range of anonymous users (not inclusive)
	 * @return the range of anonymous users
	 */
	@Override
	public List<AnonymousUser> getAnonymousUsers(int start, int end) {
		return anonymousUserPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of anonymous users.
	 *
	 * @return the number of anonymous users
	 */
	@Override
	public int getAnonymousUsersCount() {
		return anonymousUserPersistence.countAll();
	}

	/**
	 * Updates the anonymous user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param anonymousUser the anonymous user
	 * @return the anonymous user that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AnonymousUser updateAnonymousUser(AnonymousUser anonymousUser) {
		return anonymousUserPersistence.update(anonymousUser);
	}

	/**
	 * Returns the anonymous user local service.
	 *
	 * @return the anonymous user local service
	 */
	public AnonymousUserLocalService getAnonymousUserLocalService() {
		return anonymousUserLocalService;
	}

	/**
	 * Sets the anonymous user local service.
	 *
	 * @param anonymousUserLocalService the anonymous user local service
	 */
	public void setAnonymousUserLocalService(
		AnonymousUserLocalService anonymousUserLocalService) {
		this.anonymousUserLocalService = anonymousUserLocalService;
	}

	/**
	 * Returns the anonymous user remote service.
	 *
	 * @return the anonymous user remote service
	 */
	public com.liferay.content.targeting.anonymous.users.service.AnonymousUserService getAnonymousUserService() {
		return anonymousUserService;
	}

	/**
	 * Sets the anonymous user remote service.
	 *
	 * @param anonymousUserService the anonymous user remote service
	 */
	public void setAnonymousUserService(
		com.liferay.content.targeting.anonymous.users.service.AnonymousUserService anonymousUserService) {
		this.anonymousUserService = anonymousUserService;
	}

	/**
	 * Returns the anonymous user persistence.
	 *
	 * @return the anonymous user persistence
	 */
	public AnonymousUserPersistence getAnonymousUserPersistence() {
		return anonymousUserPersistence;
	}

	/**
	 * Sets the anonymous user persistence.
	 *
	 * @param anonymousUserPersistence the anonymous user persistence
	 */
	public void setAnonymousUserPersistence(
		AnonymousUserPersistence anonymousUserPersistence) {
		this.anonymousUserPersistence = anonymousUserPersistence;
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
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name remote service.
	 *
	 * @return the class name remote service
	 */
	public com.liferay.portal.service.ClassNameService getClassNameService() {
		return classNameService;
	}

	/**
	 * Sets the class name remote service.
	 *
	 * @param classNameService the class name remote service
	 */
	public void setClassNameService(
		com.liferay.portal.service.ClassNameService classNameService) {
		this.classNameService = classNameService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
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
		persistedModelLocalServiceRegistry.register("com.liferay.content.targeting.anonymous.users.model.AnonymousUser",
			anonymousUserLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.content.targeting.anonymous.users.model.AnonymousUser");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return AnonymousUserLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return AnonymousUser.class;
	}

	protected String getModelClassName() {
		return AnonymousUser.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = anonymousUserPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.content.targeting.anonymous.users.service.AnonymousUserLocalService.class)
	protected AnonymousUserLocalService anonymousUserLocalService;
	@BeanReference(type = com.liferay.content.targeting.anonymous.users.service.AnonymousUserService.class)
	protected com.liferay.content.targeting.anonymous.users.service.AnonymousUserService anonymousUserService;
	@BeanReference(type = AnonymousUserPersistence.class)
	protected AnonymousUserPersistence anonymousUserPersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.ClassNameLocalService.class)
	protected com.liferay.portal.service.ClassNameLocalService classNameLocalService;
	@BeanReference(type = com.liferay.portal.service.ClassNameService.class)
	protected com.liferay.portal.service.ClassNameService classNameService;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}