package com.google.netpcapanalysis.interfaces.dao;

import com.google.netpcapanalysis.models.PCAPdata;
import com.google.netpcapanalysis.models.MaliciousPacket;
import java.util.ArrayList;

public interface PCAPDao {

    public ArrayList<PCAPdata> getPCAPObjects(String searchEntity);
    public void setPCAPObjects(ArrayList<PCAPdata>  data, String searchEntity);
    
    public String searchMaliciousDB(String seachIP);
    public void setMaliciousIPObjects(MaliciousPacket data);
}