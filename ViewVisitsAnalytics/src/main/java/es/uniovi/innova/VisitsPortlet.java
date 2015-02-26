package main.java.es.uniovi.innova;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import main.java.es.uniovi.innova.services.ga.IGAService;
import main.java.es.uniovi.innova.services.ga.IPortalesService;
import main.java.es.uniovi.innova.services.ga.implementation.google.analytics.GAnalyticsService;
import main.java.es.uniovi.innova.services.ga.implementation.portales.APILiferayPortalesDAO;

public class VisitsPortlet extends GenericPortlet {
	
	IGAService gaService;
	
	@Override
	public void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		IPortalesService portalService = new APILiferayPortalesDAO();
		gaService = new GAnalyticsService();
		gaService.setUA("UA-57349981-1");
		request.setAttribute("mapPortal", portalService.getPortales());
		request.setAttribute("numVisitas", gaService.numOfVisitsByDay(4, 2, 2015));
		System.out.println("Numero de visitas: " + gaService.numOfVisitsByDay(4, 2, 2015) );
		include("/html/view.jsp", request, response);
	}
	
	@ProcessAction(name = "getVisitsAction")
	public void getVisitsAction(ActionRequest request, ActionResponse response)
			throws PortletException, IOException, ParseException {
		String portalID = (String) request.getParameter("portal");
		String inicio = (String) request.getParameter("fecha_inicio");
		String fin = (String) request.getParameter("fecha_fin");
		System.out.println(portalID);
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date fInicio = format.parse(inicio);
		Date fFin = format.parse(fin);
		request.setAttribute("visits",gaService.numOfVisitsByInterval(fInicio, fFin));
	}
	
	protected void include(String path, RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		
		
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
			System.err.println(path + " is not a valid include");
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}
}
