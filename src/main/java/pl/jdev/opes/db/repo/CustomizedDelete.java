package pl.jdev.opes.db.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomizedDelete<T, I> {
    @Query("UPDATE S s " +
            "SET deletedAt = CURRENT_TIMESTAMP " +
            "WHERE s = :entity")
    <S extends T> void delete(@Param("entity") S entity);

    <S extends T> void deleteAll();

    @Query("UPDATE S s " +
            "SET deletedAt = CURRENT_TIMESTAMP " +
            "WHERE id = :id")
    <S extends T> void deleteById(@Param("id") I entityId);
}
