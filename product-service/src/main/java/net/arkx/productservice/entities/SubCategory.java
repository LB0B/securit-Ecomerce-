package net.arkx.productservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "Sub_Category")
public class SubCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToMany(mappedBy = "subcategories")
    private Set<Product> products;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String name;
}
