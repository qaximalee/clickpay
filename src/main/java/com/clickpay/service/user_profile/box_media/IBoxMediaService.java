package com.clickpay.service.user_profile.box_media;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.user_profile.BoxMedia;

import java.util.List;

public interface IBoxMediaService {
    BoxMedia findById(Long id) throws EntityNotFoundException, BadRequestException;

    BoxMedia save(BoxMedia boxMedia, boolean isUpdating) throws EntityNotSavedException, BadRequestException, EntityAlreadyExistException;

    List<BoxMedia> findAllBoxMediaByUserId(Long userId) throws EntityNotFoundException;
}
