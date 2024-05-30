package comments.management.helper;

public enum UserTypes {
	AdministratorRole(1), 
	EnergyHubUserRole(6), 
	EntrepreneurUserRole(7), 
	ResearcherUserRole(8),
	EnergyCompanyUserRole(9),    
	IndustryUserRole(10),
	InvestorUserRole(11),
	Other(12),
	PolicyMakerRole(14),
	AcademicUserRole(13); 
	  
	private int value;  
	private UserTypes(int value){  
	this.value=value;  
	}  
	
	
	public static String getUserTypeName(String code){
		for(UserTypes e : UserTypes.values()){
		    if(e.value== Integer.valueOf(code)) return e.name();
		}
		return null;
	}
 
	public static String getUserTypeValue(String code){
		for(UserTypes e : UserTypes.values()){
		    if(e.name().equals(code)) return String.valueOf(e.value);
		}
		return null;
	}
}
