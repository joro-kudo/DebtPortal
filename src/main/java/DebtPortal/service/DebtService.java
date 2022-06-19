package DebtPortal.service;

import DebtPortal.data.DataHandler;
import DebtPortal.model.Debt;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * services for reading, adding, changing and deleting debts
 */
@Path("debt")
public class DebtService {

    /**
     * reads a list of all debts
     * @return  debts as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listDebts() {
        List<Debt> debtList = DataHandler.readAllDebts();
        return Response
                .status(200)
                .entity(debtList)
                .build();
    }

    /**
     * reads a debt identified by the uuid
     * @param debtUUID the key
     * @return debt
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readDebt(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String debtUUID
    ) {
        int httpStatus = 200;
        Debt debt = DataHandler.readDebtByUUID(debtUUID);
        if (debt == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(debt)
                .build();
    }

    /**
     * inserts a new debt
     * @param personUUID the uuid of the person
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertDebt(
            @Valid @BeanParam Debt debt,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("personUUID") String personUUID
    ) {

        debt.setPersonUUID(personUUID);

        DataHandler.insertDebt(debt);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a new debt
     * @param personUUID the uuid of the person
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateDebt(
            @Valid @BeanParam Debt debt,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("personUUID") String personUUID
    ) {
        int httpStatus = 200;
        Debt oldDebt = DataHandler.readDebtByUUID(debt.getDebtUUID());
        if (oldDebt != null) {
            oldDebt.setMessage(debt.getMessage());
            oldDebt.setPersonUUID(personUUID);
            oldDebt.setPrice(debt.getPrice());

            DataHandler.updateDebt();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a debt identified by its uuid
     * @param debtUUID  the key
     * @return  Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteDebt(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String debtUUID
    ) {
        int httpStatus = 200;
        if (!DataHandler.deleteDebt(debtUUID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}