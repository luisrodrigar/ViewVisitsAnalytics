package main.java.es.uniovi.innova;

import java.io.IOException;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import main.java.es.uniovi.innova.services.ga.IPortalesService;
import main.java.es.uniovi.innova.services.ga.implementation.portales.APILiferayPortalesDAO;

public class VisitsPortlet extends GenericPortlet {
	
	@Override
	public void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		IPortalesService portalService = new APILiferayPortalesDAO();
		request.setAttribute("mapPortal", portalService.getPortales());
		
		include("/html/view.jsp", request, response);
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
