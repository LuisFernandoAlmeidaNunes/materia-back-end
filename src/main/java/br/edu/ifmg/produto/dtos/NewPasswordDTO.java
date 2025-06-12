package br.edu.ifmg.produto.dtos;

import jakarta.validation.constraints.NotBlank;

public class NewPasswordDTO {

    @NotBlank(message = "Campo requerido")
    private String newPassword;
    private String token;

    public NewPasswordDTO() {}

    public NewPasswordDTO(String newPassword, String token) {
        this.newPassword = newPassword;
        this.token = token;
    }

    public @NotBlank(message = "Campo requerido") String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(@NotBlank(message = "Campo requerido") String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
