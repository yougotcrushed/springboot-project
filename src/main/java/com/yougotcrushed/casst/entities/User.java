package com.yougotcrushed.casst.entities;

import com.yougotcrushed.casst.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
//    @OneToMany(mappedBy = "user")
//    private List<Address> addresses = new ArrayList<>();

//    public void addAddress(Address address) {
//        addresses.add(address);
//        address.setUser(this);
//    }
//
//    public void removeAddress(Address address) {
//        addresses.remove(address);
//        address.setUser(null);
//    }
//
//    public void addTag(String tagName) {
//        var tag = new Tag(tagName);
//        tags.add(tag);
//        tag.getUsers().add(this);
//    }
//
//    public void removeTag(String tagName) {
//        var tag = new Tag(tagName);
//        tags.remove(tag);
//    }

    @ManyToMany
    @JoinTable(
            name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

//    @OneToOne(mappedBy = "user")
//    private Profile profile;

    @ManyToMany
    @JoinTable(
            name = "wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> wishlist = new HashSet<>();

}
