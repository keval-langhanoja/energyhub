package create.project.portlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil; 
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.DocumentException;

import create.project.constants.CreateProjectPortletKeys;
import create.project.helper.helper;

/**
 * @author vyo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=CreateProject",
		"javax.portlet.init-param.template-path=/", 
		"javax.portlet.name=" + CreateProjectPortletKeys.CREATEPROJECT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CreateProjectPortlet extends MVCPortlet {
	
	private  long[] categId;
	private  long folderId;
	private  long articleId;
	private  long ddmStructureKey;
	private  String ddmTemplateKey = "";
	private  JSONObject jsonData = new JSONObject();
	private  String[] jsonDataNames = {"ProjectTitle","ProjectStage", "ProjectCategory", "tagProgramValues","tagInvitedValues",
										"WebsiteURL", "SolutionOfferingDescription", "coverImage", "docs", "addComments"}; 
	private  String[] jsonDataNamesLiferay = {"Text73021449","SelectFromList36742361", "SelectFromList03925321", "tagProgramValues","Text58386300",//tagusers
			"Text85717445", "RichText70541782", "Image13718074", "Upload33288847", "SingleSelection04730283"}; 
	private  List<Object> projectCategList = new ArrayList<Object>();
	private  List<Object> projectStageList = new ArrayList<Object>();
	private  List<Object> allTagsObj  = new ArrayList<Object>();
	private  List<Object> allInviteesObj  = new ArrayList<Object>();
//	private  File objFile = null; 
	private  Boolean edited; 
    
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		try {
 				HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest)); 
 				ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
 				
 				if (httpReq.getParameterMap().containsKey("articleId")) { //edit
 					edited = true;
 					renderRequest.setAttribute("edit", edited);	
					categId = new long[] {Long.valueOf(httpReq.getParameter("p_r_p_categoryId"))};
					folderId = Long.valueOf(httpReq.getParameter("folderId"));
					ddmStructureKey = Long.valueOf(httpReq.getParameter("ddmStructureKey"));
					ddmTemplateKey = httpReq.getParameter("ddmTemplateKey");
					articleId = Long.valueOf(httpReq.getParameter("articleId"));
 					
					JournalArticle article = JournalArticleServiceUtil.getLatestArticle(articleId); 
 					JSONArray docArr = new JSONArray();
 					List<String> docEntryIds = new ArrayList<>();
 					for(String name : jsonDataNamesLiferay) {
 						 if(name.equalsIgnoreCase("tagProgramValues")) {
 							List<AssetTag> allTags = AssetTagLocalServiceUtil.getTags();
 							renderRequest.setAttribute("allTags", allTags);
 							
 							AssetEntry entry = AssetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(),  article.getResourcePrimKey());
 	 						List<AssetTag> tags = AssetTagLocalServiceUtil.getAssetEntryAssetTags(entry.getEntryId());
 							renderRequest.setAttribute("selectedTags", tags);	
 							continue;
						}
 						
 						JSONArray dataArr = helper.getDFromContentXml(article, themeDisplay.getLocale(), name);
 						for(int i=0 ;i < dataArr.length() ; i++) {
 							String data = dataArr.getString(i);
 	 						if(name.equalsIgnoreCase("Text73021449")) renderRequest.setAttribute("ProjectTitle", data);	
 	 						else if(name.equalsIgnoreCase("SelectFromList36742361")) renderRequest.setAttribute("ProjectStage", data);	
 	 						else if(name.equalsIgnoreCase("SelectFromList03925321")) renderRequest.setAttribute("ProjectCategory", data);	 
 	 						else if(name.equalsIgnoreCase("SingleSelection04730283")) renderRequest.setAttribute("addComments", data);	 
 	 						else if(name.equalsIgnoreCase("Text85717445")) renderRequest.setAttribute("WebsiteURL", data);
 	 						else if(name.equalsIgnoreCase("RichText70541782")) renderRequest.setAttribute("SolutionOfferingDescription", data.replace("<p>", "").replace("</p>", ""));	
 	 						else if(name.equalsIgnoreCase("Image13718074")) {
 	 							JSONObject coverImageJO = new JSONObject(data);
 	 					        DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(coverImageJO.getLong("fileEntryId"));
 	 					       
 	 							renderRequest.setAttribute("coverImageName", image.getDescription());	
 	 							renderRequest.setAttribute("FileEntryId", image.getFileEntryId());	
 	 							
 	 						}
 	 						else if(name.equalsIgnoreCase("Upload33288847")) {
 	 							JSONObject coverImageJO = new JSONObject(data);
 	 					        DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(coverImageJO.getLong("fileEntryId"));
 	 					       
 	 					        JSONObject jo = new JSONObject();
 	 					        jo.put("docTitle", image.getTitle());
 	 					        jo.put("docName", image.getDescription());
 	 					        jo.put("docExt", FileUtil.getExtension(image.getFileName()));
 	 					        jo.put("docFileEntryId", image.getFileEntryId()); 
 	 					        docEntryIds.add(String.valueOf(image.getFileEntryId()));
 	 					        docArr.put(jo);
 	 						}
 	 						else if(name.equalsIgnoreCase("Text58386300")) {
 	 							
 	 							 List<User> users =  UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
 	 							  List<User> approvedUsers = new ArrayList<User>();

 	 							for(User user : users) {
 	 								user.setComments(user.getPortraitURL(themeDisplay));
 	 								approvedUsers.add(user);
 	 							}
 	 							
 	 							renderRequest.setAttribute("allUsers", approvedUsers);
 	 							
 	 							JSONArray jaRes =  new JSONArray();
 	 							for(String emailAddress : data.split(",")) { 
 	 								if( !helper.isEmpty(emailAddress) ) {
	 	 								User user = UserLocalServiceUtil.fetchUserByEmailAddress(themeDisplay.getCompanyId(), emailAddress); 
	 	 								JSONObject jo = new JSONObject();
	 	 	 						    jo.put("id", user != null ? user.getEmailAddress() : emailAddress);
	 	 	 						    jo.put("name", user !=null ? user.getFullName() : emailAddress);
	 	 	 						    jaRes.put(jo);
 	 								}
 	 							} 
 	 							allInviteesObj = helper.toList(jaRes);
 	 							renderRequest.setAttribute("selectedUsers", allInviteesObj);	
 	 						}
 						}
 					}
 					
 					renderRequest.setAttribute("docEntryIds", String.join(",", docEntryIds));	
 					renderRequest.setAttribute("docArr", helper.toList(docArr));	
 					getDropdownsData(themeDisplay, renderRequest);
 					include("/view.jsp", renderRequest, renderResponse);
			} else if (httpReq.getParameterMap().containsKey("successMessage")) {
 				String myProjectUrl = "/my-projects?p_r_p_categoryId="+ categId[0] +"&folderId="+ folderId 
 						+"&ddmStructureKey=" + ddmStructureKey + "&ddmTemplateKey=" + ddmTemplateKey;
 				
  				renderRequest.setAttribute("categId", categId[0]);
 				renderRequest.setAttribute("myProjectUrl", myProjectUrl);
 				include("/successMessage.jsp", renderRequest, renderResponse);
 			} else if (httpReq.getParameterMap().containsKey("Overview")) {
 				renderRequest.setAttribute("edit", edited);
 				renderRequest.setAttribute("articleId", articleId);
 				
 				for(String name : jsonDataNames) {
 					if(!name.equals("docs"))
 						renderRequest.setAttribute(name, jsonData.has(name) ? jsonData.getString(name) : null);
 					if(name.equalsIgnoreCase("tagInvitedValues")) {
 						JSONArray tagInvitedValues =  new JSONArray(jsonData.getString(name));
 						JSONArray jaRes =  new JSONArray();
 					    for(int i =0; i<tagInvitedValues.length(); i++) {
 					    	JSONObject joinvitee = tagInvitedValues.getJSONObject(i);
 						    JSONObject jo = new JSONObject();
 						    jo.put("id", joinvitee.getString("id"));
 						    jo.put("name", joinvitee.getString("text"));
 						   jaRes.put(jo);
 					    }
 					    allInviteesObj = helper.toList(jaRes);
						renderRequest.setAttribute("allInvitees", allInviteesObj);	
 					}
 					if(name.equals("tagProgramValues")) { 
 						JSONArray tags =  new JSONArray(jsonData.getString(name));
 						JSONArray jaRes =  new JSONArray();
 					    for(int i =0; i<tags.length(); i++) {
 					    	JSONObject jotag = tags.getJSONObject(i);
 						    JSONObject jo = new JSONObject();
 						    jo.put("tagId", jotag.getString("id"));
 						    jo.put("name", jotag.getString("text"));
 						   jaRes.put(jo);
 					    }
 					   allTagsObj = helper.toList(jaRes);
						renderRequest.setAttribute("allTags", allTagsObj);	
 					}
 					
 					if(name.equals("ProjectStage")) {
 						for(int i=0; i<projectStageList.size(); i++) {
 							JSONObject jo = new JSONObject(JSONObject.valueToString(projectStageList.get(i)));
 							if(jo.get("id").equals(jsonData.getString(name)))
 								renderRequest.setAttribute(name, jo.get("value"));
 						} 
 					}
 					if(name.equals("ProjectCategory")) {
 						for(int i=0; i<projectStageList.size(); i++) {
 							JSONObject jo = new JSONObject(JSONObject.valueToString(projectCategList.get(i)));
 							if(jo.get("id").equals(jsonData.getString(name)))
 								renderRequest.setAttribute(name, jo.get("value"));
 						} 
 					}
 				}
 				
 				 if(jsonData.has("coverImagePath")) {
 					File f = new File(jsonData.getString("coverImagePath")); 

 	 				String base64 = "";
 	 				if(f.exists()) {
 	 					 byte[] bytes = Files.readAllBytes(f.toPath());  
 	 			        
 	 			        String extension = FileUtil.getExtension(f.getName()); 
 	 			        extension = extension.equalsIgnoreCase("svg") ? "svg+xml" : extension;
 	 			        base64 = "data:image/"+ extension +";base64," +Base64.getEncoder().encodeToString(bytes);
 	 			        jsonData.put("coverImageURL", base64); 
 	 			        
 	 			        renderRequest.setAttribute("coverImageURL", base64); 
 	 				}
 	 				renderRequest.setAttribute("coverImageURL", base64); 
 				 }
 				 
 				 List<Object> docNames = jsonData.has("docNames") ? helper.toList(jsonData.getJSONArray("docNames")) : new ArrayList<>();
 				 JSONArray docNameExts = new JSONArray();
 				docNames.forEach(docName->{
 					JSONObject json = new JSONObject();
 					json.put("name", docName);
 					json.put("ext", FileUtil.getExtension(String.valueOf(docName)));
 					docNameExts.put(json);
 				});
 				renderRequest.setAttribute("docNames", helper.toList(docNameExts)); 
 				
 				renderRequest.setAttribute("categId", categId);
 				
 				include("/overview.jsp", renderRequest, renderResponse);
 			} else {
 				edited = false;
 				renderRequest.setAttribute("edit", false);	
 				categId = new long[] {Long.valueOf(httpReq.getParameter("p_r_p_categoryId"))};
 				folderId = Long.valueOf(httpReq.getParameter("folderId"));
 				ddmStructureKey = Long.valueOf(httpReq.getParameter("ddmStructureKey"));
 				ddmTemplateKey = httpReq.getParameter("ddmTemplateKey");
			  
 				List<User> users =  UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
 				List<User> approvedUsers = new ArrayList<User>();

				for(User user : users) {
					user.setComments(user.getPortraitURL(themeDisplay));
					approvedUsers.add(user);
				}
				
				renderRequest.setAttribute("allUsers", approvedUsers);
				
				List<AssetTag> allTags = AssetTagLocalServiceUtil.getTags();
				renderRequest.setAttribute("allTags", allTags);
				getDropdownsData(themeDisplay, renderRequest);
				
				JSONArray invRes =  new JSONArray();
				if( jsonData.has("tagInvitedValues") ) {
					JSONArray tagInvitedValues =  new JSONArray(jsonData.getString("tagInvitedValues"));
				    for(int i =0; i<tagInvitedValues.length(); i++) {
				    	JSONObject joinvitee = tagInvitedValues.getJSONObject(i);
					    JSONObject jo = new JSONObject();
					    jo.put("id", joinvitee.getString("id"));
					    jo.put("name", joinvitee.getString("text"));
					    invRes.put(jo);
				    }
				}
				renderRequest.setAttribute("selectedUsers", helper.toList(invRes));	
				
				JSONArray tagRes =  new JSONArray();
				if( jsonData.has("tagProgramValues") ) { 
					JSONArray tags =  new JSONArray(jsonData.getString("tagProgramValues"));
				    for(int i =0; i<tags.length(); i++) {
				    	JSONObject jotag = tags.getJSONObject(i);
					    JSONObject jo = new JSONObject();
					    jo.put("tagId", jotag.getString("id"));
					    jo.put("name", jotag.getString("text"));
					    tagRes.put(jo);
				    }
				}
				renderRequest.setAttribute("selectedTags", helper.toList(tagRes));	
					
				include("/view.jsp", renderRequest, renderResponse);
 			}
		} catch (PortalException | DocumentException e) {
			e.printStackTrace();
		}
		
		super.render(renderRequest, renderResponse);
	}
	@Override
	public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
		if (ParamUtil.getString(request, "jspType").equalsIgnoreCase("overview.jsp")) {
			 ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			 boolean res = false;
			 boolean edit = false;
			 String articleIdEdited = "";
				try {
					if(ParamUtil.getString(request, "edit").equalsIgnoreCase("true")) {
						edit = true;
						articleIdEdited = ParamUtil.getString(request, "articleId");
					}
					res = addJournalArticle(themeDisplay, jsonData, request, edit, articleIdEdited);
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
				if (res) response.getWriter().println("successMessage.jsp"); 
				super.serveResource(request, response);
			 
		 } else if (ParamUtil.getString(request, "jspType").equalsIgnoreCase("view.jsp")) {
			 List<String> docPaths = new ArrayList<>();
			 JSONArray docNames = new JSONArray(ParamUtil.getString(request, "docNames").split(","));
			 jsonData.put("edit", edited);
			 
			 for(String dataName : jsonDataNames) {
				 String value = ParamUtil.getString(request, dataName);
				 
				 if(dataName.equals("coverImage")) {
					 try {
						 UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest( request );
						 String filename = "";
						 String pathStr = uploadRequest.getSession().getServletContext().getRealPath("/"); 
					 	 String dirPath = pathStr+"uploadedfiles";
						 String filePath = "";
						 String coverImageName = ParamUtil.getString(request, "coverImageName"); 
						 String coverImagebase64String = ParamUtil.getString(request, dataName); 
						 
						 if(!helper.isEmpty(coverImagebase64String)) {
							 String extension = FileUtil.getExtension(coverImageName); 
							 extension = extension.equalsIgnoreCase("svg") ? "svg+xml" : extension;
							 byte[] bytes = Base64.getDecoder().decode(coverImagebase64String.split("data:image/"+extension+";base64,")[1]);
							 
							 filename = UUID.randomUUID().toString()+"."+extension;
							 filePath = dirPath+"/"+filename;
							 Path fpath = Paths.get(filePath);
							 Files.write(fpath, bytes); 
						 } else if(edited && helper.isEmpty(coverImageName) ) {
							  String FileEntryId = ParamUtil.getString(request, "FileEntryId");
							  DLFileEntry fileExists = DLFileEntryLocalServiceUtil.getDLFileEntry(Long.valueOf(FileEntryId));
							  
							  coverImageName = fileExists.getTitle();
							  filename = fileExists.getTitle();
							  filePath = dirPath+"/"+filename;
						 }
						 
						 jsonData.put("coverImageName", coverImageName); 
						 jsonData.put("coverImagePath", filePath); 
							
					 } catch (Exception e) {
					 	e.printStackTrace();
					 }
				} else if (dataName.equals("docs")) {
					docNames = new JSONArray(ParamUtil.getString(request, "docNames").split(","));
					UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);
					String filename = "";
					String pathStr = uploadRequest.getSession().getServletContext().getRealPath("/");
					String dirPath = pathStr + "uploadedfiles";
					String filePath = "";

					JSONArray docsJA = new JSONArray(ParamUtil.get(request, dataName, "[]"));
					for (int i = 0; i < docsJA.length(); i++) {
						JSONObject jo = docsJA.getJSONObject(i);
						String docName = jo.getString("name");
						String docsBase64String = jo.getString("b64");

						String extension = FileUtil.getExtension(docName);
						extension = extension.equalsIgnoreCase("svg") ? "svg+xml" : extension;
						byte[] bytes = Base64.getDecoder()
								.decode(docsBase64String.split("data:image/" + extension + ";base64,")[1]);

						filename = UUID.randomUUID().toString() + "." + extension;
						filePath = dirPath + "/" + filename;
						Path fpath = Paths.get(filePath);
						Files.write(fpath, bytes);
						File doc = new File(filePath);

						if (doc.exists()) {
							docPaths.add(filePath);
						}
					}
				} else {
					 jsonData.put(dataName, value);
				 }
			 }
			 
			 if( edited ) {
				 String[] removedDocIds = ParamUtil.getString(request, "removedDocs").split(",");
				 for(String docId : ParamUtil.getString(request, "docEntryIds").split(",")) {
					 if( !helper.isEmpty(docId) ) {
						 boolean removed = false;
						 for(String removeId : removedDocIds) {
							 if( docId.equals(removeId) ) {
								 removed = true;
								 break;
							 }
						 }
						 if( !removed ) {
							  try {
								  DLFileEntry fileExists = DLFileEntryLocalServiceUtil.getDLFileEntry(Long.valueOf(docId));
								  docNames.put(fileExists.getTitle());
								  docPaths.add(docId);
							  } catch (PortalException e) {
								  e.printStackTrace();
					  		  }
						 }
					 }
				 }
			 }
			 
			 jsonData.put("docPaths", docPaths);
			 jsonData.put("docNames", docNames);
			 
			response.getWriter().println(jsonData);
			super.serveResource(request, response);
		 }
	}

	public Boolean addJournalArticle(ThemeDisplay themeDisplay, JSONObject contentEn, ResourceRequest request, boolean edit, String articleIdEdited) throws Exception {
		boolean res = false;
		long userId = themeDisplay.getUserId();
		long groupId = themeDisplay.getLayout().getGroupId(); 
		
	    ServiceContext serviceContext = new ServiceContext();
	
	    serviceContext.setAddGroupPermissions(true);
	    serviceContext.setAddGuestPermissions(true);
	    serviceContext.setScopeGroupId(groupId);
	    serviceContext.setAssetCategoryIds(categId);
	    
	    serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);
	    Map<Locale, String> titleMap = new HashMap<Locale, String>();
	    Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
	
	    titleMap.put(Locale.US, contentEn.getString("ProjectTitle"));
	    descriptionMap.put(Locale.US,  contentEn.getString("ProjectTitle"));
	    
	    
	    JournalArticle article = null; 
	    try {  
		    String xmlContent = "<?xml version=\"1.0\"?>\r\n" + 
		    		"\r\n" + 
		    		"<root available-locales=\"en_US\" default-locale=\"en_US\" version=\"1.0\">\r\n" ;

		    if(jsonData.has("ProjectTitle")) { 
		 	    xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\"Text73021449\" type=\"text\">\r\n" + 
			    		"		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("ProjectTitle")  + "]]></dynamic-content>\r\n" + 
			    		"	</dynamic-element>\r\n";
		    }

		    if(jsonData.has("SolutionOfferingDescription")) {
		 	    xmlContent +=	"	<dynamic-element index-type=\"text\" instance-id=\"HXfAY6hy\" name=\"RichText70541782\" type=\"rich_text\">\r\n" + 
			    		"		<dynamic-content language-id=\"en_US\"><![CDATA[<p>"+ contentEn.getString("SolutionOfferingDescription") +"</p>]]></dynamic-content>\r\n" + 
			    		"	</dynamic-element>\r\n" ;
		    }
		    
		    if(jsonData.has("WebsiteURL")) {
		 	    xmlContent +="	<dynamic-element index-type=\"keyword\" instance-id=\"b00ryCNH\" name=\"Text85717445\" type=\"text\">\r\n" + 
			    		"		<dynamic-content language-id=\"en_US\"><![CDATA["+ contentEn.getString("WebsiteURL") +"]]></dynamic-content>\r\n" + 
			    		"	</dynamic-element>\r\n";
		    }
		    
		    if(jsonData.has("ProjectStage")) {
		 	    xmlContent +="	<dynamic-element index-type=\"keyword\" instance-id=\"tBkABnKP\" name=\"SelectFromList36742361\" type=\"select\">\r\n" +
			    		"		<dynamic-content language-id=\"en_US\"><![CDATA["+ contentEn.getString("ProjectStage") +"]]></dynamic-content>\r\n" + 
			    		"	</dynamic-element>\r\n" ;
		    }
		    
		    if(jsonData.has("ProjectCategory")) {
		    	xmlContent +="	<dynamic-element index-type=\"keyword\" instance-id=\"tBkABnKP\" name=\"SelectFromList03925321\" type=\"select\">\r\n" +
				    		"		<dynamic-content language-id=\"en_US\"><![CDATA["+ contentEn.getString("ProjectCategory") +"]]></dynamic-content>\r\n" + 
				    		"	</dynamic-element>\r\n" ;
		    }
		    
		    if(jsonData.has("coverImagePath")) {
		    	File f = new File(jsonData.getString("coverImagePath"));
		 	    JSONObject coverImage = helper.fileUploadByDL(f, themeDisplay, request, "coverImage", jsonData.getString("coverImageName"));
		 	    xmlContent += 	"	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"Image13718074\" type=\"image\">\r\n" + 
			    		"		<dynamic-content language-id=\"en_US\"><![CDATA["+ coverImage.toString()+"]]></dynamic-content>\r\n" + 
			    		"	</dynamic-element>\r\n" ;
		    }
		   
		    if(jsonData.has("docPaths")) { 
			    JSONArray docPaths = jsonData.getJSONArray("docPaths"); 
			    JSONArray docNames = jsonData.getJSONArray("docNames"); 
			    String docsXml = "";
			    
			    for(int i=0; i< docPaths.length(); i++) {
			    	File fs =  helper.isNumeric(docPaths.getString(i)) ? null : new File(docPaths.getString(i));
			    	// if file null then file already uploaded else new upload file
			    	String fileTitleOrFileId = fs==null?docPaths.getString(i):docNames.getString(i);
			    	JSONObject doc = helper.fileUploadByDL(fs, themeDisplay, request, "documents", fileTitleOrFileId); 
			    	docsXml += "<dynamic-element index-type=\"keyword\" instance-id=\"05MtcaX0\" name=\"Upload33288847\" type=\"document_library\">\r\n" + 
			    			"		<dynamic-content language-id=\"en_US\"><![CDATA[" + doc.toString() +"]]></dynamic-content>\r\n" + 
			    			"	</dynamic-element>\r\n";
			    }
			    
			    xmlContent += docsXml;
		    }
		    
		    if(jsonData.has("tagInvitedValues")) {
		    	JSONArray invitedUsersJa =  new JSONArray(jsonData.getString("tagInvitedValues"));
			    String invitedUsers = ""; 
			    for(int i=0; i< invitedUsersJa.length(); i++) {
			    	JSONObject jo = invitedUsersJa.getJSONObject(i);
			    	invitedUsers += (i>0?",":"")+jo.getString("id");
			    }
			    xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"Cb8Xwnky\" name=\"Text58386300\" type=\"text\">\r\n" + 
	    		"		<dynamic-content language-id=\"en_US\"><![CDATA["+ invitedUsers +"]]></dynamic-content>\r\n" + 
	    		"	</dynamic-element>";
		    }
		    
		    if(jsonData.has("addComments")) {
		 	    xmlContent +=	"	<dynamic-element index-type=\"text\" instance-id=\"HXfAY6hy\" name=\"SingleSelection04730283\" type=\"rich_text\">\r\n" + 
			    		"		<dynamic-content language-id=\"en_US\"><![CDATA[<p>"+ contentEn.getString("addComments") +"</p>]]></dynamic-content>\r\n" + 
			    		"	</dynamic-element>\r\n" ;
		    }
		    xmlContent += "</root>";
		 
		    if(edit) {
		    	article = JournalArticleLocalServiceUtil.getArticle(Long.valueOf(articleIdEdited)); 
		    	article = JournalArticleLocalServiceUtil.updateArticle(userId, groupId, folderId, String
		    			.valueOf(article.getArticleId()), article.getVersion(), xmlContent, serviceContext);
		    } else {
		    	article = JournalArticleLocalServiceUtil.addArticle(userId, groupId, folderId, titleMap, 
			    		descriptionMap, xmlContent, String.valueOf(ddmStructureKey), ddmTemplateKey, serviceContext);
		    }
		    
		    edit = false;
		    
		    if(article != null) {
		    	AssetEntry entry = AssetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(),  article.getResourcePrimKey());
		    	
		    	if(contentEn.has("tagInvitedValues")) {
		    		JSONArray invitedUsersJa =  new JSONArray(jsonData.getString("tagInvitedValues"));
		    		helper.createProjectPermTable();
		    		if( jsonData.getBoolean("edit") ) {
		    			helper.deleteProjectPermission(article.getId());
		    		}
		    		
				    for(int i=0; i< invitedUsersJa.length(); i++) {
//				    	JSONObject userInvited = invitedUsersJa.getJSONObject(i);
//			   	    	Long companyId = themeDisplay.getCompanyId();
//			   	    	User Lf_user = UserLocalServiceUtil.fetchUserByEmailAddress(companyId,  userInvited.getString("id"));
			   	    	
			   	    	helper.addProjectPermission(invitedUsersJa.getJSONObject(i).getString("id"), article.getId()); 
			   	    	
//			   	    	if(Lf_user == null) {
//			   	    		helper.sendEmailInvitation(userInvited.getString("id"), request);
//			   	    	}
		   	  		}
		    	}

			    //Remove Current tags
	    		List<AssetTag> currentTags = AssetTagLocalServiceUtil.getAssetEntryAssetTags(entry.getEntryId());
			    AssetTagLocalServiceUtil.deleteAssetEntryAssetTags(entry.getEntryId(), currentTags);
			    
			    JSONArray tags =  new JSONArray(jsonData.getString("tagProgramValues"));
			    for(int i =0; i<tags.length(); i++) {
			    	JSONObject jotag = tags.getJSONObject(i);
			    	AssetTag tag = AssetTagLocalServiceUtil.fetchAssetTag(Long.valueOf(jotag.getString("id")));
				    if(tag != null)
				    	AssetTagLocalServiceUtil.addAssetEntryAssetTag(entry.getEntryId(), tag);
				    else {
				    	AssetTag newTag = AssetTagLocalServiceUtil.addTag(themeDisplay.getUserId(), groupId, jotag.getString("text"), serviceContext);
				    	AssetTagLocalServiceUtil.addAssetEntryAssetTag(entry.getEntryId(), newTag);
				    }
				    
			    }
			    
		    	res = true;
		    }
		    		 
		} catch (Exception ex) {
			ex.printStackTrace();
			res = false;
	    }
	
	    return res;
	}
	
	
	public void getDropdownsData(ThemeDisplay themeDisplay, RenderRequest renderRequest) throws DocumentException, PortalException {
		DDMStructure struct = DDMStructureManagerUtil.getStructure(themeDisplay.getLayout().getGroupId(), 
				Long.valueOf(PortalUtil.getClassNameId(JournalArticle.class)), String.valueOf(ddmStructureKey));
		JSONObject jo =  new JSONObject(struct.getDefinition());
		JSONArray ja = new JSONArray(jo.get("fields").toString());
		 
		for(int i = 0; i<ja.length(); i++) {
			JSONObject joa = ja.getJSONObject(i); 
			if(joa.get("type").equals("select")) {
				if(joa.get("name").equals("SelectFromList03925321")) { //Project Category 
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					
					projectCategList = helper.toList(jaRes);
					renderRequest.setAttribute("projectCategList", projectCategList);
				} else if(joa.get("name").equals("SelectFromList36742361")) { //Project Stage
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					
					projectStageList = helper.toList(jaRes);
					renderRequest.setAttribute("projectStageList", projectStageList);
				}
			}
		}
		
	}
}
