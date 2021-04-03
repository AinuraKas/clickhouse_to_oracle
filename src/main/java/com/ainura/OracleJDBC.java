package com.ainura;


import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import oracle.jdbc.datasource.OracleDataSource;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.OracleDriver;

public class OracleJDBC
        implements AutoCloseable {

    private static final String DB_URL = "jdbc:oracle:thin:@172.28.147.151:1521:FMSPRD";

    private final Connection conn;


    /**
     * Creates new instance
     * @throws SQLException in case of connection issue
     */
    public OracleJDBC() throws SQLException {
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();


        }

        Properties properties = new Properties();

        properties.setProperty("user", "fms_teh");
        properties.setProperty("password","sFqdWq_Ji#EOY6O");

        conn=DriverManager.getConnection(DB_URL,"fms_teh","sFqdWq_Ji#EOY6O");


    }
    public void setTO(ArrayList<Stat> rs){
        try {
             conn.setAutoCommit(false);
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into DPIservice(  dats  ,  type_service  ,  uplink  ,  downlink  ,    protocol ) values(?,?,?,?,?)");
            Iterator<Stat> it = rs.iterator();
            while(it.hasNext()){
                Stat p = it.next();
                prepStmt.setDate(1, new java.sql.Date(p.getDats().getTime()));
                prepStmt.setString(2,p.getServ());
                prepStmt.setLong(3,p.getUpl());
                prepStmt.setLong(4,p.getDl());
                prepStmt.setString(5,p.getFl());
                prepStmt.addBatch();

            }
            int [] numUpdates=prepStmt.executeBatch();
            for (int i=0; i < numUpdates.length; i++) {
                if (numUpdates[i] == -2)
                    System.out.println("Execution " + i +
                            ": unknown number of rows updated");
                else
                    System.out.println("Execution " + i +
                            "successful: " + numUpdates[i] + " rows updated");
            }
            conn.commit();
        } catch(BatchUpdateException b) {
            // process BatchUpdateException
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }












@Override
public void close() throws Exception {
        if (conn != null) {
        conn.close();
        }
        }


        }
