package restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "subcategories")
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcategory_seq")
    @SequenceGenerator(name = "subcategory_seq",allocationSize = 1,initialValue = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false,unique = true)
    private String name;
    @ManyToOne(cascade = {PERSIST,REFRESH,MERGE,DETACH},fetch = EAGER)
    private Category category;
    @OneToMany(cascade = {ALL},fetch = EAGER,mappedBy = "subcategory")
    private List<MenuItem> menuItem = new ArrayList<>();

}