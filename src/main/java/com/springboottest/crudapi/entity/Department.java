package com.springboottest.crudapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private long dept_id;

    @Column(name = "dept_name")
    private String dept_name;

    @Column(name = "dept_location")
    private String dept_location;

    //@JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
    @OneToMany(mappedBy = "department")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Employee> employees;
}
