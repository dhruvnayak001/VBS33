package com.vbs.demo.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int Id;
    @Column(nullable = false)
    double amount;
    @Column(nullable = false)
    double currBalance;
    @Column(nullable = false)
    String description;
    @Column(nullable = false)
    int userId;
    @Column(nullable = false)
    LocalDateTime date;

    @PrePersist                   //ye function call hona chaiye jab transaction succesfull ho jaye and transaction succesfull hone ke liye .. transaction class ka objkect bannana jaruri hai .... toh har object creation pe ye function trigger krneka kaam krta h prepersist annotatioon//
    protected void onCreate(){
        this.date = LocalDateTime.now();
    }
}
