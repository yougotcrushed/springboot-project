package com.yougotcrushed.casst.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(nullable = false, name = "street")
    private String street;

    @Column(nullable = false, name = "city")
    private  String city;

    @Column(nullable = false, name = "zip")
    private String zip;

    @Column(nullable = false, name = "state")
    private String state;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
}
