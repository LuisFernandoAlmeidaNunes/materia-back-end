package br.edu.ifmg.produto.dtos;

public class UserInsertDTO extends UserDTO {

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserInsertDTO() {
        super();
    }
}
