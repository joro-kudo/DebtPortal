package DebtPortal.service;

import DebtPortal.data.DataHandler;
import DebtPortal.model.Credit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * services for reading, adding, changing and deleting credits
 */
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
        List<Credit> creditList = DataHandler.getInstance().readAllCredits();
        return Response
                .status(200)
                .entity(creditList)
                .build();
    }

    /**
     * reads a credit identified by the uuid
     * @param creditUUID
     * @return credit
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readCredit(
            @QueryParam("uuid") String creditUUID
    ) {
        int httpStatus = 200;
        Credit credit = DataHandler.getInstance().readCreditByUUID(creditUUID);
        if (credit == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(credit)
                .build();
    }
}
