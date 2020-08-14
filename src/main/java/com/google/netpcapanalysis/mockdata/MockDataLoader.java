package com.google.netpcapanalysis.mockdata;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;

import com.google.netpcapanalysis.models.PCAPdata;
import com.google.netpcapanalysis.dao.PCAPDaoImpl;
import com.google.netpcapanalysis.interfaces.dao.PCAPDao;
import com.google.netpcapanalysis.dao.PCAPParserDaoImpl;
import com.google.netpcapanalysis.interfaces.dao.PCAPParserDao;

public class MockDataLoader {

  private static final String FILE_NAME = "file_1";
  // CSV format:
  // Source,Destination,Domain,Location,Protocal,Size,Flagged,Frequency
  private String CSV_FILE = "data.csv"; // CSV located in project dir /webapp

  public MockDataLoader() {

  }

  public void LoadData(ArrayList<PCAPdata> dataTable) { // uploads to datastore
    PCAPDao dataBase = new PCAPDaoImpl();
    PCAPParserDao parser = new PCAPParserDaoImpl("null");
    dataTable = parser.addDirection(dataTable);

    dataBase.setPCAPObjects(dataTable, FILE_NAME);
  }

  // Source,Destination,Protocal,Size
  public ArrayList<PCAPdata> CSVDataLoader(String csvFile) {
    ArrayList<PCAPdata> data = new ArrayList<PCAPdata>();
    String line = "";
    String cvsSplitBy = ",";

    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

      while ((line = br.readLine()) != null) {

        // use comma as separator
        String[] pcapLine = line.split(cvsSplitBy);

        PCAPdata tempPCAP = new PCAPdata(pcapLine[0], pcapLine[1], pcapLine[2],
            Integer.parseInt(pcapLine[3]));

        data.add(tempPCAP);
      }

    } catch (IOException e) {
      System.out.println("CVS file failed to load");
      e.printStackTrace();
    }
    return data;
  }

  public void CSVDataUpload() {
    LoadData(CSVDataLoader(CSV_FILE));
  }

}
