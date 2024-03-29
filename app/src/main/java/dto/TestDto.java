package dto;

import com.google.gson.annotations.SerializedName;

public class TestDto {
    @SerializedName("type")
    private String type;

    @SerializedName("setup")
    private String setup;

    @SerializedName("punchline")
    private String punchline;

    @SerializedName("id")
    private int id;

    @Override
    public String toString() {
        return "Test{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", setup='" + setup + '\'' +
                ", punchline='" + punchline + '\'' +
                '}';
    }

    public TestDto(Integer id, String type, String setup, String punchline) {
        this.id = id;
        this.type = type;
        this.setup = setup;
        this.punchline = punchline;
    }

    public TestDto() {
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSetup() {
        return setup;
    }

    public String getPunchline() {
        return punchline;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public void setPunchline(String punchline) {
        this.punchline = punchline;
    }
}
