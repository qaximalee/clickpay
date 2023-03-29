package com.clickpay.service.validation;

import com.clickpay.errors.general.EntityAlreadyExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ValidationService<T> implements IValidationService<T>{

    private static final String ID = "id";
    private static final String IS_DELETED = "isDeleted";

    private final EntityManager entityManager;

    @Override
    public void getRecords(Class clazz, String fieldName, String createdBy, String value, Long userId, String errorMessage, boolean forDeletion) throws EntityAlreadyExistException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> tupleCriteriaQuery = criteriaBuilder.createTupleQuery();
        Root<T> root = tupleCriteriaQuery.from(clazz);
        List<Expression<?>> selectExpressionList = getSelectedColumns(root);
        List<Selection<?>> selectClauses = new ArrayList<>(selectExpressionList);
        tupleCriteriaQuery.multiselect(selectClauses);

        // where
        if(forDeletion) {
            tupleCriteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(fieldName), Long.parseLong(value)),
                            criteriaBuilder.equal(root.get(createdBy), userId)
                    )
            );
            TypedQuery<Tuple> query = entityManager.createQuery(tupleCriteriaQuery);
            List<Tuple> queryResultList = query.getResultList();
            if (queryResultList.isEmpty()) {
                log.error(errorMessage);
                throw new EntityAlreadyExistException(errorMessage);
            }
        }else{
            tupleCriteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(fieldName), value),
                            criteriaBuilder.equal(root.get(createdBy), userId),
                            criteriaBuilder.equal(root.get(IS_DELETED), false)
                    )
            );
            TypedQuery<Tuple> query = entityManager.createQuery(tupleCriteriaQuery);
            List<Tuple> queryResultList = query.getResultList();
            if (!queryResultList.isEmpty()) {
                log.error(errorMessage);
                throw new EntityAlreadyExistException(errorMessage);
            }
        }
    }

    private List<Expression<?>> getSelectedColumns(Root<T> root) {
        List<Expression<?>> selectedColumns = new ArrayList<>();
        // Simulate adding select values. In real scenario these values come dynamically, e.g. from the user input
        selectedColumns.add((Expression<?>) root.get(ID).alias(ID));
        return selectedColumns;
    }
}
