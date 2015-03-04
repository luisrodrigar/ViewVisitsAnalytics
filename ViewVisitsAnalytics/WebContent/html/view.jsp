<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>

<portlet:defineObjects />
<%
	Map<String, String> map = (Map<String, String>) renderRequest
	.getAttribute("mapPortal");
%>
<%
	Map<String, String> mapCountry = (Map<String, String>) renderRequest
	.getAttribute("mapCountry");
%>
<%
	Map<String, String> mapSO = (Map<String, String>) renderRequest
	.getAttribute("mapSO");
%>
<%
	Map<String, String> mapPages = (Map<String, String>) renderRequest
	.getAttribute("mapPages");
%>
<%
	String numVisitasDay = renderRequest.getAttribute("numVisitasDay")
	.toString();
%>
<%
	String numVisitasMonth = renderRequest.getAttribute(
	"numVisitasMonth").toString();
%>
<%
	String numVisitasYear = renderRequest
	.getAttribute("numVisitasYear").toString();
%>


<div id="prueba">
	<h3>Portales</h3>
	<ul>
		<%
			for (String portal : map.keySet()) {
		%>
		<li><%=portal%> - <%=map.get(portal)%></li>
		<li>Numero de visitas en el dia 6/1/2015: <%=numVisitasDay%></li>
		<li>Numero de visitas en el mes 12/2014: <%=numVisitasMonth%></li>
		<li>Numero de visitas en el a�o 2015: <%=numVisitasYear%></li>
		<%
			}
		%>
	</ul>
	<fieldset class="date">
		Select a portal: <select>
			<%
				for (String portal : map.keySet()) {
			%>
			<option value="<%=portal%>"><%=portal%></option>
			<%
				}
			%>
		</select>
	</fieldset>
	<form action="<portlet:actionURL></portlet:actionURL>" method="post">
		<fieldset class="date">
			<legend>Numero de visitas por intervalo</legend>
			<legend>
				<u>Start Date</u>
			</legend>
			<br> <label for="day_start">Day</label> <select id="day_start"
				name="day_start" />
			<option>01</option><option>02</option><option>03</option><option>04</option><option>05</option>
			<option>06</option><option>07</option><option>08</option><option>09</option>
			<option>10</option><option>11</option><option>12</option><option>13</option>
			<option>14</option><option>15</option><option>16</option><option>17</option>
			<option>18</option><option>19</option><option>20</option><option>21</option>
			<option>22</option><option>23</option><option>24</option><option>25</option>
			<option>26</option><option>27</option><option>28</option><option>29</option>
			<option>30</option><option>31</option>
			</select>- <label for="month_start">Month</label> <select id="month_start"
				name="month_start" />
			<option>01</option><option>02</option><option>03</option><option>04</option>
			<option>05</option><option>06</option><option>07</option><option>08</option>
			<option>09</option><option>10</option><option>11</option><option>12</option>
			</select> - <label for="year_start">Year</label> <select id="year_start"
				name="year_start" />
			<option>2013</option><option>2014</option><option>2015</option>
			</select> <br>
			<br>
			<legend>
				<u>End Date</u>
			</legend>
			<br> <label for="day_end">Day</label> <select id="day_end"
				name="day_end" />
			<option>01</option><option>02</option><option>03</option><option>04</option><option>05</option>
			<option>06</option><option>07</option><option>08</option><option>09</option>
			<option>10</option><option>11</option><option>12</option><option>13</option>
			<option>14</option><option>15</option><option>16</option><option>17</option>
			<option>18</option><option>19</option><option>20</option><option>21</option>
			<option>22</option><option>23</option><option>24</option><option>25</option>
			<option>26</option><option>27</option><option>28</option><option>29</option>
			<option>30</option><option>31</option>
			</select> - <label for="month_end">Month</label> <select id="month_end"
				name="month_end" />
			<option>01</option><option>02</option><option>03</option><option>04</option>
			<option>05</option><option>06</option><option>07</option><option>08</option>
			<option>09</option><option>10</option><option>11</option><option>12</option>
			</select> - <label for="year_end">Year</label> <select id="year_end"
				name="year_end" />
			<option>2015</option><option>2014</option><option>2013</option>

			</select> <br> <br>
			<input type="submit" value="Mostrar" />
			</r>
		</fieldset>


		<%
			if (renderRequest.getAttribute("numVisitasIntervalo") != null) {
				System.out.println(renderRequest.getAttribute("numVisitasIntervalo"));
		%>
		<option value="<%=renderRequest.getAttribute("numVisitasIntervalo")%>">Numero
			de visitas:
			<%=renderRequest.getAttribute("numVisitasIntervalo")%> visitas.
		</option>
		<%
			}
		%>



	</form>

  <script type="text/javascript" src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1','packages':['corechart']}]}"></script>
    
          <div id="ex0"></div>
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<script type="text/javascript">

      // Load the Visualization API and the piechart package.
      google.load('visualization', '1.0', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.setOnLoadCallback(drawChart);
      
 var dataPieCountry = [
    				<%if(mapCountry!=null){ for(int i = 0; i<mapCountry.keySet().size(); i++){ 
    				if(i<mapCountry.keySet().size()-1){%>			
    					["<%=mapCountry.keySet().toArray()[i]%>",<%=mapCountry.get(mapCountry.keySet().toArray()[i])%>],
     				<%}else {%>
     				["<%=mapCountry.keySet().toArray()[i]%>",<%=mapCountry.get(mapCountry.keySet().toArray()[i])%>],
     				<%}
     				}
    				}%>
     			]; 
     			

     	
 var dataPieSSOO = [
      				<%if(mapSO!=null){ for(int i = 0; i<mapSO.keySet().size(); i++){ 
      				if(i<mapSO.keySet().size()-1){%>			
      					['<%=mapSO.keySet().toArray()[i]%>',<%=mapSO.get(mapSO.keySet().toArray()[i])%>],
       				<%}else {%>
       					['<%=mapSO.keySet().toArray()[i]%>',<%=mapSO.get(mapSO.keySet().toArray()[i])%>]
       				<%}
       				}
      				}%>
       			]; 
        

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {

       // Create the data table.
       /*  var dataCountry = new google.visualization.DataTable(); */
/*         dataCountry.addColumn('string', 'Country');
        dataCountry.addColumn('number', 'Visits'); */
      //  dataCountry.addRows(dataPieCountry);
  /*       dataCountry.addColumn('timeofday', 'Time of Day');
        dataCountry.addColumn('number', 'Motivation Level');
      */
       var dataSO = new google.visualization.DataTable();
       dataSO.addColumn('string', 'SSOO');
       dataSO.addColumn('number', 'Visitas por ssoo de Android');
      dataSO.addRows(dataPieSSOO); 
         

        // Set chart options
      /*   var optionsCountry = {'title':'N�mero de visitas hechas por pais',
                       'width':400,
                       'height':300}; */
                      /*  dataPieCountry.addRows([
                                		        [{v: [8, 0, 0], f: '8 am'}, 1],
                                		        [{v: [9, 0, 0], f: '9 am'}, 2],
                                		        [{v: [10, 0, 0], f:'10 am'}, 3],
                                		        [{v: [11, 0, 0], f: '11 am'}, 4],
                                		        [{v: [12, 0, 0], f: '12 pm'}, 5],
                                		        [{v: [13, 0, 0], f: '1 pm'}, 6],
                                		        [{v: [14, 0, 0], f: '2 pm'}, 7],
                                		        [{v: [15, 0, 0], f: '3 pm'}, 8],
                                		        [{v: [16, 0, 0], f: '4 pm'}, 9],
                                		        [{v: [17, 0, 0], f: '5 pm'}, 10],
                                		      ]); */
      /*     var optionsCountry = {
        		  width: 1000,
        	        height: 563,
        	        hAxis: {
        	        	  title: 'Time of Day',
        	              format: 'h:mm a',
        	              gridlines: {count: 10}
        	        },
        	        vAxis: {
        	          title: 'Rating (scale of 1-10)'
        	        }
        	      };          
                       
                                		      */             
         var optionsSO = {'title':'Versiones de sistema operativo',
                'width':400,
                'height':300};  
        

        // Instantiate and draw our chart, passing in some options.
      /*   var chartCountry = new google.visualization.ColumnChart(document.getElementById('chart_div_country'));
        chartCoutry.draw(dataCountry, optionsCountry);*/
         var chartSO = new google.visualization.PieChart(document.getElementById('chart_div_ssoo'));
        chartSO.draw(dataSO, optionsSO);  

          var data = new google.visualization.DataTable();
          data.addColumn('string', 'Time of Day');
          data.addColumn('number', 'Motivation Level');

          data.addRows(dataPieCountry);

          var options = {
            width: 400,
            height: 300,
            hAxis: {
              title: 'Time of Day',
              gridlines: {count: 5}
            },
            vAxis: {
              title: 'Rating (scale of 1-10)'
            }
          };

          var chart = new google.visualization.ColumnChart(
            document.getElementById('ex0'));

          chart.draw(dataSO, optionsSO);
        
        
      }
    </script>
	<body>
		<!--Div that will hold the pie chart-->

		<%
			if (mapCountry != null) {
		%>
		<div id="ex0"></div>
		<%
			}
		%>
		<%
			if (mapSO != null) {
		%>
		<div id="chart_div_ssoo"></div>
		<%
			}
		%>
	</body>



</div>
