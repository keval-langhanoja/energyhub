package news.portlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import news.constants.NewsPortletKeys;
import news.helper.UserTypes;
import news.helper.db_helper;
import news.helper.helper;
import news.helper.util;

/**
 * @author vyo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=News",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.name=" + NewsPortletKeys.NEWS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class NewsPortlet extends MVCPortlet {
	private final  Configuration _portletConfiguration = ConfigurationFactoryUtil.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");

	private String NewsCategoryName = "News", NewsStructureName = "news";
	
	private long NewsFolderId;
	
	private String NewsTitle = "Text54400606", NewsDescription = "RichText47916009", 
			NewsImage = "Image71759550", NewsType = "SelectFromList84062816";
	
	private String[] NewsNames = {"NewsTitle","NewsDescription", "NewsImage", "NewsType"};
	
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY); 
			 
			HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
			
			String ipadd =  PortalUtil.getHttpServletRequest(renderRequest).getRemoteAddr();
			if(!ipadd.equalsIgnoreCase("51.178.45.207")) { //Offline
				NewsFolderId = 40818;
			}else if(!ipadd.equalsIgnoreCase("51.178.45.174")) { //Online
				NewsFolderId = 42693;
			}else { //Local
				NewsFolderId = 87577;
			}
			
			String newsId = httpReq.getParameter("articleId");
			int pageNo = Integer.valueOf(helper.ifEmpty(httpReq.getParameter("pageNo"), "0"));
			int pageSize = Integer.valueOf(helper.ifEmpty(httpReq.getParameter("pageSize"), "9"));
			JSONObject filter = new JSONObject(helper.ifEmpty(httpReq.getParameter("filter"), "{}"));
			
			com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
					.filter(st -> st.getName("en_US").equalsIgnoreCase(NewsStructureName)).findFirst().orElse(null);	
			
			AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(NewsCategoryName)).findFirst().orElse(null);
			
			String token = util.getToken(_portletConfiguration);
			User user = themeDisplay.getUser();
			String userString = util.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), user.getEmailAddress());
			
			if(new JSONObject(userString).has("roleId")) {
				int userRole = new JSONObject(userString).getInt("userApplicationRoleId");
				if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.ResearcherUser.name())) 
						|| userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.InvestorUser.name()))
						|| userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.AcademicUser.name()))
						|| userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.PolicyMaker.name()))
						|| userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.Administrator.name()))){
					renderRequest.setAttribute("showAddNews", true);  
				}
			}
			
			if(themeDisplay.getUser().getRoles().contains(RoleLocalServiceUtil.getRole(37615))) {
				renderRequest.setAttribute("showAddNews", true);  
			}
			
			List<JournalArticle> newsList = new ArrayList<JournalArticle>();
			List<JournalArticle> similarTopics = new ArrayList<JournalArticle>();
			JSONArray jaRes = new JSONArray();
			AssetEntryQuery assetEntryQuery = new AssetEntryQuery(); 
			assetEntryQuery.setAllCategoryIds(new long[] { assetCategory.getCategoryId() });
			assetEntryQuery.setOrderByCol1("publishDate");
			
			if(httpReq.getParameterMap().containsKey("p_r_p_categoryId") || httpReq.getParameterMap().containsKey("allNews")) {
				if(httpReq.getParameterMap().containsKey("news")) {
					assetEntryQuery.setStart(0);
					assetEntryQuery.setEnd(6);
				}
				
				List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
				for (AssetEntry ae : assetEntryList) {
					JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
					JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());
					if(journalArticle.getDDMStructureKey().equals(struct.getStructureKey()))
						newsList.add(journalArticle);
				}
				
				//FILTER
				if(filter.has("type") && filter.getJSONArray("type").length()>0 ) {
					newsList = getFilteredArticles(themeDisplay, newsList, filter.getJSONArray("type"), NewsType);
				}
				
				//PAGINATION
				newsList = getPageLimit(newsList, pageNo, pageSize);
				
			}else if(!helper.isEmpty(newsId)){
				JournalArticle news = JournalArticleLocalServiceUtil.fetchLatestArticle(Long.valueOf(newsId));
				if(news != null)
					newsList.add(news);
				
				String content = news.getContentByLocale(themeDisplay.getLanguageId());
			    Document document = SAXReaderUtil.read(content);
				
			    String type = document.selectSingleNode("/root/dynamic-element[@name='"+ NewsType +"']").hasContent()?
		        		document.selectSingleNode("/root/dynamic-element[@name='"+ NewsType +"']/dynamic-content").getText() : "";
	        		
				assetEntryQuery.setStart(0);
				assetEntryQuery.setEnd(5);
				List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
				for (AssetEntry ae : assetEntryList) {
					JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
					JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());
					similarTopics.add(journalArticle);
				}
				
				//FILTER
				if(filter.has("type") && filter.getJSONArray("type").length()>0 ) {//RenewableEnergySources
					similarTopics = getFilteredArticles(themeDisplay,similarTopics, new JSONArray().put(type), NewsType);
				}
 
				JSONArray similarTopicsJA = getNewsData(similarTopics, themeDisplay, renderRequest);
				renderRequest.setAttribute("similarTopics", helper.toList(similarTopicsJA)); 
			}  
			
			jaRes = getNewsData(newsList, themeDisplay, renderRequest);
			renderRequest.setAttribute("newsList", helper.toList(jaRes));
			
			if (httpReq.getParameterMap().containsKey("allNews")) {
				getFilterData(themeDisplay, renderRequest, struct);
				int totalPages=  jaRes.isEmpty() ? 0 : (int) Math.floor(jaRes.length()/pageSize);
				
				renderRequest.setAttribute("totalPages", totalPages);
				renderRequest.setAttribute("pageNo", pageNo > totalPages ? 0 : pageNo);
				renderRequest.setAttribute("pageSize", pageSize);
				
				include("/allNews.jsp", renderRequest, renderResponse);
				super.render(renderRequest, renderResponse); 
			}else if (httpReq.getParameterMap().containsKey("news")) {
				include("/news.jsp", renderRequest, renderResponse);
				super.render(renderRequest, renderResponse); 
			}else {
				getFilterData(themeDisplay, renderRequest, struct);
				include("/view.jsp", renderRequest, renderResponse);
				super.render(renderRequest, renderResponse); 
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JSONArray getNewsData(List<JournalArticle> newsList, ThemeDisplay themeDisplay, RenderRequest renderRequest) {
		JSONArray jaRes = new JSONArray();
		
		try {
			for (JournalArticle news : newsList) { 
			    String title = helper.getDFromContentXmlString (news, NewsTitle, themeDisplay);
			    String description = helper.getDFromContentXmlString (news, NewsDescription, themeDisplay);
			    String type = helper.getDFromContentXmlString (news, NewsType, themeDisplay);
			    String publishDate = "";
			    if(news.getLastPublishDate() != null)
			    	publishDate = new SimpleDateFormat("MMM dd, yyyy").format(news.getLastPublishDate());
			    
			    String content = news.getContentByLocale(themeDisplay.getLanguageId());
			    Document document = SAXReaderUtil.read(content);
			    JSONObject coverImageJO = new JSONObject();
			    if(document.selectSingleNode("/root/dynamic-element[@name='"+ NewsImage +"']") != null) {
			    	coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='"+ NewsImage +"']").hasContent()?
			    			document.selectSingleNode("/root/dynamic-element[@name='"+ NewsImage +"']/dynamic-content").getText() : "{}");
			    }
		        String imageUrl = "";
		        if(coverImageJO.has("fileEntryId")) {
		        	DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(coverImageJO.getLong("fileEntryId"));
			           
			        if (image != null) {
			            imageUrl =
			                PortalUtil.getPortalURL(renderRequest) + "/documents/" + image.getGroupId() + "/" +
			                    image.getFolderId() + "/" + image.getTitle() + "/" + image.getUuid() + "?t=" +
			                    System.currentTimeMillis();
			        } 
		        } 
		        
		        long authorId = news.getUserId();
		        User author = UserLocalServiceUtil.getUser(authorId);
		        
		        JSONObject forumDetailsCount = db_helper.getCommentDetailsCount(String.valueOf(news.getResourcePrimKey()));
		        String commentcount = forumDetailsCount.has("commentcount") ? forumDetailsCount.getString("commentcount") : "0";
	    		JSONObject res = new JSONObject();
				res.put("id", news.getId());
				res.put("resourcePrimKey", news.getResourcePrimKey());
				res.put("author", author.getFullName());
				res.put("userImageURL", author.getPortraitURL(themeDisplay));
				res.put("title", title);
				res.put("description", description);
				res.put("imageURL", imageUrl);
				res.put("publishDate", publishDate);
				res.put("type", type);
				res.put("commentcount", commentcount);
				res.put("detailURL", "/news-detail?news&articleId="+ news.getResourcePrimKey());
				
				jaRes.put(res);  
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jaRes;
	}

	public void getFilterData(ThemeDisplay themeDisplay, RenderRequest renderRequest, 
			com.liferay.dynamic.data.mapping.model.DDMStructure struct) throws DocumentException, PortalException {
		  
		JSONObject jo = new JSONObject(struct.getDefinition());
		JSONArray ja = new JSONArray(jo.get("fields").toString());

		for (int i = 0; i < ja.length(); i++) {
			JSONObject joa = ja.getJSONObject(i);
			if (joa.get("type").equals("select")) {
				if (joa.get("name").equals(NewsType)) {
					JSONArray joOptions = new JSONArray(joa.get("options").toString());
					JSONArray jaRes = new JSONArray();
					for (int j = 0; j < joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j);
						JSONObject res = new JSONObject(); 
						String val = new JSONObject(option.get("label").toString()).get("en_US").toString();
						if(!val.equalsIgnoreCase("none")) {
							res.put("id", option.getString("value"));
							res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
							jaRes.put(res);
						}
					}
					if(joa.get("name").equals(NewsType))
						renderRequest.setAttribute("NewsType", helper.toList(jaRes));
				} 	
			}
		}
	}
	
	private List<JournalArticle> getFilteredArticles(ThemeDisplay themeDisplay, List<JournalArticle> fetchedArticles, JSONArray filter, String fieldName) throws DocumentException {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		
		for (JournalArticle article : fetchedArticles) {
			String content = article.getContentByLocale(themeDisplay.getLanguageId());
			Document document = SAXReaderUtil.read(content);
			String fieldValue = document.selectSingleNode("/root/dynamic-element[@name='"+ fieldName +"']").hasContent() ? 
					document.selectSingleNode("/root/dynamic-element[@name='"+ fieldName +"']/dynamic-content").getText() : "";
			
			for(int i=0; i< filter.length();i++) {
				String val = filter.getString(i);
				if( fieldValue.equalsIgnoreCase(val) ) {
					returnedArticles.add(article);
					break;
				}
			}
		}
		return returnedArticles;
	}
	
	public List<JournalArticle> getPageLimit(List<JournalArticle> articles, int pageNum, int pageSize) {
		if (CollectionUtils.isEmpty(articles))
			return articles;
		
		int start = pageNum*pageSize, end = (pageNum+1)*pageSize;
		if( start>articles.size() ) {
			start = 0;
			end = pageSize;
		}
		if( end>articles.size() ) {
			end = articles.size();
		}
		return articles.subList(start, end);
	}

	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) 
				throws IOException, PortletException {
			resourceResponse.setContentType("text/html");
			PrintWriter out = resourceResponse.getWriter();
			String retVal = "";
			try {
				ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
				JSONObject content = new JSONObject();
				
				for(String dataName : NewsNames) {
					 String value = ParamUtil.getString(resourceRequest, dataName);
					 String name = "NewsImageName";
					 String path = "NewsImagePath";
					 if(dataName.equalsIgnoreCase("NewsImage")) {
						 try {
							 UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest( resourceRequest );
							 String filename = "";
							 String pathStr = uploadRequest.getSession().getServletContext().getRealPath("/"); 
						 	 String dirPath = pathStr+"uploadedfiles";
							 String filePath = "";
							 String NewsImage = ParamUtil.getString(resourceRequest, name); 
							 
							 String coverImagebase64String = ParamUtil.getString(resourceRequest, dataName); 
							 String extension = FileUtil.getExtension(NewsImage); 
							 extension = extension.equalsIgnoreCase("svg") ? "svg+xml" : extension;
							 String base64 = coverImagebase64String.split("data:image/"+extension+";base64,")[1];
							 byte[] bytes = Base64.getDecoder().decode(base64);
							 
							 filename = UUID.randomUUID().toString()+"."+extension;
							 filePath = dirPath+ "/" + filename;
							 Path fpath = Paths.get(filePath);
							 Files.write(fpath, bytes);
						        
//							 if(uploadRequest.getFile(dataName, true) != null) {
//								 
//								File objFile = uploadRequest.getFile(dataName, true);
//								filename = UUID.randomUUID().toString()+"."+FileUtil.getExtension(objFile.getName());
//								filePath = dirPath+"\\"+filename;
//								
//								if( !new File(dirPath).exists() ) {
//									FileUtil.mkdirs(dirPath);
//								}
//								
//								OutputStream os = new FileOutputStream(new File(filePath));
//								Files.copy(objFile.toPath(), os);
//							 }  
							 content.put(name, NewsImage); 
							 content.put(path, filePath); 
								
						 } catch (Exception e) {
						 	e.printStackTrace();
						 }
					 }   else content.put(dataName, value);
				 }
				
				 addJournalArticle(themeDisplay, content, resourceRequest);
				
				out.println(String.valueOf(retVal));
				out.flush();
				super.serveResource(resourceRequest, resourceResponse);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public boolean addJournalArticle(ThemeDisplay themeDisplay, JSONObject contentEn, ResourceRequest request) throws Exception {
			boolean res = false;
			long userId = themeDisplay.getUserId();
			long groupId = themeDisplay.getLayout().getGroupId();
			ServiceContext serviceContext = new ServiceContext();
			
			com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
					.filter(st -> st.getName("en_US").equalsIgnoreCase(NewsStructureName)).findFirst().orElse(null);	
			
			AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(NewsCategoryName)).findFirst().orElse(null);

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
			serviceContext.setScopeGroupId(groupId);
			serviceContext.setAssetCategoryIds(new long[] { assetCategory.getCategoryId() });
			Map<Locale, String> titleMap = new HashMap<Locale, String>();
			Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
			
			JournalArticle article = null;
			try {
				
				serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);
				String xmlContent = getNewsFormXml(contentEn, themeDisplay, request);
				titleMap.put(Locale.US, contentEn.getString("NewsTitle"));
				descriptionMap.put(Locale.US, contentEn.getString("NewsTitle"));				
				 
				article = JournalArticleLocalServiceUtil.addArticle(userId, groupId, NewsFolderId, titleMap, descriptionMap,
						xmlContent, struct.getStructureKey(), "", serviceContext);
				
				if (article != null) res = true;
			} catch (Exception ex) {
				ex.printStackTrace();
				res = false;
			}
			return res;
		}

		private String getNewsFormXml(JSONObject contentEn, ThemeDisplay themeDisplay, ResourceRequest request) {
			String xmlContent = "<?xml version=\"1.0\"?>\r\n" + "\r\n"
					+ "<root available-locales=\"en_US\" default-locale=\"en_US\" version=\"1.0\">\r\n";
			
			if (contentEn.has("NewsTitle")) {
				xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\"" + NewsTitle+ "\" type=\"text\">\r\n"
						+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("NewsTitle")
						+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
			}
			if (contentEn.has("NewsDescription")) {
				xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+NewsDescription+"\" type=\"text\">\r\n"
						+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("NewsDescription")
						+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
			}
			if (contentEn.has("NewsType")) {
				xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+NewsType+"\" type=\"text\">\r\n"
						+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("NewsType")
						+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
			}
			
			if (contentEn.has("NewsImagePath") && !helper.isEmpty(contentEn.getString("NewsImagePath"))) {
				File f = new File(contentEn.getString("NewsImagePath"));
				JSONObject InnovationProgramImageJO = helper.fileUploadByDL(f, themeDisplay, request, NewsCategoryName,
						contentEn.getString("NewsImageName"));
				xmlContent += "	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"" + NewsImage + "\" type=\"image\">\r\n"
						+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + InnovationProgramImageJO.toString()
						+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
			} 
			xmlContent += "</root>";
			return xmlContent;
		}
}