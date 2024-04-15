package dto;

import java.io.Serializable;
import java.time.LocalDate;

public class UserDto implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private LocalDate dob;
    private String phonenumber;

    public UserDto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getPhonenumber() {
        return phonenumber;
    }
}
