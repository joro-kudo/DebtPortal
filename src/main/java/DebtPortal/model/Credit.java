package DebtPortal.model;

import DebtPortal.data.DataHandler;
import DebtPortal.model.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.ws.rs.FormParam;
import java.math.BigDecimal;

/**
 * a credit in the creditshelf
 */
@Data
public class Credit {
    @JsonIgnore
    private Person person;

    @FormParam("creditUUID")
    private String creditUUID;

    @FormParam("message")
    private String message;

    @FormParam("price")
    private BigDecimal price;


    /**
     * gets the creditorUUID from the Person-object
     * @return
     */
    public String getPersonUUID() {
        return getPerson().getPersonUUID();
    }

    /**
     * creates a Person-object without the creditlist
     * @param personUUID
     */
    public void setPersonUUID(String personUUID) {
        setPerson(new Person());
        Person person = DataHandler.readPersonByUUID(personUUID);
        getPerson().setPersonUUID(personUUID);
        getPerson().setPersonName(person.getPersonName());

    }


}
