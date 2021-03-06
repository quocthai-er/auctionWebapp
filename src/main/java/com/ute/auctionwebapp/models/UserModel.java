package com.ute.auctionwebapp.models;

import com.ute.auctionwebapp.beans.User;
import com.ute.auctionwebapp.beans.WatchList;
import com.ute.auctionwebapp.utills.DbUtills;
import org.sql2o.Connection;

import java.util.List;

public class UserModel {
    public static User findByUsername(String email) {
        final String query = "select * from users where email = :email";
        try (Connection con = DbUtills.getConnection()) {
            List<User> list = con.createQuery(query)
                    .addParameter("email", email)
                    .executeAndFetch(User.class);

            if (list.size() == 0) {
                return null;
            }

            return list.get(0);
        }
    }

    public static void add(User c) {
        String insertSql = "INSERT INTO users (name, email, password, request, dob, role, address, gg_acc) VALUES (:name,:email,:password,:request,:dob,:role,:address,:gg_acc)";
        try (Connection con = DbUtills.getConnection()) {
            con.createQuery(insertSql)
                    .addParameter("password", c.getPassword())
                    .addParameter("name", c.getName())
                    .addParameter("email", c.getEmail())
                    .addParameter("dob", c.getDob())
                    .addParameter("role",c.getRole())
                    .addParameter("request", c.getReQuest())
                    .addParameter("address", c.getAddress())
                    .addParameter("gg_acc", c.isGg_acc())
                    .executeUpdate();
        }
    }

    public static void update(User c) {
        String insertSql = "UPDATE users SET  name = :name, address = :address, dob = :dob WHERE id = :id \n";
        try (Connection con = DbUtills.getConnection()) {
            con.createQuery(insertSql)
                   .addParameter("id",c.getId())
                    .addParameter("name", c.getName())
                    .addParameter("dob", c.getDob())
                    .addParameter("address", c.getAddress())
                    .executeUpdate();
        }
    }
    public static boolean deleteUser(int uid) {
        String Sql = "delete from users where id=:uid";
        try (Connection con = DbUtills.getConnection()) {
            con.createQuery(Sql)
                    .addParameter("uid", uid)
                    .executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static void upgrage(User c) {
        String insertSql = "UPDATE users SET request = :request WHERE id = :id \n";
        try (Connection con = DbUtills.getConnection()) {
            con.createQuery(insertSql)
                    .addParameter("id", c.getId())
                    .addParameter("request", c.getReQuest())
                    .executeUpdate();
        }
    }

    public static void upgrageSeller(User c) {
        String insertSql = "UPDATE users SET  role = :role, request = :request, request_date = date_add(NOW(),interval 7 day ) WHERE id = :id \n";
        try (Connection con = DbUtills.getConnection()) {
            con.createQuery(insertSql)
                    .addParameter("id", c.getId())
                    .addParameter("role", c.getRole())
                    .addParameter("request", c.getReQuest())
                    .executeUpdate();
        }
    }

    public static void downSeller(User c) {
        String insertSql = "UPDATE users SET  role = :role, request = :request WHERE id = :id \n";
        try (Connection con = DbUtills.getConnection()) {
            con.createQuery(insertSql)
                    .addParameter("id", c.getId())
                    .addParameter("role", c.getRole())
                    .addParameter("request",c.getReQuest())
                    .executeUpdate();
        }
    }
    public static void changepassword(User c) {
        String insertSql = "UPDATE users SET password = :password WHERE id = :id \n";
        try (Connection con = DbUtills.getConnection()) {
            con.createQuery(insertSql)
                    .addParameter("id",c.getId())
                    .addParameter("password", c.getPassword())
                    .executeUpdate();
        }
    }


    public static void resetPassword(String email, String password) {
        String insertSql = "UPDATE users SET password = :password WHERE email= :email";
        try (Connection con = DbUtills.getConnection()) {
            con.createQuery(insertSql)
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .executeUpdate();
        }
    }
    public static User findById(int id) {
        final String query = "select * from users where id=:id";
        try (Connection con = DbUtills.getConnection()) {
            List<User> list = con.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetch(User.class);

            if (list.size() == 0) {
                return null;
            }
            return list.get(0);
        }
    }

    public static boolean findByuid(int id) {
        final String query = "select * from users where id=:id";
        try (Connection con = DbUtills.getConnection()) {
            List<User> list = con.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetch(User.class);
            if (list.size() == 0) {
                return true;
            }
            return false;
        }
    }
    public static List<User> findAll(){
        final String query = "select * from users";
        try (Connection con = DbUtills.getConnection()) {
            return con.createQuery(query)
                    .executeAndFetch(User.class);
        }
    }
    public static void updateExpiredSeller(int uid) {
        String insertSql = "UPDATE users SET  role = 1,request_date = null WHERE id = :id \n";
        try (Connection con = DbUtills.getConnection()) {
            con.createQuery(insertSql)
                    .addParameter("id", uid)
                    .executeUpdate();
        }
    }
}
