<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ page import="java.util.*" %>

<portlet:defineObjects />
<% Map<String, String> map = (Map<String, String>) renderRequest.getAttribute("mapPortal"); %>
<div id="prueba">
	<h3>Portales</h3>
	<ul>
	<%for(String portal: map.keySet()) {%>
		<li><%=portal %> - <%=map.get(portal) %></li>
	<%} %>
	</ul>
</div>
