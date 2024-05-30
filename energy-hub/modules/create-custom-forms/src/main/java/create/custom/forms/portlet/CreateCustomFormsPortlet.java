package create.custom.forms.portlet;

import create.custom.forms.constants.CreateCustomFormsPortletKeys;
import create.custom.forms.helper.helper;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

/**
 * @author vyo
 */
@SuppressWarnings("unused")
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=CreateCustomForms",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.name=" + CreateCustomFormsPortletKeys.CREATECUSTOMFORMS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CreateCustomFormsPortlet extends MVCPortlet {
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		try {
			HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest)); 
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			
			long folderId = Long.valueOf(helper.ifEmpty(httpReq.getParameter("folderId"), "0"));
			long ddmStructureKey = Long.valueOf(helper.ifEmpty(httpReq.getParameter("ddmStructureKey"), "0"));
			long parentCategId = Long.valueOf(helper.ifEmpty(httpReq.getParameter("parentCategId"), "0"));
			
			renderRequest.setAttribute("folderId", folderId);
			renderRequest.setAttribute("ddmStructureKey", ddmStructureKey);
			renderRequest.setAttribute("parentCategId", parentCategId);
			
			if(!httpReq.getParameterMap().containsKey("successMessage") && !httpReq.getParameterMap().containsKey("overview"))
				getDropdownsData(themeDisplay, renderRequest, ddmStructureKey);
			
			if (httpReq.getParameterMap().containsKey("successMessage")) {
				long[] categId = new long[] { Long.valueOf(httpReq.getParameter("p_r_p_categoryId")) };
 				String myProjectUrl = "/my-projects?p_r_p_categoryId="+ categId[0] +"&parentCategId="+ parentCategId 
 						+"&folderId="+ folderId + "&ddmStructureKey=" + ddmStructureKey;
 				renderRequest.setAttribute("categId", categId[0]);
 				renderRequest.setAttribute("myProjectUrl", myProjectUrl);
 				
 				AssetCategory category = AssetCategoryLocalServiceUtil.getAssetCategory(categId[0]);
 				boolean showAdminApprove = false;
 				
 				if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.InnovationChallengeCategoryName) 
 						|| category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.OppIndustCategoryName)
 						|| category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.SuccessStoryCategoryName)) {
 					showAdminApprove = true;
				} 
 				
 				renderRequest.setAttribute("showAdminApprove", showAdminApprove);
 				include("/successMessage.jsp", renderRequest, renderResponse);
 			} else if (httpReq.getParameterMap().containsKey("overview")) {
 				include("/overview.jsp", renderRequest, renderResponse);
 			} else if(httpReq.getParameterMap().containsKey("createInnovationProgram") && !httpReq.getParameterMap().containsKey("articleId")) {
 				long[] categId = new long[] { Long.valueOf(httpReq.getParameter("p_r_p_categoryId")) };
 				
 				renderRequest.setAttribute("categId", categId[0]);
				renderRequest.setAttribute("edit", false);
				renderRequest.setAttribute("articleId", 0);
				String myProjectUrl = "p_r_p_categoryId="+ categId[0] +"&folderId="+ folderId +"&parentCategId="+ parentCategId 
 						+"&ddmStructureKey=" + ddmStructureKey;
				renderRequest.setAttribute("myProjectUrl", myProjectUrl);
				include("/createInnovationProgram.jsp", renderRequest, renderResponse);
				
				super.render(renderRequest, renderResponse);
			} else if(httpReq.getParameterMap().containsKey("createInnovationChallenge") && !httpReq.getParameterMap().containsKey("articleId")) {
 				long[] categId = new long[] { Long.valueOf(httpReq.getParameter("p_r_p_categoryId")) };
 				
 				renderRequest.setAttribute("categId", categId[0]);
				renderRequest.setAttribute("edit", false);
				renderRequest.setAttribute("articleId", 0);
				String myProjectUrl = "p_r_p_categoryId="+ categId[0] +"&folderId="+ folderId +"&parentCategId="+ parentCategId 
 						+"&ddmStructureKey=" + ddmStructureKey;
				renderRequest.setAttribute("myProjectUrl", myProjectUrl);
				
				include("/createInnovationChallenge.jsp", renderRequest, renderResponse);
				super.render(renderRequest, renderResponse);
			} else if(httpReq.getParameterMap().containsKey("createOpportunitiesIndustries") && !httpReq.getParameterMap().containsKey("articleId")) {
 				long[] categId = new long[] { Long.valueOf(httpReq.getParameter("p_r_p_categoryId")) };
 				
 				renderRequest.setAttribute("categId", categId[0]);
				renderRequest.setAttribute("edit", false);
				renderRequest.setAttribute("articleId", 0);
				String myProjectUrl = "p_r_p_categoryId="+ categId[0] +"&folderId="+ folderId +"&parentCategId="+ parentCategId 
 						+"&ddmStructureKey=" + ddmStructureKey;
				renderRequest.setAttribute("myProjectUrl", myProjectUrl);
				
				include("/createOppIndust.jsp", renderRequest, renderResponse);
				super.render(renderRequest, renderResponse);
			} else if(httpReq.getParameterMap().containsKey("createSuccessStory") && !httpReq.getParameterMap().containsKey("articleId")) {
				long[] categId = new long[] { Long.valueOf(httpReq.getParameter("p_r_p_categoryId")) };
				
				renderRequest.setAttribute("categId", categId[0]);
				renderRequest.setAttribute("edit", false);
				renderRequest.setAttribute("articleId", 0);
				String myProjectUrl = "p_r_p_categoryId="+ categId[0] +"&folderId="+ folderId +"&parentCategId="+ parentCategId 
						+"&ddmStructureKey=" + ddmStructureKey;
				renderRequest.setAttribute("myProjectUrl", myProjectUrl);
				
				include("/createSuccessStory.jsp", renderRequest, renderResponse);
				super.render(renderRequest, renderResponse);
			}  else if(httpReq.getParameterMap().containsKey("createCars") && !httpReq.getParameterMap().containsKey("articleId")) {
				long[] categId = new long[] { Long.valueOf(httpReq.getParameter("p_r_p_categoryId")) };
				
				renderRequest.setAttribute("categId", categId[0]);
				renderRequest.setAttribute("parentCategId", parentCategId);
				renderRequest.setAttribute("edit", false);
				renderRequest.setAttribute("articleId", 0);
				String myProjectUrl = "p_r_p_categoryId="+ categId[0] +"&folderId="+ folderId +"&parentCategId="+ parentCategId 
						+"&ddmStructureKey=" + ddmStructureKey;
				renderRequest.setAttribute("myProjectUrl", myProjectUrl);
				
				List<Integer> years = IntStream.rangeClosed(1800, Calendar.getInstance().get(Calendar.YEAR)+1)
					    .boxed().collect(Collectors.toList());
				Collections.reverse(years);
				renderRequest.setAttribute("ModelYear", years);
				
				include("/createCars.jsp", renderRequest, renderResponse);
				super.render(renderRequest, renderResponse);
			} else if (httpReq.getParameterMap().containsKey("articleId")) { //edit
				long articleId = Long.valueOf(httpReq.getParameter("articleId"));
				long[] p_r_p_categoryId = new long[] { Long.valueOf(httpReq.getParameter("p_r_p_categoryId")) };
				JournalArticle article = JournalArticleServiceUtil.getLatestArticle(articleId); 
				Document  document = SAXReaderUtil.read(article.getContentByLocale(themeDisplay.getLanguageId()));
				AssetCategory category = AssetCategoryLocalServiceUtil.getAssetCategory(p_r_p_categoryId[0]);
				renderRequest.setAttribute("categId", p_r_p_categoryId[0]);
				
				String myProjectUrl = "p_r_p_categoryId="+ p_r_p_categoryId[0] +"&folderId="+ folderId +"&parentCategId="+ parentCategId 
 						+"&ddmStructureKey=" + ddmStructureKey;
				renderRequest.setAttribute("myProjectUrl", myProjectUrl);
				
				if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.InnovationProgramCategoryName)) {
					getInnovationProgramData(renderRequest, articleId, document);
					include("/createInnovationProgram.jsp", renderRequest, renderResponse);
				} else if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.InnovationChallengeCategoryName)) {
					getInnovationChallengeData(renderRequest, articleId, document);
					include("/createInnovationChallenge.jsp", renderRequest, renderResponse);
				} else if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.OppIndustCategoryName)) {
					getOppIndustData(renderRequest, articleId, document);
					include("/createOppIndust.jsp", renderRequest, renderResponse);
				} else if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.SuccessStoryCategoryName)) {
					getSuccessStoryData(renderRequest, articleId, document);
					include("/createSuccessStory.jsp", renderRequest, renderResponse);
				} else if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.CarCategoryName)) {
					getCarData(renderRequest, articleId, document);
					
					List<Integer> years = IntStream.rangeClosed(1800, Calendar.getInstance().get(Calendar.YEAR)+1)
						    .boxed().collect(Collectors.toList());
					Collections.reverse(years);
					renderRequest.setAttribute("ModelYear", years);
					include("/createCars.jsp", renderRequest, renderResponse);
				}
				super.render(renderRequest, renderResponse);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getInnovationProgramData(RenderRequest renderRequest, long articleId, Document document) throws PortalException, JSONException {
		renderRequest.setAttribute("articleId", articleId);
		renderRequest.setAttribute("InnovationProgramTitle", helper.getData(document, CreateCustomFormsPortletKeys.InnovationProgramTitle));
		renderRequest.setAttribute("InnovationProgramDescription", helper.getData(document, CreateCustomFormsPortletKeys.InnovationProgramDescription));
		renderRequest.setAttribute("InnovationProgramActivityType", helper.getData(document, CreateCustomFormsPortletKeys.InnovationProgramActivityType));
		renderRequest.setAttribute("InnovationProgramStage", helper.getData(document, CreateCustomFormsPortletKeys.InnovationProgramStage));
		renderRequest.setAttribute("InnovationProgramStartDate", helper.getData(document, CreateCustomFormsPortletKeys.InnovationProgramStartDate));
		renderRequest.setAttribute("InnovationProgramEndDate", helper.getData(document, CreateCustomFormsPortletKeys.InnovationProgramEndDate));
		renderRequest.setAttribute("InnovationProgramURL", helper.getData(document, CreateCustomFormsPortletKeys.InnovationProgramURL));
		renderRequest.setAttribute("InnovationProgramTitle", helper.getData(document, CreateCustomFormsPortletKeys.InnovationProgramTitle));
		
		JSONObject coverImageJO = new JSONObject(helper.getData(document, CreateCustomFormsPortletKeys.InnovationProgramImage));
        DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(coverImageJO.getLong("fileEntryId"));
       
		renderRequest.setAttribute("InnovationProgramImageName", image.getDescription());	
		renderRequest.setAttribute("FileEntryId", image.getFileEntryId());	
		renderRequest.setAttribute("edit", true);
	}
	
	private void getInnovationChallengeData(RenderRequest renderRequest, long articleId, Document document) throws PortalException, JSONException {
		renderRequest.setAttribute("articleId", articleId);
		renderRequest.setAttribute("InnovationChallengeTitle", helper.getData(document, CreateCustomFormsPortletKeys.InnovationChallengeTitle));
		renderRequest.setAttribute("InnovationChallengeDescription", helper.getData(document, CreateCustomFormsPortletKeys.InnovationChallengeDescription));
		renderRequest.setAttribute("InnovationChallngeCreatedByIAM", helper.getData(document, CreateCustomFormsPortletKeys.InnovationChallngeCreatedByIAM));
		renderRequest.setAttribute("InnovationChallengeChallengeType", helper.getData(document, CreateCustomFormsPortletKeys.InnovationChallengeChallengeType));
		renderRequest.setAttribute("InnovationChallengeStartDate", helper.getData(document, CreateCustomFormsPortletKeys.InnovationChallengeStartDate));
		renderRequest.setAttribute("InnovationChallengeEndDate", helper.getData(document, CreateCustomFormsPortletKeys.InnovationChallengeEndDate));
		renderRequest.setAttribute("InnovationChallengeEligibility", helper.getData(document, CreateCustomFormsPortletKeys.InnovationChallengeEligibility));
		
		JSONObject coverImageJO = new JSONObject(helper.getData(document, CreateCustomFormsPortletKeys.InnovationChallengeImage));
        DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(coverImageJO.getLong("fileEntryId"));
       
		renderRequest.setAttribute("InnovationChallengeImage", image.getDescription());	
		renderRequest.setAttribute("FileEntryId", image.getFileEntryId());	
		renderRequest.setAttribute("edit", true);
	}
	
	private void getOppIndustData(RenderRequest renderRequest, long articleId, Document document) throws PortalException, JSONException {
		renderRequest.setAttribute("articleId", articleId);
		renderRequest.setAttribute("OppIndustTitle", helper.getData(document, CreateCustomFormsPortletKeys.OppIndustTitle));
		renderRequest.setAttribute("OppIndustDescription", helper.getData(document, CreateCustomFormsPortletKeys.OppIndustDescription));
		renderRequest.setAttribute("OppIndustType", helper.getData(document, CreateCustomFormsPortletKeys.OppIndustType));
		renderRequest.setAttribute("OppIndustEligibility", helper.getData(document, CreateCustomFormsPortletKeys.OppIndustEligibility));
		renderRequest.setAttribute("OppIndustStartDate", helper.getData(document, CreateCustomFormsPortletKeys.OppIndustStartDate));
		renderRequest.setAttribute("OppIndustEndDate", helper.getData(document, CreateCustomFormsPortletKeys.OppIndustEndDate));
		renderRequest.setAttribute("OppIndustURL", helper.getData(document, CreateCustomFormsPortletKeys.OppIndustURL));
		
		JSONObject coverImageJO = new JSONObject(helper.getData(document, CreateCustomFormsPortletKeys.OppIndustImage));
		if(!coverImageJO.isEmpty()) {
			DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(coverImageJO.getLong("fileEntryId"));
			
			renderRequest.setAttribute("OppIndustImageName", image.getDescription());	
			renderRequest.setAttribute("FileEntryId", image.getFileEntryId());	
		}
		renderRequest.setAttribute("edit", true);
	}
	
	private void getCarData(RenderRequest renderRequest, long articleId, Document document) throws PortalException, JSONException {
		renderRequest.setAttribute("articleId", articleId);
		renderRequest.setAttribute("CarMakeValue", helper.getData(document, CreateCustomFormsPortletKeys.CarMake));
		renderRequest.setAttribute("CarModelValue", helper.getData(document, CreateCustomFormsPortletKeys.CarModel));
		renderRequest.setAttribute("ModelYearValue", helper.getData(document, CreateCustomFormsPortletKeys.ModelYear));
		renderRequest.setAttribute("RangeValue", helper.getData(document, CreateCustomFormsPortletKeys.Range));
		renderRequest.setAttribute("PriceRangeFromValue", helper.getData(document, CreateCustomFormsPortletKeys.PriceRangeFrom));
		renderRequest.setAttribute("PriceRangeToValue", helper.getData(document, CreateCustomFormsPortletKeys.PriceRangeTo));
		renderRequest.setAttribute("FuelTypeValue", helper.getData(document, CreateCustomFormsPortletKeys.FuelType));
		renderRequest.setAttribute("ChargingTimeValue", helper.getData(document, CreateCustomFormsPortletKeys.ChargingTime));
		renderRequest.setAttribute("FuelEfficiencyValue", helper.getData(document, CreateCustomFormsPortletKeys.FuelEfficiency));
		renderRequest.setAttribute("ElectricMotorBatteryValue", helper.getData(document, CreateCustomFormsPortletKeys.ElectricMotorBattery));
		renderRequest.setAttribute("VehicleClassSizeValue", helper.getData(document, CreateCustomFormsPortletKeys.VehicleClassSize));
		renderRequest.setAttribute("SedanSubClassValue", helper.getData(document, CreateCustomFormsPortletKeys.SedanSubClass));
		renderRequest.setAttribute("StationWagonSubClassValue", helper.getData(document, CreateCustomFormsPortletKeys.StationWagonSubClass));
		renderRequest.setAttribute("PickUpTruckSubClassValue", helper.getData(document, CreateCustomFormsPortletKeys.PickUpTruckSubClass));
		renderRequest.setAttribute("VanSubClassValue", helper.getData(document, CreateCustomFormsPortletKeys.VanSubClass));
		renderRequest.setAttribute("TransmissionValue", helper.getData(document, CreateCustomFormsPortletKeys.Transmission));
		renderRequest.setAttribute("CarWebsiteLinkValue", helper.getData(document, CreateCustomFormsPortletKeys.CarWebsiteLink));
		
		JSONObject coverImageJO = new JSONObject(helper.getData(document, CreateCustomFormsPortletKeys.CarImage));
		if(!coverImageJO.isEmpty()) {
			DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(coverImageJO.getLong("fileEntryId"));
			
			renderRequest.setAttribute("CarImageName", image.getDescription());	
			renderRequest.setAttribute("FileEntryId", image.getFileEntryId());	
		}
		
		renderRequest.setAttribute("edit", true);
	}
	
	private void getSuccessStoryData(RenderRequest renderRequest, long articleId, Document document) throws PortalException, JSONException {
		renderRequest.setAttribute("articleId", articleId);
		renderRequest.setAttribute("SuccessStoryTitle", helper.getData(document, CreateCustomFormsPortletKeys.SuccessStoryTitle));
		renderRequest.setAttribute("SuccessStoryDescription", helper.getData(document, CreateCustomFormsPortletKeys.SuccessStoryDescription));
		renderRequest.setAttribute("SuccessStoryType", helper.getData(document, CreateCustomFormsPortletKeys.SuccessStoryType));
		renderRequest.setAttribute("SuccessStorySize", helper.getData(document, CreateCustomFormsPortletKeys.SuccessStorySize));
		
		JSONObject coverImageJO = new JSONObject(helper.getData(document, CreateCustomFormsPortletKeys.SuccessStoryImage));
		if(!coverImageJO.isEmpty()) {
			DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(coverImageJO.getLong("fileEntryId"));
			
			renderRequest.setAttribute("SuccessStoryImageName", image.getDescription());	
			renderRequest.setAttribute("FileEntryId", image.getFileEntryId());	
		}
		
//		SuccessStoryMedia
		renderRequest.setAttribute("edit", true);
	}
	
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) 
			throws IOException, PortletException {
		resourceResponse.setContentType("text/html");
		PrintWriter out = resourceResponse.getWriter();
		String retVal = "";
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
			long folderId = ParamUtil.getLong(resourceRequest, "folderId");
			long categId =  ParamUtil.getLong(resourceRequest, "categId");
			long articleId =  ParamUtil.getLong(resourceRequest, "articleId");
			boolean edit = ParamUtil.getBoolean(resourceRequest, "edit");
			boolean overview = ParamUtil.getBoolean(resourceRequest, "overview");
			String ddmStructureKey = ParamUtil.getString(resourceRequest, "ddmStructureKey");
			JSONObject content = new JSONObject();
			String[] dataNames = null;
			
			AssetCategory category = AssetCategoryLocalServiceUtil.getAssetCategory(categId);

			if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.InnovationProgramCategoryName))
				dataNames = CreateCustomFormsPortletKeys.InnovationProgramNames;
			if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.InnovationChallengeCategoryName))
				dataNames = CreateCustomFormsPortletKeys.InnovationChallengeNames;
			if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.OppIndustCategoryName))
				dataNames = CreateCustomFormsPortletKeys.OppIndustNames;
			if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.SuccessStoryCategoryName))
				dataNames = CreateCustomFormsPortletKeys.SuccessStoryNames;
			if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.CarCategoryName))
				dataNames = CreateCustomFormsPortletKeys.CarsNames;
			
			
			List<String> docPaths = new ArrayList<>();
			JSONArray docNames = new JSONArray();
			for(String dataName : dataNames) {
				 String value = ParamUtil.getString(resourceRequest, dataName);
				 String name = "";
				 String path = "";
				 if(dataName.equalsIgnoreCase("InnovationProgramImage") || dataName.equalsIgnoreCase("SuccessStoryImage") ||
						 dataName.equalsIgnoreCase("InnovationChallengeImage") ||dataName.equalsIgnoreCase("OppIndustImage")
						 || dataName.equalsIgnoreCase("CarImage")) {
					 if(dataName.equals("InnovationProgramImage")) {
						 name = "InnovationProgramImageName";
						 path = "InnovationProgramImagePath";
					 }
					 if(dataName.equals("InnovationChallengeImage")) {
						 name = "InnovationChallengeImageName";
						 path = "InnovationChallengeImagePath";
					 }
					 if(dataName.equals("OppIndustImage")) {
						 name = "OppIndustImageName";
						 path = "OppIndustImagePath";
					 }
					 if(dataName.equals("SuccessStoryImage")) {
						 name = "SuccessStoryImageName";
						 path = "SuccessStoryImagePath";
					 }
					 if(dataName.equals("CarImage")) {
						 name = "CarImageName";
						 path = "CarImagePath";
					 }
					 
					 try {
						 UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest( resourceRequest );
						 String filename = "";
						 String pathStr = uploadRequest.getSession().getServletContext().getRealPath("/"); 
					 	 String dirPath = pathStr+"uploadedfiles";
						 String filePath = "";
						 String imageName = ParamUtil.getString(resourceRequest, name); 
						 String coverImagebase64String = ParamUtil.getString(resourceRequest, dataName); 
						 
						 if(!helper.isEmpty(coverImagebase64String)) {
							 String extension = FileUtil.getExtension(imageName); 
							 extension = extension.equalsIgnoreCase("svg") ? "svg+xml" : extension;
							 byte[] bytes = Base64.getDecoder().decode(coverImagebase64String.split("data:image/"+extension+";base64,")[1]);
							 
							 filename = UUID.randomUUID().toString()+"."+extension;
							 filePath = dirPath+"/"+filename;
							 Path fpath = Paths.get(filePath);
							 Files.write(fpath, bytes);  
						 }   else if(edit && helper.isEmpty(imageName) ) {
							  String FileEntryId = ParamUtil.getString(resourceRequest, "FileEntryId");	
							  DLFileEntry fileExists = DLFileEntryLocalServiceUtil.getDLFileEntry(Long.valueOf(FileEntryId));
							  
							  imageName = fileExists.getTitle();
							  filename = fileExists.getTitle();
							  filePath = dirPath+"/"+filename;
							  
							  if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.CarCategoryName) 
									  && dataName.equals("CarImage")) {
								  content.put("FileEntryId", FileEntryId); 
							  }
						 }
						 content.put(name, imageName); 
						 content.put(path, filePath); 
							
					 } catch (Exception e) {
					 	e.printStackTrace();
					 }
				 } else if( dataName.equals("SuccessStoryMedia") ) {
					 docNames = new JSONArray(ParamUtil.getString(resourceRequest, "SuccessStoryMediaNames").split(","));
					 UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest( resourceRequest );
					 String filename = "";
					 String pathStr = uploadRequest.getSession().getServletContext().getRealPath("/"); 
				 	 String dirPath = pathStr+"uploadedfiles";
					 String filePath = "";
					 
				    JSONArray docsJA = new JSONArray(ParamUtil.get(resourceRequest, "SuccessStoryMedia", "[]"));
				    if(docsJA.length() > 0) {
						for (int i = 0; i < docsJA.length(); i++) {
							JSONObject jo = docsJA.getJSONObject(i);
							String docName = jo.getString("name");
							String docsBase64String = jo.getString("b64");
							String extension = FileUtil.getExtension(docName); 
							extension = extension.equalsIgnoreCase("svg") ? "svg+xml" : extension;
							byte[] bytes = Base64.getDecoder().decode(docsBase64String.replace("data:image/"+extension+";base64,",""));
							 
							filename = UUID.randomUUID().toString()+"."+extension;
							filePath = dirPath+"/"+filename;
							Path fpath = Paths.get(filePath);
							Files.write(fpath, bytes);
							File doc = new File(filePath);
						 	 
							if( doc.exists() ) {
								docPaths.add(filePath);
							}
						 }
					 }
				 } else content.put(dataName, value);
			 }
			
			 if( edit ) {
				 String[] removedDocIds = ParamUtil.getString(resourceRequest, "removedDocs").split(",");
				 for(String docId : ParamUtil.getString(resourceRequest, "docEntryIds").split(",")) {
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

			 content.put("docPaths", docPaths);
			 content.put("docNames", docNames);
			 
			 if(!overview) {
				 retVal = addJournalArticle(themeDisplay, content, resourceRequest, folderId, ddmStructureKey, 
						 category, edit, articleId);
			 }
			out.println(String.valueOf(retVal));
			out.flush();
			super.serveResource(resourceRequest, resourceResponse);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String addJournalArticle(ThemeDisplay themeDisplay, JSONObject contentEn, ResourceRequest request,  long folderId,
			String ddmStructureKey,AssetCategory category, boolean edit, long articleId) throws Exception {
		String res = "";
		long userId = themeDisplay.getUserId();
		long groupId = themeDisplay.getLayout().getGroupId();
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setAssetCategoryIds(new long[] { category.getCategoryId() });
		Map<Locale, String> titleMap = new HashMap<Locale, String>();
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
		
		JournalArticle article = null;
		try {
			
			String xmlContent = "";
			if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.InnovationProgramCategoryName)) {
				serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
				xmlContent = getInnovationProgramFormXml(contentEn, themeDisplay, request);
				titleMap.put(Locale.US, contentEn.getString("InnovationProgramTitle"));
			    descriptionMap.put(Locale.US, contentEn.getString("InnovationProgramTitle"));				
			}
			if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.InnovationChallengeCategoryName)) {
				serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);
				xmlContent = getInnovationChallengeFormXml(contentEn, themeDisplay, request);
				titleMap.put(Locale.US, contentEn.getString("InnovationChallengeTitle"));
			    descriptionMap.put(Locale.US, contentEn.getString("InnovationChallengeTitle"));				
			}
			if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.OppIndustCategoryName)) {
				serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);
				xmlContent = getOppIndustFormXml(contentEn, themeDisplay, request);
				titleMap.put(Locale.US, contentEn.getString("OppIndustTitle"));
			    descriptionMap.put(Locale.US, contentEn.getString("OppIndustTitle"));				
			}
			if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.SuccessStoryCategoryName)) {
				serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);
				xmlContent = getSuccessStoryFormXml(contentEn, themeDisplay, request);
				titleMap.put(Locale.US, contentEn.getString("SuccessStoryTitle"));
				descriptionMap.put(Locale.US, contentEn.getString("SuccessStoryTitle"));				
			}
			if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.CarCategoryName)) {
				serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
				xmlContent = getCarFormXml(contentEn, themeDisplay, request);
				com.liferay.dynamic.data.mapping.kernel.DDMStructure struct = DDMStructureManagerUtil.getStructure(themeDisplay.getLayout().getGroupId(),
						Long.valueOf(PortalUtil.getClassNameId(JournalArticle.class)), String.valueOf(ddmStructureKey));
				String name = getFilterData(themeDisplay, struct, CreateCustomFormsPortletKeys.CarMake, 
						contentEn.getString("CarMake"));
				titleMap.put(Locale.US, name);
				descriptionMap.put(Locale.US, name);				
			}
 
			if(edit) {
				article = JournalArticleLocalServiceUtil.getLatestArticle(articleId); 
		    	article = JournalArticleLocalServiceUtil.updateArticle(userId, groupId, folderId, String
		    			.valueOf(article.getArticleId()), article.getVersion(), xmlContent, serviceContext);
			}else {
				article = JournalArticleLocalServiceUtil.addArticle(themeDisplay.getUserId(), groupId, folderId, titleMap, descriptionMap,
						xmlContent, ddmStructureKey, "", serviceContext);
				if(category.getName().equalsIgnoreCase(CreateCustomFormsPortletKeys.CarCategoryName))
					res = "detail?userDetail&categName=CarDealerUser&groupId="+ groupId +"&articleId=" + article.getResourcePrimKey();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return res;
	}

	private String getCarFormXml(JSONObject contentEn, ThemeDisplay themeDisplay, ResourceRequest request) {
		String xmlContent = "<?xml version=\"1.0\"?>\r\n" + "\r\n"
				+ "<root available-locales=\"en_US\" default-locale=\"en_US\" version=\"1.0\">\r\n";
		
		if (contentEn.has("CarModel")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\"" + CreateCustomFormsPortletKeys.CarModel+ "\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("CarModel")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("CarMake")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.CarMake+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("CarMake")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("ModelYear")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.ModelYear+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("ModelYear")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("Range")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.Range+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("Range")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("PriceRangeFrom")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.PriceRangeFrom+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("PriceRangeFrom")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("PriceRangeTo")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.PriceRangeTo+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("PriceRangeTo")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("FuelType")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.FuelType+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("FuelType")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("ChargingTime")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.ChargingTime+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("ChargingTime")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("FuelEfficiency")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.FuelEfficiency+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("FuelEfficiency")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("ElectricMotorBattery")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.ElectricMotorBattery+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("ElectricMotorBattery")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("VehicleClassSize")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.VehicleClassSize+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("VehicleClassSize")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("SedanSubClass")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.SedanSubClass+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("SedanSubClass")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("StationWagonSubClass")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.StationWagonSubClass+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("StationWagonSubClass")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("PickUpTruckSubClass")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.PickUpTruckSubClass+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("PickUpTruckSubClass")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("VanSubClass")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.VanSubClass+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("VanSubClass")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("Transmission")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.Transmission+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("Transmission")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("CarWebsiteLink")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.CarWebsiteLink+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("CarWebsiteLink")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("CarImagePath") && !helper.isEmpty(contentEn.getString("CarImagePath"))) {
			File f = new File(contentEn.getString("CarImagePath"));
			String FileEntryId  =  contentEn.has("FileEntryId") ? contentEn.getString("FileEntryId") : "";
			JSONObject CarImageJO = helper.fileUploadByDL(f, themeDisplay, request, 
					CreateCustomFormsPortletKeys.CarCategoryName, contentEn.getString("CarImageName"), true, FileEntryId);
			xmlContent += "	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"" + CreateCustomFormsPortletKeys.CarImage + "\" type=\"image\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + CarImageJO.toString()
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}else {
			File f = null;
			JSONObject CarImageJO = helper.fileUploadByDL(f, themeDisplay, request, 
					CreateCustomFormsPortletKeys.CarCategoryName, contentEn.getString("CarImageName"), true, "");
			xmlContent += "	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"" + CreateCustomFormsPortletKeys.CarImage + "\" type=\"image\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + CarImageJO.toString()
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		xmlContent += "</root>";
		return xmlContent;
	}

	private String getInnovationProgramFormXml(JSONObject contentEn, ThemeDisplay themeDisplay, ResourceRequest request) {
		String xmlContent = "<?xml version=\"1.0\"?>\r\n" + "\r\n"
				+ "<root available-locales=\"en_US\" default-locale=\"en_US\" version=\"1.0\">\r\n";
		
		if (contentEn.has("InnovationProgramTitle")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\"" + CreateCustomFormsPortletKeys.InnovationProgramTitle+ "\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("InnovationProgramTitle")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("InnovationProgramDescription")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.InnovationProgramDescription+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("InnovationProgramDescription")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("InnovationProgramActivityType")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.InnovationProgramActivityType+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("InnovationProgramActivityType")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("InnovationProgramStage")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.InnovationProgramStage+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("InnovationProgramStage")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("InnovationProgramURL")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.InnovationProgramURL+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("InnovationProgramURL")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("InnovationProgramStartDate")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.InnovationProgramStartDate+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("InnovationProgramStartDate")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("InnovationProgramEndDate")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.InnovationProgramEndDate+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("InnovationProgramEndDate")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}

//		if (contentEn.has("InnovationProgramImagePath") && !helper.isEmpty(contentEn.getString("InnovationProgramImagePath"))) {
//			File f = new File(contentEn.getString("InnovationProgramImagePath"));
//			JSONObject InnovationProgramImageJO = helper.fileUploadByDL(f, themeDisplay, request, CreateCustomFormsPortletKeys.InnovationProgramCategoryName,
//					contentEn.getString("InnovationProgramImageName"), false, "");
//			xmlContent += "	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"" + CreateCustomFormsPortletKeys.InnovationProgramImage + "\" type=\"image\">\r\n"
//					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + InnovationProgramImageJO.toString()
//					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
//		} 
		
		if (contentEn.has("InnovationProgramImagePath") && !helper.isEmpty(contentEn.getString("InnovationProgramImagePath"))) {
			File f = new File(contentEn.getString("InnovationProgramImagePath"));
			String FileEntryId  =  contentEn.has("FileEntryId") ? contentEn.getString("FileEntryId") : "";
			JSONObject CarImageJO = helper.fileUploadByDL(f, themeDisplay, request, 
					CreateCustomFormsPortletKeys.InnovationProgramCategoryName, contentEn.getString("InnovationProgramImageName"), true, FileEntryId);
			xmlContent += "	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"" + CreateCustomFormsPortletKeys.InnovationProgramImage + "\" type=\"image\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + CarImageJO.toString()
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}else {
			File f = null;
			JSONObject CarImageJO = helper.fileUploadByDL(f, themeDisplay, request, 
					CreateCustomFormsPortletKeys.InnovationProgramCategoryName, contentEn.getString("InnovationProgramImageName"), true, "");
			xmlContent += "	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"" + CreateCustomFormsPortletKeys.InnovationProgramImage + "\" type=\"image\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + CarImageJO.toString()
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		xmlContent += "</root>";
		return xmlContent;
	}
	
	private String getOppIndustFormXml(JSONObject contentEn, ThemeDisplay themeDisplay, ResourceRequest request) {
		String xmlContent = "<?xml version=\"1.0\"?>\r\n" + "\r\n"
				+ "<root available-locales=\"en_US\" default-locale=\"en_US\" version=\"1.0\">\r\n";
	
		if (contentEn.has("OppIndustTitle")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\"" + CreateCustomFormsPortletKeys.OppIndustTitle+ "\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("OppIndustTitle")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("OppIndustDescription")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.OppIndustDescription+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("OppIndustDescription")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("OppIndustType")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.OppIndustType+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("OppIndustType")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("OppIndustEligibility")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.OppIndustEligibility+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("OppIndustEligibility")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("OppIndustStartDate")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.OppIndustStartDate+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("OppIndustStartDate")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("OppIndustEndDate")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.OppIndustEndDate+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("OppIndustEndDate")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("OppIndustURL")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.OppIndustURL+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("OppIndustURL")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
//		if (contentEn.has("OppIndustImagePath") && !helper.isEmpty(contentEn.getString("OppIndustImagePath"))) {
//			File f = new File(contentEn.getString("OppIndustImagePath"));
//			JSONObject OppIndustImageJO = helper.fileUploadByDL(f, themeDisplay, request, CreateCustomFormsPortletKeys.OppIndustCategoryName,
//					contentEn.getString("OppIndustImageName"), false, "");
//			xmlContent += "	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"" + CreateCustomFormsPortletKeys.OppIndustImage + "\" type=\"image\">\r\n"
//					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + OppIndustImageJO.toString()
//					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
//		} 
		
		if (contentEn.has("OppIndustImagePath") && !helper.isEmpty(contentEn.getString("OppIndustImagePath"))) {
			File f = new File(contentEn.getString("OppIndustImagePath"));
			String FileEntryId  =  contentEn.has("FileEntryId") ? contentEn.getString("FileEntryId") : "";
			JSONObject CarImageJO = helper.fileUploadByDL(f, themeDisplay, request, 
					CreateCustomFormsPortletKeys.OppIndustCategoryName, contentEn.getString("OppIndustImageName"), true, FileEntryId);
			xmlContent += "	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"" + CreateCustomFormsPortletKeys.OppIndustImage + "\" type=\"image\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + CarImageJO.toString()
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}else {
			File f = null;
			JSONObject CarImageJO = helper.fileUploadByDL(f, themeDisplay, request, 
					CreateCustomFormsPortletKeys.OppIndustCategoryName, contentEn.getString("OppIndustImageName"), true, "");
			xmlContent += "	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"" + CreateCustomFormsPortletKeys.OppIndustImage + "\" type=\"image\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + CarImageJO.toString()
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		xmlContent += "</root>";
		return xmlContent;
	}
	
	private String getSuccessStoryFormXml(JSONObject contentEn, ThemeDisplay themeDisplay, ResourceRequest request) {
		String xmlContent = "<?xml version=\"1.0\"?>\r\n" + "\r\n"
				+ "<root available-locales=\"en_US\" default-locale=\"en_US\" version=\"1.0\">\r\n";
		
		if (contentEn.has("SuccessStoryTitle")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\"" + CreateCustomFormsPortletKeys.SuccessStoryTitle+ "\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("SuccessStoryTitle")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("SuccessStoryDescription")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.SuccessStoryDescription+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("SuccessStoryDescription")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("SuccessStoryType")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.SuccessStoryType+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("SuccessStoryType")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("SuccessStorySize")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.SuccessStorySize+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("SuccessStorySize")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
//		if (contentEn.has("SuccessStoryImagePath") && !helper.isEmpty(contentEn.getString("SuccessStoryImagePath"))) {
//			File f = new File(contentEn.getString("SuccessStoryImagePath"));
//			JSONObject SuccessStoryImageJO = helper.fileUploadByDL(f, themeDisplay, request, CreateCustomFormsPortletKeys.SuccessStoryCategoryName,
//					contentEn.getString("SuccessStoryImageName"), false, "");
//			xmlContent += "	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"" + CreateCustomFormsPortletKeys.SuccessStoryImage + "\" type=\"image\">\r\n"
//					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + SuccessStoryImageJO.toString()
//					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
//		} 
		
		if (contentEn.has("SuccessStoryImagePath") && !helper.isEmpty(contentEn.getString("SuccessStoryImagePath"))) {
			File f = new File(contentEn.getString("SuccessStoryImagePath"));
			String FileEntryId  =  contentEn.has("FileEntryId") ? contentEn.getString("FileEntryId") : "";
			JSONObject CarImageJO = helper.fileUploadByDL(f, themeDisplay, request, 
					CreateCustomFormsPortletKeys.SuccessStoryCategoryName, contentEn.getString("SuccessStoryImageName"), true, FileEntryId);
			xmlContent += "	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"" + CreateCustomFormsPortletKeys.SuccessStoryImage + "\" type=\"image\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + CarImageJO.toString()
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}else {
			File f = null;
			JSONObject CarImageJO = helper.fileUploadByDL(f, themeDisplay, request, 
					CreateCustomFormsPortletKeys.SuccessStoryCategoryName, contentEn.getString("SuccessStoryImageName"), true, "");
			xmlContent += "	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"" + CreateCustomFormsPortletKeys.SuccessStoryImage + "\" type=\"image\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + CarImageJO.toString()
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if(contentEn.has("docPaths")) { 
		    JSONArray docPaths = contentEn.getJSONArray("docPaths"); 
		    JSONArray docNames = contentEn.getJSONArray("docNames"); 
		    String docsXml = "";
		    
		    for(int i=0; i< docPaths.length(); i++) {
		    	File fs =  helper.isNumeric(docPaths.getString(i)) ? null : new File(docPaths.getString(i));
		    	// if file null then file already uploaded else new upload file
		    	String fileTitleOrFileId = fs==null?docPaths.getString(i):docNames.getString(i);
		    	JSONObject doc = helper.fileUploadByDL(fs, themeDisplay, request, "documents", fileTitleOrFileId, false, ""); 
		    	docsXml += "<dynamic-element index-type=\"keyword\" instance-id=\"05MtcaX0\" name=\""+ CreateCustomFormsPortletKeys.SuccessStoryMedia +"\" type=\"document_library\">\r\n" + 
		    			"		<dynamic-content language-id=\"en_US\"><![CDATA[" + doc.toString() +"]]></dynamic-content>\r\n" + 
		    			"	</dynamic-element>\r\n";
		    }
		    
		    xmlContent += docsXml;
	    }
		xmlContent += "</root>";
		return xmlContent;
	}
	
	private String getInnovationChallengeFormXml(JSONObject contentEn, ThemeDisplay themeDisplay, ResourceRequest request) {
		String xmlContent = "<?xml version=\"1.0\"?>\r\n" + "\r\n"
				+ "<root available-locales=\"en_US\" default-locale=\"en_US\" version=\"1.0\">\r\n";
		
		if (contentEn.has("InnovationChallengeTitle")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\"" + CreateCustomFormsPortletKeys.InnovationChallengeTitle+ "\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("InnovationChallengeTitle")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("InnovationChallengeDescription")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.InnovationChallengeDescription+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("InnovationChallengeDescription")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("InnovationChallengeChallengeType")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.InnovationChallengeChallengeType+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("InnovationChallengeChallengeType")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("InnovationChallngeCreatedByIAM")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.InnovationChallngeCreatedByIAM+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("InnovationChallngeCreatedByIAM")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("InnovationChallengeEligibility")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.InnovationChallengeEligibility+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("InnovationChallengeEligibility")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("InnovationChallengeStartDate")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.InnovationChallengeStartDate+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("InnovationChallengeStartDate")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		if (contentEn.has("InnovationChallengeEndDate")) {
			xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+CreateCustomFormsPortletKeys.InnovationChallengeEndDate+"\" type=\"text\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("InnovationChallengeEndDate")
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		} 
//		if (contentEn.has("InnovationChallengeImagePath") && !helper.isEmpty(contentEn.getString("InnovationChallengeImagePath"))) {
//			File f = new File(contentEn.getString("InnovationChallengeImagePath"));
//			JSONObject InnovationChallengeImageJO = helper.fileUploadByDL(f, themeDisplay, request, CreateCustomFormsPortletKeys.InnovationChallengeCategoryName,
//					contentEn.getString("InnovationChallengeImageName"), false, "");
//			xmlContent += "	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"" + CreateCustomFormsPortletKeys.InnovationChallengeImage + "\" type=\"image\">\r\n"
//					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + InnovationChallengeImageJO.toString()
//					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
//		} 
		
		if (contentEn.has("InnovationChallengeImagePath") && !helper.isEmpty(contentEn.getString("InnovationChallengeImagePath"))) {
			File f = new File(contentEn.getString("InnovationChallengeImagePath"));
			String FileEntryId  =  contentEn.has("FileEntryId") ? contentEn.getString("FileEntryId") : "";
			JSONObject CarImageJO = helper.fileUploadByDL(f, themeDisplay, request, 
					CreateCustomFormsPortletKeys.InnovationChallengeCategoryName, contentEn.getString("InnovationChallengeImageName"), true, FileEntryId);
			xmlContent += "	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"" + CreateCustomFormsPortletKeys.InnovationChallengeImage + "\" type=\"image\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + CarImageJO.toString()
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}else {
			File f = null;
			JSONObject CarImageJO = helper.fileUploadByDL(f, themeDisplay, request, 
					CreateCustomFormsPortletKeys.InnovationChallengeCategoryName, contentEn.getString("InnovationChallengeImageName"), true, "");
			xmlContent += "	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"" + CreateCustomFormsPortletKeys.InnovationChallengeImage + "\" type=\"image\">\r\n"
					+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + CarImageJO.toString()
					+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
		}
		xmlContent += "</root>";
		return xmlContent;
	}

	public void getDropdownsData(ThemeDisplay themeDisplay, RenderRequest renderRequest, long ddmStructureKey) throws DocumentException, PortalException {
		DDMStructure struct = null;
		List<DDMStructure> structs = DDMStructureLocalServiceUtil.getStructures();
		for(DDMStructure st : structs) {
			if(st.getStructureKey().equals(String.valueOf(ddmStructureKey))) {
				struct = st;
			}
		}
		
		JSONObject jo =  new JSONObject(struct.getDefinition());
		JSONArray ja = new JSONArray(jo.get("fields").toString());
		 
		for(int i = 0; i<ja.length(); i++) {
			JSONObject joa = ja.getJSONObject(i); 
			if(joa.get("type").equals("select")) {
				if(joa.get("name").equals(CreateCustomFormsPortletKeys.InnovationProgramActivityType)) { //TypeofActivity
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					renderRequest.setAttribute("TypeofActivity", helper.toList(jaRes));
				} else if(joa.get("name").equals(CreateCustomFormsPortletKeys.InnovationProgramStage)) { //Stage
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					renderRequest.setAttribute("Stage", helper.toList(jaRes));
				}else if(joa.get("name").equals(CreateCustomFormsPortletKeys.InnovationChallengeChallengeType)) { //Challenge Type
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					renderRequest.setAttribute("ChallengeType", helper.toList(jaRes));
				}else if(joa.get("name").equals(CreateCustomFormsPortletKeys.OppIndustType)) { //Type
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					renderRequest.setAttribute("Type", helper.toList(jaRes));
				}else if(joa.get("name").equals(CreateCustomFormsPortletKeys.SuccessStoryType)) { //SuccessStoryType
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					renderRequest.setAttribute("SuccessStoryType", helper.toList(jaRes));
				}
				//Transportation Module
				else if(joa.get("name").equals(CreateCustomFormsPortletKeys.CarMake)) {
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					renderRequest.setAttribute("CarMake", helper.toList(jaRes));
				}else if(joa.get("name").equals(CreateCustomFormsPortletKeys.FuelType)) {
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					renderRequest.setAttribute("FuelType", helper.toList(jaRes));
				}else if(joa.get("name").equals(CreateCustomFormsPortletKeys.VehicleClassSize)) {
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					renderRequest.setAttribute("VehicleClassSize", helper.toList(jaRes));
				}else if(joa.get("name").equals(CreateCustomFormsPortletKeys.SedanSubClass)) {
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					renderRequest.setAttribute("SedanSubClass", helper.toList(jaRes));
				}else if(joa.get("name").equals(CreateCustomFormsPortletKeys.StationWagonSubClass)) {
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					renderRequest.setAttribute("StationWagonSubClass", helper.toList(jaRes));
				}else if(joa.get("name").equals(CreateCustomFormsPortletKeys.PickUpTruckSubClass)) {
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					renderRequest.setAttribute("PickUpTruckSubClass", helper.toList(jaRes));
				}else if(joa.get("name").equals(CreateCustomFormsPortletKeys.VanSubClass)) {
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					renderRequest.setAttribute("VanSubClass", helper.toList(jaRes));
				}else if(joa.get("name").equals(CreateCustomFormsPortletKeys.Transmission)) {
					JSONArray joOptions = new JSONArray(joa.get("options").toString());
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j);
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					renderRequest.setAttribute("Transmission", helper.toList(jaRes));
				}
			}
		}
	}
	
	public String getFilterData(ThemeDisplay themeDisplay, com.liferay.dynamic.data.mapping.kernel.DDMStructure struct, String fieldName, String value) throws DocumentException, PortalException {
		String title = "";
		JSONObject jo = new JSONObject(struct.getDefinition());
		JSONArray ja = new JSONArray(jo.get("fields").toString());
		 
		for (int i = 0; i < ja.length(); i++) {
			JSONObject joa = ja.getJSONObject(i);
			if (joa.get("type").equals("select")) {
				if (fieldName.equalsIgnoreCase(joa.getString("name"))) {
					JSONArray joOptions = new JSONArray(joa.get("options").toString());
					for (int j = 0; j < joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j);
						if(value.equalsIgnoreCase(option.getString("value"))) {
							title = String.valueOf(new JSONObject(option.get("label").toString()).get("en_US"));
						}
					}  
				} 	
			}
		}
		return title;
	}
}