package idatt2106.systemutvikling.sparesti.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Date;

@Entity
@Table(name = "manual_savings")
@Getter
public class ManualSavingDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    @Setter
    private Long amount;

    @Column(name = "time_of_transfer")
    @Temporal(TemporalType.TIMESTAMP)
    @Setter
    private Date timeOfTransfer;

    @Setter
    @Nullable
    @Column(name = "milestone_id")
    private Long milestoneId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    @Setter
    private UserDAO user;
}
