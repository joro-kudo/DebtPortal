package DebtPortal.data;

import DebtPortal.model.Credit;
import DebtPortal.model.Debt;
import DebtPortal.model.Person;
import DebtPortal.model.User;
import DebtPortal.service.Config;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public final class DataHandler {
    private static List<Debt> debtList;
    private static List<Credit> creditList;

    private static List<Person> personList;
    private static List<User> userList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
    }

    /**
     * initialize the lists with the data
     */
    public static void initLists() {
        DataHandler.setCreditList(null);

        DataHandler.setPersonList(null);
        DataHandler.setDebtList(null);

    }

    /**
     * reads all debts
     * @return list of debts
     */
    public static List<Debt> readAllDebts() {
        return getDebtList();
    }

    /**
     * reads a debt by its uuid
     * @param debtUUID
     * @return the Debt (null=not found)
     */
    public static Debt readDebtByUUID(String debtUUID) {
        Debt debt = null;
        for (Debt entry : getDebtList()) {
            if (entry.getDebtUUID().equals(debtUUID)) {
                debt = entry;
            }
        }
        return debt;
    }

    /**
     * inserts a new debt into the debtList
     *
     * @param debt the debt to be saved
     */
    public static void insertDebt(Debt debt) {
        getDebtList().add(debt);
        writeDebtJSON();
    }

    /**
     * updates the debtList
     */
    public static void updateDebt() {
        writeDebtJSON();
    }

    /**
     * deletes a debt identified by the debtUUID
     * @param debtUUID  the key
     * @return  success=true/false
     */
    public static boolean deleteDebt(String debtUUID) {
        Debt debt = readDebtByUUID(debtUUID);
        if (debt != null) {
            getDebtList().remove(debt);
            writeDebtJSON();
            return true;
        } else {
            return false;
        }
    }
