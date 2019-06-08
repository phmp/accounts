package com.accounts.app;

import static spark.Spark.*;

public class AccountsApp {


    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }


}
