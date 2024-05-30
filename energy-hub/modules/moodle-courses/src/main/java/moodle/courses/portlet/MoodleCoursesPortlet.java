package moodle.courses.portlet;

import moodle.courses.constants.MoodleCoursesPortletKeys;
import moodle.courses.helper.MoodleCoursesUtil;
import moodle.courses.helper.helper;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import java.io.IOException;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

/**
 * @author vyo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=MoodleCourses",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.name=" + MoodleCoursesPortletKeys.MOODLECOURSES,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class MoodleCoursesPortlet extends MVCPortlet {
	private final static Configuration _portletConfiguration = ConfigurationFactoryUtil
			.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");
	
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));

		int pageNo = Integer.valueOf(helper.ifEmpty(httpReq.getParameter("pageNo"), "0")) +1;
		int pageSize = Integer.valueOf(helper.ifEmpty(httpReq.getParameter("pageSize"), "4"));
		JSONObject filter = new JSONObject(helper.ifEmpty(httpReq.getParameter("filter"), "{}"));
		String filterCateg = "";
		if(filter.has("category") && filter.getJSONArray("category").length()>0 ) {
			for(int i=0; i< filter.getJSONArray("category").length();i++) {
				String val = filter.getJSONArray("category").getString(i);
				 filterCateg += val +",";
			}
		} 
		filterCateg = helper.isEmpty(filterCateg) ? "" : filterCateg.substring(0, filterCateg.length() - 1);
		try {
			AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
					.filter(categ -> categ.getName().equalsIgnoreCase("Courses")).findFirst().orElse(null);
			renderRequest.setAttribute("categName", assetCategory.getName());
			
			String categories = _portletConfiguration.get("moodle-url") + _portletConfiguration.get("get-all-categories");
			String categs = MoodleCoursesUtil.callRestGet(categories, "");
			if(!helper.isEmpty(categs))
				renderRequest.setAttribute("categories", helper.toList(new JSONArray(categs)));
			
			if (httpReq.getParameterMap().containsKey("seeAllCourses")) {
				pageSize = Integer.valueOf(helper.ifEmpty(httpReq.getParameter("pageSize"), "10"));
				String url = "";
				if(helper.isEmpty(filterCateg))
					url = _portletConfiguration.get("moodle-url") + _portletConfiguration.get("get-paged-courses")
								+ "?pageNo="+ pageNo +"&pageSize=" +pageSize;
				else
					url = _portletConfiguration.get("moodle-url") + _portletConfiguration.get("get-paged-courses-categ")
							+ "?pageNo="+ pageNo +"&pageSize=" +pageSize + "&catID=" + filterCateg;
				
				String coursesString = MoodleCoursesUtil.callRestGet(url, "");
				if(!helper.isEmpty(coursesString)) {
					JSONArray courses = new JSONArray(coursesString); 
					int coursesSize = courses.getJSONObject(courses.length()-1).getInt("size");
					courses.remove(courses.length()-1);
					renderRequest.setAttribute("courses", helper.toList(courses));
					
					pageNo = pageNo-1;
					int totalPages=  courses.isEmpty() ? 0 : (int) Math.nextUp(coursesSize/pageSize);
					renderRequest.setAttribute("totalPages", totalPages);
					renderRequest.setAttribute("pageNo", pageNo > totalPages ? 1 : pageNo);
					renderRequest.setAttribute("pageSize", pageSize);
				}
				include("/seeAllCourses.jsp", renderRequest, renderResponse);
				super.render(renderRequest, renderResponse);
			} else {
				include("/view.jsp", renderRequest, renderResponse);
				super.render(renderRequest, renderResponse);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}