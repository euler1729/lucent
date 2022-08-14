package com.lucent.backend.service;

import com.lucent.backend.Notifications.TextService;
import com.lucent.backend.Repo.DonationCollectionRepo;
import com.lucent.backend.Repo.DonationRepo;
import com.lucent.backend.Repo.OrganizationRepo;
import com.lucent.backend.Repo.SpendingRepo;
import com.lucent.backend.api.Exception.ResourceNotFound;
import com.lucent.backend.api.dto.SpendingRequest;
import com.lucent.backend.api.dto.SpendingResponse;
import com.lucent.backend.domain.*;
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
public class SpendingService {
    @Autowired private SpendingRepo spendingRepo;
    @Autowired private OrganizationRepo organizationRepo;
    @Autowired private DonationRepo donationRepo;
    @Autowired TextService textService;
    @Autowired private DonationCollectionRepo donationCollectionRepo;
    private Double collectDonation(Spending spending, Double toCollectAmount, Double totalCollected){
        Double collectionLeft = toCollectAmount - totalCollected;
        Double currentOrganizationBalance = spending.getOrganization().getBalance();
        Double collected;
//        Select a donation
//        Donation donation = donationRepo.getFirstByOrganizationAndAvailableIsGreaterThan(spending.getOrganization(), 0.0).get(0);
//        Donation donation = donationRepo.findAll();
        Iterable<Donation> donations =  donationRepo.getFirstByOrganizationAndAvailableIsGreaterThan(spending.getOrganization(), 0.0);
        Donation donation = donations.iterator().next();

        System.out.println("donation = " + donation);
//        reduce available balance
        Double availableDonationAmount = donation.getAvailable();

        if(availableDonationAmount > collectionLeft){
            collected = collectionLeft;
//            Update donation available
            donation.setAvailable(availableDonationAmount - collected);
        }
        else{
            collected = availableDonationAmount;
//            Update donation available
            donation.setAvailable(0.0);
        }
//            update spending collection
        spending.setCollectedAmount(totalCollected + collected);
//            reduce organization balance
        spending.getOrganization().setBalance(currentOrganizationBalance - collected);
//          Save record
        DonationCollection donationCollection = DonationCollection.builder()
                .spending(spending)
                .collected(true)
                .amount(collected)
                .donation(donation)
                .build();
        donationCollectionRepo.save(donationCollection);
//                notify user
        textService.sendDonationCollectionText(donationCollection, collected);

        return collected;
    }

    public Spending processSpending(SpendingRequest spendingRequest, AppUser manager) throws AccessDeniedException, ResourceNotFound {
        Optional<Organization> organization = organizationRepo.findById(spendingRequest.getOganizationId());

        if(organization.isPresent()){
            if(organization.get().isPublished()){
                if(manager == organization.get().getManager()){
                    if(spendingRequest.getAmount() <= organization.get().getBalance()){
                        Spending  spending = Spending.builder()
                                .organization(organization.get())
                                .description(spendingRequest.getDescription())
                                .amount(spendingRequest.getAmount())
                                .collectedAmount(0.0)
                                .build();
                        spendingRepo.save(spending);

//                    Collect Donation From Users
                        Double toCollectAmount = spending.getAmount();
                        Double collectedAmount = 0.0;

                        while(!collectedAmount.equals(toCollectAmount)){
                            collectedAmount += collectDonation(spending, toCollectAmount, collectedAmount);
                            System.out.println("collectedAmount = " + collectedAmount);
                        }
                        organizationRepo.increaseSpending(collectedAmount, organization.get().getId());
                        return spending;
                    }
                    else{
                        throw new AccessDeniedException("Spending has to be less than or equal to balance of organization.");
                    }
                }
                else{
                    throw new AccessDeniedException("Only manager can request for spending.");
                }
            }
            else{
                throw new AccessDeniedException("Organization is not published.");
            }
        }
        else{
            throw new ResourceNotFound("Organization not valid.");
        }
    }

    /**
     * Get latest spendings | Permit all
     * @param organizationId organizationId
     * @param page page no
     * @param size page size
     * @return List of SpendingResponse
     * @throws ResourceNotFound if organizationId is invalid
     */
    public List<SpendingResponse> getSpendings(Long organizationId, Integer page, Integer size) throws ResourceNotFound {
        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        Optional<Organization> organization = organizationRepo.findById(organizationId);
        List<SpendingResponse> spendingResponses = new ArrayList<>();
        if(organization.isPresent()){
            List<Spending> spendings = spendingRepo.findAllByOrganization(organization.get(), paging);
            spendings.forEach(spending -> {
                spendingResponses.add(new SpendingResponse(spending));
            });
            return spendingResponses;
        }
        else {
            throw new ResourceNotFound("Organization not found");
        }
    }
}
