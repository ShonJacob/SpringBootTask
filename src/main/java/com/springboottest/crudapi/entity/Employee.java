package com.springboottest.crudapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private long emp_id;

    @Column(name = "emp_name")
    private String emp_name;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "dept_id", referencedColumnName = "dept_id")
    private Department department;
}
