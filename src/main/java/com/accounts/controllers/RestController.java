package com.accounts.controllers;

import com.accounts.model.ErrorResponse;
import com.accounts.model.TransferResponse;
import com.accounts.storage.AccountRepositoryException;
import com.accounts.storage.AccountsRepository;
import com.accounts.transfer.TransferFailureException;
import com.accounts.model.Account;
import com.accounts.transfer.TransferManager;
import com.google.gson.Gson;
import com.google.inject.Inject;
import spark.Request;
import spark.Response;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.path;

public class RestController {

    private TransferManager manager;
    private AccountsRepository repository;
    private Gson gson = new Gson();

    @Inject
    public RestController(TransferManager manager, AccountsRepository repository) {
        this.manager = manager;
        this.repository = repository;
    }

    public void setupEndpoints() {
        path("/accounts", () -> {
            get("", this::listAccountRoute, gson::toJson);
            get("/", this::listAccountRoute, gson::toJson);
            get("/:id", this::getAccountRoute, gson::toJson);
            get("/:id/new/:amount", this::createAccountWithInitValueRoute, gson::toJson);
            get("/:from/transfer/:to/:amount", this::transferRoute, gson::toJson);
        });
        exception(AccountRepositoryException.class, (exception, request, response) -> {
            response.body(gson.toJson(new ErrorResponse(exception.getMessage())));
        });
        exception(TransferFailureException.class, (exception, request, response) -> {
            response.body(gson.toJson(TransferResponse.failedTranser(exception.getMessage())));
        });
    }

    private Account getAccountRoute(Request req, Response res) {
        String id = req.params(":id");
        return repository.getAccount(id);
    }

    private Account createAccountWithInitValueRoute(Request req, Response res) {
        String id = req.params(":id");
        String amount = req.params(":amount");
        repository.addAccount(id, new BigInteger(amount));
        return repository.getAccount(id);
    }

    private Collection<Account> listAccountRoute(Request req, Response res) {
        return repository.getAccounts();
    }

    private TransferResponse transferRoute(Request req, Response res) {
        String from = req.params(":from");
        String to = req.params(":to");
        String amount = req.params(":amount");
        manager.transfer(from, to , new BigInteger(amount));
        return TransferResponse.successedTranser();
    }
}
