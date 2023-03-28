package restaurant.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "subcategories")
@Getter
@Setter
@NoArgsConstructor
public class Subcategory {
    @Id
    @SequenceGenerator(
            name = "subcategory_id_gen",
            sequenceName = "subcategory_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "subcategory_id_gen"
    )
    private Long id;
    private String name;

    @ManyToOne(cascade =
            {CascadeType.PERSIST,
                    MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH})
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "subcategory", cascade = {MERGE,DETACH,PERSIST,REFRESH,REMOVE}, orphanRemoval = true)
    private List<MenuItem> menuItems = new ArrayList<>();

}