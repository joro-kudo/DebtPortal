package DebtPortal.model;

import DebtPortal.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import java.math.BigDecimal;

/**
 * a credit in the creditshelf
 */
@Data
public class Credit {
    @JsonIgnore
    private Person debitor;
    @JsonIgnore
    private Person creditor;

    @FormParam("creditUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String creditUUID;

    @FormParam("description")
    @NotEmpty
    @Size(min = 5, max = 450)
    private String description;


    @FormParam("price")
    @NotNull
    @DecimalMax(value = "1999.95")
    @DecimalMin(value = "0.05")
    private BigDecimal price;


    /**
     * gets the UUIDs for all debitors of this book
     *
     * @return list of uuids
     */
    public String getDebitorUUID() {
        if (this.getDebitor() != null) {
            return this.getDebitor().getPersonUUID();
        }
        return null;
    }

    /**
     * sets the debitors from their UUIDs
     */
    public void setDebitorUUID(String uuid) {


        Person debitorr = DataHandler.readPersonByUUID(uuid);
        this.setDebitor(debitorr);

    }


    /**
     * gets the UUIDs for all debitors of this book
     *
     * @return list of uuids
     */
    public String getCreditorUUID() {
        if (this.getCreditor() != null) {
            return this.getCreditor().getPersonUUID();
        }
        return null;
    }


    /**
     * sets the debitors from their UUIDs
     */
    public void setCreditorUUID(String uuid) {


        Person creditorr = DataHandler.readPersonByUUID(uuid);
        this.setCreditor(creditorr);

    }

    /**
     * gets allthe creditors of a book
     * @return all creditor names as comma separated string
     */


}
