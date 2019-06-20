package rs.travel.bookingWithEase.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import rs.travel.bookingWithEase.model.Flight;

public class FlightSerializer extends JsonSerializer<Flight>{

	@Override
	public void serialize(Flight flight, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
        gen.writeObjectField("flightId", flight.getId());
        gen.writeObjectField("flightStartDestination", flight.getStartDestination().getName());
        gen.writeObjectField("flightEndDestination", flight.getEndDestination().getName());

        gen.writeObjectField("flightAirlineId", flight.getAirline().getId());
        gen.writeObjectField("flightAirlineName", flight.getAirline().getName());
        gen.writeObjectField("flightAirlineAddress",flight.getAirline().getAddress());
        gen.writeEndObject();
	}

}
