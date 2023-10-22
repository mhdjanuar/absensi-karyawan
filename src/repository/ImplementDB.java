package repository;
import models.Absen;
import models.Cuti;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class ImplementDB {
    private static final String DB_URL = "jdbc:sqlite:D:\\Study\\Java\\sqlite\\absensi.db";

    public String getStringDate (String dateTimeString) {
        // Split the string by the hyphen
        String[] parts = dateTimeString.split(" - ");

        // Extract the date part
        String datePart = parts[0];

        return datePart;
    }
    public void inputAbsenKaryawan(Integer idKaryawan, String dateHours) {
        /*
         *  Transaction for Input Absen Karyawan
         *  */
        try {
            Connection connection = DriverManager.getConnection(DB_URL);

            // Check if a record exists for the given id_karyawan
            String selectQuery = "SELECT id_absen FROM Absen WHERE id_karyawan = ? AND dateCheckIn LIKE "  + "'" + getStringDate(dateHours) + "%'" + ";";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, idKaryawan);
            if (selectStatement.executeQuery().next()) {
                // Record exists, update dateCheckOut if dateCheckIn is not null
                String updateQuery = "UPDATE Absen SET dateCheckOut = ? WHERE id_karyawan = ? AND dateCheckIn LIKE " + "'" + getStringDate(dateHours) + "%'" + ";";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, dateHours);
                updateStatement.setInt(2, idKaryawan);
                updateStatement.executeUpdate();
            } else {
                // Record does not exist or dateCheckIn is null, insert a new record
                String insertQuery = "INSERT INTO Absen (id_karyawan, dateCheckIn) VALUES (?, ?);";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, idKaryawan);
                insertStatement.setString(2, dateHours);
                insertStatement.executeUpdate();
            }

            // Close resources
            selectStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Absen> getRiwayatAbsen (Integer idKaryawan) {
        List<Absen> absenList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            String query = "SELECT * FROM Absen WHERE id_karyawan = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idKaryawan);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Absen absen = new Absen();
                absen.setDateCheckIn(resultSet.getString("dateCheckIn"));
                absen.setDateCheckOut(resultSet.getString("dateCheckOut"));
                absenList.add(absen);
            }


            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return absenList;
    }

    public Integer getTotalHariKerjaByMonth (Integer idKaryawan, String month) {
        Integer total = 0;

        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            String query = "SELECT COUNT(*) as record_count FROM Absen WHERE id_karyawan = ? AND (dateCheckIn LIKE ? AND dateCheckOut LIKE ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idKaryawan);
            preparedStatement.setString(2, "%" + month + "%");
            preparedStatement.setString(3, "%" + month + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                total = resultSet.getInt("record_count");
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void inputFormCutiKaryawan (int idKaryawan, int jumlahCuti, String alasan, String startDate, String endDate) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            String query = "INSERT INTO cuti (id_karyawan, jumlah_cuti, alasan, startDate, endDate, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idKaryawan);
            preparedStatement.setInt(2, jumlahCuti);
            preparedStatement.setString(3, alasan);
            preparedStatement.setString(4, startDate);
            preparedStatement.setString(5, endDate);
            preparedStatement.setString(6, "PENDING");

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");

            // Close resources
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cuti> listOfCutiKaryawan () {
        List<Cuti> cutiList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            String query = "SELECT cuti.*, Users.Username FROM cuti JOIN Users ON cuti.id_karyawan = Users.ID WHERE cuti.status = 'PENDING';";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Cuti cuti = new Cuti();
                cuti.setIdCuti(resultSet.getInt("id_cuti"));
                cuti.setIdKaryawan(resultSet.getInt("id_karyawan"));
                cuti.setNameKaryawan(resultSet.getString("Username"));
                cuti.setJumlahCuti(resultSet.getInt("jumlah_cuti"));
                cuti.setAlasan(resultSet.getString("alasan"));
                cuti.setStartDateCuti(resultSet.getString("startDate"));
                cuti.setEndDateCuti(resultSet.getString("endDate"));
                cutiList.add(cuti);
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cutiList;
    }

    public void updateCutiStatus(int idCuti, String newStatus) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            String updateCutiQuery = "UPDATE cuti SET status = ? WHERE id_cuti = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateCutiQuery);
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, idCuti);
            preparedStatement.executeUpdate();

            // Close resources
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUsersJumlahCuti(Integer idCuti) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            String selectCutiJumlahCuti = "SELECT jumlah_cuti, id_karyawan FROM cuti WHERE id_cuti = ?";
            PreparedStatement selectCutiJumlahCutiStmt = connection.prepareStatement(selectCutiJumlahCuti);
            selectCutiJumlahCutiStmt.setInt(1, idCuti);
            int cutiJumlahCuti = 0;
            int idKaryawan = 0;
            try (var resultSet = selectCutiJumlahCutiStmt.executeQuery()) {
                if (resultSet.next()) {
                    cutiJumlahCuti = resultSet.getInt("jumlah_cuti");
                    idKaryawan = resultSet.getInt("id_karyawan");
                }
            }

            String updateUsersJumlahCuti = "UPDATE Users SET jumlah_cuti = jumlah_cuti - ? WHERE ID = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(updateUsersJumlahCuti);
            preparedStatement.setInt(1, cutiJumlahCuti);
            preparedStatement.setInt(2, idKaryawan);
            preparedStatement.executeUpdate();

            // Close resources
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
