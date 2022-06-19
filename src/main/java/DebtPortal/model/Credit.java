package DebtPortal.model;

import DebtPortal.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * a credit in the creditshelf
 */
@Data
public class Credit {
    @JsonIgnore
    private Person person;

    @FormParam("creditUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String creditUUID;

    @FormParam("message")
    @NotEmpty
    @Size(min=1, max=450)
    private String message;


    @FormParam("price")
    @NotNull
    @DecimalMax(value="1999.95")
    @DecimalMin(value="0.05")
    private BigDecimal price;




    /**
     * gets the personUUID from the Person-object
     *
     * @return
     */
    public String getPersonUUID() {
        if (getPerson()== null) return null;

        return getPerson().getPersonUUID();
    }

    /**
     * creates a Person-object without the creditlist
     *
     * @param personUUID
     */
    public void setPersonUUID(String personUUID) {
        setPerson(new Person());
        Person person = DataHandler.readPersonByUUID(personUUID);
        getPerson().setPersonUUID(personUUID);
        getPerson().setPersonName(person.getPersonName());

    }



}
