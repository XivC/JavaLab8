package com.xivs.common.Utils;

import com.xivs.common.lab.*;

import java.time.LocalDate;

public class WorkerContainer{
    public final String owner;;
    public final String key;
    public final long id;
    public final String name;
    public final LocalDate creationDate;
    public final float salary;
    public final LocalDate endDate;
    public final Position position;
    public final Status status;
    public final Long x;
    public final Double y;
    public Integer annualTurnover;
    public OrganizationType type;
    public String street;
    public String zipCode;
    public WorkerContainer(String key, String owner, Worker worker){

        this.key = key;
        this.id = worker.id;
        this.owner = owner;
        this.name = worker.name;
        this.creationDate = worker.creationDate;
        this.salary = worker.salary;
        this.endDate = worker.endDate;
        this.status = worker.status;
        this.position = worker.position;
        this.x = worker.coordinates.x;
        this.y = worker.coordinates.y;
        this.street = worker.organization.officialAddress.street;
        this.zipCode = worker.organization.officialAddress.zipCode;
        this.annualTurnover = worker.organization.annualTurnover;
        this.type = worker.organization.type;


    }

}
