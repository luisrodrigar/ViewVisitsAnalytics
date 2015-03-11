package es.uniovi.innova.ViewVisitsAnalytics.services.portal.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import es.uniovi.innova.ViewVisitsAnalytics.services.portal.IPortalesService;

public class APILiferayPortalesDAO implements IPortalesService {

	final static Logger log = LoggerFactory.getLogger(APILiferayPortalesDAO.class);
	
	@Override
	@Cacheable(value = "portalCache", key = "#root.methodName")
	public Map<String, String> getPortales() {
		log.info("Portal cache || getting all websites from the Liferay portal");
		Map<String, String> mapPortal = new HashMap<String, String>();
		List<Group> listaGrupos;
		try {
			listaGrupos = GroupLocalServiceUtil.getGroups(0,GroupLocalServiceUtil.getGroupsCount());
			for (Group group: listaGrupos) {
				String name = group.getDescriptiveName();
				String idGoogleAnalytics = group.getTypeSettingsProperties().getProperty("googleAnalyticsId");
				if(idGoogleAnalytics!=null)
					mapPortal.put(name, idGoogleAnalytics);
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}
		log.info("Portal have been stored successfully!");
		return mapPortal;
	}

	@Override
	public Map<String, String> getPortalContent(ThemeDisplay themeDisplay) {
		Map<String, String> mapPortal = new HashMap<String, String>();
		Group group = themeDisplay.getScopeGroup();
		mapPortal.put("name", group.getDescriptiveName());
		mapPortal.put("idGoogleAnalytics", group.getTypeSettingsProperties().getProperty("googleAnalyticsId"));
		return mapPortal;
	}

}
