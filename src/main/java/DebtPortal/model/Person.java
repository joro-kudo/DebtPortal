package DebtPortal.model;

/**
 * a debt person
 */
public class Person {
    private String personUUID;
    private String person;

    /**
     * gets personUUID
     *
     * @return value of personUUID
     */
    public String getPersonUUID() {
        return personUUID;
    }

    /**
     * sets personUUID
     *
     * @param personUUID the value to set
     */
    public void setPersonUUID(String personUUID) {
        this.personUUID = personUUID;
    }

    /**
     * gets person
     *
     * @return value of person
     */
    public String getPerson() {
        return person;
    }

    /**
     * sets person
     *
     * @param person the value to set
     */
    public void setPerson(String person) {
        this.person = person;
    }
}
