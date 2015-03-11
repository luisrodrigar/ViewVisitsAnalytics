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
	<div class="page-header">
		<h1>
			Obtener información del sitio web <br><small>Google Analytics</small>
		</h1>
	</div>
	
	<%
	if(map.get("idGoogleAnalytics")!=null && !map.get("idGoogleAnalytics").isEmpty()){
	%>
	<form class="form-horizontal"
		action="<portlet:actionURL></portlet:actionURL>" method="post">
		<div class="form-group">
			<label for="name" class="col-sm-2">Sitio Web</label>
		<div class="col-sm-6">
			<input class="form-control" id="name" name="name" type="text" value="    <%=map.get("name") %>" readonly>
		</div>
		</div>
		<div class="form-group">
			<label for="inicio" class="col-sm-2">Inicio</label>
			<div class="col-sm-6">
				<input id="datepicker_start" name="fecha_inicio"
					class="form-control" placeholder="Fecha inicial" required>
			</div>
		</div>
		<div class="form-group">
			<label for="fin" class="col-sm-2">Fin</label>
			<div class="col-sm-6">
				<input id="datepicker_end" class="form-control" name="fecha_fin"
					placeholder="Fecha final" required>
			</div>
		</div>
		<input type="submit" class="btn btn-primary" value="Consultar" />
	</form>
	<%}else{ %>
	<div class="alert alert-danger" role="alert">
	No se ha definido un UA para el sitio en el que está...
	</div>
	<%} %>
</div>
<%
	if (renderRequest.getAttribute("id") != null) {
%>
<div id="info">
	<hr>
	<h3>
		Información de "<%=renderRequest.getAttribute("name")%>"
	</h3>
	<p>
		Identificador
		<%=renderRequest.getAttribute("id")%>
		de Google Analytics <br>Entre las fechas
		<%=renderRequest.getAttribute("fInicio")%>
		-
		<%=renderRequest.getAttribute("fFin")%>
		<br>
		<%=renderRequest.getAttribute("visits")%>
		visitas totales
	</p>
	<input type="submit" onclick="show('chart_div_os')"
		class="btn btn-primary" value="Sistemas operativos" /> <input
		type="submit" onclick="show('chart_div_browser')"
		class="btn btn-primary" value="Navegadores web" /> <input
		type="submit" onclick="show('paginas_visitas')"
		class="btn btn-primary" value="Los más visto" />
	<div id="paginas_visitas" style="display: none;">
		<hr>
		<table class="table">
			<tr>
				<th>Página</th>
				<th>Visitas</th>
			</tr>
			<%
				for (String pagina : mapVisitsPage.keySet()) {
			%>
			<tr>
				<td><%=pagina%></td>
				<td><%=mapVisitsPage.get(pagina)%></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
	<div id="graficos">
		<div id="chart_div_os" style="display: none;"></div>
		<div id="chart_div_browser" style="display: none;"></div>
	</div>

</div>
<%
	}
%>

<script type="text/javascript">
	function show(string){
		document.getElementById('chart_div_os').style.display='none';
		document.getElementById('chart_div_browser').style.display='none';
		document.getElementById('paginas_visitas').style.display='none';
		document.getElementById(string).style.display='block';
	};
</script>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">

      // Load the Visualization API and the piechart package.
      google.load('visualization', '1.0', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.setOnLoadCallback(drawChart);
      
      var dataPieOS = [
     				<%if(mapOS!=null){ for(int i = 0; i<mapOS.keySet().size(); i++){ 
     				if(i<mapOS.keySet().size()-1){%>			
     					['<%=mapOS.keySet().toArray()[i]%>',<%=mapOS.get(mapOS.keySet().toArray()[i])%>],
     				<%}else {%>
     					['<%=mapOS.keySet().toArray()[i]%>',<%=mapOS.get(mapOS.keySet().toArray()[i])%>]
     				<%}
     				}
     				}%>
     			];
      
      var dataPieBrowser = [
        				<%if(mapBrowser!=null){ for(int i = 0; i<mapBrowser.keySet().size(); i++){ 
        				if(i<mapBrowser.keySet().size()-1){%>			
        					['<%=mapBrowser.keySet().toArray()[i]%>',<%=mapBrowser.get(mapBrowser.keySet().toArray()[i])%>],
        				<%}else {%>
        					['<%=mapBrowser.keySet().toArray()[i]%>',<%=mapBrowser.get(mapBrowser.keySet().toArray()[i])%>]
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
        var optionsOS = {'title':'Visitas por sistema operativo',
                       'width':350,
                       'height':250};
        
        var optionsBrowser = {'title':'Visitas por navegador web',
                'width':350,
                'height':250};

        // Instantiate and draw our chart, passing in some options.
        var chartOS = new google.visualization.PieChart(document.getElementById('chart_div_os'));
        var chartBrowser = new google.visualization.PieChart(document.getElementById('chart_div_browser'));
        chartOS.draw(dataOS, optionsOS);
        chartBrowser.draw(dataBrowser, optionsBrowser);
      }
    </script>

<!--  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.3/themes/smoothness/jquery-ui.css"> -->
</html><script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript"
	src="//code.jquery.com/ui/1.11.3/jquery-ui.js"></script>

<script type="text/javascript">
  $(function() {
    $( "#datepicker_start" ).datepicker({
    	dateFormat: "yy-mm-dd"
    });
    $( "#datepicker_end" ).datepicker({
    	dateFormat: "yy-mm-dd"
    });
    $( "#anim" ).change(function() {
        $( "#datepicker_start" ).datepicker( "option", "showAnim", "blind");
      });
    $( "#anim" ).change(function() {
        $( "#datepicker_end" ).datepicker( "option", "showAnim", "blind");
      });
  });
  </script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script type="text/javascript"
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

