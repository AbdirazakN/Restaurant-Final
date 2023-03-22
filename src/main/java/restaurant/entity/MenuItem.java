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
import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_item_seq")
    @SequenceGenerator(name = "menu_item_seq",allocationSize = 1,initialValue = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "image", nullable = false)
    private String image;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "is_vegetarian", nullable = false)
    private Boolean isVegetarian;
    @ManyToOne(cascade = {PERSIST,REFRESH,DETACH,MERGE},fetch = EAGER)
    private Restaurant restaurant;
    @OneToOne(cascade = {ALL},fetch = EAGER)
    private StopList stopList;
    @ManyToOne(cascade = {PERSIST,DETACH,REFRESH,MERGE},fetch = EAGER)
    private Subcategory subcategory;
    @ManyToMany(cascade = {REFRESH,PERSIST,MERGE,DETACH},fetch = LAZY,mappedBy = "menuItem")
    private List<Cheque> cheque = new ArrayList<>();

}