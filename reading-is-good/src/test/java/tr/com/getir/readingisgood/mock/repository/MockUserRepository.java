package tr.com.getir.readingisgood.mock.repository;

import tr.com.getir.readingisgood.model.AuthUser;
import tr.com.getir.readingisgood.repository.IUserRepository;

import java.util.Optional;

public class MockUserRepository implements IUserRepository {
    @Override
    public AuthUser findByUsername(String username) {
        return null;
    }

    @Override
    public <S extends AuthUser> S save(S entity) {
        return null;
    }

    @Override
    public <S extends AuthUser> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<AuthUser> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<AuthUser> findAll() {
        return null;
    }

    @Override
    public Iterable<AuthUser> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(AuthUser entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends AuthUser> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
