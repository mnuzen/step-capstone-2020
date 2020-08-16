package com.google.netpcapanalysis.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.netpcapanalysis.dao.PCAPDaoImpl;
import com.google.netpcapanalysis.interfaces.dao.PCAPDao;
import com.google.netpcapanalysis.dao.MaliciousIPDaoImpl;
import com.google.netpcapanalysis.interfaces.dao.MaliciousIPDao;
import com.google.netpcapanalysis.models.PCAPdata;
import com.google.netpcapanalysis.dao.UtilityPCAPDaoImpl;
import com.google.netpcapanalysis.interfaces.dao.UtilityPCAPDao;
import com.google.netpcapanalysis.models.FileAttribute;
import com.google.netpcapanalysis.models.Flagged;
import com.google.netpcapanalysis.models.MaliciousPacket;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Collections;
import java.util.Map;
import java.util.Arrays;


@WebServlet("/test")
public class TestServlet extends HttpServlet {
  private static final String FILE_NAME = "file_1";
  private UtilityPCAPDao pcapUtility = new UtilityPCAPDaoImpl();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

      PCAPDao dataBase = new PCAPDaoImpl();

      for(int i =1;i<5;i++){

        FileAttribute data = new FileAttribute("Hashed_Name_" + String.valueOf(i) 
        , "/File_" + String.valueOf(i), "192.168.1." + String.valueOf(i));
        dataBase.setFileAttribute(data);
      }

        response.sendRedirect("/index.html");
    }

}