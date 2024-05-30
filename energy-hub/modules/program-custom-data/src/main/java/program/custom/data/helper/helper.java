package program.custom.data.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

public class helper {
	
	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
	    Map<String, Object> map = new HashMap<String, Object>();

	    Iterator<String> keysItr = object.keys();
	    while(keysItr.hasNext()) {
	        String key = keysItr.next();
	        Object value = object.get(key);
	        
	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }
	        
	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        map.put(key, value);
	    }
	    return map;
	}
	
	
	public static List<Object> toList(JSONArray array) throws JSONException {
	    List<Object> list = new ArrayList<Object>();
	    for(int i = 0; i < array.length(); i++) {
	        Object value = array.get(i);
	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        list.add(value);
	    }
	    return list;
	}
	
	public static boolean isEmpty(String str) {
		return str==null||str.trim().isEmpty();
	}

	public static String ifEmpty(String str, String defaultValue) {
		return isEmpty(str) ? defaultValue : str;
	}


	public static JSONArray getFieldGroupFromContentXml(JournalArticle article, Locale currentLocale, String nodeName) throws DocumentException
	{    
		JSONArray retVal = new JSONArray();
		Document document = SAXReaderUtil.read(article.getContentByLocale(LocaleUtil.toLanguageId(currentLocale)));
        List<Node> nodes = document.selectNodes("/root/dynamic-element[@name='"+nodeName+"']");
        for(Node descriptionNode : nodes) {
        	if(descriptionNode.getText() != null) {
        		JSONObject fieldData = new JSONObject();
        		String degreeType = descriptionNode.selectSingleNode("./dynamic-element[@name='SelectFromList75723891']").hasContent() ? 
        				descriptionNode.selectSingleNode("./dynamic-element[@name='SelectFromList75723891']/dynamic-content").getText() : "";
        		String attendance = descriptionNode.selectSingleNode("./dynamic-element[@name='SelectFromList52092032']").hasContent() ? 
        				descriptionNode.selectSingleNode("./dynamic-element[@name='SelectFromList52092032']/dynamic-content").getText() : "";
        		String energyPrograms = descriptionNode.selectSingleNode("./dynamic-element[@name='RichText80306402']").hasContent() ? 
        				descriptionNode.selectSingleNode("./dynamic-element[@name='RichText80306402']/dynamic-content").getText() : "";
        		String description = descriptionNode.selectSingleNode("./dynamic-element[@name='RichText48466122']").hasContent() ? 
        				descriptionNode.selectSingleNode("./dynamic-element[@name='RichText48466122']/dynamic-content").getText() : "";
        		String location = descriptionNode.selectSingleNode("./dynamic-element[@name='Text84722742']").hasContent() ? 
        				descriptionNode.selectSingleNode("./dynamic-element[@name='Text84722742']/dynamic-content").getText() : "";
				String links = descriptionNode.selectSingleNode("./dynamic-element[@name='Text45772616']").hasContent() ? 
						descriptionNode.selectSingleNode("./dynamic-element[@name='Text45772616']/dynamic-content").getText() : "";
        				
        		fieldData.put("degreeType", degreeType);		
        		fieldData.put("attendance", attendance);		
        		fieldData.put("energyPrograms", energyPrograms);		
        		fieldData.put("description", description);		
        		fieldData.put("location", location);	 	
        		fieldData.put("links", links);	 	
        		retVal.put(fieldData);
        	}
        } 
		return retVal;
	}

	public static JSONArray getFieldGroupFromContentXmlArea(JournalArticle article, Locale currentLocale, String nodeName) throws DocumentException
	{    
		JSONArray retVal = new JSONArray();
		Document document = SAXReaderUtil.read(article.getContentByLocale(LocaleUtil.toLanguageId(currentLocale)));
        List<Node> nodes = document.selectNodes("/root/dynamic-element[@name='"+nodeName+"']");
        for(Node descriptionNode : nodes) {
        	if(descriptionNode.getText() != null) {
        		JSONObject fieldData = new JSONObject();
        		String area = descriptionNode.selectSingleNode("./dynamic-element[@name='SelectFromList23403899']").hasContent() ? 
        				descriptionNode.selectSingleNode("./dynamic-element[@name='SelectFromList23403899']/dynamic-content").getText() : "";
        		String level = descriptionNode.selectSingleNode("./dynamic-element[@name='SelectFromList47700469']").hasContent() ? 
        				descriptionNode.selectSingleNode("./dynamic-element[@name='SelectFromList47700469']/dynamic-content").getText() : "";
        		
        		if(area.equalsIgnoreCase("Option42922869")) area = "CertifiedEnergyAuditor";
    			if(area.equalsIgnoreCase("Option94615156")) area = "CertifiedEnergyManager";
				if(area.equalsIgnoreCase("Option80843510")) area = "CertifiedMeasurementandVerificationProfessional";
				if(area.equalsIgnoreCase("Option67258359")) area = "SolarPVDesigner";
				if(area.equalsIgnoreCase("Option33294007")) area = "SkilledElectricityTechnician";
				if(area.equalsIgnoreCase("Option36898172")) area = "ARZAssessor";
				if(area.equalsIgnoreCase("Option21732006")) area = "LEEDGreenAssociate";
				if(area.equalsIgnoreCase("Option72012576")) area = "EDGEAuditor";
				if(area.equalsIgnoreCase("Option47028475")) area = "ProjectManagementProfessional";
				if(area.equalsIgnoreCase("Option27543967")) area = "GreenBuildingDesigner";
				if(area.equalsIgnoreCase("Option56769960")) area = "EnergyRetrofits";
				if(area.equalsIgnoreCase("Option39718996")) area = "FinancialModeler";
				if(area.equalsIgnoreCase("Option72280361")) area = "PerformanceContracting";
				if(area.equalsIgnoreCase("Option51390762")) area = "OperationandMaintenance";
				if(area.equalsIgnoreCase("Option22670074")) area = "ElectricalEngineering";
				if(area.equalsIgnoreCase("Option43479858")) area = "MechanicalEngineering";
				if(area.equalsIgnoreCase("Option94008998")) area = "Trainer";

				if(level.equalsIgnoreCase("Option42922869")) level = "Beginner";
				if(level.equalsIgnoreCase("Option58824923")) level = "Intermediate";
				if(level.equalsIgnoreCase("Option92357002")) level = "Professional";
				if(level.equalsIgnoreCase("Option56899373")) level = "Certified";
			
        		fieldData.put("area", area);		
        		fieldData.put("level", level);		  	
        		retVal.put(fieldData);
        	}
        } 
		return retVal;
	}
}
