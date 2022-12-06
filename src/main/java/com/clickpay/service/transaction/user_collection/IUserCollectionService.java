package com.clickpay.service.transaction.user_collection;

import com.clickpay.dto.transaction.UnpaidCollectionResponse;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.utils.Message;

public interface IUserCollectionService {
    Message<UnpaidCollectionResponse> getUserUnpaidCollections(Long userId) throws EntityNotFoundException;
}
