package br.edu.ifmg.produto.services;

import br.edu.ifmg.produto.dtos.EmailDTO;
import br.edu.ifmg.produto.dtos.NewPasswordDTO;
import br.edu.ifmg.produto.dtos.RequestTokenDTO;
import br.edu.ifmg.produto.entities.PasswordRecover;
import br.edu.ifmg.produto.entities.User;
import br.edu.ifmg.produto.repositories.PasswordPrecoverRepository;
import br.edu.ifmg.produto.repositories.UserRepository;
import br.edu.ifmg.produto.services.exceptions.ResourceNotFound;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${email.password-recover.token.minutes}")
    private int tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String uri;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordPrecoverRepository passwordPrecoverRepository;

    @Autowired
    private UserRepository userRepository;

    public void createRecoverToken(RequestTokenDTO dto) {

        User user = userRepository.findByEmail(dto.getEmail());

        if (user == null) {
            throw new ResourceNotFound("Email not found");
        }

        String token = UUID.randomUUID().toString();

        PasswordRecover passwordRecover = new PasswordRecover();
        passwordRecover.setToken(token);
        passwordRecover.setEmail(user.getEmail());
        passwordRecover.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60));
        passwordPrecoverRepository.save(passwordRecover);

        String body = "Acesse o link para definir uma nova senha (Válido por "+ tokenMinutes + ")" + "\n\n " + uri + token;

        emailService.sendEmail(new EmailDTO(user.getEmail(), "Recuperacao de senha", body));
    }

    public void saveNewPassword(@Valid NewPasswordDTO dto) {



    }
}
