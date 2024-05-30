package see.all.projects.constants;

public enum UserTypes {
	Administrator(0), 
	EnergyHubUser(0), 
	EntrepreneurUser(0), 
	ResearcherUser(0),
	EnergyCompanyUser(0),    
	IndustryUser(0),
	InvestorUser(0),
	PolicyMakerUser(0),
	CarDealerUser(0),
	ContentManagementUser(0),
    AcademicUser(0),
	AdministratorIAM(1), 
	EnergyHubUserIAM(6), 
	EntrepreneurUserIAM(7), 
	ResearcherUserIAM(8),
	EnergyCompanyUserIAM(9),    
	IndustryUserIAM(10),
	InvestorUserIAM(11),
	OtherIAM(12),
	PolicyMakerIAM(14),
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
