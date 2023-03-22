package restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "stop_lists")
public class StopList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stop_list_seq")
    @SequenceGenerator(name = "stop_list_seq",allocationSize = 1,initialValue = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "reason", nullable = false)
    private String reason;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @OneToOne(cascade = {PERSIST,DETACH,REFRESH,MERGE},fetch = EAGER)
    private MenuItem menuItem;

}