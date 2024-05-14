package org.mjulikelion.memomanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity(name = "UserOrganization")
public class UserOrganization extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Organization organization;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

}
