package com.accounts.configuration;

import com.accounts.controllers.AccountsRepository;
import com.accounts.controllers.TransactionManager;
import com.google.inject.Inject;
import spark.Request;
import spark.Response;

import java.math.BigInteger;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;

public class RestController {

    private TransactionManager manager;
    private AccountsRepository repository;

    @Inject
    public RestController(TransactionManager manager, AccountsRepository repository) {
        this.manager = manager;
        this.repository = repository;
    }

    public void setupEndpoints() {
        path("/accounts", () -> {
            get("", this::listAccountRoute);
            get("/", this::listAccountRoute);
            get("/:id", this::createAccountRoute);
            get("/:id/new/:amount", this::createAccountWithInitValueRoute);
            get("/:from/transfer/:to/:amount", this::transferRoute);
        });
    }

    private String createAccountRoute(Request req, Response res) {
        String id = req.params(":id");
        repository.addAccount(id);
        return id + " - created";
    }

    private String createAccountWithInitValueRoute(Request req, Response res) {
        String id = req.params(":id");
        String amount = req.params(":amount");
        repository.addAccount(id, new BigInteger(amount));
        return id + " - created";
    }

    private String listAccountRoute(Request req, Response res) {
        return repository.getAccounts().toString();
    }

    private String transferRoute(Request req, Response res) {
        String from = req.params(":from");
        String to = req.params(":to");
        String amount = req.params(":amount");
        manager.transfer(from, to , new BigInteger(amount));
        return repository.getAccounts().toString();
    }
}
