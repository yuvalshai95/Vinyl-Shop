package Enum;

public enum City {
	
	Eilat,Natanya,Emek_Yeerzel,Tel_Aviv,Ramat_Yishai,Kiriat_Yam;
	
	// Get Enum of city and return the value in string:
//	public static String GetStringValueFromEnum(City c) {
//		if (c.equals(Eilat))
//			return "Eilat";
//		if (c.equals(Natanya))
//			return "Natanya";
//		if (c.equals(Emek_Yeerzel))
//			return "Emek_Yeerzel";
//		if (c.equals(Tel_Aviv))
//			return "Tel_Aviv";
//		if (c.equals(Ramat_Yishai))
//			return "Ramat_Yishai";
//		if (c.equals(Kiriat_Yam))
//			return "Kiriat_Yam";
//		return "0";
//	}
	
	// Get Enum of city and return the value in string
	public static String GetStringValueFromEnum(City c) {
		if (c.equals(Eilat))
			return "Eilat";
		if (c.equals(Natanya))
			return "Natanya";
		if (c.equals(Emek_Yeerzel))
			return "Emek Yeezrel";
		if (c.equals(Tel_Aviv))
			return "Tel Aviv";
		if (c.equals(Ramat_Yishai))
			return "Ramat Yishai";
		if (c.equals(Kiriat_Yam))
			return "Kiriat Yam";
		return "0";
	}
	
	// Get string of city and return the value in Enum:
	public static City GetEnumValueFromString(String p) {
		if (p.equals("Eilat"))
			return Eilat;
		if (p.equals("Natanya"))
			return Natanya;
		if (p.equals("Emek Yeerzel"))
			return Emek_Yeerzel;
		if (p.equals("Tel Aviv"))
			return Tel_Aviv;
		if (p.equals("Ramat Yishai"))
			return Ramat_Yishai;
		if (p.equals("Kiriat Yam"))
			return Kiriat_Yam;
		return null;
	}

}
