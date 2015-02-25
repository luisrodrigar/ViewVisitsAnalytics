package main.java.es.uniovi.innova;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;

public class VisitsPortlet extends GenericPortlet {
	
	@Override
	public void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		Map<String, String> mapPortal = new HashMap<String, String>();
		List<Group> listaGrupos;
		try {
			listaGrupos = GroupLocalServiceUtil.getGroups(0,GroupLocalServiceUtil.getGroupsCount());
			for (Group group: listaGrupos) {
				String name = group.getName();
				String idGoogleAnalytics = group.getTypeSettingsProperties().getProperty("googleAnalyticsId");
				System.out.println(name + "\t" + idGoogleAnalytics);
				mapPortal.put(name, idGoogleAnalytics);
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}
		request.setAttribute("mapPortal", mapPortal);
		
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
