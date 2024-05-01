package idatt2106.systemutvikling.sparesti.dao;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data access object for User
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserDAO {

    @Id
    private String username;
    @Column(name = "password_hash")
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    @Lob
    @Column(length=67000)
    private byte[] profilePicture;
    private Long monthlyIncome;
    private Long monthlySavings;
    private Long monthlyFixedExpenses;
    private Long currentAccount;
    private Long savingsAccount;

    @ManyToMany
    @JoinTable(name = "user_achievements",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "achievement_id"))
    private List<AchievementDAO> achievements = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<ManualSavingDAO> manualSavings = new ArrayList<>();
}
