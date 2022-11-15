package service;

import java.util.ArrayList;

import models.*;

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
        Connection connection = this.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM products";
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                String type =           res.getString("type");
                int productId =         res.getInt("id");
                String productName =    res.getString("name");
                double productPrice =   res.getDouble("price");
                double productCost =    res.getDouble("cost");
                int productStock =      res.getInt("stock");
                int productSize =       res.getInt("size");
                boolean productPromo =  res.getBoolean("isOnSale");

                Product product = null;

                // type in product table
                switch (type) {
                    case "Cloth" ->     product = new Cloth(productId,productName,productPrice,productCost,productStock,productSize,productPromo);
                    case "Shoe" ->      product = new Shoe(productId,productName,productPrice,productCost,productStock,productSize,productPromo);
                    case "Accessory" -> product = new Accessory(productId,productName,productPrice,productCost,productStock,productPromo);
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
    public Connection getConnection(){
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
            myConn = this.getConnection();
            String type = product.getType();
            String sql;

            if (type.equals("Accessory"))   sql = "INSERT INTO products(name,type,price,cost,stock,isOnSale) VALUES (?,?,?,?,?,?);";
            else                            sql = "INSERT INTO products(name,type,price,cost,stock,isOnSale,size) VALUES (?,?,?,?,?,?,?);";

            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, product.getName());
            myStmt.setString(2, type);
            myStmt.setDouble(3, product.getPrice());
            myStmt.setDouble(4, product.getCost());
            myStmt.setInt(5, product.getStock());
            myStmt.setBoolean(6, product.getPromotion());

            if (!type.equals("Accessory")) myStmt.setInt(7,product.getSize());

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
            myConn = this.getConnection();
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
            c = this.getConnection();
            String sql = "UPDATE products SET name = ?, type = ?, size = ?, price = ?, cost = ?, stock = ?, isOnSale = ? WHERE id = ? ";
            s = c.prepareStatement(sql);
            s.setString(1, product.getName());
            s.setString(2, product.getType());
            s.setInt(3,product.getSize());
            s.setDouble(4,product.getPrice());
            s.setDouble(5,product.getCost());
            s.setInt(6,product.getStock());
            s.setBoolean(7, product.getPromotion());
            s.setInt(8,product.getId());

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

    public boolean orderProduct(Product p, String action, int amount){
        Connection c = null;
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            c = this.getConnection();
            String insert = "INSERT INTO orders(orderType,dateOrder) VALUES (?,?)";
            s = c.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            s.setString(1,action);
            s.setDate(2, new Date(new java.util.Date().getTime()));

            int rows = s.executeUpdate();
            if (rows == 0) throw new SQLException("Creating order failed, no rows affected");
            else System.out.println(s);

            try {
                r = s.getGeneratedKeys();
                if (r.next()) {
                    insert = "INSERT INTO quantityOrder(productId,orderId,quantity) VALUES(?,?,?)";
                    s = c.prepareStatement(insert);
                    s.setInt(1, p.getId());
                    s.setInt(2, r.getInt(1));
                    s.setInt(3, amount);
                    rows = s.executeUpdate();
                    if (rows == 0) throw new SQLException("Inserting quantityOrder failed, no rows affected");
                    else System.out.println(s);

                    String updateProduct = "UPDATE products SET stock = ? WHERE id = ?";
                    s = c.prepareStatement(updateProduct);
                    s.setInt(1,p.getStock());
                    s.setInt(2, p.getId());
                    rows = s.executeUpdate();
                    if (rows == 0) throw new SQLException("Updated product stock");
                    else System.out.println(s);

                    return rows == 1;
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(c,s,r);
        }
    }

    public Double getDashboardValue(String type) {
        try {
            if (!type.equals("sell") && !type.equals("buy")) throw new IllegalArgumentException("Dashboard value either sell or buy");

            Connection c = null;
            PreparedStatement s = null;
            ResultSet r = null;
            try {
                c = this.getConnection();
                // SELECT o.id, name, orderType operation, quantity FROM products p INNER JOIN quantityOrder q ON p.id = q.productId INNER JOIN orders o ON o.id = q.orderId WHERE orderType = 'buy';
                String sql = (type.equals("sell"))
                    ?
                    "SELECT sum(p.price * quantity) total, sum(quantity) quantity FROM products p INNER JOIN quantityOrder q ON p.id = q.productId INNER JOIN orders o ON o.id = q.orderId WHERE orderType = 'sell'"
                    :
                    "SELECT sum(p.cost * quantity) total, sum(quantity) quantity FROM products p INNER JOIN quantityOrder q ON p.id = q.productId INNER JOIN orders o ON o.id = q.orderId WHERE orderType = 'buy'";
                s = c.prepareStatement(sql);
                r = s.executeQuery();
                if (r.next()) return r.getDouble("total");
                else return null;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            } finally {
                close(c,s,r);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Order> loadOrders() {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet r = null;
        ArrayList<Order> list = null;
        try {
            c = getConnection();
            String sql = "SELECT o.id, name, orderType operation, dateOrder, quantity FROM products p INNER JOIN quantityOrder q ON p.id = q.productId INNER JOIN orders o ON o.id = q.orderId GROUP BY o.id";
            s = c.prepareStatement(sql);
            r = s.executeQuery();

            list = new ArrayList<>();
            while (r.next()) {
                Order o = new Order(r.getInt("id"), r.getString("name"), r.getString("operation"), r.getInt("quantity"), r.getDate("dateOrder"));

                list.add(o);
            }

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        } finally {
            close(c,s,r);
        }
    }
}