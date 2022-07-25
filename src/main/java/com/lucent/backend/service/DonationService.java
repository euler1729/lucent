package com.lucent.backend.service;

import com.lucent.backend.Repo.DonationRepo;
import com.lucent.backend.Repo.OrganizationRepo;
import com.lucent.backend.Repo.TransactionRepo;
import com.lucent.backend.api.Exception.ResourceNotFound;
import com.lucent.backend.api.dto.DonationRequest;
import com.lucent.backend.api.dto.DonationResponse;
import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Donation;
import com.lucent.backend.domain.Organization;
import com.lucent.backend.domain.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class DonationService {
    @Autowired private DonationRepo donationRepo;
    @Autowired private TransactionRepo transactionRepo;
    @Autowired private OrganizationRepo organizationRepo;

    /**
     * Make donation and saves a transaction
     * @param donationRequest contains organization, amount, gateway
     * @param donor AppUser
     * @return DonationResponse
     * @throws ResourceNotFound if organizationId is invalid
     */
    public DonationResponse donate(DonationRequest donationRequest, AppUser donor) throws ResourceNotFound {
        Optional<Organization> organization = organizationRepo.findById(donationRequest.organizationID);
        if(organization.isPresent()) {
            Donation donation = Donation.builder()
                    .donor(donor)
                    .organization(organization.get())
                    .amount(donationRequest.getAmount())
                    .available(donationRequest.getAmount())
                    .build();

            Transaction transaction = Transaction.builder()
                    .donor(donor)
                    .organization(organization.get())
                    .amount(donationRequest.getAmount())
                    .gateway(donationRequest.getGateway())
                    .build();
            transactionRepo.save(transaction);

            Double CurrentBalance = organization.get().getBalance();
            organizationRepo.setBalance(CurrentBalance + donationRequest.amount, donationRequest.organizationID);

            return new DonationResponse(donationRepo.save(donation));
        }
        else{
            throw new ResourceNotFound("Invalid organization");
        }
    }

    /**
     * Get donations of an organization
     * @param organizationId organization id
     * @param page page no
     * @param size page size
     * @param top if true returns highest amount of donations, else latest donations
     * @return List of DonationResponse
     * @throws ResourceNotFound if organization not found
     */
    public List<DonationResponse> getDonations(Long organizationId, Boolean top, Integer page, Integer size) throws ResourceNotFound {
        String sortby;
        if(top){
            sortby = "amount";
        }
        else{
            sortby = "id";
        }
        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,sortby));

        Optional<Organization> organization = organizationRepo.findById(organizationId);
        List<DonationResponse> donationResponses = new ArrayList<>();
        if(organization.isPresent()){
            List<Donation> donations = donationRepo.findAllByOrganization(organization.get(), paging);
            donations.forEach(donation -> {
                donationResponses.add(new DonationResponse(donation));
            });
            return donationResponses;
        }
        else {
            throw new ResourceNotFound("Organization not found");
        }
    }
}
