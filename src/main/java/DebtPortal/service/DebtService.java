package DebtPortal.service;

import DebtPortal.data.DataHandler;
import DebtPortal.model.Debt;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

/**
 * services for reading, adding, changing and deleting debts
 */
@Path("debt")
public class DebtService {
    /**
     * reads a list of all debts
     *
     * @return debts as JSON
     */
    @RolesAllowed({"admin", "user"})
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listDebts(
            @CookieParam("userRole") String userRole,
            @Pattern(regexp = "^description|price|debitor|creditor")
            @QueryParam("sortField") String sortField,
            @QueryParam("filterField") String filterField,
            @QueryParam("filter") String filter,
            @QueryParam("sort") String sortOrder
    ) {

        List<Debt> debtList;
        if (sortField != null && sortOrder != null) {
            debtList = DataHandler.readSortedDebts(sortField, sortOrder, filterField, filter);
        } else if (sortField != null && filter != null) {
            debtList = DataHandler.readFilteredDebts(filterField, filter);
        } else {
            debtList = DataHandler.readAllDebts();
        }

        NewCookie cookie = new NewCookie(
                "userRole",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        return Response
                .status(200)
                .entity(debtList)
                .cookie(cookie)
                .build();
    }

    /**
     * reads a debt identified by the uuid
     *
     * @param debtUUID the key
     * @return debt
     */
    @RolesAllowed({"admin", "user"})

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readDebt(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String debtUUID,
            @CookieParam("userRole") String userRole

    ) {
        int httpStatus = 200;
        Debt debt = DataHandler.readDebtByUUID(debtUUID);
        if (debt == null) {
            httpStatus = 410;
        }
        NewCookie cookie = new NewCookie(
                "userRole",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        return Response
                .status(httpStatus)
                .entity(debt)
                .cookie(cookie)
                .build();
    }

    /**
     * inserts a new debt
     *
     * @return Response
     */
    @RolesAllowed({"admin", "user"})

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertDebt(
            @Valid @BeanParam Debt debt,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("debtUUID") String debtUUID,

            @FormParam("debitorUUID") String debitorUUID,
            @FormParam("creditorUUID") String creditorUUID,
            @CookieParam("userRole") String userRole


    ) {
        int httpStatus = 200;
        if (debtUUID == null) {
            debt.setDebtUUID(UUID.randomUUID().toString());

        } else {
            debt.setDebtUUID(debtUUID);

        }
        debt.setDebitorUUID(debitorUUID);

        debt.setCreditorUUID(creditorUUID);
        DataHandler.insertDebt(debt);

        NewCookie cookie = new NewCookie(
                "userRole",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );

        return Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
    }

    /**
     * updates a new debt
     *
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateDebt(
            @Valid @BeanParam Debt debt,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("debitorUUID") String debitorUUID,
            @FormParam("creditorUUID") String creditorUUID,
            @CookieParam("userRole") String userRole

    ) {
        int httpStatus = 200;

        Debt oldDebt = DataHandler.readDebtByUUID(debt.getDebtUUID());
        if (oldDebt != null) {
            oldDebt.setDescription(debt.getDescription());
            oldDebt.setPrice(debt.getPrice());
            oldDebt.setDebitorUUID(debitorUUID);
            oldDebt.setCreditorUUID(creditorUUID);

            DataHandler.updateDebt();
        } else {
            httpStatus = 410;
        }

        NewCookie cookie = new NewCookie(
                "userRole",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        return Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
    }

    /**
     * deletes a debt identified by its uuid
     *
     * @param debtUUID the key
     * @return Response
     */
    @RolesAllowed({"admin"})
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteDebt(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String debtUUID,
            @CookieParam("userRole") String userRole

    ) {
        int httpStatus = 200;
        if (userRole.equals("admin")) {
            if (!DataHandler.deleteDebt(debtUUID)) {
                httpStatus = 410;
            }
        } else {
            httpStatus = 403;
        }

        NewCookie cookie = new NewCookie(
                "userRole",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        return Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
    }
}