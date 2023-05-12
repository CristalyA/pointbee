package br.com.pointbee.afrotech.repository;

import br.com.pointbee.afrotech.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    public Optional<User> findByEmail(String user);

}
