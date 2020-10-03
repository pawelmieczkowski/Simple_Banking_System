package banking;

import org.sqlite.SQLiteDataSource;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreditCardDB {
    SQLiteDataSource dataSource;

    CreditCardDB(String urlInput) {
        String url = "jdbc:sqlite:" + urlInput;
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            try (Statement stmt = con.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "number TEXT, " +
                        "pin TEXT, " +
                        "balance INTEGER DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewCreditCard(String cardNumber, String pinNumber, int balance) {
        try (Connection con = dataSource.getConnection()) {
            try (Statement stmt = con.createStatement()) {
                stmt.executeUpdate("INSERT INTO card (number, pin, balance)" +
                        "VALUES ('" + cardNumber + "', '" + pinNumber + "', '" + balance + "');");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkCreditCard(String cardNumber, String pinNumber) {
        boolean isValidCard = false;
        try (Connection con = dataSource.getConnection()) {
            try (Statement stmt = con.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM card WHERE number = " + cardNumber);
                if (rs.next()) {
                    if (rs.getString("pin").equals(pinNumber)) {
                        isValidCard = true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValidCard;
    }

    public int getBalance(String cardNumber) {
        int balance = 0;
        try (Connection con = dataSource.getConnection()) {
            try (Statement stmt = con.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM card WHERE number = " + cardNumber);
                if (rs.next()) {
                    balance = rs.getInt("balance");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public void addIncome(String cardNumber, int income) {
        try (Connection con = dataSource.getConnection()) {
            try (Statement stmt = con.createStatement()) {
                stmt.executeUpdate("UPDATE card SET balance = balance + " + income +
                        " WHERE number = " + cardNumber);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void transfer(String cardNumberOwner, String cardNumberToTransferTo, int moneyAmount) {
        addIncome(cardNumberOwner, -moneyAmount);
        addIncome(cardNumberToTransferTo, moneyAmount);
    }

    public boolean checkCreditCard(String cardNumber) {
        boolean isValidCard = false;
        try (Connection con = dataSource.getConnection()) {
            try (Statement stmt = con.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM card WHERE number = " + cardNumber);
                if (rs.next()) {
                    isValidCard = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValidCard;
    }

    public void deleteAccount(String cardNumber) {
        try (Connection con = dataSource.getConnection()) {
            try (Statement stmt = con.createStatement()) {
                stmt.executeUpdate("DELETE FROM card WHERE NUMBER = " + cardNumber);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int dbSize() {
        int dbSize = 0;
        try (Connection con = dataSource.getConnection()) {
            try (Statement stmt = con.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT Count(*) FROM card");
                dbSize = rs.getRow();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbSize;
    }

//    public void closeConnection() {
//        try {
//            con.close();
//        } catch (Exception e) {
//            System.out.println("Error while closing database");
//        }
//    }
}
