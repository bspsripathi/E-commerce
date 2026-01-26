package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="user_table")
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name="city_name")
    private String city;
}
