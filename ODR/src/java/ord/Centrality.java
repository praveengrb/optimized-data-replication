/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ord;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import java.util.Map.Entry;
import java.io.IOException;

/**
 *
 * @author Praveen Sankarasubramanian
 */
public class Centrality {

    private static String START;
    private static String END;
    private static boolean flag;

    public static int count = 0;
    public static int node = 0;
    public static int centr = 0;
    public static int read = 0;
    public static int write = 0;
    public static int nodecount = 0;
    public static int totlanumberofsp = 0;
    public static HashMap<String, Integer> root = new HashMap<>();
    public static HashMap<String, String> vall = new HashMap<>();
    public static HashMap<String, Integer> si = new HashMap<>();
    public static HashMap<String, Integer> rep = new HashMap<>();
    public static HashMap<String, Integer> root1 = new HashMap<>();

    public static HashMap<String, Integer> cen = new HashMap<>();

    public static HashMap<String, Integer> repli = new HashMap<>();

    public static ArrayList segment = new ArrayList();

    public static void fragmentation(int size, int fid) throws ClassNotFoundException, SQLException, IOException, InstantiationException, IllegalAccessException {

        // write new line
        ArrayList pla = new ArrayList();

        HashMap<String, Integer> result = new HashMap<>();

        Class.forName("com.mysql.jdbc.Driver").newInstance();

        Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/odr", "root", "root");

        String sa = "select * from servernode";
        PreparedStatement pr = con.prepareStatement(sa);
        ResultSet rs = pr.executeQuery();

        while (rs.next()) {

            String jj = rs.getString(2);
            result.put(rs.getString(1), Integer.parseInt(jj));
            nodecount = nodecount + 1;
        }

        List<Entry<String, Integer>> list = sortByValueInDecreasingOrder(result);
        int i = 0;
        PrintWriter writer1 = new PrintWriter("F://Project/MyFile.txt");//F://Project
        writer1.print("");
        writer1.close();
        System.out.println(nodecount);
        int cc = nodecount + 1;
        String hj = String.valueOf(cc);
        try {
            FileWriter writer = new FileWriter("F://Project/MyFile.txt", true);
            writer.write(hj);
            writer.write("\r\n");
            writer.write("\r\n");
            for (Map.Entry<String, Integer> entry1 : list) {
                String gg = (String) entry1.getKey();
                String gg1 = String.valueOf(entry1.getValue());

                i = i + 1;
                String so = "0," + gg;
                String so1 = gg + ",1";
                root.put(so, Integer.parseInt(gg1));
                root.put(so1, Integer.parseInt(gg1));
                System.out.println(so + " ========= " + gg1);
                //System.out.println(gg+"   ============================    "+gg1);
                int j = 0;
                for (Map.Entry<String, Integer> entry2 : list) {
                    j = j + 1;

                    String g = (String) entry2.getKey();
                    String g1 = String.valueOf(entry2.getValue());
                    if (gg == null ? g != null : !gg.equals(g)) {
                        String pair = gg + "," + g;

                        int k = Integer.parseInt(gg1);

                        int k1 = Integer.parseInt(g1);
                        int distance = 0;
                        if (k >= k1) {
                            distance = k - k1;
                        } else {
                            distance = k1 - k;
                        }
                        root.put(pair, distance);
                        writer.write(gg + " " + g + " " + distance);
                        writer.write("\r\n");
                        vall.put(gg, g);
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
                LinkedList<String> visited = new LinkedList();
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
                String sa1 = "select * from servernode where id='" + id + "'";
                PreparedStatement pr1 = con.prepareStatement(sa1);
                ResultSet rs1 = pr1.executeQuery();

                if (rs1.next()) {

                    capacity = Integer.parseInt(rs1.getString(3));
                }
                if (capacity > 10) {

                    System.out.println(id);

                    placeing = placeing + id + ",";
                    iu = iu + 1;
                    segment.add(id);
                    capacity = capacity - 10;

                    String query = "update servernode set capacity = '" + capacity + "' where id ='" + id + "'";
                    PreparedStatement preparedStmt = con.prepareStatement(query);

                    preparedStmt.executeUpdate();

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
                String sa1 = "select * from servernode where id='" + id + "'";
                PreparedStatement pr1 = con.prepareStatement(sa1);
                ResultSet rs1 = pr1.executeQuery();

                if (rs1.next()) {

                    capacity = Integer.parseInt(rs1.getString(3));
                }
                if (capacity > 10) {

                    System.out.println(id);

                    replaceing = replaceing + id + ",";

                    iu = iu + 1;

                    capacity = capacity - 10;
                    segment.add(id);
                    String query = "update servernode set capacity = '" + capacity + "' where id ='" + id + "'";
                    PreparedStatement preparedStmt = con.prepareStatement(query);

                    preparedStmt.executeUpdate();

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

        String sql = "insert into fileplaceing (id,location,replacing)values('" + fid + "','" + placeing + "','" + replaceing + "')";
        Statement st = con.createStatement();
        int s = st.executeUpdate(sql);

    }
    static int h = 0;

    public static void printPath(LinkedList<String> visited) {
        int value = 0;

        ArrayList fg = new ArrayList();

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
            for (Object fg1 : fg) {
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
