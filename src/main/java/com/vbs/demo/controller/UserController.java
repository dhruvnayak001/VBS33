package com.vbs.demo.controller;

import com.vbs.demo.models.*;
import com.vbs.demo.repositories.HistoryRepo;
import com.vbs.demo.repositories.TransactionRepo;
import com.vbs.demo.repositories.UserRepo;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController                      //batayega tu manager hai//
@CrossOrigin(origins = "*")          //cors error bolte h isko..usko thik krta h ... 2 poort ke bich mai data share ho sakta h.... hamare isme 2 port ke isme data share nahk ho sakta for securtity resons .... permission dete h ham//
public class UserController {
    @Autowired                       //UserReppo is an interface uska obj nhi ban sakta toh autowired sab sambhal lega//
    UserRepo userRepo;
    @Autowired                       //UserReppo is an interface uska obj nhi ban sakta toh autowired sab sambhal lega//
    HistoryRepo historyRepo;
    @Autowired                       //UserReppo is an interface uska obj nhi ban sakta toh autowired sab sambhal lega//
    TransactionRepo transactionRepo;

    @PostMapping("/register")        // manager ko bolta h ki post aya h register pe (trigger ho gaya)
    public String register(@RequestBody User user) //User class mai sab table hai na toh uska obj banayenge and then User repo( majdoor) ko bolenge usse save kr DB mai//
    {
        userRepo.save(user);  //User repo interface ka obj banaya and usko(majdoor) bola save kr user ko//
        return"Signup Succesfull";
    }


    @PostMapping("/login")
    public String login(@RequestBody LoginDto u)             //logindto is class uska u object banaya and jaha 6 ane vala tha user use krke vaha 3 ayega toh space waste nmahi hoga//
    {
        User user = userRepo.findByUsername(u.getUsername());   //isme database mai username ham search krenge because voi unique hai and agar same username mila toh pura ka pura 6 user mai ayega//
        if(user==null){                                         // fir compare agar username DB mai nahi mila then user mai null jayega //
            return "User not found";
        }
        if(!u.getPassword().equals(user.getPassword())){        // get use krna pada because u and user alag class ka object hai unka acces dusre classes ko nahi milta hai .... unka acces sirf unke class purta hi hota h//
            return "Password not found";                        // isiliyue batana pda u ka get(Password)//
        }
        if(!u.getRole().equals(user.getRole())){
            return "Role not found";
        }

        return String.valueOf(user.getId());                  //  yaha tak pocha hai toh banda sahi hai and DB mai naam mila hai and print kiya uska ID next page pe dashboard hai soo vaha welcome dhruv ID=45 aisa print hoga something//
    }

    @GetMapping("/get-details/{id}")
    public DisplayDto display(@PathVariable int id){
        User user = userRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
        DisplayDto displayDto = new DisplayDto();
        displayDto.setUsername(user.getUsername());
        displayDto.setBalance(user.getBalance());
        return displayDto;
    }

    @PostMapping("/add/{adminId}")
    public String add(@RequestBody User user , @PathVariable int adminId){
        History h1 = new History();
        h1.setDescription("Admin " + adminId + " Created User " + user.getUsername());
        historyRepo.save(h1);

        userRepo.save(user);

        if(user.getBalance()>0){
            Transaction t = new Transaction();
            t.setAmount(user.getBalance());
            t.setUserId(user.getId());
            t.setCurrBalance(user.getBalance());
            t.setDescription("Rs "+user.getBalance()+" Deposite Successfull");
            transactionRepo.save(t);
        }
        return "Added Successfully";
    }

    @GetMapping("/users")
    public List<User> getALLUsers(@RequestParam String sortBy , @RequestParam String order){
        Sort  sort;
        if(order . equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }
        else {
            sort = Sort.by(sortBy).ascending();
        }

        return userRepo.findAllByRole("customer",sort);
    }

    @GetMapping("/users/{keyword}")
    public List<User> getUser(@PathVariable String keyword){
        return userRepo.findByUsernameContainingIgnoreCaseAndRole(keyword,"customer");
    }

    @DeleteMapping("delete-user/{userId}/admin/{adminId}")
    public String delete(@PathVariable int userId, @PathVariable int adminId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Not Found"));
        if (user.getBalance() > 0) {
            return "Balance should be zero";
        }
        userRepo.delete(user);

        History h1 = new History();
        h1.setDescription("Admin " + adminId + " Deleted User " + user.getUsername());
        historyRepo.save(h1);

        return "User Deleted Successfully";

    }


}


