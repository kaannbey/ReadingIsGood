package tr.com.getir.readingisgood.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.com.getir.readingisgood.model.Customer;

import java.util.List;

@Repository
public interface ICustomerRepository extends CrudRepository<Customer, Long> {

    @Query(value = "SELECT u FROM Customer u where(u.username = ?1 and u.password = ?2)")
    List<Customer> findByLoginInfo(String username, String password);
}
