package restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq",allocationSize = 1,initialValue = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false,unique = true)
    private String name;
    @OneToMany(cascade = {ALL},fetch = LAZY,mappedBy = "category")
    private List<Subcategory> subcategory = new ArrayList<>();
}