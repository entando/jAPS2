<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="aps-core.tld" prefix="wp" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>
<%-- check for extra parameter --%>
<s:if test="%{null != #targetParamName}"> <%-- Add here in && blocks other contexts allowed to include "get" parameters. See resource-intro.jsp . --%>
	<wpsa:actionParam var="URLlist" action="list">
		<wpsa:actionSubParam name="%{#targetParamName}" value="%{#targetParamValue}"></wpsa:actionSubParam>
	</wpsa:actionParam>
	<wpsa:actionParam var="URLnew" action="new">
		<wpsa:actionSubParam name="%{#targetParamName}" value="%{#targetParamValue}"></wpsa:actionSubParam>
	</wpsa:actionParam>
</s:if>
<s:else>
	<wpsa:actionParam var="URLlist" action="list" />
	<wpsa:actionParam var="URLnew" action="new" />
</s:else>
<%-- anchor print --%>
<a href="<s:url action="%{#URLlist}" namespace="%{#targetNS}" />" title="<s:text name="label.list" />">
	<img src="<wp:resourceURL/>administration/common/img/icons/32x32/general-list.png" alt="<s:text name="label.list" />" />
</a>
<a href="<s:url action="%{#URLnew}" namespace="%{#targetNS}" />" title="<s:text name="label.new" />">
	<img src="<wp:resourceURL/>administration/common/img/icons/32x32/general-new.png" alt="<s:text name="label.new" />" />
</a>