package org.mjulikelion.memomanagement.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.memomanagement.dto.organization.OrganizationCreateDto;
import org.mjulikelion.memomanagement.dto.organization.OrganizationUpdateDto;
import org.mjulikelion.memomanagement.errorcode.ErrorCode;
import org.mjulikelion.memomanagement.exception.ConflictException;
import org.mjulikelion.memomanagement.exception.NotFoundException;
import org.mjulikelion.memomanagement.model.Organization;
import org.mjulikelion.memomanagement.model.User;
import org.mjulikelion.memomanagement.model.UserOrganization;
import org.mjulikelion.memomanagement.repository.OrganizationRepository;
import org.mjulikelion.memomanagement.repository.UserOrganizationRepository;
import org.mjulikelion.memomanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final UserOrganizationRepository userOrganizationRepository;

    // org 생성
    public void createOrganization(OrganizationCreateDto organizationCreateDto) {
        Organization organization = Organization.builder()
                .name(organizationCreateDto.getName())
                .build();
        this.organizationRepository.save(organization);
    }

    // org 삭제
    public void deleteOrganizationById(UUID orgId) {
        this.organizationRepository.deleteById(orgId);
    }

    // org 이름 변경
    public void updateOrganizationById(UUID orgId, OrganizationUpdateDto organizationUpdateDto) {
        Organization organization = this.organizationRepository.findById(orgId).orElseThrow(() -> new NotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND));
        organization.updateOrganization(organizationUpdateDto);
        this.organizationRepository.save(organization);
    }

    // user의 org 가입
    public void joinOrganization(UUID userId, UUID orgId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        Organization organization = this.organizationRepository.findById(orgId).orElseThrow(() -> new NotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND));
        UserOrganization userOrganization = new UserOrganization(organization, user);
        this.userOrganizationRepository.save(userOrganization);
    }

    // user의 org 탈퇴
    public void leaveOrganization(UUID orgId, UUID userId) {
        UUID userOrgId = this.isUserInOrganization(orgId, userId);
        this.userOrganizationRepository.deleteById(userOrgId);

    }

    public void validateOrganization(UUID orgId) {
        if (!this.organizationRepository.existsById(orgId)) {
            throw new NotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND);
        }
    }

    // user가 해당 org에 속해있는지 확인
    // 있으면 UserOrganiztion의 Id를 반환
    public UUID isUserInOrganization(UUID orgId, UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        List<UserOrganization> userOrganizations = user.getUserOrganizations();

        for (UserOrganization userOrg : userOrganizations) {

            if (userOrg.getOrganization().getId().equals(orgId)) {
                if (userOrg.getUser().getId().equals(userId)) {
                    return userOrg.getId();
                }
                throw new NotFoundException(ErrorCode.USER_ORGANIZATION_NOT_FOUND);
            }
        }
        throw new NotFoundException(ErrorCode.USER_ORGANIZATION_NOT_FOUND);
    }

    public boolean isUserAlreadyjoinedOrganization(UUID orgId, User user) {
        List<UserOrganization> userOrganizations = user.getUserOrganizations();
        for (UserOrganization userOrg : userOrganizations) {
            if (userOrg.getOrganization().getId().equals(orgId)) {
                throw new ConflictException(ErrorCode.USER_ORGANIZATION_ALREADY_EXISTS);
            }
        }
        return true;
    }
}
