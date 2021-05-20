package com.xivs.server.responseInterpreter.commands;


import com.xivs.common.dataTransfer.DataTransference;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.server.responseInterpreter.Interpreter;

import java.util.ArrayList;
import java.util.HashMap;

public class Sync extends Command {


    public Sync(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {

        HashMap<String, DataTransference<?>> attachments = new HashMap<>();
        attachments.put("workers", new DataTransference<>(HashMap.class, manager.getWorkers()));
        attachments.put("keyOwnerRelating", new DataTransference<>(HashMap.class, manager.getKeyOwnerRelating()));
        return new Response(Response.Status.OK, new ArrayList<>(), attachments);

    }

}
