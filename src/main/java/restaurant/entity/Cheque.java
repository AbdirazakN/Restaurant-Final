package restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cheques")
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cheque_seq")
    @SequenceGenerator(name = "cheque_seq",allocationSize = 1,initialValue = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "price_average", nullable = false)
    private Double priceAverage;
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;
    @ManyToOne(cascade = {PERSIST,REFRESH,MERGE,DETACH},fetch = EAGER)
    private User user;
    @ManyToMany(cascade = {REFRESH,DETACH,MERGE,PERSIST},fetch = EAGER)
    private List<MenuItem> menuItem = new ArrayList<>();

}