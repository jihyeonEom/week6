package org.mjulikelion.memomanagement.repository;

import org.mjulikelion.memomanagement.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

}
