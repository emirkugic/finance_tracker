package ba.edu.ibu.finance_tracker.rest.dto.UserDTO;

public class EmailUpdateResponseDTO {

    private String id;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailUpdateResponseDTO(String id, String email) {
        this.id = id;
        this.email = email;
    }

}