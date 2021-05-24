package com.xivs.server.responseInterpreter.commands;


import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.server.responseInterpreter.Interpreter;
import com.xivs.server.workersManager.exceptions.IllegalDataAccessException;
import com.xivs.server.workersManager.exceptions.NotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class ReplaceIfLower extends Command {
    public ReplaceIfLower(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        ArrayList<String> messages = new ArrayList<>();
        String key = (String) rq.attachments.get("key").get();
        Float salary = (Float) rq.attachments.get("salary").get();
        try {
            if (manager.replaceIfLower(key, salary, rq.auth.login)) {
                messages.add("Item updated");
            } else {
                messages.add("Item not updated");
            }
        } catch (NotFoundException ex) {
            messages.add("Item not found");
        }
        catch(SQLException ex) {
            messages.add("Internal server error");
        }
        catch(IllegalDataAccessException ex){
            messages.add("Illegal item access");
        }

        return new Response(Response.Status.OK, messages, new HashMap<>());


    }
}