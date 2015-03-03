<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>

<portlet:defineObjects />
<%
	Map<String, String> map = (Map<String, String>) renderRequest
			.getAttribute("mapPortal");
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
		<li>Numero de visitas en el año 2015: <%=numVisitasYear%></li>
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
			<legend><u>Start Date</u></legend>
			<br>
			<label for="day_start">Day</label> <select id="day_start"
				name="day_start" />
			<option>01</option>
			<option>02</option>
			<option>03</option>
			<option>04</option>
			<option>05</option>
			<option>06</option>
			<option>07</option>
			<option>08</option>
			<option>09</option>
			<option>10</option>
			<option>11</option>
			<option>12</option>
			<option>13</option>
			<option>14</option>
			<option>15</option>
			<option>16</option>
			<option>17</option>
			<option>18</option>
			<option>19</option>
			<option>20</option>
			<option>21</option>
			<option>22</option>
			<option>23</option>
			<option>24</option>
			<option>25</option>
			<option>26</option>
			<option>27</option>
			<option>28</option>
			<option>29</option>
			<option>30</option>
			<option>31</option>
			</select>- <label for="month_start">Month</label> <select id="month_start"
				name="month_start" />
			<option>01</option>
			<option>02</option>
			<option>03</option>
			<option>04</option>
			<option>05</option>
			<option>06</option>
			<option>07</option>
			<option>08</option>
			<option>09</option>
			<option>10</option>
			<option>11</option>
			<option>12</option>
			</select> - <label for="year_start">Year</label> <select id="year_start"
				name="year_start" />
			<option>2013</option>
			<option>2014</option>
			<option>2015</option>
			</select>
			<br><br>
			<legend><u>End Date</u></legend>
			<br>
			<label for="day_end">Day</label> <select id="day_end" name="day_end" />
			<option>01</option>
			<option>02</option>
			<option>03</option>
			<option>04</option>
			<option>05</option>
			<option>06</option>
			<option>07</option>
			<option>08</option>
			<option>09</option>
			<option>10</option>
			<option>11</option>
			<option>12</option>
			<option>13</option>
			<option>14</option>
			<option>15</option>
			<option>16</option>
			<option>17</option>
			<option>18</option>
			<option>19</option>
			<option>20</option>
			<option>21</option>
			<option>22</option>
			<option>23</option>
			<option>24</option>
			<option>25</option>
			<option>26</option>
			<option>27</option>
			<option>28</option>
			<option>29</option>
			<option>30</option>
			<option>31</option>
			</select> - <label for="month_end">Month</label> <select id="month_end"
				name="month_end" />
			<option>01</option>
			<option>02</option>
			<option>03</option>
			<option>04</option>
			<option>05</option>
			<option>06</option>
			<option>07</option>
			<option>08</option>
			<option>09</option>
			<option>10</option>
			<option>11</option>
			<option>12</option>
			</select> - <label for="year_end">Year</label> <select id="year_end"
				name="year_end" />
			<option>2013</option>
			<option>2014</option>
			<option>2015</option>

			</select>
			<br>
			<br><input type="submit" value="Mostrar" /></r>
		</fieldset>

		
		<%
			if (renderRequest.getAttribute("numVisitasIntervalo") != null) {
				System.out.println(renderRequest.getAttribute("numVisitasIntervalo"));	
		%>
		<option value="<%=renderRequest.getAttribute("numVisitasIntervalo")%>">Numero de visitas: <%=renderRequest.getAttribute("numVisitasIntervalo")%> visitas.</option>
		<% }%>
		<%
			if (renderRequest.getAttribute("operativo") != null) {
				System.out.println(renderRequest.getAttribute("operativo"));	
		%>
		<option value="<%=renderRequest.getAttribute("operativo")%>">Sistema operativo: <%=renderRequest.getAttribute("operativo")%></option>
		<% }%>
		

	</form>

	</fieldset>

</div>
