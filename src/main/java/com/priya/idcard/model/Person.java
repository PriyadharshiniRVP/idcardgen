package com.priya.idcard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.awt.*;

import jakarta.persistence.*;


@Entity
@Table(name="idcard")

public class Person {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String department;
    private String city;
    private String photopath;


    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }

    public String getName(){
        return name;
    }
   public void setName(String name){
        this.name=name;
   }


   public String getDepartment(){
       return department;

   }
   public void setDepartment(String department){
        this.department=department;
   }


    public void setCity(String city){
        this.city=city;
    }
   public String getCity(){
        return city;
   }
  public String getPhotopath(){
        return photopath;
    }

    public void setPhotopath(){
        this.photopath=photopath;
    }




}
