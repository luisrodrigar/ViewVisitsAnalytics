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
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import main.java.es.uniovi.innova.services.ga.IGAService;
import main.java.es.uniovi.innova.services.portal.IPortalesService;
import main.java.es.uniovi.innova.factory.Factory;

public class VisitsPortlet extends GenericPortlet {
	
	private IGAService gaService;
	private IPortalesService portalService;
	private BeanFactory factory;
	private Factory factoryService;
	
	@Override
	public void init(){
		factory = new ClassPathXmlApplicationContext("beans.xml");
	    factoryService = (Factory) factory.getBean("factory");
	    gaService = factoryService.getServiceGoggleAnalytics();
		portalService = factoryService.getServicePortales();
	}
	
	@Override
	public void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		request.setAttribute("mapPortal", portalService.getPortales());
		include("/html/view.jsp", request, response);
	}
	
	@Override
	public void processAction(ActionRequest request, ActionResponse response)
			throws PortletException, IOException {
		String portalID = (String) request.getParameter("portal");
		String inicio = (String) request.getParameter("fecha_inicio");
		String fin = (String) request.getParameter("fecha_fin");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date fInicio = null, fFin = null;
		try {
			fInicio = format.parse(inicio);
			fFin = format.parse(fin);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		gaService.setUA(portalID);
		request.setAttribute("id", portalID);
		request.setAttribute("fInicio",fInicio);
		request.setAttribute("fFin",fFin);
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
