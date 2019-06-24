package com.accounts.controllers;

import com.accounts.storage.IncorrectRequestedDataException;
import com.accounts.transfer.TransferFailureException;
import com.google.gson.Gson;
import com.google.inject.Inject;

import static spark.Spark.*;

public class RestController {

    private RestRouter router;
    private Gson gson = new Gson();

    @Inject
    public RestController(RestRouter router) {
        this.router = router;
    }

    public void setupEndpoints() {
        path("/accounts", () -> {
            get("", router::listAccountRoute, gson::toJson);
            get("/", router::listAccountRoute, gson::toJson);
            get("/:id", router::getAccountRoute, gson::toJson);
            get("/:id/new/:amount", router::createAccountRoute, gson::toJson);
            get("/:from/transfer/:to/:amount", router::transferRoute, gson::toJson);
        });
        exception(IncorrectRequestedDataException.class, router::wrongIdRoute);
        exception(TransferFailureException.class, router::transferErrorRoute);
    }

}
