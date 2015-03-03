package main.java.es.uniovi.innova;

import java.io.IOException;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import main.java.es.uniovi.innova.services.ga.IGAService;
import main.java.es.uniovi.innova.services.ga.IPortalesService;
import main.java.es.uniovi.innova.services.ga.implementation.google.analytics.GAnalyticsService;
import main.java.es.uniovi.innova.services.ga.implementation.portales.APILiferayPortalesDAO;

public class VisitsPortlet extends GenericPortlet {

	@Override
	public void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		IPortalesService portalService = new APILiferayPortalesDAO();
		IGAService gaService = new GAnalyticsService();
		gaService.setUA("UA-376062-58");
		request.setAttribute("mapPortal", portalService.getPortales());
		request.setAttribute("numVisitasDay",
				gaService.numOfVisitsByDay(6, 1, 2015));
		request.setAttribute("numVisitasMonth",
				gaService.numOfVisitsByMonth(12, 2014));
		request.setAttribute("numVisitasYear",
				gaService.numOfVisitsByYear(2015));

		include("/html/view.jsp", request, response);
	}

	

	@Override
	public void processAction(ActionRequest actionrequest, ActionResponse actionResponse) {

		int monthStart = Integer.parseInt(actionrequest.getParameter("month_start"));
		int dayStart = Integer.parseInt(actionrequest.getParameter("day_start"));
		int yearStart = Integer.parseInt(actionrequest.getParameter("year_start"));
		int monthEnd = Integer.parseInt(actionrequest.getParameter("month_end"));
		int dayEnd = Integer.parseInt(actionrequest.getParameter("day_end"));
		int yearEnd = Integer.parseInt(actionrequest.getParameter("year_end"));

		
		IGAService gaService = new GAnalyticsService();
		gaService.setUA("UA-376062-58");

		int valor =  gaService
				.numOfVisitsBetweenTwoDates(dayStart, monthStart, yearStart,
						dayEnd, monthEnd, yearEnd);
		Map<String,String> countries =  gaService.getVisitsByCountry(dayStart, monthStart, yearStart,
						dayEnd, monthEnd, yearEnd);
		
		Map<String,String> ssoo =  gaService.getVisitsBySSOO(dayStart, monthStart, yearStart,
				dayEnd, monthEnd, yearEnd);
		
		actionrequest.setAttribute("numVisitasIntervalo",valor);
		actionrequest.setAttribute("mapCountry",countries);
		actionrequest.setAttribute("mapSO",ssoo);
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
