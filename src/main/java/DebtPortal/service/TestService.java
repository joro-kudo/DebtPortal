package DebtPortal.service;


import DebtPortal.data.DataHandler;
import DebtPortal.service.Config;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * test service
 */
@Path("test")
public class TestService {

    /**
     * confirms the application runs
     * @return  message
     */
    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {

        return Response
                .status(200)
                .entity("Test erfolgreich")
                .build();
    }

    /**
     * restores the json-files
     * @return Response
     */
    @GET
    @Path("restore")
    @Produces(MediaType.TEXT_PLAIN)
    public Response restore() {
        try {
            java.nio.file.Path path = Paths.get(Config.getProperty("creditJSON"));
            String filename = path.getFileName().toString();
            String folder = path.getParent().toString();

            byte[] creditJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
            FileOutputStream fileOutputStream = new FileOutputStream(Config.getProperty("creditJSON"));
            fileOutputStream.write(creditJSON);

            path = Paths.get(Config.getProperty("debtJSON"));
             filename = path.getFileName().toString();
             folder = path.getParent().toString();

            byte[] debtJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
             fileOutputStream = new FileOutputStream(Config.getProperty("debtJSON"));
            fileOutputStream.write(debtJSON);

            path = Paths.get(Config.getProperty("personJSON"));
            filename = path.getFileName().toString();
            folder = path.getParent().toString();

            byte[] personJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
            fileOutputStream = new FileOutputStream(Config.getProperty("personJSON"));
            fileOutputStream.write(personJSON);

        } catch (IOException e) {
            e.printStackTrace();
        }

        DataHandler.initLists();
        return Response
                .status(200)
                .entity("Erfolgreich")
                .build();
    }
}

