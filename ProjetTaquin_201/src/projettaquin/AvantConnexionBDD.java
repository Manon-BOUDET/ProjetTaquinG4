/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projettaquin;

import java.util.ArrayList;

/**
 *
 * @author Manon
 */
public class AvantConnexionBDD {

    /**
     * @param args the command line arguments
     */
    public static ArrayList<String> avCo(String s,Integer identifiant, String query) {
        String host = "localhost";
        String port = "8889";
        String dbname = "taquin";
        String username = "root";
        String password = "root";
        ConnexionBDD c = new ConnexionBDD(host, port, dbname, username, password);

        if (s.equals("UPDATE") || s.equals("INSERT")) {
            c.getTuplesUpdateInsert(query);
            return null;
        } else if (s.equals("SELECT")) {
            ArrayList<String> l = c.getTuplesSelect(query);
            return l;
        }
        return null;
    }
}
