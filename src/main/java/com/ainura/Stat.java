package com.ainura;

import java.util.Date;

public class Stat {
    private Date dats;
    private String serv;
    private Long upl;
    private Long dl;
    private String fl;

    public Stat(Date dats, String serv, Long upl, Long dl, String fl) {
        this.dats = dats;
        this.serv = serv;
        this.upl = upl;
        this.dl = dl;
        this.fl = fl;
    }

    public Date getDats() {
        return dats;
    }

    public void setDats(Date dats) {
        this.dats = dats;
    }

    public String getServ() {
        return serv;
    }

    public void setServ(String serv) {
        this.serv = serv;
    }

    public Long getUpl() {
        return upl;
    }

    public void setUpl(Long upl) {
        this.upl = upl;
    }

    public Long getDl() {
        return dl;
    }

    public void setDl(Long dl) {
        this.dl = dl;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }
}
