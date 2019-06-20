package rs.travel.booking_with_ease.exceptions;

public class EntityNotEditableException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public EntityNotEditableException(String message) {
		super(message);
	}
	
}
