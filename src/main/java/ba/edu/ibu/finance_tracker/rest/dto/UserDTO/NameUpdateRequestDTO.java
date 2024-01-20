package ba.edu.ibu.finance_tracker.rest.dto.UserDTO;

public class NameUpdateRequestDTO {
    private String name;
    private String surname;

    public NameUpdateRequestDTO() {
    }

    public NameUpdateRequestDTO(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
