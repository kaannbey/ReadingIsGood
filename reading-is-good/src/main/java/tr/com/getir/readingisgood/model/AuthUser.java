package tr.com.getir.readingisgood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.getir.readingisgood.enums.ERole;
import tr.com.getir.readingisgood.model.infra.AEntity;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authuser")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser extends AEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    @JsonIgnore
    private String password;
    @Column(name = "email")
    private String email;
    @ElementCollection(targetClass = ERole.class,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "roles", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<ERole> roles = new HashSet<>();

    @Override
    public String toString() {
        return "AuthUser{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
