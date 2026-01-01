package com.vbs.demo.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity     //batana pdta h tu table hai //
@Data          //get username & set username vese hi sabka get and set krta h automatic...also so tostring(),equals()//
@AllArgsConstructor // create a constructor......this.username= username..... this.email= email//
@NoArgsConstructor    //create a empty/default constructor usko hi no arg bolte h //
public class User {
    @Id  //NOT NULL and unique banata haii//
            @GeneratedValue(strategy = GenerationType.IDENTITY) //id dekhke increment ho jayega automatic only for ID and long//
    int id;
    @Column(nullable = false,unique = true)   //NOT NULL hona chaiye
    String username;
    @Column(nullable = false)
    String password;
    @Column(nullable = false,unique = true)
    String email;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String role;
    @Column(nullable = false)
    double balance;
}
