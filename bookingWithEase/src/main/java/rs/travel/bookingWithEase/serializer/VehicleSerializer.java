package rs.travel.bookingWithEase.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import rs.travel.bookingWithEase.model.Vehicle;

public class VehicleSerializer extends JsonSerializer<Vehicle> {

	@Override
	public void serialize(Vehicle vehicle, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("vehicleId", vehicle.getId());
        gen.writeObjectField("vehicleRegistration", vehicle.getRegistrationNumber());
        gen.writeObjectField("vehicleType", vehicle.getType());
        gen.writeObjectField("vehicleGear", vehicle.getGear());
        gen.writeObjectField("vehicleColor", vehicle.getColor());
        gen.writeObjectField("vehicleBrId", vehicle.getBranch().getId());
        gen.writeObjectField("vehicleRacId", vehicle.getBranch().getRac().getId());
        gen.writeObjectField("vehicleRacName", vehicle.getBranch().getRac().getName());
        gen.writeObjectField("vehicleRacAddress", vehicle.getBranch().getRac().getAddress());
        gen.writeObjectField("passengerNumber", vehicle.getPassenggers());
        gen.writeEndObject();
		
	}

}
