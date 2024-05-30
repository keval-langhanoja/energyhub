package create.account.constants;

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
	CarDealerUser(15),//97701
	AcademicUser(13); 
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
