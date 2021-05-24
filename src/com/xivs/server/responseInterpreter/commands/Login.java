package com.xivs.server.responseInterpreter.commands;

import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.server.responseInterpreter.Interpreter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Login extends Command {
    public Login(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        ArrayList<String> messages = new ArrayList<>();
        try {
            if (interpreter.controller.checkExistence(rq.auth.login, rq.auth.password)) messages.add("Authorization complete successfully");
            else {
                messages.add("Account doesn't match");
                return new Response(Response.Status.ERROR, messages, new HashMap<>());
            }

        }
        catch(SQLException ex){
                messages.add("Internal server error");
                logger.error(ex.getMessage());
            return new Response(Response.Status.ERROR, messages, new HashMap<>());
        }



        return new Response(Response.Status.OK, messages, new HashMap<>());
    }
}
