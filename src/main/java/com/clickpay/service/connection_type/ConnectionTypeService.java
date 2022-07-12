package com.clickpay.service.connection_type;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.connection_type.ConnectionType;
import com.clickpay.repository.connection_type.ConnectionTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ConnectionTypeService implements IConnectionTypeService{

    private final ConnectionTypeRepository repo;

    public ConnectionTypeService(final ConnectionTypeRepository repo) {
        this.repo = repo;
    }

    @Override
    public ConnectionType findById(Long id) throws EntityNotFoundException, BadRequestException {
        log.info("Finding connection type by id: "+id);
        if (id == null || id < 1) {
            log.error("Connection type id " + id + " is invalid.");
            throw new BadRequestException("Provided connection type id should be a valid and non null value.");
        }
        Optional<ConnectionType> data = repo.findByIdAndActive(id, true);
        if (!data.isPresent()) {
            log.error("No connection type found with id: "+id);
            throw new EntityNotFoundException("No connection type found with provided connection type id.");
        }
        return data.get();
    }

    @Override
    public ConnectionType save(ConnectionType connectionType) throws EntityNotSavedException, BadRequestException {
        log.info("Creating connection type.");
        if (connectionType == null) {
            log.error("Connection type should not be null.");
            throw new BadRequestException("Connection type should not be null.");
        }

        try {
            connectionType = repo.save(connectionType);
            log.debug("Connection type with city id: "+connectionType.getId()+ " created successfully.");
            return connectionType;
        } catch (Exception e) {
            log.error("Connection type can not be saved.");
            throw new EntityNotSavedException("Connection type can not be saved.");
        }
    }

    @Override
    public List<ConnectionType> findAllConnectionTypeByUserId(Long userId) throws EntityNotFoundException {
        log.info("Fetching all connection type for user id: "+userId);
        List<ConnectionType> connectionTypeList = repo.findAllByCreatedByAndActive(userId, true);
        if (connectionTypeList == null || connectionTypeList.isEmpty()) {
            log.error("No connection type data found.");
            throw new EntityNotFoundException("Connection type not found.");
        }
        return connectionTypeList;
    }
}
