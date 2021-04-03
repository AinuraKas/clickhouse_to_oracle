package com.ainura;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try (ClickHouseJDBC gf = new ClickHouseJDBC()) {
            ArrayList<Stat> rs=gf.popularYearRoutes();
            try (OracleJDBC ef = new OracleJDBC()) {
                ef.setTO(rs);
            }
            catch (final SQLException e) {
                throw new RuntimeException(e);} catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (final SQLException e) {
            throw new RuntimeException(e);} catch (Exception e) {
            e.printStackTrace();
        }


    }
}
