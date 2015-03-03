<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ page import="java.util.*"%>

<portlet:defineObjects />

<%
	Map<String, String> map = (Map<String, String>) renderRequest
			.getAttribute("mapPortal");
	Map<String, String> mapVisitsPage = (Map<String, String>) renderRequest
			.getAttribute("visitsPage");
	Map<String, String> mapOS = (Map<String, String>) renderRequest
			.getAttribute("visitsOS");
	Map<String, String> mapBrowser = (Map<String, String>) renderRequest
			.getAttribute("visitsBrowser");
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
			type="date" name="fecha_inicio" > <label for="fin">Fecha
			fin</label> <input id="fin" type="date" name="fecha_fin"> <input
			type="submit" value="Consultar" />
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
	<div id="graficos">
	<div id="chart_div_os"></div>
	<div id="chart_div_browser"></div>
	</div>
	<div id="paginas_visitas"" >
	<hr>
	<h3>Páginas más vistas</h3>
	<table border="1">
	<tr>
		<th>Página</th>
		<th>Visitas</th>
	</tr>
	<%
		for (String pagina : mapVisitsPage.keySet()) {
	%>
	<tr>
	<td>
		<%= pagina %>
	</td>
	<td>
		<%=mapVisitsPage.get(pagina) %>
	</td>
	</tr>
	<%
		}
	%>
	</table>
	</div>
	

</div>
<%
	}
%>
			
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">

      // Load the Visualization API and the piechart package.
      google.load('visualization', '1.0', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.setOnLoadCallback(drawChart);
      
      var dataPieOS = [
     				<% if(mapOS!=null){ for(int i = 0; i<mapOS.keySet().size(); i++){ 
     				if(i<mapOS.keySet().size()-1){%>			
     					['<%=mapOS.keySet().toArray()[i]%>',<%=mapOS.get(mapOS.keySet().toArray()[i]) %>],
     				<%}else {%>
     					['<%=mapOS.keySet().toArray()[i]%>',<%=mapOS.get(mapOS.keySet().toArray()[i]) %>]
     				<%}
     				}
     				}%>
     			];
      
      var dataPieBrowser = [
        				<% if(mapBrowser!=null){ for(int i = 0; i<mapBrowser.keySet().size(); i++){ 
        				if(i<mapBrowser.keySet().size()-1){%>			
        					['<%=mapBrowser.keySet().toArray()[i]%>',<%=mapBrowser.get(mapBrowser.keySet().toArray()[i]) %>],
        				<%}else {%>
        					['<%=mapBrowser.keySet().toArray()[i]%>',<%=mapBrowser.get(mapBrowser.keySet().toArray()[i]) %>]
        				<%}
        				}
        				}%>
        			];

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {

        // Create the data table.
        var dataOS = new google.visualization.DataTable();
        dataOS.addColumn('string', 'OS');
        dataOS.addColumn('number', 'Slices');
        dataOS.addRows(dataPieOS);
        
        var dataBrowser = new google.visualization.DataTable();
        dataBrowser.addColumn('string', 'Browser');
        dataBrowser.addColumn('number', 'Slices');
        dataBrowser.addRows(dataPieBrowser);

        // Set chart options
        var optionsOS = {'title':'Número de visitas hechas por cada sistema operativo',
                       'width':300,
                       'height':200};
        
        var optionsBrowser = {'title':'Número de visitas hechas por cada navegador web',
                'width':300,
                'height':200};

        // Instantiate and draw our chart, passing in some options.
        var chartOS = new google.visualization.PieChart(document.getElementById('chart_div_os'));
        var chartBrowser = new google.visualization.PieChart(document.getElementById('chart_div_browser'));
        chartOS.draw(dataOS, optionsOS);
        chartBrowser.draw(dataBrowser, optionsBrowser);
      }
    </script>
