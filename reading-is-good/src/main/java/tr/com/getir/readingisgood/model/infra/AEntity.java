
package tr.com.getir.readingisgood.model.infra;


import jakarta.persistence.*;

import java.util.Date;

@MappedSuperclass
public class AEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date creationDate;
}
