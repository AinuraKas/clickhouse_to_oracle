package com.ainura;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import com.opencsv.CSVWriter;

import java.io.FileWriter;

public class ClickHouseJDBC
        implements AutoCloseable {

    private static final String DB_URL = "jdbc:clickhouse://10.255.149.202:8123/def";

    private final Connection conn;


    /**
     * Creates new instance
     * @throws SQLException in case of connection issue
     */
    public ClickHouseJDBC() throws SQLException {
        Properties properties = new Properties();

        properties.setProperty("user", "akasymalieva");
        properties.setProperty("password","pass");
        properties.put("socket_timeout", 100000000);
        properties.put("connection_timeout",100000000);
        properties.put("dataTransferTimeout",100000000);
        properties.put("keepAliveTimeout",100000000);
        properties.put("connect_timeout",100000000);
        conn = DriverManager.getConnection(DB_URL,properties);

    }

    public ArrayList<Stat> popularYearRoutes() throws SQLException {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // manipulate date

        c.add(Calendar.DATE, -1); //same with c.add(Calendar.DAY_OF_MONTH, 1);

        // convert calendar to date
        Date currentDateOne = c.getTime();
        String t1=formatter.format(currentDateOne)+" 00:00:00";
        String t2=formatter.format(date)+" 00:00:00";
        String fileName = "e:/ipdr/cdr/whatsapp_service_"+t1.substring(0,10)+".csv";
        Path myPath = Paths.get(fileName);
        String query =
                "SELECT toStartOfHour(date)dats,whatsapp_service,sum( uplink ), sum( downlink ), protocol from ipdr.ipdr  " +
                        "where toStartOfDay(month)>='"+t1+"' and  toStartOfDay(month)<'"+t2+"' and protocol='whatsapp' " +
                        " group by toStartOfHour(date), whatsapp_service, protocol";
        long time = System.currentTimeMillis();
        ArrayList<Stat> ar=new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            try (ResultSet rs = statement.executeQuery(query)) {
                /*
                try (CSVWriter writer = new CSVWriter(Files.newBufferedWriter(myPath,
                        StandardCharsets.UTF_8), CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END)) {

                    writer.writeAll(rs, true);

                }*/
                while ( rs.next()) {
                    ar.add(new Stat(new Date(rs.getTimestamp(1).getTime()),rs.getString(2),rs.getLong(3),rs.getLong(4),rs.getString(5)));
                }



            } catch (final SQLException e) {
                throw new RuntimeException(e);

            } /*catch (IOException e) {
                e.printStackTrace();
            }*/

            //


        }

        System.out.println("Time: " + (System.currentTimeMillis() - time) +" ms");
        return ar;
    }







    @Override
    public void close() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }


}
