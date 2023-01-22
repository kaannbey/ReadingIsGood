package tr.com.getir.readingisgood.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.com.getir.readingisgood.model.AuthUser;

@Repository
public interface IUserRepository extends CrudRepository<AuthUser, Long> {
    /**
     * Finds user by username
     *
     * @param username username
     * @return user
     */
    @Query(value = "SELECT u FROM AuthUser u where(u.username = ?1 )")
    AuthUser findByUsername(String username);
}
