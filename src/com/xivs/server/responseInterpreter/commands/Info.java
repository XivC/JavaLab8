package com.xivs.server.responseInterpreter.commands;


import com.xivs.common.dataTransfer.DataTransference;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.server.responseInterpreter.Interpreter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Info extends Command {
    public Info(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {

        ArrayList<String> messages = new ArrayList<>();
        messages.add(String.valueOf(manager.getWorkers().keySet().size()));
        messages.add(manager.getClass().toString());
        HashMap<String, DataTransference<?>> attachments = new HashMap<>();
        attachments.put("creationDate", new DataTransference<>(LocalDateTime.class, manager.creationTime));
        return new Response(Response.Status.OK, messages, attachments);


    }

}
