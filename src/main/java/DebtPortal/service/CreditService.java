package DebtPortal.service;

import DebtPortal.data.DataHandler;
import DebtPortal.model.Credit;

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
 * services for reading, adding, changing and deleting credits
 */
@Path("credit")
public class CreditService {
    /**
     * reads a list of all credits
     *
     * @return credits as JSON
     */
    @RolesAllowed({"admin", "user"})
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listCredits(
            @CookieParam("userRole") String userRole

    ) {

        List<Credit> creditList;

        creditList = DataHandler.readAllCredits();


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
                .entity(creditList)
                .cookie(cookie)
                .build();
    }

    /**
     * reads a credit identified by the uuid
     *
     * @param creditUUID the key
     * @return credit
     */
    @RolesAllowed({"admin", "user"})

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readCredit(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String creditUUID,
            @CookieParam("userRole") String userRole

    ) {
        int httpStatus = 200;
        Credit credit = DataHandler.readCreditByUUID(creditUUID);
        if (credit == null) {
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
                .entity(credit)
                .cookie(cookie)
                .build();
    }

    /**
     * inserts a new credit
     *
     * @return Response
     */
    @RolesAllowed({"admin", "user"})

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertCredit(
            @Valid @BeanParam Credit credit,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("creditUUID") String creditUUID,

            @FormParam("debitorUUID") String debitorUUID,
            @FormParam("creditorUUID") String creditorUUID,
            @CookieParam("userRole") String userRole


    ) {
        int httpStatus = 200;
        if (creditUUID == null) {
            credit.setCreditUUID(UUID.randomUUID().toString());

        } else {
            credit.setCreditUUID(creditUUID);

        }
        credit.setDebitorUUID(debitorUUID);

        credit.setCreditorUUID(creditorUUID);
        DataHandler.insertCredit(credit);

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
     * updates a new credit
     *
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateCredit(
            @Valid @BeanParam Credit credit,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("debitorUUID") String debitorUUID,
            @FormParam("creditorUUID") String creditorUUID,
            @CookieParam("userRole") String userRole

    ) {
        int httpStatus = 200;

        Credit oldCredit = DataHandler.readCreditByUUID(credit.getCreditUUID());
        if (oldCredit != null) {
            oldCredit.setDescription(credit.getDescription());
            oldCredit.setPrice(credit.getPrice());
            oldCredit.setDebitorUUID(debitorUUID);
            oldCredit.setCreditorUUID(creditorUUID);

            DataHandler.updateCredit();
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
     * deletes a credit identified by its uuid
     *
     * @param creditUUID the key
     * @return Response
     */
    @RolesAllowed({"admin"})
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteCredit(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String creditUUID,
            @CookieParam("userRole") String userRole

    ) {
        int httpStatus = 200;
        if (userRole.equals("admin")) {
            if (!DataHandler.deleteCredit(creditUUID)) {
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