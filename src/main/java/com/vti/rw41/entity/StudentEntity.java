package com.vti.rw41.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Student")
public class StudentEntity extends BaseEntity{
    @Id
    @GenericGenerator(//khai bao
            name ="StudentIdGenerator",
    strategy = "com.vti.rw41.entity.generator.StudentIdGenerator")
    @GeneratedValue(generator ="StudentIdGenerator" )//su dung
    private String id;//Syyyyxxxxx->S202200001,S202200002

    private String name;
}
