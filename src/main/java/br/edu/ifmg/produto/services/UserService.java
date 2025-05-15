package br.edu.ifmg.produto.services;

import br.edu.ifmg.produto.dtos.RoleDTO;
import br.edu.ifmg.produto.dtos.UserDTO;
import br.edu.ifmg.produto.dtos.UserInsertDTO;
import br.edu.ifmg.produto.entities.Role;
import br.edu.ifmg.produto.entities.User;
import br.edu.ifmg.produto.repositories.RoleRepository;
import br.edu.ifmg.produto.repositories.UserRepository;
import br.edu.ifmg.produto.services.exceptions.ResourceNotFound;
import ch.qos.logback.classic.encoder.JsonEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {

        Page<User> list = repository.findAll(pageable);
        return list.map(u -> new UserDTO(u));
    }
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {

        Optional<User> opt = repository.findById(id);
        User user = opt.orElseThrow(() -> new ResourceNotFound("User not found"));
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {

        User entity = new User();

        copyDtoToEntity(dto, entity);

        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

        User novo = repository.save(entity);

        return new UserDTO(novo);

    }

    private void copyDtoToEntity(UserInsertDTO dto, User entity) {

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();

        for (RoleDTO role : dto.getRoles()){
            Role r = roleRepository.getReferenceById(role.getId());
            entity.getRoles().add(r);
        }
    }



}
