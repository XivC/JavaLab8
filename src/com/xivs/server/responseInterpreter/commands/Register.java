package com.xivs.server.responseInterpreter.commands;

import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.server.database.exceptions.UserAlreadyExistsException;
import com.xivs.server.responseInterpreter.Interpreter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Register extends Command{
    public Register(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        ArrayList<String> messages = new ArrayList<>();
        try {
            interpreter.controller.register(rq.auth.login, rq.auth.password);

        }
        catch(SQLException ex){
            messages.add("Internal server error");
            logger.error(ex.getMessage());
            return new Response(Response.Status.ERROR, messages, new HashMap<>());
        }
        catch(UserAlreadyExistsException ex){
            messages.add("Username already exists");
            return new Response(Response.Status.ERROR, messages, new HashMap<>());

        }



        return new Response(Response.Status.OK, messages, new HashMap<>());
    }
}
