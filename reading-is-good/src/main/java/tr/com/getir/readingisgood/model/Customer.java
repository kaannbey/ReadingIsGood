package tr.com.getir.readingisgood.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.getir.readingisgood.dto.CustomerRegistrationDTO;
import tr.com.getir.readingisgood.model.infra.AEntity;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends AEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "username",unique = true)
    private String username;
    @Column(name = "password")
    private String password;

    public static Customer buildCustomer(CustomerRegistrationDTO registrationDTO) {
        return new Customer(registrationDTO.getName(),registrationDTO.getSurname(),registrationDTO.getUsername(),registrationDTO.getPassword());
    }
}
