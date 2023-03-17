package org.example;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.time.format.DateTimeFormatter;

public class MySQLAccess {
    private Connection connection;
    private final Dotenv dotenv = Dotenv.configure().directory(System.getProperty("user.dir").replace("\\", "/")).ignoreIfMissing().load();

    public MySQLAccess() {

        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://" + dotenv.get("HOST") + ":" + dotenv.get("PORT") +
                            "/" + dotenv.get("DB_NAME"), dotenv.get("DB_USERNAME"), dotenv.get("PASSWORD"));
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données.");
        }
    }

    public void insertData(FlightData flightData) {
        try {
            PreparedStatement preparedStatement = this.connection
                    .prepareStatement("insert into  flight_data values (null, ?, ?, ?, ?, ? , ?, ?, ?)");

            preparedStatement.setInt(1, flightData.getFlightId());
            preparedStatement.setString(2, flightData.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
            if (flightData.getAltitude() != null) {
                preparedStatement.setDouble(3, flightData.getAltitude());
            } else {
                preparedStatement.setNull(3, Types.DOUBLE);
            }
            if (flightData.getCo2() != null) {
                preparedStatement.setDouble(4, flightData.getCo2());
            } else {
                preparedStatement.setNull(4, Types.DOUBLE);
            }
            if (flightData.getTemperature() != null) {
                preparedStatement.setDouble(5, flightData.getTemperature());
            } else {
                preparedStatement.setNull(5, Types.DOUBLE);
            }
            if (flightData.getGps() != null) {
                preparedStatement.setString(6, flightData.getGps());
            } else {
                preparedStatement.setNull(6, Types.VARCHAR);
            }
            if (flightData.getHumidity() != null) {
                preparedStatement.setDouble(7, flightData.getHumidity());
            } else {
                preparedStatement.setNull(7, Types.DOUBLE);
            }
            if (flightData.getAirPressure() != null) {
                preparedStatement.setDouble(8, flightData.getAirPressure());
            } else {
                preparedStatement.setNull(8, Types.DOUBLE);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur d'insertion de données dans la base de données.");
            System.err.println(e.getMessage());

            try {
                connection = DriverManager
                        .getConnection("jdbc:mysql://" + dotenv.get("HOST") + ":" + dotenv.get("PORT") +
                                "/" + dotenv.get("DB_NAME"), dotenv.get("USERNAME"), dotenv.get("PASSWORD"));
            } catch (SQLException ex) {
                System.err.println("Erreur de connexion à la base de données.");
            }
        }
    }
}
