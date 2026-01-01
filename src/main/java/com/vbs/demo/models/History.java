package com.vbs.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    String description;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    LocalDateTime data;

    @PrePersist                   //ye function call hona chaiye jab transaction succesfull ho jaye and transaction succesfull hone ke liye .. transaction class ka objkect bannana jaruri hai .... toh har object creation pe ye function trigger krneka kaam krta h prepersist annotatioon//
    protected void onCreate(){
        this.data = LocalDateTime.now();
    }

}
