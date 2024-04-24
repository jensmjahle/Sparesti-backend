package idatt2106.systemutvikling.sparesti.dao;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "manual_savings")
public class ManualSavingDAO {

    @Id
    private Long id;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private UserDAO user;

    @Column(name = "time_of_transfer")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOfTransfer;
}
