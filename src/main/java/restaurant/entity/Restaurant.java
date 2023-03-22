package restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import restaurant.entity.enums.RestaurantType;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seq")
    @SequenceGenerator(name = "restaurant_seq",allocationSize = 1,initialValue = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false,unique = true)
    private String name;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "rest_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RestaurantType restType;
    @Column(name = "number_of_employees", nullable = false)
    private int numberOfEmployees;
    @Column(name = "service", nullable = false)
    private double service;
    @OneToMany(cascade = {ALL},fetch = LAZY,mappedBy = "restaurant")
    private List<MenuItem> menuItem = new ArrayList<>();
    @OneToMany(cascade = {ALL},fetch = LAZY,mappedBy = "restaurant")
    private List<User> user = new ArrayList<>();

}