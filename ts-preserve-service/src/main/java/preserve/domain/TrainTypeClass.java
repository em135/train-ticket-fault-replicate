package preserve.domain;

import javax.validation.Valid;


public class TrainTypeClass {
    @Valid
    private String id;

    @Valid
    private int economyClass;

    @Valid
    private int confortClass;

    public TrainTypeClass(){
        //Default Constructor
    }

    public TrainTypeClass(String id, int economyClass, int confortClass) {
        this.id = id;
        this.economyClass = economyClass;
        this.confortClass = confortClass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEconomyClass() {
        return economyClass;
    }

    public void setEconomyClass(int economyClass) {
        this.economyClass = economyClass;
    }

    public int getConfortClass() {
        return confortClass;
    }

    public void setConfortClass(int confortClass) {
        this.confortClass = confortClass;
    }
}