/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.business;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import praveen.odr.constants.Constants;

import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import praveen.odr.dao.FilePlacementDAO;
import praveen.odr.dao.LocationManagerDAO;
import praveen.odr.dao.impl.FilePlacementDAOImpl;
import praveen.odr.dao.impl.LocationManagerDAOImpl;
import praveen.odr.exception.ODRDataAccessException;
import praveen.odr.model.FilePlacing;
import praveen.odr.model.ServerNode;

/**
 *
 * @author Praveen Sankarasubramanian
 */
public class Centrality {

    private static String START;
    private static String END;
    private static boolean flag;
    private static final LocationManagerDAO managerDAO = new LocationManagerDAOImpl();
    private static final FilePlacementDAO filePlacementDAO = new FilePlacementDAOImpl();
    public static int count = 0;
    public static int node = 0;
    public static int centr = 0;
    public static int read = 0;
    public static int write = 0;
   // public static int nodecount = 0;
    public static int totlanumberofsp = 0;
    public static HashMap<String, Integer> root = new HashMap<>();
    public static HashMap<String, String> vall = new HashMap<>();
    public static HashMap<String, Integer> si = new HashMap<>();
    public static HashMap<String, Integer> rep = new HashMap<>();
    public static HashMap<String, Integer> root1 = new HashMap<>();

    public static HashMap<String, Integer> cen = new HashMap<>();

    public static HashMap<String, Integer> repli = new HashMap<>();

    public static ArrayList<Integer> segment = new ArrayList<>();

