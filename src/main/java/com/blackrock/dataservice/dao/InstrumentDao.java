package com.blackrock.dataservice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstrumentDao {

    private static final Logger LOGGER = Logger.getLogger(InstrumentDao.class.getName());

    public List<String> displayAveragePriceForAllInstrumentsInLastTenSec() {
        List<String> averagePriceList = new ArrayList<>();
        try (Statement statement = Database.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery("select Product, ROUND(AVG(price), 2) as AveragePrice\n" +
                    "from instruments\n" +
                    "where seconds between 21 and 30\n" +
                    "group by Product ");
            while (result.next()) {
                averagePriceList.add(result.getString("Product") + " " + result.getDouble("AveragePrice"));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            ex.printStackTrace();
        }

        return averagePriceList;
    }

    public List<String> displaySecondHighestPriceForAllInstruments() {
        List<String> secondHighestPriceList = new ArrayList<>();
        try (Statement statement = Database.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery("select i1.product, max(i1.price) as price\n" +
                    "from instruments i1\n" +
                    "where i1.price < (select max(price)\n" +
                    "                  from instruments i2\n" +
                    "                  where i2.product = i1.product\n" +
                    "                 )\n" +
                    "group by i1.product");

            while (result.next()) {
                secondHighestPriceList.add(result.getString("Product") + " " + result.getDouble("price"));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            ex.printStackTrace();
        }

        return secondHighestPriceList;
    }

}
