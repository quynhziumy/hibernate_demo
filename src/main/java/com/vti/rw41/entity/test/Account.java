package com.vti.rw41.entity.test;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")
    private  Integer id;

    private String username;
    private String email;
    private String firstName;
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "DepartmentID")
    private Department department;
}
