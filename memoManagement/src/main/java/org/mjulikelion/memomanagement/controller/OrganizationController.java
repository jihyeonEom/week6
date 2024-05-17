package org.mjulikelion.memomanagement.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.memomanagement.authentication.user.AuthenticatedUser;
import org.mjulikelion.memomanagement.dto.ResponseDto;
import org.mjulikelion.memomanagement.dto.organization.OrganizationCreateDto;
import org.mjulikelion.memomanagement.dto.organization.OrganizationUpdateDto;
import org.mjulikelion.memomanagement.model.User;
import org.mjulikelion.memomanagement.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    // org 생성
    @PostMapping("/organizations")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto<Void>> createOrganization(@RequestBody @Valid OrganizationCreateDto organizationCreateDto) {
        this.organizationService.createOrganization(organizationCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "create organization"), HttpStatus.CREATED);
    }

    // org 삭제
    @DeleteMapping("/organizations/{orgId}")
    public ResponseEntity<ResponseDto<Void>> deleteOrganizationById(@PathVariable("orgId") UUID orgId) {
        this.organizationService.validateOrganization(orgId);
        this.organizationService.deleteOrganizationById(orgId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "delete organization"), HttpStatus.OK);
    }

    // org 이름 변경
    @PatchMapping("/organizations/{orgId}")
    public ResponseEntity<ResponseDto<Void>> updateOrganizationById(@PathVariable("orgId") UUID orgId,
                                                                    @RequestBody @Valid OrganizationUpdateDto organizationUpdateDto) {
        this.organizationService.validateOrganization(orgId);
        this.organizationService.updateOrganizationById(orgId, organizationUpdateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "update organization"), HttpStatus.OK);
    }

    // user의 org 가입
    @PostMapping("/organizations/join/{orgId}")
    public ResponseEntity<ResponseDto<Void>> joinOrganization(@PathVariable("orgId") UUID orgId, @AuthenticatedUser User user) {
        this.organizationService.validateOrganization(orgId);
        this.organizationService.isUserAlreadyJoinedOrganization(orgId, user); // 유저가 이미 가입한 조직인지 검사

        this.organizationService.joinOrganization(user, orgId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "join organization"), HttpStatus.OK);
    }

    // user의 org 탈퇴
    @DeleteMapping("/organizations/leave/{orgId}")
    public ResponseEntity<ResponseDto<Void>> leaveOrganization(@PathVariable("orgId") UUID orgId, @AuthenticatedUser User user) {
        this.organizationService.validateOrganization(orgId);

        this.organizationService.leaveOrganization(user, orgId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "leave organization"), HttpStatus.OK);
    }
}
