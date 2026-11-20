package com.yougotcrushed.casst.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "bio")
    private String bio;

    @Column(nullable = false, name = "phoneNumber")
    private String phoneNumber;

    @Column(nullable = false, name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "loyaltyPoints")
    private Integer loyaltyPoints;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    @ToString.Exclude
    private User user;
}
