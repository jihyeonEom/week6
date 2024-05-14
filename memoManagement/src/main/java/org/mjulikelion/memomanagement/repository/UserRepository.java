package org.mjulikelion.memomanagement.repository;

import org.mjulikelion.memomanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findUserByEmail(String email);
}
