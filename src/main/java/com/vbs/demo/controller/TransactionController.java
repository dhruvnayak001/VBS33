package com.vbs.demo.controller;


import com.vbs.demo.models.*;
import com.vbs.demo.repositories.TransactionRepo;
import com.vbs.demo.repositories.UserRepo;
import jakarta.persistence.Id;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")

public class TransactionController {
    @Autowired                       //UserReppo is an interface uska obj nhi ban sakta toh autowired sab sambhal lega//
    UserRepo userRepo;
    @Autowired
    TransactionRepo transactionRepo;

    @PostMapping("/deposit")
    public String deposit(@RequestBody TransactionDto obj){
        User user = userRepo.findById(obj.getId())
                .orElseThrow(()-> new RuntimeException("Not found"));
        double newBalance = user.getBalance() + obj.getAmount();
        user.setBalance(newBalance);
        userRepo.save(user);

        Transaction t = new Transaction();
        t.setAmount(obj.getAmount());
        t.setCurrBalance(newBalance);
        t.setDescription("Rs "+obj.getAmount()+" Deposit successful");
        t.setUserId(user.getId());



        transactionRepo.save(t);
        return "Deposit successful!";


    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestBody TransactionDto obj) {
        User user = userRepo.findById(obj.getId())
                .orElseThrow(() -> new RuntimeException("Not found"));
        double newBalance = user.getBalance() - obj.getAmount();
        if(newBalance<0)
        {
            return "Balance Insuffcient";
        }
        user.setBalance(newBalance);
        userRepo.save(user);

        Transaction t = new Transaction();
        t.setAmount(obj.getAmount());
        t.setCurrBalance(newBalance);
        t.setDescription("Rs " + obj.getAmount() + " Withdrawal Succesful");
        t.setUserId(user.getId());


        transactionRepo.save(t);
        return "Withdrawal Succesful";

    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferDto obj){
        User sender = userRepo.findById(obj.getId())
                .orElseThrow(()-> new RuntimeException("Not found"));
        User rec = userRepo.findByUsername(obj.getUsername());

        if(rec == null)  return "Username Not Found";
        if (sender.getId() == rec.getId()) return "Self Transaction Not allowed";
        if(obj.getAmount()<1) return "Invalid Amount";

        double sbalance = sender.getBalance() - obj.getAmount();
        double rbalance = rec.getBalance() + obj.getAmount();

        if(sbalance < 0) return "Insufficient balance";


        sender.setBalance(sbalance);
        rec.setBalance(rbalance);

        userRepo.save(sender);
        userRepo.save(rec);

        Transaction t1 = new Transaction();
        Transaction t2 = new Transaction();

        t1.setAmount(obj.getAmount());
        t1.setCurrBalance(sbalance);
        t1.setDescription("Rs "+obj.getAmount()+" sent to user "+rec.getUsername());
        t1.setUserId(obj.getId());

        t2.setAmount(obj.getAmount());
        t2.setCurrBalance(rbalance);
        t2.setDescription("Rs "+obj.getAmount()+"recieved from user "+sender.getUsername());
        t2.setUserId(rec.getId());

        transactionRepo.save(t1);
        transactionRepo.save(t2);

        return "Transfer Done Successfully";



    }


    @GetMapping("/passbook/{id}")
    public List<Transaction> getPassbook(@PathVariable int id)
    {
        return transactionRepo.findAllByUserId(id);
    }

    @PostMapping("/update")
    public String update(@RequestBody UpdateDto obj){
        User user = userRepo.findById(obj.getId())
                .orElseThrow(()->new RuntimeException("Not found"));

        if(obj.getKey().equalsIgnoreCase("name")){
            if(user.getName().equals(obj.getValue())){
                return "Cannot Be Same";
            }

            user.setName(obj.getValue());
        }

        else if(obj.getKey().equalsIgnoreCase("password")){
            if(user.getPassword().equals(obj.getValue())){
                return "Cannot Be Same";
            }

            user.setPassword(obj.getValue());
        }

        else if(obj.getKey().equalsIgnoreCase("email")){
            if(user.getEmail().equals(obj.getValue())){
                return "Cannot Be Same";
            }

            User user2 = userRepo.findByEmail(obj.getValue());
            if(user2 != null){
                return "Email Already Exist";
            }

            user.setEmail(obj.getValue());
        }
        else
        {
            return "Invalid key";
        }

        userRepo.save(user);
        return "Update successfully";

    }
}
