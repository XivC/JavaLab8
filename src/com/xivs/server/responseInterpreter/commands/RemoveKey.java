package com.xivs.server.responseInterpreter.commands;

import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.server.responseInterpreter.Interpreter;
import com.xivs.server.workersManager.exceptions.IllegalDataAccessException;
import com.xivs.server.workersManager.exceptions.NotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class RemoveKey extends Command {
    public RemoveKey(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        String key = (String) rq.attachments.get("key").get();
        ArrayList<String> messages = new ArrayList<>();
        try {
            messages.add("Item [" + manager.removeKey(key, rq.auth.login) + "] deleted");
            return new Response(Response.Status.OK, messages, new HashMap<>());

        } catch (NotFoundException ex) {
            messages.add("Item not found");
        }
        catch(SQLException ex) {
            messages.add("Internal server error");
            logger.error(ex.getMessage());
        }
        catch(IllegalDataAccessException ex){
            messages.add("Illegal item access");
        }


        return new Response(Response.Status.ERROR, messages, new HashMap<>());

    }


}
