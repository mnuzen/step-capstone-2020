package com.google.netpcapanalysis.servlets;

import java.util.ArrayList;

import com.google.netpcapanalysis.models.FileAttribute;
import com.google.netpcapanalysis.models.PCAPdata;
import com.google.netpcapanalysis.dao.PCAPDaoImpl;
import com.google.netpcapanalysis.interfaces.dao.PCAPDao;
import com.google.netpcapanalysis.dao.MaliciousIPDaoImpl;
import com.google.netpcapanalysis.interfaces.dao.MaliciousIPDao;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.netpcapanalysis.utils.SessionManager;
import com.google.netpcapanalysis.utils.NetUtils;

@WebServlet("/data-table")
public class TableServlet extends HttpServlet {

  private PCAPDao datastore = new PCAPDaoImpl();
  private MaliciousIPDao maliciousLookup = new MaliciousIPDaoImpl();
  private ArrayList<PCAPdata> dataTable;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String entityName = SessionManager.getSessionEntity(request);
    FileAttribute entity = datastore.getFileAttribute(entityName);

    //will need to run lookups for Domain/location to display for datatable
    dataTable = maliciousLookup.run(datastore.getPCAPObjects(entityName), entity.myIP);

    String json = NetUtils.convertPCAPdataToJson(dataTable);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

}
