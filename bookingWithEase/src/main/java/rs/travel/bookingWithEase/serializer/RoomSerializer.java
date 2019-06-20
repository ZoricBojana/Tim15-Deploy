package rs.travel.bookingWithEase.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import rs.travel.bookingWithEase.model.Room;

public class RoomSerializer extends JsonSerializer<Room> {
	
	  @Override
	    public void serialize(Room room, 
	                          JsonGenerator jsonGenerator, 
	                          SerializerProvider serializerProvider) 
	                          throws IOException, JsonProcessingException {
	        
	        jsonGenerator.writeStartObject();
	        jsonGenerator.writeObjectField("roomId", room.getId());
	        jsonGenerator.writeNumberField("roomNumber", room.getRoomNumber());
	        jsonGenerator.writeNumberField("roomCapacity", room.getCapacity());
	        jsonGenerator.writeObjectField("hotelName", room.getHotel().getName());
	        jsonGenerator.writeObjectField("hotelAddress", room.getHotel().getAddress());
	        jsonGenerator.writeNumberField("roomNumber", room.getRoomNumber());
	        jsonGenerator.writeEndObject();
	    }
}
