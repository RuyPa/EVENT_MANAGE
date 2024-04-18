package dto;

import java.io.Serializable;

public class EventCategoryDto implements Serializable {
    private Integer id;
    private CategoryDto categoryDto;

    public Integer getId() {
        return id;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }
}
