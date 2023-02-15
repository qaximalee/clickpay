package com.clickpay.service.recovery_officer.area_allocation;

import com.clickpay.dto.recovery_officer.area_allocation.AreaAllocationDto;
import com.clickpay.dto.recovery_officer.area_allocation.AreaAllocationRequest;
import com.clickpay.dto.recovery_officer.area_allocation.AreaAllocationResponse;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.model.area.SubLocality;
import com.clickpay.model.recovery_officer.OfficerArea;
import com.clickpay.model.user.User;
import com.clickpay.repository.recovery_officer.AreaAllocationRepository;
import com.clickpay.service.area.sub_locality.ISubLocalityService;
import com.clickpay.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AreaAllocationService implements IAreaAllocationService{

    private final String OFFICER = "OFFICER";

    private final AreaAllocationRepository repo;
    private final IUserService userService;
    private final ISubLocalityService subLocalityService;

    @Override
    public void createOfficerArea(AreaAllocationRequest request, User loggedInUser) throws EntityNotFoundException {
        User user = userService.findById(request.getUserId());
        if(!user.getUserType().getType().equalsIgnoreCase(OFFICER)){
            log.error("User fetched by id provided is not a recovery officer type.");
            throw new EntityNotFoundException("User is not a recovery officer type.");
        }
        List<SubLocality> subLocalities = subLocalityService.findAllByIdInAndUserId(request.getSubLocalitiesId(), loggedInUser.getId());
        repo.saveAll(OfficerArea.fromSubLocalitiesAndUserAndLoggedInUserId(subLocalities, user, loggedInUser.getId()));
        log.info("Officer area allocation process completed.");
    }

    @Override
    public AreaAllocationResponse getAllAreaAllocationsByUserId(Long userId, User loggedInUser) throws EntityNotFoundException {
        User user = userService.findById(userId);
        List<SubLocality> allSubLocalities = subLocalityService.findAllSubLocalitiesByUserId(loggedInUser.getId());
        List<Long> selectedSubLocalitiesId = getAllOfficerAreaByUserId(userId).stream()
                .map(e -> e.getSubLocality().getId()).collect(Collectors.toList());
        List<AreaAllocationDto> areaAllocatedList = AreaAllocationDto.to(allSubLocalities, selectedSubLocalitiesId);
        return AreaAllocationResponse.to(userId, user.getFirstName()+" "+user.getLastName(), user.getEmail(), areaAllocatedList);
    }

    public List<OfficerArea> getAllOfficerAreaByUserId(Long userId) throws EntityNotFoundException {
        log.info("Fetching all officer area allocation data.");
        List<OfficerArea> selectedSubLocalities = repo.findAllByUserId(userId);
        if (CollectionUtils.isEmpty(selectedSubLocalities)) {
            log.error("No area allocated to the provided user id.");
            throw new EntityNotFoundException("No area allocated to the provided user id.");
        }

        return selectedSubLocalities;
    }
}
