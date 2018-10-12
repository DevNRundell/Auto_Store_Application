package com.autostore.app.customer;

public abstract class StoreModel {
    double storeBalance = 0;
    Date orderDate; = new Date();

    public void setStoreBalance(double balance) {
        this.storeBalance = balance;
    }
    public double getBalance() {return this.storeBalance;}

    public void selectBalance() {
        string balanceQuery = "SELECT balance FROM accounting ORDER BY account_id DESC LIMIT 1"
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnect.getConnection();
            if(!connection.isClosed()) {
                preparedStatement = connection.prepareStatement(balanceQuery);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    this.storeBalance = resultSet.getString(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DialogController.showDatabaseError();
        } finally {
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeResultSet(resultSet);
            DBUtils.closeConn(connection);
        }
    }

    public void updateBalance(int transactionID, double credit, double debit, Date date) {
        double currentBalance = selectBalance();
        currentBalance += credit + debit;
        string orderQuery = "INSERT INTO accounting (transaction_id, credit, debit, balance , date) VALUES (" +
                   transactionID + "," +  credit + "," + debit + "," + currentBalance + "," +
                date + ")";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnect.getConnection();
            if(!connection.isClosed()) {
                preparedStatement = connection.prepareStatement(balanceQuery);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    this.storeBalance = resultSet.getString(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DialogController.showDatabaseError();
        } finally {
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeResultSet(resultSet);
            DBUtils.closeConn(connection);
        }
    }
}