<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ page import="java.util.*"%>

<portlet:defineObjects />

<%
	Map<String, String> map = (Map<String, String>) renderRequest
			.getAttribute("mapPortal");
%>
<div id="prueba">
	<form action="<portlet:actionURL></portlet:actionURL>" method="post">
		<label for="portal">Portal</label> <select name="portal" id="portal">
			<%
				for (String portal : map.keySet()) {
			%>
			<option value="<%=map.get(portal)%>"><%=portal%></option>
			<%
				}
			%>
		</select> <label for="inicio">Fecha inicio</label> <input id="inicio"
			type="date" name="fecha_inicio"> <label for="fin">Fecha
			fin</label> <input id="fin" type="date" name="fecha_fin"> <input
			type="submit" value="Consultar visitas" />
	</form>
</div>
<%
	if (renderRequest.getAttribute("id") != null) {
%>
<div id="info">
	<hr>
	<p>
		Visitas para el portal
		<%=renderRequest.getAttribute("id")%>
		</br>Entre las fechas:
		<%=renderRequest.getAttribute("fInicio")%>
		-
		<%=renderRequest.getAttribute("fFin")%>
		</br>
		<%=renderRequest.getAttribute("visits")%>
		visitas
	</p>
</div>
<%} %>
