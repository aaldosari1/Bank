package com.example.bank.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import com.example.bank.Users.Bank;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BankController {

    @GetMapping("/bankBalance")
    public int getBankBalance() {
        return Bank.getBankBalance();
    }

}
