package DebtPortal.service;

import DebtPortal.data.DataHandler;
import DebtPortal.model.Debt;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("debt")
public class DebtService {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listDebts() {
        List<Debt> debtList = DataHandler.getInstance().readAllDebts();
        return Response
                .status(200)
                .entity(debtList)
                .build();
    }


    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readDebt(
            @QueryParam("uuid") String debtUUID
    ) {
        int httpStatus = 200;
        Debt debt = DataHandler.getInstance().readDebtByUUID(debtUUID);
        if (debt == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(debt)
                .build();
    }
}
