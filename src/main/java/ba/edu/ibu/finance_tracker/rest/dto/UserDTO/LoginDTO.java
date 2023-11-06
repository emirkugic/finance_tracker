package ba.edu.ibu.finance_tracker.rest.dto.UserDTO;

public class LoginDTO {
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public LoginDTO(String jwt) {
        this.jwt = jwt;
    }
}
