package Enum;

public enum PhoneAreaCode {
	

	Cellcom_052,	Partner_050,	HotMobile_053;
	
	
	// Get Enum of phone area code and return the value in string:
	public static String GetStringValueFromEnum(PhoneAreaCode p) {
		if (p.equals(Cellcom_052))
			return "052";
		if (p.equals(Partner_050))
			return "050";
		if (p.equals(HotMobile_053))
			return "053";
		return "0";
	}
	
	// Get string of phone area code and return the value in Enum:
	public static PhoneAreaCode GetEnumValueFromString(String p) {
		if (p.equals("052"))
			return Cellcom_052;
		if (p.equals("050"))
			return Partner_050;
		if (p.equals("053"))
			return HotMobile_053;
		return null;
	}

}
