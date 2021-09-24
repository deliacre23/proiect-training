package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Country {
    @Id
//    @GeneratedValue  (strategy = GenerationType.IDENTITY)
//    private int id;
    private String id;
    private String name;
    private String flag;


}
