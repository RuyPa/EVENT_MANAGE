package dto;

import java.io.Serializable;
import java.time.LocalDate;

public class UserDto implements Serializable {
    private String id;
    private String username;
    private String password;
    private String name;
    private LocalDate dob;
    private String phonenumber;
}
