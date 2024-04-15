package dto;

import java.io.Serializable;

public class CategoryDto implements Serializable {
    private Integer id;
    private String name;
    private String des;

    public CategoryDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDes() {
        return des;
    }
}
