package com.example.bank.Controller;

import com.example.bank.Users.Users;
import com.example.bank.Users.Bank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class Controller {


    ArrayList<Users> bankUsers = new ArrayList<Users>();

    @PostMapping("/users")
    public void addUser(@RequestBody Users users) {
        bankUsers.add(users);
    }

    @PostMapping("/users/deposit/{amount}")
    public ResponseEntity addBalance(@PathVariable int amount, @RequestBody Users users) {

        if (bankUsers.get(users.getId()).getId() == (users.getId())) {
            if (bankUsers.get(users.getId()).getPassword().equals(users.getPassword())) {
                bankUsers.get(users.getId()).setBalance(bankUsers.get(users.getId()).getBalance() + amount);
                return ResponseEntity.status(200).body("OK");
            } else {
                return ResponseEntity.status(400).body("Password error");
            }
        } else {
            return ResponseEntity.status(400).body("error");
        }
    }

    @PostMapping("/users/withdraw/{amount}")
    public ResponseEntity withdrawBalance(@PathVariable int amount, @RequestBody Users users) {

        if (bankUsers.get(users.getId()).getId() == (users.getId())) {
            if (bankUsers.get(users.getId()).getPassword().equals(users.getPassword()) && bankUsers.get(users.getId()).getBalance() >= amount) {
                bankUsers.get(users.getId()).setBalance(bankUsers.get(users.getId()).getBalance() - amount);
                return ResponseEntity.status(200).body("OK");
            } else {
                return ResponseEntity.status(400).body("Password error or insufficient balance ");
            }
        } else {
            return ResponseEntity.status(400).body("error");
        }

    }

    @GetMapping("/users")
    public ArrayList<Users> getUser() {
        return bankUsers;
    }

    @PostMapping("/users/requestLoan/{amount}")
    public ResponseEntity requestLoan(@PathVariable int amount, @RequestBody Users users) {
        if (Bank.getBankBalance() >= amount) {
            bankUsers.get(users.getId()).setLoanAmount(bankUsers.get(users.getId()).getLoanAmount() + amount);
            Bank.setBankBalance(Bank.getBankBalance() - amount);
            return ResponseEntity.status(200).body("OK");
        } else {
            return ResponseEntity.status(400).body("insufficient bank balance");
        }
    }

    @PostMapping("/users/returnLoan/{amount}")
    public ResponseEntity returnLoan(@PathVariable int amount, @RequestBody Users users) {
        if (bankUsers.get(users.getId()).getBalance() >= amount && amount <= bankUsers.get(users.getId()).getLoanAmount()) {
            bankUsers.get(users.getId()).setLoanAmount(bankUsers.get(users.getId()).getLoanAmount() - amount);
            bankUsers.get(users.getId()).setBalance(bankUsers.get(users.getId()).getBalance() - amount);
            Bank.setBankBalance(Bank.getBankBalance() + amount);
            return ResponseEntity.status(200).body("OK");
        } else {
            return ResponseEntity.status(400).body("insufficient User balance or amount is larger than the loan");
        }
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {

        if (bankUsers.get(id).getLoanAmount() == 0) {
            bankUsers.remove(id);
            return ResponseEntity.status(200).body("delete was successful");
        } else {
            return ResponseEntity.status(200).body("This user still have a loan");
        }
    }
}
