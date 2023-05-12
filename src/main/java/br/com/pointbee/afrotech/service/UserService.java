package br.com.pointbee.afrotech.service;

import org.apache.tomcat.util.codec.binary.Base64;
import br.com.pointbee.afrotech.model.User;
import br.com.pointbee.afrotech.model.UserLogin;
import br.com.pointbee.afrotech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail())
                .isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existente!", null);
        user.setPassword(encryptPassword(user.getPassword()));

        return Optional.of(userRepository.save(user));
    }

    public Optional<User> updateUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            Optional<User> searchUser = userRepository
                    .findByEmail(user.getEmail());

            if (searchUser.isPresent()) {
                if (searchUser.get().getId() != user.getId())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
            }
            user.setPassword(encryptPassword(user.getPassword()));
            return Optional.of(userRepository.save(user));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);
    }

    public Optional<UserLogin> loginUser(Optional<UserLogin> userLogin) {

        Optional<User> user = userRepository.findByEmail(userLogin.get().getUserLogin());

        if (user.isPresent()) {
            if (comparePassword(userLogin.get().getPassword(), user.get().getPassword())) {

                userLogin.get().setId(user.get().getId());
                userLogin.get().setName(user.get().getName_user());
                userLogin.get().setToken(generateBasicToken(userLogin.get().getUserLogin(), userLogin.get().getPassword()));
                userLogin.get().getPassword();
                userLogin.get().setPassword(user.get().getPassword());
                userLogin.get().setTypeUser(user.get().getUserType());

                return userLogin;

            }
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos!", null);
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String passwordEnconder = encoder.encode(password);

        return passwordEnconder;
    }

    private boolean comparePassword(String typedPassword, String bankPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(typedPassword, bankPassword);
    }

    private String generateBasicToken(String email, String password) {
        String structure = email + ":" + password;

        byte[] structureBase64 = java.util.Base64.getEncoder().encode(structure.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(structureBase64);
    }
}
