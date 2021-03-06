package com.google.netpcapanalysis.dao;

import com.google.netpcapanalysis.models.PCAPdata;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import com.google.netpcapanalysis.interfaces.dao.FrequencyDao;

public class FrequencyDaoImpl implements FrequencyDao {
  private ArrayList<PCAPdata> allPCAP; 
  private LinkedHashMap<String, Integer> finalMap;

  private String myip = "";

  public FrequencyDaoImpl(ArrayList<PCAPdata> packets) {
    allPCAP = packets; 
    findMyIP();
    processData();
  }

  public ArrayList<PCAPdata> getAllPCAP() {
    return allPCAP;
  }

  /* Find local IP address based on highest recurring IP address */
  private void findMyIP() {
    HashMap<String, Integer> hm = new HashMap<String, Integer>();
    for (PCAPdata packet : allPCAP) {
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
  }

  public String getMyIP() {
    return myip;
  }

  /* Fills out finalMap with frequencies based on IP address only (not protocol). */
  private void processData(){
    //LinkedHashMap<String, Integer> hm = getUniqueOutIPs();
    getFrequentIPs(getUniqueOutIPs());
  } 
  
  /* Sort IPs by frequency and get most frequent addresses*/
  private void getFrequentIPs(LinkedHashMap<String, Integer> hm) {
    Set<Map.Entry<String, Integer>> set = hm.entrySet();
    List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(set);
    Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
      @Override
      public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
        return e2.getValue().compareTo(e1.getValue()); // reverse order
      }
    });

    finalMap = new LinkedHashMap<String, Integer>();
    for(Map.Entry<String, Integer> map : entries){
      finalMap.put(map.getKey(), map.getValue());
    }

  }

  /* Gets all unique outgoing IPs and puts OUTIP as key in hashmap, where value represents frequency */
  private LinkedHashMap<String, Integer> getUniqueOutIPs() {
    LinkedHashMap<String, Integer> hm = new LinkedHashMap<String, Integer>();
    for (PCAPdata packet : allPCAP) {
      String srcip = packet.source;
      String dstip = packet.destination;
      String outip = "";

      if (srcip.equals(myip)) {
        outip = dstip;
      }
      else {
        outip = srcip;
      }
      
      if (hm.containsKey(outip)){
        // retrieve current value with outip and increments frequency
        hm.merge(outip, 1, Integer::sum);
      }
      else {
        hm.put(outip, 1);
      }
    }
    return hm;
  }

  public LinkedHashMap<String, Integer> getFinalMap() {
    return finalMap;
  }

}