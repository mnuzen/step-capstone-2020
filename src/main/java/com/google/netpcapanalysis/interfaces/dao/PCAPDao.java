package com.google.netpcapanalysis.interfaces.dao;

import com.google.netpcapanalysis.models.PCAPdata;
import java.util.ArrayList;
import com.google.netpcapanalysis.models.FileAttribute;

public interface PCAPDao {

    public FileAttribute getFileAttribute(String searchEntity);
    public ArrayList<FileAttribute> getFileAttributes(String searchEntity);
    public void setFileAttribute(FileAttribute data);

    public ArrayList<PCAPdata> getPCAPObjects(String searchEntity);
    public void setPCAPObjects(ArrayList<PCAPdata>  data, String searchEntity);
}