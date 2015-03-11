package es.uniovi.innova.ViewVisitsAnalytics;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.uniovi.innova.ViewVisitsAnalytics.factory.Factory;
import es.uniovi.innova.ViewVisitsAnalytics.services.ga.IGAService;
import es.uniovi.innova.ViewVisitsAnalytics.services.portal.IPortalesService;

public class VisitsPortlet extends GenericPortlet {
	
	protected IGAService gaServiceTemp, gaServicePermanent;
	protected IPortalesService portalService;
	protected BeanFactory factory;
	protected Factory factoryService;
	
	@Override
	public void init(){
		factory = new ClassPathXmlApplicationContext("beans.xml");
	    factoryService = (Factory) factory.getBean("factory");
	    gaServiceTemp = factoryService.getServiceGoggleAnalyticsTemp();
	    gaServicePermanent = factoryService.getServiceGoggleAnalyticsPermanent();
		portalService = factoryService.getServicePortales();
	}
	
	@Override
	public void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		request.setAttribute("mapPortal", portalService.getPortales());
		include("/html/view.jsp", request, response);
	}
	
	@SuppressWarnings("deprecation")
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
		
		Map<String, String> mapPortales = portalService.getPortales();
		
		for(String portal : mapPortales.keySet())
			if(mapPortales.get(portal).equals(portalID))
				request.setAttribute("name", portal);
		
		request.setAttribute("id", portalID);
		
		request.setAttribute("fInicio",fInicio.getDate()+"/"+((Integer)fInicio.getMonth()+1)+"/"+((Integer)fInicio.getYear()+1900));
		request.setAttribute("fFin",fFin.getDate()+"/"+((Integer)fFin.getMonth()+1)+"/"+((Integer)fFin.getYear()+1900));
		
		if(equalsDates(fInicio, new Date()) || equalsDates(fFin, new Date()))
			executeGetData(request, gaServiceTemp, fInicio, fFin, portalID);
		else
			executeGetData(request, gaServicePermanent, fInicio, fFin, portalID);
	
	}
	
	protected void executeGetData(ActionRequest request, IGAService service, Date fInicio, Date fFin, String portalID){
		// Data about visits between the input dates
		request.setAttribute("visits",service.getVisitsByInterval(fInicio, fFin, portalID));
		// Data about visits per page
		request.setAttribute("visitsPage",service.getVisitsByPage(fInicio, fFin, portalID));
		// Data about visits by operative system
		request.setAttribute("visitsOS", service.getVisitsByOS(fInicio, fFin, portalID));
		// Data about visits by web browser
		request.setAttribute("visitsBrowser", service.getVisitsByBrowser(fInicio, fFin, portalID));
	
	}
	
	@SuppressWarnings("deprecation")
	protected boolean equalsDates(Date date1, Date date2){
		if(date1.getDate()==date2.getDate() && date1.getMonth()==date2.getMonth() && date1.getYear()==date2.getYear())
			return true;
		return false;
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
