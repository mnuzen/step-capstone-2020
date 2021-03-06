package com.google.netpcapanalysis.servlets;

import java.util.ArrayList;

import com.google.netpcapanalysis.models.FileAttribute;
import com.google.netpcapanalysis.models.PCAPdata;
import com.google.netpcapanalysis.dao.PCAPDaoImpl;
import com.google.netpcapanalysis.interfaces.dao.PCAPDao;
import com.google.netpcapanalysis.interfaces.dao.ReverseDNSLookupDao;
import com.google.netpcapanalysis.dao.MaliciousIPDaoImpl;
import com.google.netpcapanalysis.interfaces.dao.MaliciousIPDao;
import com.google.netpcapanalysis.dao.GeolocationDaoImpl;
import com.google.netpcapanalysis.interfaces.dao.GeolocationDao;
import com.google.netpcapanalysis.dao.ReverseDNSLookupDaoImpl;
import com.google.netpcapanalysis.interfaces.dao.ReverseDNSLookupDao;

import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.netpcapanalysis.utils.SessionManager;
import com.google.netpcapanalysis.utils.UtilityPCAP;
import com.google.netpcapanalysis.utils.NetUtils;

@WebServlet("/data-malicious")
public class MaliciousServlet extends HttpServlet {

  private PCAPDao datastore = new PCAPDaoImpl();
  private MaliciousIPDao maliciousLookup = new MaliciousIPDaoImpl();
  private GeolocationDao locationLookup = new GeolocationDaoImpl();
  private ReverseDNSLookupDao dnsLookup = new ReverseDNSLookupDaoImpl();
  private List<PCAPdata> dataTable;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String entityName = SessionManager.getSessionEntity(request);
    FileAttribute entity = datastore.getFileAttribute(entityName);

    dataTable = datastore.getPCAPObjects(entityName);
    dataTable = UtilityPCAP.getUniqueIPs(dataTable);
    dataTable = maliciousLookup.run(dataTable, entity.myIP);
    dataTable = locationLookup.getLocation(dataTable);
    dataTable = dnsLookup.dnsLookup(dataTable);

    String json = NetUtils.convertPCAPdataToJson(dataTable);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

}
