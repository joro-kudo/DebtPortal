package DebtPortal.service;

import DebtPortal.data.DataHandler;
import DebtPortal.model.Person;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("person")
public class PersonService {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPeople() {
        List<Person> personList = DataHandler.getInstance().readAllPeople();
        return Response
                .status(200)
                .entity(personList)
                .build();
    }


    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readPerson(
            @QueryParam("uuid") String personUUID
    ) {
        int httpStatus = 200;
        Person person = DataHandler.getInstance().readPersonByUUID(personUUID);
        if (person == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(person)
                .build();
    }
}
