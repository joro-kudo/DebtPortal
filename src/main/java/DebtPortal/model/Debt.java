package DebtPortal.model;

import DebtPortal.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

/**
 * a debt in the debtshelf
 */
public class Debt {
    @JsonIgnore
    private Person person;

    private String debtUUID;
    private String message;
    private BigDecimal price;


    /**
     * gets the personUUID from the Person-object
     * @return
     */
    public String getPersonUUID() {
        return getPerson().getPersonUUID();
    }

    /**
     * creates a Person-object without the debtlist
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
     * gets debtUUID
     *
     * @return value of debtUUID
     */

    public String getDebtUUID() {
        return debtUUID;
    }

    /**
     * sets debtUUID
     *
     * @param debtUUID the value to set
     */

    public void setDebtUUID(String debtUUID) {
        this.debtUUID = debtUUID;
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
