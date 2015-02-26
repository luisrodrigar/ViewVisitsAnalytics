<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ page import="java.util.*" %>

<portlet:defineObjects />
<% Map<String, String> map = (Map<String, String>) renderRequest.getAttribute("mapPortal"); %>
<% String numVisitasDay = renderRequest.getAttribute("numVisitasDay").toString(); %>
<% String numVisitasMonth = renderRequest.getAttribute("numVisitasMonth").toString(); %>
<% String numVisitasYear = renderRequest.getAttribute("numVisitasYear").toString(); %>
<div id="prueba">
	<h3>Portales</h3>
	<ul>
	<%for(String portal: map.keySet()) {%>
		<li><%=portal %> - <%=map.get(portal) %></li>
	<li>Numero de visitas en el dia 6/1/2015: <%=numVisitasDay %></li>
	<li>Numero de visitas en el mes 12/2014: <%=numVisitasMonth %></li>
		<li>Numero de visitas en el año 2015: <%=numVisitasYear %></li>
	<%} %>
	</ul>
</div>
