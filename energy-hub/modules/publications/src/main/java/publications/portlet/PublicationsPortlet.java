package publications.portlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
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
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import publications.constants.PublicationsPortletKeys;
import publications.helper.db_helper;
import publications.helper.helper;

/**
 * @author vyo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Publications",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.name=" + PublicationsPortletKeys.PUBLICATIONS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class PublicationsPortlet extends MVCPortlet {
	private final  Configuration _portletConfiguration = ConfigurationFactoryUtil.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");

	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY); 
			 
			HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
			
			String publicationId = httpReq.getParameter("articleId");
			int pageNo = Integer.valueOf(helper.ifEmpty(httpReq.getParameter("pageNo"), "0"));
			int pageSize = Integer.valueOf(helper.ifEmpty(httpReq.getParameter("pageSize"), "9"));
			JSONObject filter = new JSONObject(helper.ifEmpty(httpReq.getParameter("filter"), "{}"));
			
			com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
					.filter(st -> st.getName("en_US").equalsIgnoreCase("publications")).findFirst().orElse(null);	
			
			AssetCategory assetCategory = null;
			if (httpReq.getParameterMap().containsKey("publications") || httpReq.getParameterMap().containsKey("publication")) {
				assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase("Publications")).findFirst().orElse(null);
			}else {
				assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase("Publications Header")).findFirst().orElse(null);
			}
			
			List<JournalArticle> publications = new ArrayList<JournalArticle>();
			List<JournalArticle> similarTopics = new ArrayList<JournalArticle>();
			JSONArray jaRes = new JSONArray();
			AssetEntryQuery assetEntryQuery = new AssetEntryQuery(); 
			assetEntryQuery.setAnyCategoryIds(new long[] { assetCategory.getCategoryId() });  
			assetEntryQuery.setOrderByCol1("createDate");
			
			if(!httpReq.getParameterMap().containsKey("publication")) {
				List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
				for (AssetEntry ae : assetEntryList) {
					JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
					JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());
					publications.add(journalArticle);
				} 
				//FILTER
				if(filter.has("source") && filter.getJSONArray("source").length()>0 ) {//RenewableEnergySources
					publications = getFilteredArticles(themeDisplay, publications, filter.getJSONArray("source"), "SelectFromList73350436");
				}
				if(filter.has("efficiency") && filter.getJSONArray("efficiency").length()>0 ) {//EnergyEfficiency
					publications = getFilteredArticles(themeDisplay, publications, filter.getJSONArray("efficiency"), "SelectFromList66003516");
				}
				if(filter.has("date") && filter.getJSONArray("date").length()>0 ) {
					publications = getFilteredArticlesByCreatedDate(publications, filter.getJSONArray("date"), themeDisplay);
				}
				
				//PAGINATION
				publications = getPageLimit(publications, pageNo, pageSize);
				
			}else {
				JournalArticle publication = JournalArticleLocalServiceUtil.fetchLatestArticle(Long.valueOf(publicationId));
				if(publication != null)
					publications.add(publication);
				
				String content = publication.getContentByLocale(themeDisplay.getLanguageId());;
			    Document document = SAXReaderUtil.read(content);
				
			    String source = document.selectSingleNode("/root/dynamic-element[@name='SelectFromList73350436']").hasContent()?
		        		document.selectSingleNode("/root/dynamic-element[@name='SelectFromList73350436']/dynamic-content").getText() : "";
	        		
				assetEntryQuery.setStart(0);
				assetEntryQuery.setEnd(5);
				List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
				for (AssetEntry ae : assetEntryList) {
					JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
					JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());
					similarTopics.add(journalArticle);
				}
				
				if(filter.has("source") && filter.getJSONArray("source").length()>0 ) {//RenewableEnergySources
					similarTopics = getFilteredArticles(themeDisplay, similarTopics, new JSONArray().put(source), "SelectFromList73350436");
				}
				if(filter.has("efficiency") && filter.getJSONArray("efficiency").length()>0 ) {//EnergyEfficiency
					similarTopics = getFilteredArticles(themeDisplay, similarTopics, filter.getJSONArray("efficiency"), "SelectFromList66003516");
				}
 
				JSONArray similarTopicsJA = getPublicationData(similarTopics, themeDisplay, renderRequest);
				renderRequest.setAttribute("similarTopics", helper.toList(similarTopicsJA)); 
			}  
			
			jaRes = getPublicationData(publications, themeDisplay, renderRequest);
			renderRequest.setAttribute("publications", helper.toList(jaRes));
			
			if (httpReq.getParameterMap().containsKey("publications")) {
				getFilterData(themeDisplay, renderRequest, struct);
				int totalPages=  jaRes.isEmpty() ? 0 : (int) Math.floor(jaRes.length()/pageSize);
				
				renderRequest.setAttribute("totalPages", totalPages);
				renderRequest.setAttribute("pageNo", pageNo > totalPages ? 0 : pageNo);
				renderRequest.setAttribute("pageSize", pageSize);
				
				include("/publications.jsp", renderRequest, renderResponse);
				super.render(renderRequest, renderResponse); 
			}else if (httpReq.getParameterMap().containsKey("publication")) {
				include("/publication.jsp", renderRequest, renderResponse);
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
	
	private JSONArray getPublicationData(List<JournalArticle> publications, ThemeDisplay themeDisplay, RenderRequest renderRequest) {
		JSONArray jaRes = new JSONArray();
		
		try {
			for (JournalArticle pub : publications) { 
			    String title = helper.getDFromContentXmlString (pub, "Text54116795", themeDisplay);
			    String subTitle = helper.getDFromContentXmlString (pub, "Text88419662", themeDisplay);
			    String description = helper.getDFromContentXmlString (pub, "RichText33657893", themeDisplay);
			    String source = helper.getDFromContentXmlString (pub, "SelectFromList73350436", themeDisplay);
			    String efficiency = helper.getDFromContentXmlString (pub, "SelectFromList66003516", themeDisplay);
			    String featured = helper.getDFromContentXmlString (pub, "SingleSelection10656072", themeDisplay);
			    String author = helper.getDFromContentXmlString (pub, "Text96155733", themeDisplay);
			    String publicationDate = helper.getDFromContentXmlString (pub, "Date36394968", themeDisplay);
			    String url = helper.getDFromContentXmlString (pub, "Text80788693", themeDisplay); 
			    
			    
			    String content = pub.getContentByLocale(themeDisplay.getLanguageId());
			    Document document = SAXReaderUtil.read(content);
	    		JSONObject coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='Image98198242']").hasContent()?
	        		document.selectSingleNode("/root/dynamic-element[@name='Image98198242']/dynamic-content").getText() : "{}");
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
		        List<String> docEntryIds = new ArrayList<>();
		        JSONArray docArr = new JSONArray();
		        
		        JSONArray dataArr = helper.getDFromContentXml(pub, themeDisplay.getLocale(), "Upload73581333");
		        for(int i=0 ;i < dataArr.length() ; i++) {
					String data = dataArr.getString(i);
					JSONObject attachmentJO = new JSONObject(data);
					if(attachmentJO.has("fileEntryId")) {
				        DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(attachmentJO.getLong("fileEntryId"));
				       
				        JSONObject jo = new JSONObject();
				        jo.put("docTitle", image.getTitle());
				        jo.put("docName", image.getDescription());
				        jo.put("docExt", FileUtil.getExtension(image.getFileName()));
				        jo.put("docFileEntryId", image.getFileEntryId()); 
				        jo.put("docUrl",  PortalUtil.getPortalURL(renderRequest) + "/documents/" + image.getGroupId() + "/" +
			                    image.getFolderId() + "/" + image.getTitle() + "/" + image.getUuid() + "?t=" +
			                    System.currentTimeMillis()); 
				        docEntryIds.add(String.valueOf(image.getFileEntryId()));
						docArr.put(jo); 
					}
		        }
		        	
		        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
		        Pattern pattern = Pattern.compile(regex);  
		        Matcher matcher = pattern.matcher(author);  
		        String userImageURL = "";
		        if(matcher.matches()) {
		        	String token = db_helper.getToken(_portletConfiguration);
		            String userString = db_helper.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), author); 
		            if(!helper.isEmpty(userString)) {
		            	JSONObject userJson = new JSONObject(userString); 
		            	if(userJson.has("firstName")) {
		            		author = userJson.getString("firstName") + " " +userJson.getString("lastName");
		            		String userImageBase64 = userJson.getJSONObject("logo").getString("data");
		            		String extension = userJson.getJSONObject("logo").getString("extension");
		            		byte[] decodedString = Base64.getDecoder().decode(userImageBase64.getBytes("UTF-8"));
		            		
		            		userImageURL = "data:image/"+ extension +";base64," + Base64.getEncoder().encodeToString(decodedString); 
		            	}
		            }
		        }
		        
	    		JSONObject res = new JSONObject();
				res.put("id", pub.getId());
				res.put("resourcePrimKey", pub.getResourcePrimKey());
				res.put("title", title);
				res.put("subTitle", subTitle); 
				res.put("description", description);
				res.put("author", author);
				res.put("userImageURL", userImageURL);
				res.put("url", url);
				res.put("imageURL", imageUrl);
				if( !helper.isEmpty(publicationDate)) {
					Date formatedDate = new SimpleDateFormat("yyyy-MM-dd").parse(publicationDate);
					String formatedDateString = new SimpleDateFormat("MMM dd, yyyy").format(formatedDate);
					res.put("createDate", formatedDateString);
					String modifiedDate = new SimpleDateFormat("MMM dd, yyyy").format(pub.getModifiedDate());
					res.put("modifiedDate", modifiedDate);
				}
				res.put("source", source);
				res.put("efficiency", efficiency);
				res.put("featured", featured); 
				res.put("attachments", docArr); 
				res.put("detailURL", "/resources-detail?publication&articleId="+ pub.getResourcePrimKey());
				
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
				if (joa.get("name").equals("SelectFromList73350436") || joa.get("name").equals("SelectFromList66003516")) {
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
					if(joa.get("name").equals("SelectFromList73350436"))
						renderRequest.setAttribute("RenewableEnergySources", helper.toList(jaRes));
					else if (joa.get("name").equals("SelectFromList66003516"))
						renderRequest.setAttribute("EnergyEfficiency", helper.toList(jaRes));
				} 	
			}
		}
	}
	
	private List<JournalArticle> getFilteredArticles(ThemeDisplay themeDisplay, List<JournalArticle> fetchedArticles, JSONArray filter, String fieldName) throws DocumentException {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		
		for (JournalArticle article : fetchedArticles) {
			String content = article.getContentByLocale(themeDisplay.getLanguageId());;
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
	
	private List<JournalArticle> getFilteredArticlesByCreatedDate(List<JournalArticle> filtered, JSONArray filter, ThemeDisplay themeDisplay) throws DocumentException, ParseException {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		for(JournalArticle article : filtered) {
			String publicationDateString = helper.getDFromContentXmlString (article, "Date36394968", themeDisplay);
			Date publicationDate = new SimpleDateFormat("yyyy-MM-dd").parse(publicationDateString);
			LocalDate cdate = publicationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			for(int j=0; j< filter.length();j++) {
				String date = filter.getString(j);
				boolean addArticle = false;
				if(date.equalsIgnoreCase("week")) {
					LocalDate start = LocalDate.now().with(DayOfWeek.MONDAY);
				    if( cdate.isBefore(start) && cdate.isAfter(start.minusDays(7)) ) {
				    	addArticle = true;
					}
				} else if(date.equalsIgnoreCase("more")) {
				    if( cdate.isBefore(LocalDate.now().minusDays(7))) {
				    	addArticle = true;
					}
				} if(date.equalsIgnoreCase("recent")) {
				    if( cdate.isAfter(LocalDate.now().minusDays(7)) ) {
				    	addArticle = true;
					}
				} else if(date.equalsIgnoreCase("month")) {
					if( cdate.isBefore(LocalDate.now().minusDays(30)) ) {
				    	addArticle = true;
					}
				}
				
				if( addArticle ) {
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
}