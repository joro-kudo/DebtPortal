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
 * a debt in the debtshelf
 */
@Data
public class Debt {
    @JsonIgnore
    private Person person;

    @FormParam("debtUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String debtUUID;

    @FormParam("description")
    @NotEmpty
    @Size(min=5, max=450)
    private String description;


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
     * creates a Person-object without the debtlist
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
