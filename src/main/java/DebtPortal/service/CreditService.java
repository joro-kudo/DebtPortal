package DebtPortal.service;

import DebtPortal.data.DataHandler;
import DebtPortal.model.Credit;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("credit")
public class CreditService {

    /**
     * reads a list of all credits
     * @return  credits as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listCredits() {
        List<Credit> creditList = DataHandler.readAllCredits();
        return Response
                .status(200)
                .entity(creditList)
                .build();
    }


    /**
     * reads a credit identified by the uuid
     * @param creditUUID the key
     * @return credit
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readCredit(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String creditUUID
    ) {
        int httpStatus = 200;
        Credit credit = DataHandler.readCreditByUUID(creditUUID);
        if (credit == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(credit)
                .build();
    }
    /**
     * inserts a new credit
     * @param personUUID the uuid of the person
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertCredit(
            @Valid @BeanParam Credit credit,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("personUUID") String personUUID
    ) {

        credit.setPersonUUID(personUUID);

        DataHandler.insertCredit(credit);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a new credit
     * @param personUUID the uuid of the person
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateCredit(
            @Valid @BeanParam Credit credit,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("personUUID") String personUUID
    ) {
        int httpStatus = 200;
        Credit oldCredit = DataHandler.readCreditByUUID(credit.getCreditUUID());
        if (oldCredit != null) {
            oldCredit.setMessage(credit.getMessage());
            oldCredit.setPersonUUID(personUUID);
            oldCredit.setPrice(credit.getPrice());

            DataHandler.updateCredit();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a credit identified by its uuid
     * @param creditUUID  the key
     * @return  Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteCredit(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String creditUUID
    ) {
        int httpStatus = 200;
        if (!DataHandler.deleteCredit(creditUUID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}
