package DebtPortal.model;

import lombok.*;

@Data
public class User {
    private String userUUID;
    private String username;
    private String password;
    private String role;

}
