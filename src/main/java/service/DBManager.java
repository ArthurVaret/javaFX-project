package service;

import java.util.ArrayList;

import models.Product;
import models.Cloth;
import models.Shoe;
import models.Accessory;

import static service.DBCredentials.DBMS;
import static service.DBCredentials.HOST;
import static service.DBCredentials.PORT;
import static service.DBCredentials.DATABASE;
import static service.DBCredentials.USERNAME;
import static service.DBCredentials.PASSWORD;

import java.sql.*;

public class DBManager {
    public ArrayList<Product> loadProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        Connection connection = this.Connector();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM products";
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                String type =           res.getString("type");
                int productId =         res.getInt("id");
                String productName =    res.getString("name");
                double productPrice =   res.getDouble("price");
                int productStock =      res.getInt("stock");
                int productSize =       res.getInt("size");

                Product product = null;
                switch (type) {
                    case "Cloth" ->     product = new Cloth(productId,productName,productPrice,productStock, productSize);
                    case "Shoe" ->      product = new Shoe(productId,productName,productPrice,productStock, productSize);
                    case "Accessory" -> product = new Accessory(productId,productName,productPrice,productStock);
                }
                productList.add(product);
            }
            this.close(connection, statement, res);
            return productList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Connection Connector(){
        try {
            return DriverManager.getConnection(
                    "jdbc:" + DBMS + "://" + HOST + ":" + PORT + "/" + DATABASE +"",
                    USERNAME,
                    PASSWORD
            );
        }
        catch (Exception e) {
            System.out.print("Couldn't create a DB connection.");
            e.printStackTrace();
            return null;
        }
    }
    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try{
            if (myStmt != null)     myStmt.close();
            if (myRs != null)       myRs.close();
            if (myConn != null)     myConn.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean addProduct(Product product){
        Connection myConn=null;
        PreparedStatement myStmt = null;
        try {
            myConn = this.Connector();
            String type = product.getType();
            String sql;

            if (type.equals("Accessory"))   sql = "INSERT INTO products(name,type,price,stock) VALUES (?, ?, ?, ?);";
            else                            sql = "INSERT INTO products(name,type,price,stock,size) VALUES (?, ?, ?, ?, ?);";

            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, product.getName());
            myStmt.setString(2, type);
            myStmt.setDouble(3, product.getPrice());
            myStmt.setInt(4, product.getStock());

            if (!type.equals("Accessory")) myStmt.setInt(5,product.getSize());

            int res = myStmt.executeUpdate();
            System.out.println("Product added");
            System.out.println(myStmt);
            return res == 1;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            close(myConn,myStmt,null);
        }
    }

    public boolean deleteProduct(int id){
        Connection myConn=null;
        PreparedStatement myStmt = null;
        try {
            myConn = this.Connector();
            String sql = "DELETE FROM products WHERE id=?;";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1,id);
            int res = myStmt.executeUpdate();
            System.out.println("Product deleted");
            System.out.println(myStmt);
            return res == 1;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            close(myConn,myStmt,null);
        }
    }

    public boolean updateProduct(Product product) {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = this.Connector();
            String sql = "UPDATE products SET name = ?, type = ?, size = ?, price = ?, stock = ? WHERE id = ? ";
            s = c.prepareStatement(sql);
            s.setString(1, product.getName());
            s.setString(2, product.getType());
            s.setInt(3,product.getSize());
            s.setDouble(4,product.getPrice());
            s.setInt(5,product.getStock());       // stock

            s.setInt(6,product.getId());            // id

            int res = s.executeUpdate();
            System.out.println("Product updated");
            System.out.println(s);
            return res == 1;
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        } finally {
            close(c,s,null);
        }
    }

}