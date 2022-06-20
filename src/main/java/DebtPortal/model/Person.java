package DebtPortal.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

/**
 * a debitor/creditor person
 */
@Data
public class Person {
    @FormParam("personUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String personUUID;
    @FormParam("personName")
    @NotEmpty
    @Size(min=4, max=50)
    private String personName;
}
