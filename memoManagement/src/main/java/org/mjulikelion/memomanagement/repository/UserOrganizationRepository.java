package org.mjulikelion.memomanagement.repository;

import org.mjulikelion.memomanagement.model.UserOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserOrganizationRepository extends JpaRepository<UserOrganization, UUID> {
}
