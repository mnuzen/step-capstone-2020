package com.google.netpcapanalysis.dao;

import com.google.netpcapanalysis.models.PCAPdata;
import com.google.netpcapanalysis.interfaces.dao.UtilityPCAPDao;
import com.google.netpcapanalysis.models.Flagged;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Collections;
import java.util.Map;

public class UtilityPCAPDaoImpl implements UtilityPCAPDao {

  public UtilityPCAPDaoImpl() {

  }

 //Gets most use IP in PCAPdata
 public String findMyIP(ArrayList<PCAPdata> allData) {
  String myip = "";
  HashMap<String, Integer> hm = new HashMap<String, Integer>();
  for (PCAPdata packet : allData) {
    // source
    if (hm.containsKey(packet.source)) { 
      // if IP already exists, increment
      hm.merge(packet.source, 1, Integer::sum);
    }
    else {
      hm.put(packet.source, 1);
    }
    // destination
    if (hm.containsKey(packet.destination)) { 
      // if IP already exists, increment
      hm.merge(packet.destination, 1, Integer::sum);
    }
    else {
      hm.put(packet.destination, 1);
    }
  }
  // find largest recurrence
  myip = Collections.max(hm.entrySet(), Map.Entry.comparingByValue()).getKey();
  return myip;
}

//Finds all unique IPs and sets myip to source
public ArrayList<PCAPdata> getUniqueIPs(ArrayList<PCAPdata> allData){
  HashMap<String, PCAPdata> finalMap = new HashMap<String, PCAPdata>();
  String myip = findMyIP(allData);;
  String outip = "";

  for (PCAPdata packet : allData) {

    //swaps packet order based on myip
    if (packet.source.equals(myip)) {
      outip = packet.destination;
    }
    else {
      outip = packet.source;
    }
    
    //puts data into map if not already there
    if (!finalMap.containsKey(outip)){
      PCAPdata tempPCAP = new PCAPdata(myip, outip, packet.protocol, packet.size); 
      finalMap.put(outip, tempPCAP);
    }
  }
  return (new ArrayList<PCAPdata>(finalMap.values()));
}

}
