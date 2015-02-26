<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ page import="java.util.*" %>

<portlet:defineObjects />

<portlet:actionURL name="getVisitsAction" var="getVisitsActionURL"></portlet:actionURL>

<script>
	function showDiv(){
		document.getElementById("info").style.display = "block";
	};
</script>

<% Map<String, String> map = (Map<String, String>) renderRequest.getAttribute("mapPortal"); %>
<div id="prueba">
	<form action="${getVisitsActionURL }" method="post">
		<label for="portal">Portal</label>
			<select name="portal" id="portal">
				<%for(String portal: map.keySet()) {%>
					<option value="<%=map.get(portal) %>"><%=portal %></option>
				<%} %>
			</select>
		<label for="inicio">Fecha inicio</label>	
			<input id="inicio" type="date" name="fecha_inicio">
		<label for="fin">Fecha inicio</label>	
			<input id="fin" type="date" name="fecha_fin">
		<input type="submit" value="Consultar visitas"/>
	</form>
</div>
<div id="info">
	<p><%= renderRequest.getAttribute("visits") %></p>
</div>
