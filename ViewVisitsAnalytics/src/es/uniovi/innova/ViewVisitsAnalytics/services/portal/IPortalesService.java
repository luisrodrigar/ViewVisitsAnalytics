package es.uniovi.innova.ViewVisitsAnalytics.services.portal;

import java.util.Map;

import com.liferay.portal.theme.ThemeDisplay;

public interface IPortalesService {
	Map<String, String> getPortales();
	
	Map<String, String> getPortalContent(ThemeDisplay themeDisplay);
}
