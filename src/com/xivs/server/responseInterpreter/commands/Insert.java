package com.xivs.server.responseInterpreter.commands;


import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.lab.Worker;
import com.xivs.server.responseInterpreter.Interpreter;
import com.xivs.server.workersManager.exceptions.ExistenceException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class Insert extends Command {
    public Insert(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        ArrayList<String> messages = new ArrayList<>();
        String key = (String) rq.attachments.get("key").get();
        Worker w = (Worker) rq.attachments.get("worker").get();
        try {
            manager.insert(key, w, rq.auth.login);
            messages.add("Item added successfully");
            return new Response(Response.Status.OK, messages, new HashMap<>());
        } catch (ExistenceException ex) {
            messages.add("Item not found");
            return new Response(Response.Status.ERROR, messages, new HashMap<>());

        }
        catch (SQLException ex) {
            messages.add("Internal server error");
            logger.error(ex.getMessage());
            return new Response(Response.Status.ERROR, messages, new HashMap<>());
        }



    }

}
