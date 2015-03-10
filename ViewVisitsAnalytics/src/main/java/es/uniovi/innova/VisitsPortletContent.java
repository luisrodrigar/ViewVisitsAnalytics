package main.java.es.uniovi.innova;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

public class VisitsPortletContent extends VisitsPortlet{
	
	Map<String, String> mapPortal = new HashMap<String, String>();
	
	@Override
	public void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		request.setAttribute("mapPortal", portalService.getPortales());
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		mapPortal = portalService.getPortalContent(themeDisplay);
		request.setAttribute("mapPortal", mapPortal);
		include("/html/content_view.jsp", request, response);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void processAction(ActionRequest request, ActionResponse response)
			throws PortletException, IOException {
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
		
		String portalID = mapPortal.get("idGoogleAnalytics");
		
		request.setAttribute("name", mapPortal.get("name"));
		request.setAttribute("id", portalID);
		
		request.setAttribute("fInicio",fInicio.getDate()+"/"+((Integer)fInicio.getMonth()+1)+"/"+((Integer)fInicio.getYear()+1900));
		request.setAttribute("fFin",fFin.getDate()+"/"+((Integer)fFin.getMonth()+1)+"/"+((Integer)fFin.getYear()+1900));
		
		if(equalsDates(fInicio, new Date()) || equalsDates(fFin, new Date()))
			executeGetData(request, gaServiceTemp, fInicio, fFin, portalID);
		else
			executeGetData(request, gaServicePermanent, fInicio, fFin, portalID);
	}
		
	
}