/**
     * reads all credits
     * @return list of credits
     */
    public static List<Credit> readAllCredits() {
        return getCreditList();
    }

    /**
     * reads a credit by its uuid
     * @param creditUUID
     * @return the Credit (null=not found)
     */
    public static Credit readCreditByUUID(String creditUUID) {
        Credit credit = null;
        for (Credit entry : getCreditList()) {
            if (entry.getCreditUUID().equals(creditUUID)) {
                credit = entry;
                return credit;
            }
        }
        return credit;
    }

    /**
     * inserts a new credit into the creditList
     *
     * @param credit the credit to be saved
     */
    public static void insertCredit(Credit credit) {
        getCreditList().add(credit);
        writeCreditJSON();
    }

    /**
     * updates the creditList
     */
    public static void updateCredit() {
        writeCreditJSON();
    }

    /**
     * deletes a credit identified by the creditUUID
     * @param creditUUID  the key
     * @return  success=true/false
     */
    public static boolean deleteCredit(String creditUUID) {
        Credit credit = readCreditByUUID(creditUUID);
        if (credit != null) {
            getCreditList().remove(credit);
            writeCreditJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads all people
     * @return list of debts
     */
    public static List<Person> readAllPeople() {
        return getPersonList();
    }

    /**
     * reads a person by its uuid
     * @param personUUID
     * @return the Person (null=not found)
     */
    public static Person readPersonByUUID(String personUUID) {
        Person person = null;
        for (Person entry : getPersonList()) {
            if (entry.getPersonUUID().equals(personUUID)) {
                person = entry;
            }
        }
        return person;
    }

    /**
     * inserts a new person into the debtList
     *
     * @param person the person to be saved
     */
    public static void insertPerson(Person person) {
        getPersonList().add(person);
        writePersonJSON();
    }

    /**
     * updates the personList
     */
    public static void updatePerson() {
        writePersonJSON();
    }

    /**
     * deletes a person identified by the personUUID
     * @param personUUID  the key
     * @return  success=true/false
     */
    public static boolean deletePerson(String personUUID) {
        Person person = readPersonByUUID(personUUID);
        if (person != null) {
            getPersonList().remove(person);
            writePersonJSON();
            return true;
        } else {
            return false;
        }
    }
    /**
     * reads a user by the username/password provided
     *
     * @param username  the username
     * @param password  the password
     * @return user-object
     */
    public static User readUser(String username, String password) {
        User user = new User();
        for (User entry : getUserList()) {
            if (entry.getUsername().equals(username) &&
                    entry.getPassword().equals(password)) {
                user = entry;
            }
        }
        return user;
    }
/**
 * reads the role of the user
 * */
    public String readUserRole(String username, String password) {
        for (User user : getUserList()) {
            if (user.getUsername().equals(username) &&
                    user.getPassword().equals(password)) {
                return user.getUsername();
            }
        }
        return "guest";
    }

    /**
     * reads the debts from the JSON-file
     */
    private static void readDebtJSON() {
        try {
            String path = Config.getProperty("debtJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Debt[] debts = objectMapper.readValue(jsonData, Debt[].class);
            for (Debt debt : debts) {
                getDebtList().add(debt);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the debtList to the JSON-file
     */
    private static void writeDebtJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String debtPath = Config.getProperty("debtJSON");
        try {
            fileOutputStream = new FileOutputStream(debtPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getDebtList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the credits from the JSON-file
     */
    private static void readCreditJSON() {
        try {
            String path = Config.getProperty("creditJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Credit[] credits = objectMapper.readValue(jsonData, Credit[].class);
            for (Credit credit : credits) {
                getCreditList().add(credit);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the creditList to the JSON-file
     */
    private static void writeCreditJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String creditPath = Config.getProperty("creditJSON");
        try {
            fileOutputStream = new FileOutputStream(creditPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getCreditList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the people from the JSON-file
     */
    private static void readPersonJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("personJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Person[] people = objectMapper.readValue(jsonData, Person[].class);
            for (Person person : people) {
                getPersonList().add(person);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the personList to the JSON-file
     */
    private static void writePersonJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String debtPath = Config.getProperty("personJSON");
        try {
            fileOutputStream = new FileOutputStream(debtPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getPersonList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the users from the JSON-file
     */
    private static void readUserJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("userJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            User[] users = objectMapper.readValue(jsonData, User[].class);
            for (User user : users) {
                getUserList().add(user);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * gets debtList
     *
     * @return value of debtList
     */

    private static List<Debt> getDebtList() {

        if (debtList == null) {
            setDebtList(new ArrayList<>());
            readDebtJSON();
        }
        return debtList;
    }

    /**
     * sets debtList
     *
     * @param debtList the value to set
     */

    private static void setDebtList(List<Debt> debtList) {
        DataHandler.debtList = debtList;
    }


    /**
     * gets creditList
     *
     * @return value of creditList
     */

    private static List<Credit> getCreditList() {

        if (creditList == null) {
            setCreditList(new ArrayList<>());
            readCreditJSON();
        }
        return creditList;
    }

    /**
     * sets creditList
     *
     * @param creditList the value to set
     */

    private static void setCreditList(List<Credit> creditList) {
        DataHandler.creditList = creditList;
    }

    /**
     * gets personList
     *
     * @return value of personList
     */

    private static List<Person> getPersonList() {
        if (personList == null) {
            setPersonList(new ArrayList<>());
            readPersonJSON();
        }

        return personList;
    }

    /**
     * sets personList
     *
     * @param personList the value to set
     */

    private static void setPersonList(List<Person> personList) {
        DataHandler.personList = personList;
    }

    /**
     * gets userList
     *
     * @return value of userList
     */

    public static List<User> getUserList() {
        if (DataHandler.userList == null) {
            DataHandler.setUserList(new ArrayList<>());
            readUserJSON();
        }
        return userList;
    }

    /**
     * sets userList
     *
     * @param userList the value to set
     */

    public static void setUserList(List<User> userList) {
        DataHandler.userList = userList;
    }
}