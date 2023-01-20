package org.example;

import java.sql.*;

public class MySQLAccess {

    private final MySQLDatabaseInformations databaseInformations = new MySQLDatabaseInformations();
    private PreparedStatement preparedStatement = null;
    private Connection connection = null;

//    Class.forName("com.mysql.jdbc.Driver");


    public void insertData(FlightData flightData) throws SQLException {
        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://" + databaseInformations.getHost() + ":" + databaseInformations.getPort() +
                            "/" + databaseInformations.getDBName(), databaseInformations.getUsername(), databaseInformations.getPassword());

            this.preparedStatement = this.connection
                    .prepareStatement("insert into  flight_data values (null, ?, ?, ?, ? , ?, ?, ?)");

            this.preparedStatement.setString(1, flightData.getFk_id_flight());
            this.preparedStatement.setString(2, flightData.getTime_stamp());
            this.preparedStatement.setString(3, flightData.getCo2());
            this.preparedStatement.setString(4, flightData.getTemperature());
            this.preparedStatement.setString(5, flightData.getGps());
            this.preparedStatement.setString(6, flightData.getHumidity());
            this.preparedStatement.setString(7, flightData.getAir_pressure());
            this.preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
}
