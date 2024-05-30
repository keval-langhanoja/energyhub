package manage.user.projects.helper;

public enum UserTypes { 
	AdministratorIAM(1), 
	EnergyHubUserIAM(6), 
	EntrepreneurUserIAM(7), 
	ResearcherUserIAM(8),
	EnergyCompanyUserIAM(9),    
	IndustryUserIAM(10),
	InvestorUserIAM(11),
	OtherIAM(12),
	PolicyMakerUserIAM(14),
	CarDealerUserIAM(15),
	AcademicUserIAM(13);
	
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
