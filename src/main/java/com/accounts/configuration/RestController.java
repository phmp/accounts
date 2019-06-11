package com.accounts.configuration;

import com.accounts.controllers.AccountsRepository;
import com.accounts.controllers.TransferManager;
import com.accounts.resopnse.ErrorResponse;
import com.accounts.controllers.transfers.TransferFailureException;
import com.accounts.model.Account;
import com.google.gson.Gson;
import com.google.inject.Inject;
import spark.Request;
import spark.Response;

import java.math.BigInteger;
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
        exception(TransferFailureException.class, (exception, request, response) -> {
            response.body(gson.toJson(new ErrorResponse(exception.getMessage())));
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

    private List<Account> listAccountRoute(Request req, Response res) {
        return repository.getAccounts();
    }

    private String transferRoute(Request req, Response res) {
        String from = req.params(":from");
        String to = req.params(":to");
        String amount = req.params(":amount");
        manager.transfer(from, to , new BigInteger(amount));
        return repository.getAccounts().toString();
    }
}
