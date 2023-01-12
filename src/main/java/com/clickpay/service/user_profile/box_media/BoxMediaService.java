package com.clickpay.service.user_profile.box_media;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.SubLocality;
import com.clickpay.model.user_profile.BoxMedia;
import com.clickpay.repository.user_profile.BoxMediaRepository;
import com.clickpay.service.user.IUserService;
import com.clickpay.service.validation.IValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BoxMediaService implements IBoxMediaService{

    private final IUserService userService;
    private final BoxMediaRepository repo;
    private final IValidationService<BoxMedia> validationService;

    public BoxMediaService(final IUserService userService,
                           final BoxMediaRepository repo, IValidationService<BoxMedia> validationService) {
        this.userService = userService;
        this.repo = repo;
        this.validationService = validationService;
    }

    @Override
    public BoxMedia findById(Long id) throws EntityNotFoundException, BadRequestException {
        log.info("Finding box media by id: "+id);
        if (id == null || id < 1) {
            log.error("Box media id " + id + " is invalid.");
            throw new BadRequestException("Provided box media id should be a valid and non null value.");
        }
        Optional<BoxMedia> data = repo.findByIdAndActiveAndIsDeleted(id, true, false);
        if (!data.isPresent()) {
            log.error("No box media found with id: "+id);
            throw new EntityNotFoundException("No box media found with provided box media id.");
        }
        return data.get();
    }

    @Override
    public BoxMedia save(BoxMedia boxMedia) throws EntityNotSavedException, BadRequestException, EntityAlreadyExistException {
        log.info("Creating box media.");
        if (boxMedia == null) {
            log.error("Box media should not be null.");
            throw new BadRequestException("Box media should not be null.");
        }

        validationService.getRecords(
                BoxMedia.class,
                "boxNumber",
                "createdBy",
                boxMedia.getBoxNumber(),
                boxMedia.getCreatedBy(),
                "Box media number: "+boxMedia.getBoxNumber()+" already exists."
        );

        try {
            boxMedia = repo.save(boxMedia);
            log.debug("Box media with city id: "+boxMedia.getId()+ " created successfully.");
            return boxMedia;
        } catch (Exception e) {
            log.error("Box media can not be saved.");
            throw new EntityNotSavedException("Box media can not be saved.");
        }
    }

    @Override
    public List<BoxMedia> findAllBoxMediaByUserId(Long userId) throws EntityNotFoundException {
        log.info("Fetching all box media for user id: "+userId);
        List<BoxMedia> boxMediaList = repo.findAllByCreatedByAndActiveAndIsDeleted(userId, true, false);
        if (boxMediaList == null || boxMediaList.isEmpty()) {
            log.error("No box media data found.");
            throw new EntityNotFoundException("Box media not found.");
        }
        return boxMediaList;
    }
}
