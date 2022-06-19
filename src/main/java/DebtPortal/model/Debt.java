package DebtPortal.model;

import DebtPortal.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * a debt in the debtshelf
 */
@Data
public class Debt {
    @JsonIgnore
    private Person person;

    private String debtUUID;
    private String message;
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
