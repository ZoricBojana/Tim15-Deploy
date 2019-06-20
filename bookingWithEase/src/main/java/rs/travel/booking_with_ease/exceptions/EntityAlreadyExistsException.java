package rs.travel.booking_with_ease.exceptions;

public class EntityAlreadyExistsException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public EntityAlreadyExistsException(String message) {
		super(message);
	}
	
}
