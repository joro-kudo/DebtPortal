package DebtPortal.service;


import DebtPortal.data.DataHandler;
import DebtPortal.model.Person;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

/**
 * services for reading, adding, changing and deleting people
 */
@Path("person")
public class PersonService {

    /**
     * reads a list of all people
     *
     * @param fieldname the name of the field to be filtered or sorted
     * @param filter    the filter to be applied (null=no filter)
     * @param sortOrder the sorting order (null=unsorted)
     * @return people as JSON
     */
    @RolesAllowed({"admin", "user"})
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPeople(
            @Pattern(regexp = "^personName|personUUID")
            @QueryParam("field") String fieldname,
            @QueryParam("filter") String filter,
            @QueryParam("sort") String sortOrder
    ) {
        List<Person> personList;
        if (fieldname != null && sortOrder != null) {
            personList = DataHandler.readSortedPeople(fieldname, filter, sortOrder);
        } else if (fieldname != null && filter != null) {
            personList = DataHandler.readFilteredPeople(fieldname, filter);
        } else {
            personList = DataHandler.readAllPeople();
        }
        return Response
                .status(200)
                .entity(personList)
                .build();
    }

    /**
     * reads a person identified by the uuid
     *
     * @param personUUID the key
     * @return person
     */
    @RolesAllowed({"admin", "user"})
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readPerson(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
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
     *
     * @param name the name of the person
     * @return Response
     */
    @RolesAllowed({"admin", "user"})
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
     *
     * @param person a valid person-object
     * @return Response
     */
    @RolesAllowed({"admin", "user"})
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePerson(
            @Valid @BeanParam Person person
    ) {
        int httpStatus = 200;
        Person oldPerson = DataHandler.readPersonByUUID(person.getPersonUUID());
        if (oldPerson != null) {
            oldPerson.setPersonName(person.getPersonName());

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
     *
     * @param personUUID the key
     * @return Response
     */
    @RolesAllowed({"admin"})
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