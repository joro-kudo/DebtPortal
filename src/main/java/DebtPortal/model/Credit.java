package DebtPortal.model;

import DebtPortal.data.DataHandler;
import DebtPortal.model.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

/**
 * a credit in the creditshelf
 */
public class Credit {
    @JsonIgnore
    private Person person;

    private String creditUUID;
    private String message;
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
        Person person = DataHandler.getInstance().readPersonByUUID(personUUID);
        getPerson().setPersonUUID(personUUID);
        getPerson().setPerson(person.getPerson());

    }

    /**
     * gets person
     *
     * @return value of person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * sets person
     *
     * @param person the value to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * gets creditUUID
     *
     * @return value of creditUUID
     */

    public String getCreditUUID() {
        return creditUUID;
    }

    /**
     * sets creditUUID
     *
     * @param creditUUID the value to set
     */

    public void setCreditUUID(String creditUUID) {
        this.creditUUID = creditUUID;
    }

    /**
     * gets message
     *
     * @return value of message
     */

    public String getMessage() {
        return message;
    }

    /**
     * sets message
     *
     * @param message the value to set
     */

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * gets price
     *
     * @return value of price
     */

    public BigDecimal getPrice() {
        return price;
    }

    /**
     * sets price
     *
     * @param price the value to set
     */

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


}
