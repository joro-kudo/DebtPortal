package DebtPortal.service;


import DebtPortal.data.DataHandler;
import DebtPortal.model.Person;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

/**
 * services for reading, adding, changing and deleting persons
 */
@Path("person")
public class PersonService {

    /**
     * reads a list of all persons
     * @return  persons as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPersons() {
        List<Person> personList = DataHandler.readAllPeople();
        return Response
                .status(200)
                .entity(personList)
                .build();
    }

    /**
     * reads a person identified by the uuid
     * @param personUUID  the key
     * @return person
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readPerson(
            @QueryParam("uuid") String personUUID
    ) {
        int httpStatus = 200;
        Person person = DataHandler.readPersonByUUID(personUUID);
        if (person == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(person)
                .build();
    }


    /**
     * inserts a new person
     * @param name the name of the person
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertPerson(
            @FormParam("name") String name
    ) {
        Person person = new Person();
        person.setPersonUUID(UUID.randomUUID().toString());
        person.setPersonName(name);

        DataHandler.insertPerson(person);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a person
     * @param personUUID the key
     * @param name the name of the person
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePerson(
            @FormParam("uuid") String personUUID,
            @FormParam("name") String name
    ) {
        int httpStatus = 200;
        Person person = DataHandler.readPersonByUUID(personUUID);
        if (person != null) {
            person.setPersonName(name);

            DataHandler.updatePerson();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a person identified by its uuid
     * @param personUUID  the key
     * @return  Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletePerson(
            @QueryParam("uuid") String personUUID
    ) {
        int httpStatus = 200;
        if (!DataHandler.deletePerson(personUUID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}
