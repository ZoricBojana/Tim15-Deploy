package rs.travel.bookingWithEase.model;

public enum ADMIN_TYPE {
	SYSTEM, AIRLINE, HOTEL, RENT_A_CAR;

	public static ADMIN_TYPE strToAdmin(String type) {
		switch (type) {
		case "sistem":
			return SYSTEM;
		case "airline":
			return AIRLINE;
		case "hotel":
			return HOTEL;
		case "rent-a-car":
			return RENT_A_CAR;
		default:
			return null;
		}
	}
}