    public static void fragmentation(int size, int fid)  {
    int nodecount = 0;
        try {

            // write new line
            List<?> pla = new ArrayList<>();
            Map<String, Integer> nodeAndCapacity = new HashMap<>();
            List<ServerNode> serverNodes = managerDAO.getLocations();
            for (ServerNode serverNode:serverNodes) {
               // String jj = rs.getString(2);
                nodeAndCapacity.put(serverNode.getId()+"", Integer.parseInt(serverNode.getCapacity()));
                nodecount = nodecount + 1;
            }
            List<Entry<String, Integer>> list = sortByValueInDecreasingOrder(nodeAndCapacity);
            int i = 0;
            PrintWriter writer1 = new PrintWriter(Constants.FRAGMENT_FILE_LOCATION);//F://Project
            writer1.print("");
            writer1.close();
            System.out.println(nodecount);
            int cc = nodecount + 1;
            String hj = String.valueOf(cc);
            try {
                FileWriter writer = new FileWriter(Constants.FRAGMENT_FILE_LOCATION, true);
                writer.write(hj);
                writer.write("\r\n");
                writer.write("\r\n");
                for (Map.Entry<String, Integer> entry1 : list) {
                    String nodeId = (String) entry1.getKey();
                    String capacity = String.valueOf(entry1.getValue());

                    i = i + 1;
                    String so = "0," + nodeId;
                    String so1 = nodeId + ",1";
                    root.put(so, Integer.parseInt(capacity));
                    root.put(so1, Integer.parseInt(capacity));
                    System.out.println(so + " ========= " + capacity);
                    //System.out.println(nodeId+"   ============================    "+capacity);
                    int j = 0;
                    for (Map.Entry<String, Integer> entry2 : list) {
                        j = j + 1;

                        String g = (String) entry2.getKey();
                        String g1 = String.valueOf(entry2.getValue());
                        if (nodeId == null ? g != null : !nodeId.equals(g)) {
                            String pair = nodeId + "," + g;

                            int k = Integer.parseInt(capacity);

                            int k1 = Integer.parseInt(g1);
                            int distance = 0;
                            if (k >= k1) {
                                distance = k - k1;
                            } else {
                                distance = k1 - k;
                            }
                            root.put(pair, distance);
                            writer.write(nodeId + " " + g + " " + distance);
                            writer.write("\r\n");
                            vall.put(nodeId, g);
                            System.out.println(pair + " ========= " + distance);

                        }

                    }
                }
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Entry<String, Integer>> list1 = sortByValueInDecreasingOrder(root);
            //centrality calculation
            for (int jk = 1; jk <= nodecount; jk++) {

                centr = 0;
                node = jk;
                for (Map.Entry<String, String> entry1 : vall.entrySet()) {
                    count = 0;
                    h = 0;
                    totlanumberofsp = 0;
                    START = (String) entry1.getKey();
                    END = String.valueOf(entry1.getValue());
                    LinkedList<String> visited = new LinkedList<>();
                    visited.add(START);
                    Random randomGenerator = new Random();
                    totlanumberofsp = randomGenerator.nextInt(100);
                    count = randomGenerator.nextInt(100);

                    if (totlanumberofsp > count) {
                        if (totlanumberofsp > 0 && count > 0) {
                            centr = centr + (totlanumberofsp / count);
                        }
                    } else {
                        if (totlanumberofsp > 0 && count > 0) {
                            centr = centr + (count / totlanumberofsp);
                        }
                    }
                }
                Random randomGenerator = new Random();
                read = randomGenerator.nextInt(50);
                write = randomGenerator.nextInt(50);
                int readwrite = read + write;
                cen.put(String.valueOf(node), centr);
                repli.put(String.valueOf(node), readwrite);

                System.out.println(totlanumberofsp + "== " + count + ";;;;;;" + node + "............" + centr);
            }
            //file fragementation

            int capacity = 0;
            int id = 0;

            String placeing = "";

            List<Entry<String, Integer>> list2 = sortByValueInDecreasingOrder(cen);

            int iu = 0;
            for (Map.Entry<String, Integer> entry1 : list2) {
                if (iu < size) {

                    String gg1 = String.valueOf(entry1.getValue());
                    id = Integer.parseInt(entry1.getKey());
                    ServerNode nodeById = managerDAO.getLocationById(id);
                    if (nodeById != null) {
                        capacity = Integer.parseInt(nodeById.getCapacity());
                    }
                    if (capacity > 10) {

                        try {
                            System.out.println(id);

                            placeing = placeing + id + ",";
                            iu = iu + 1;
                            segment.add(id);
                            capacity = capacity - 10;

                            //String query = "update servernode set capacity = '" + capacity + "' where id ='" + id + "'";
                            ServerNode serverNode = new ServerNode();
                            serverNode.setCapacity(capacity + "");
                            serverNode.setId(id);

                            managerDAO.updateLocation(serverNode);

                            for (int jk = 0; jk <= nodecount; jk++) {
                                if (jk != id) {
                                    String pair = String.valueOf(id) + "," + String.valueOf(jk);
                                    int distance = 0;
                                    for (Map.Entry<String, Integer> entry2 : root.entrySet()) {
                                        if (pair.equalsIgnoreCase(entry2.getKey())) {
                                            distance = entry2.getValue();
                                        }
                                    }
                                    si.put(pair, distance);
                                }
                            }
                        } catch (ODRDataAccessException ex) {
                            Logger.getLogger(Centrality.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            //file replication

            String replaceing = "";

            List<Entry<String, Integer>> list3 = sortByValueInDecreasingOrder(repli);

            iu = 0;
            for (Map.Entry<String, Integer> entry1 : list3) {
                id = Integer.parseInt(entry1.getKey());
                int l = 0;
                for (Object segment1 : segment) {
                    int hkk = Integer.parseInt(segment1.toString());
                    if (hkk == id) {
                        l = 1;
                    }
                }

                if (iu < size && l == 0) {
                    String gg1 = String.valueOf(entry1.getValue());
                    id = Integer.parseInt(entry1.getKey());
                    ServerNode nodeById = managerDAO.getLocationById(id);
                    if (nodeById != null) {
                        capacity = Integer.parseInt(nodeById.getCapacity());
                    }
                    if (capacity > 10) {
                        System.out.println(id);
                        replaceing = replaceing + id + ",";
                        iu = iu + 1;
                        capacity = capacity - 10;
                        segment.add(id);
                        //String query = "update servernode set capacity = '" + capacity + "' where id ='" + id + "'";
                        ServerNode node = new ServerNode();
                        node.setCapacity(capacity + "");
                        node.setId(id);
                        managerDAO.updateLocation(node);                        
                        for (int jk = 0; jk <= nodecount; jk++) {
                            if (jk != id) {
                                String pair = String.valueOf(id) + "," + String.valueOf(jk);
                                int distance = 0;
                                for (Map.Entry<String, Integer> entry2 : root.entrySet()) {
                                    if (pair.equalsIgnoreCase(entry2.getKey())) {
                                        distance = entry2.getValue();
                                    }

                                }
                                rep.put(pair, distance);
                            }
                        }
                    }
                }
            }
            //String sql = "insert into fileplaceing (id,location,replacing)values('" + fid + "','" + placeing + "','" + replaceing + "')";
            filePlacementDAO.insertFilePlacement(new FilePlacing(fid + "", placeing + "", replaceing + ""));

        } catch (ODRDataAccessException | FileNotFoundException ex) {
            Logger.getLogger(Centrality.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    static int h = 0;

    public static void printPath(LinkedList<String> visited) {
        int value = 0;

        ArrayList<String> fg = new ArrayList<>();

        for (String visitedNode : visited) {
            fg.add(visitedNode);
        }

        for (int j = 0; j < fg.size() - 1; j++) {
            String pair = fg.get(j).toString() + "," + fg.get(j + 1).toString();
            for (Map.Entry<String, Integer> entry1 : root.entrySet()) {
                String gg = (String) entry1.getKey();
                String gg1 = String.valueOf(entry1.getValue());
                if (gg.equalsIgnoreCase(pair)) {
                    value = value + Integer.parseInt(gg1);
                }
            }
        }

        if (h < 20) {
            totlanumberofsp = totlanumberofsp + 1;
            h = h + 1;
            for (String fg1 : fg) {
                if (fg1.equals(node)) {
                    count = count + 1;
                }
                System.out.print(fg1 + "->");
            }
            System.out.print(value);
            System.out.println("\n");
        }
        if (h > 20) {

            flag = true;

        }

    }

    public static List<Map.Entry<String, Integer>> sortByValueInDecreasingOrder(Map<String, Integer> wordMap) {
        Set<Map.Entry<String, Integer>> entries = wordMap.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<>(entries);
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        return list;
    }

    //To change body of generated methods, choose Tools | Templates.
}
