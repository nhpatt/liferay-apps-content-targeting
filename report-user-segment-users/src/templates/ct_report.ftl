<#assign aui = PortletJspTagLibs["/META-INF/aui.tld"] />
<#assign liferay_theme = PortletJspTagLibs["/META-INF/liferay-theme.tld"] />
<#assign liferay_ui = PortletJspTagLibs["/META-INF/liferay-ui.tld"] />
<#assign portlet = PortletJspTagLibs["/META-INF/liferay-portlet.tld"] />

<@portlet["defineObjects"] />

<@liferay_theme["defineObjects"] />

<#setting number_format="computer">

<@portlet["renderURL"] varImpl="portletURL">
	<@portlet["param"] name="mvcPath" value="${contentTargetingPath.VIEW_REPORT}" />
	<@portlet["param"] name="redirect" value="${redirect}" />
	<@portlet["param"] name="reportInstanceId" value="${reportInstanceId}" />
	<@portlet["param"] name="reportKey" value="${report.getReportKey()}" />
	<@portlet["param"] name="className" value="${className}" />
	<@portlet["param"] name="classPK" value="${classPK?string}" />
</@>

<div style="margin-bottom: 10px;">
	<@liferay_ui["message"] key="total-users" />:&nbsp;
	<b>
		${totalUsersCount}
	</b>
</div>

<div>
	<@liferay_ui["message"] key="total-anonymous-users" />:&nbsp;
	<b>
		${anonymousUsersCount}
	</b>
</div>

<div>
	<@liferay_ui["message"] key="total-registered-users" />:&nbsp;
	<b>
		${searchContainerIterator.getTotal()}
	</b>
</div>


<@liferay_ui["search-container"]
	emptyResultsMessage="there-is-not-enough-data-to-generate-this-report"
	iteratorURL=portletURL
	total=searchContainerIterator.getTotal()
>
	<@liferay_ui["search-container-results"]
		results=searchContainerIterator.getResults(searchContainer.getStart(), searchContainer.getEnd())
	/>

	<@liferay_ui["search-container-row"]
		className="com.liferay.portal.model.User"
		modelVar="user"
	>

		<@portlet["renderURL"] varImpl="rowURL" portletName="125">
			<@portlet["param"] name="struts_action" value="/users_admin/edit_user" />
			<@portlet["param"] name="redirect" value="${portletURL}" />
			<@portlet["param"] name="p_u_i_d" value=user.getUserId()?string />
		</@>

		<#if !userPermissionUtil.contains(permissionChecker, user.getUserId(), actionKeys.UPDATE)>
			<#assign rowURL=null>
		</#if>

		<@liferay_ui["search-container-column-text"]
			href=rowURL
			name="first-name"
			value=user.getFirstName()
		/>

		<@liferay_ui["search-container-column-text"]
			href=rowURL
			name="last-name"
			value=user.getLastName()
		/>

		<@liferay_ui["search-container-column-text"]
			href=rowURL
			name="screen-name"
			value=user.getScreenName()
		/>

		<@liferay_ui["search-container-column-text"]
			href=rowURL
			name="email-address"
			value=user.getEmailAddress()
		/>

	</@>

<@liferay_ui["search-iterator"] />
</@>