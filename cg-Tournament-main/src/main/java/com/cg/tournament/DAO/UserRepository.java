package com.cg.tournament.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.tournament.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailAddress(String emailAddress);
    boolean existsByUsername(String username);
    User findOneById(Long Id);
    User findOneByUsername(String username);

}
