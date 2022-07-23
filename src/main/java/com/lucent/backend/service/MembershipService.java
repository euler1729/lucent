package com.lucent.backend.service;

import com.lucent.backend.Repo.MembershipRepo;
import com.lucent.backend.Repo.OrganizationRepo;
import com.lucent.backend.api.Exception.ResourceNotFound;
import com.lucent.backend.api.dto.MembershipRequest;
import com.lucent.backend.api.dto.MembershipResponse;
import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Membership;
import com.lucent.backend.domain.Organization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class MembershipService {
    @Autowired
    private MembershipRepo membershipRepo;

    @Autowired
    private OrganizationRepo organizationRepo;

    /**
     * Saves a membership request.
     * @param membershipRequest Contains organizationId, nid, membershipCode
     * @param donor AppUser object
     * @return saved membership as MembershipResponse
     * @throws ResourceNotFound if organization id is invalid
     */
    public MembershipResponse saveMembership(MembershipRequest membershipRequest, AppUser donor) throws ResourceNotFound {
        Optional<Organization> organization = organizationRepo.findById(membershipRequest.getOrganizationId());

        if(organization.isPresent()) {
            Boolean autoApproval = organization.get().getAutoApprove();

            Membership membership = Membership.builder()
                    .donor(donor)
                    .organization(organization.get())
                    .nid(membershipRequest.getNid())
                    .membershipCode(membershipRequest.getMembershipCode())
                    .approved(autoApproval)
                .build();

            Membership savedMemberhip = membershipRepo.save(membership);
            return new MembershipResponse(savedMemberhip);
        }
        else {
            throw new ResourceNotFound("Invalid Organization");
        }
    }

    /**
     * Change membership approval
     * @param membershipId Membership ID
     * @param requestingUser AppUser object
     * @param approval Boolean value, approval will change to this.
     * @return true if succeeded
     * @throws ResourceNotFound if membership id is invalid
     * @throws AccessDeniedException if requestingUser is not the manager of the membership's organization manager
     */
    public Boolean changeMembershipApproval(Long membershipId, AppUser requestingUser, Boolean approval) throws ResourceNotFound, AccessDeniedException {
        Optional<Membership> membership = membershipRepo.findById(membershipId);
        if(membership.isPresent()){

            if(membership.get().getOrganization().isPublished()) {
                if (membership.get().getOrganization().getManager() == requestingUser) {
                    membershipRepo.changeApproval(approval, membershipId);
                    return true;
                } else {
                    throw new AccessDeniedException("Only organization manager can approve");
                }
            }
            else{
                throw  new AccessDeniedException("Organization is not published.");
            }
        }
        else{
            throw new ResourceNotFound("Membership not found");
        }
    }

    /**
     * Returns membership list
     * @param organizationId organization id
     * @param approved if true then approved is included in return
     * @param notApproved if true then not approved is included in return
     * @param page page no
     * @param size page size
     * @param sortBy sortBY field
     * @return List of MembershipResponse
     * @throws ResourceNotFound if organizationId is invalid
     */
    public List<MembershipResponse> getMembership(Long organizationId, Boolean approved, Boolean notApproved, int page, int size, String sortBy) throws ResourceNotFound {
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy));
        List<MembershipResponse> membershipResponses = new ArrayList<>();

        Optional<Organization> organization = organizationRepo.findById(organizationId);
        if(organization.isPresent()){

            if(approved && notApproved){
                membershipRepo.findAllByOrganization(organization.get()).forEach(membership -> {
                    membershipResponses.add(new MembershipResponse(membership));
                });
            }
            else if(approved){
                membershipRepo.findAllByOrganizationAndApprovedIsTrue(organization.get()).forEach(membership -> {
                    membershipResponses.add(new MembershipResponse(membership));
                });
            }
            else if(notApproved){
                membershipRepo.findAllByOrganizationAndApprovedIsFalse(organization.get()).forEach(membership -> {
                    membershipResponses.add(new MembershipResponse(membership));
                });
            }
            return membershipResponses;
        }
        else{
            throw new ResourceNotFound("Organization not found");
        }
    }
}
