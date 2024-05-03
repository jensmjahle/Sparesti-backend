package idatt2106.systemutvikling.sparesti.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

/**
 * Data Access Object for ManualSaving
 */
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
  @JoinColumn(name = "user_id")
  @Setter
  private UserDAO user;
}
