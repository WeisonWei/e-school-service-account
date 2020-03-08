package com.es.account.common;

import com.es.account.entity.BaseEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

public abstract class RepositoryTest<T extends CrudRepository<V, Long>, V extends BaseEntity> extends CommonTest {

    protected T repository;
    protected V entity;

    @Before
    public void createEntity() {
        entity = repository.save(entity);
    }

    @After
    public void removeEntity() {
        repository.deleteById(entity.getId());
    }

    @Test
    public void getEntity() {
        Optional<V> result = repository.findById(entity.getId());
        assertTrue(result.isPresent());
        assertThat(result.get().getId(), greaterThan(0L));
    }

    @Test
    public void saveEntity() {
        V result = repository.save(entity);
        assertThat(result.getId(), greaterThan(0L));
        assertEquals(entity.getId(), result.getId());
    }
}