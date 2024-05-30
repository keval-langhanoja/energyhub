package reportsModule.helper;

import org.json.JSONArray;
import org.json.JSONObject;

public enum UserTypes {
	Administrator(1), 
	EnergyHubUser(6), 
	EntrepreneurUser(7), 
	ResearcherUser(8), 
	EnergyCompanyUser(9), 
	IndustryUser(10),
	InvestorUser(11), 
	Other(12), 
	PolicyMakerUser(14), 
	AcademicUser(13);

	private int value;

	private UserTypes(int value) {
		this.value = value;
	}

	public static String getUserTypeName(String code) {
		for (UserTypes e : UserTypes.values()) {
			if (e.value == Integer.valueOf(code))
				return e.name();
		}
		return null;
	}

	public static String getUserTypeValue(String code) {
		for (UserTypes e : UserTypes.values()) {
			if (e.name().equals(code))
				return String.valueOf(e.value);
		}
		return null;
	}
	 
 
	public int getValue() {
		return value;
	}

	public static JSONArray valuesList() {
		JSONArray roles = new JSONArray();
		for (int i = 0; i < UserTypes.values().length; i++) {
			JSONObject role = new JSONObject();
			role.put("id", UserTypes.values()[i].getValue());
			role.put("value", UserTypes.getUserTypeName(String.valueOf(UserTypes.values()[i].getValue())));
			roles.put(role);
		}
		return roles;
	}
}
