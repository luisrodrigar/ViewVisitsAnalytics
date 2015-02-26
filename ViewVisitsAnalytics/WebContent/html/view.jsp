<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ page import="java.util.*" %>

<portlet:defineObjects />
<% Map<String, String> map = (Map<String, String>) renderRequest.getAttribute("mapPortal"); %>
<% int numVisitasDay = Integer.parseInt(renderRequest.getAttribute("numVisitasDay")); %>
<% int numVisitasMonth = Integer.parseInt(renderRequest.getAttribute("numVisitasMonth")); %>
<% int numVisitasYear = Integer.parseInt(renderRequest.getAttribute("numVisitasYear")); %>
<div id="prueba">
	<h3>Portales</h3>
	<ul>
	<%for(String portal: map.keySet()) {%>
		<li><%=portal %> - <%=map.get(portal) %></li>
		<li>Numero de visitas en el dia: <%=numVisitasDay %></li>
		<li>Numero de visitas en el mes: <%=numVisitasMonth %></li>
		<li>Numero de visitas en el año: <%=numVisitasYear %></li>
	<%} %>
	</ul>
</div>
