package org.orbisgis.server.mapcatalog; /**
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information.
 * <p/>
 * OrbisGIS is distributed under GPL 3 license. It is produced by the "Atelier
 * SIG" team of the IRSTV Institute <http://www.irstv.fr/> CNRS FR 2488.
 * <p/>
 * Copyright (C) 2007-2012 IRSTV (FR CNRS 2488)
 * <p/>
 * This file is part of OrbisGIS.
 * <p/>
 * OrbisGIS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p/>
 * OrbisGIS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License along with
 * OrbisGIS. If not, see <http://www.gnu.org/licenses/>.
 * <p/>
 * For more information, please consult: <http://www.orbisgis.org/> or contact
 * directly: info_at_ orbisgis.org
 */

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Java model of the table User
 * @author Mario Jothy
 */
public class User {
    private String id_user = null;
    private String name = "";
    private String email = "";
    private String password = "";
    private String location = "";
    private String profession = "";
    private String additional = "";
    private String admin_wms = "30";
    private String admin_mapcatalog = "30";
    private String admin_wps = "30";

    /**
     * Constructor that hashes the password of the user (used for saving into database when signing in)
     * @param name The name of the user
     * @param email the email of the user
     * @param passwordtohash The password, not hashed
     * @param location The location of the user
     */
    public User(String name, String email, String passwordtohash, String location) throws NoSuchAlgorithmException {
        this.name = name;
        this.email = email;
        this.password = MapCatalog.hasher(passwordtohash);
        this.location = location;
    }

    /**
     * Constructor with primarykey (usually used for select queries)
     * @param id_user The id of the user
     * @param name The name of the user
     * @param email The email of the user
     * @param password the password of the user
     * @param location The location of the user
     * @param profession The profession of the user
     * @param additional The additional information about a user
     * @param admin_wms The level of accreditation of the user in the wms branch
     * @param admin_mapcatalog The level of accreditation of the user in the mapcatalog branch
     * @param admin_wps The level of accreditation of the user in the wps branch
     */
    public User(String id_user, String name, String email, String password, String location, String profession, String additional, String admin_wms, String admin_mapcatalog, String admin_wps) {
        this.id_user = id_user;
        this.name = name;
        this.email = email;
        this.password = password;
        this.location = location;
        this.profession = profession;
        this.additional = additional;
        this.admin_wms = admin_wms;
        this.admin_mapcatalog = admin_mapcatalog;
        this.admin_wps = admin_wps;
    }

    public String getId_user() {
        return id_user;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }

    public String getAdditional() {
        return additional;
    }

    public String getProfession() {
        return profession;
    }

    public String getAdmin_wms() {
        return admin_wms;
    }

    public String getAdmin_mapcatalog() {
        return admin_mapcatalog;
    }

    public String getAdmin_wps() {
        return admin_wps;
    }

    /**
     * Method that saves a instantiated User into database. Handles SQL injections.
     * @param MC the mapcatalog object for the connection
     * @return The ID of the User just created (primary key)
     */
    public  Long save(MapCatalog MC) throws SQLException{
        Long last = null;
        String query = "INSERT INTO user (name,email,password,location,profession,additional) VALUES (? , ? , ? , ? , ? , ?);";
        PreparedStatement pstmt = MC.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, name);
        pstmt.setString(2, email);
        pstmt.setString(3, password);
        pstmt.setString(4, location);
        pstmt.setString(5, profession);
        pstmt.setString(6, additional);
        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        if(rs.next()){
            last = rs.getLong(1);
        }
        rs.close();
        pstmt.close();
        return last;
    }

    /**
     * Deletes a user from database
     * @param MC the mapcatalog object for the connection
     * @param id_user The primary key of the user
     */
    public static void delete(MapCatalog MC, Long id_user) throws SQLException{
        String query = "DELETE FROM user WHERE id_user = ? ;";
        PreparedStatement stmt = MC.getConnection().prepareStatement(query);
        stmt.setLong(1, id_user);
        stmt.executeUpdate();
        stmt.close();
    }

    /**
     * Method that queries the database for users, with a where clause, be careful, as only the values in the where clause will be checked for SQL injections
     * @param MC the mapcatalog object for the connection
     * @param attributes The attributes in the where clause, you should NEVER let the user bias this parameter, always hard code it.
     * @param values The values of the attributes, this is totally SQL injection safe
     * @return A list of User containing the result of the query
     */
    public static List<User> page(MapCatalog MC, String[] attributes, String[] values) throws SQLException{
        String query = "SELECT * FROM user WHERE ";
        List<User> paged = new LinkedList<User>();
        //case argument invalid
        if(attributes == null || values == null){
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        if(attributes.length != values.length){
            throw new IllegalArgumentException("String arrays have to be of the same length");
        }
        //preparation of the query
        query+=attributes[0]+" = ?";
        for(int i=1; i<attributes.length; i++){
            if(values[i]==null){
                query += "AND "+attributes[i]+" IS NULL";
            }else{
                query += " AND "+attributes[i]+" = ?";
            }
        }
        //preparation of the statement
        PreparedStatement stmt = MC.getConnection().prepareStatement(query);
        int j=1;
        for (String value : values) {
            if (value != null) {
                stmt.setString(j, value);
                j++;
            }
        }
        //Retrieving values
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            String id_user = rs.getString("id_user");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String location = rs.getString("location");
            String profession = rs.getString("profession");
            String additional =rs.getString("additional");
            String admin_wms = rs.getString("admin_wms");
            String admin_mapcatalog = rs.getString("admin_mapcatalog");
            String admin_wps = rs.getString("admin_wps");
            User use = new User(id_user,name,email,password,location,profession,additional, admin_wms, admin_mapcatalog, admin_wps);
            paged.add(use);
        }
        rs.close();
        stmt.close();
        return paged;
    }

    /**
     * Method that sends a query to database SELECT * FROM USER
     * @param MC the mapcatalog object for the connection
     * @return A list of user containing the result of the query
     */
    public static List<User> page(MapCatalog MC) throws SQLException{
        String query = "SELECT * FROM user";
        List<User> paged = new LinkedList<User>();
        PreparedStatement stmt = MC.getConnection().prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            String id_user = rs.getString("id_user");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String location = rs.getString("location");
            String profession = rs.getString("profession");
            String additional =rs.getString("additional");
            String admin_wms = rs.getString("admin_wms");
            String admin_mapcatalog = rs.getString("admin_mapcatalog");
            String admin_wps = rs.getString("admin_wps");
            User use = new User(id_user,name,email,password,location,profession,additional, admin_wms, admin_mapcatalog, admin_wps);
            paged.add(use);
        }
        rs.close();
        stmt.close();
        return paged;
    }

    /**
     * Execute a query "UPDATE" in the database, password is not updated, so it can be set to whatever.
     * @param MC the mapcatalog used for database connection
     */
    public void update(MapCatalog MC) throws SQLException{
        String query = "UPDATE user SET name = ? , email = ? , location = ?, profession = ? , additional = ? WHERE id_user = ?;";
        //preparation of the statement
        PreparedStatement stmt = MC.getConnection().prepareStatement(query);
        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.setString(3, location);
        stmt.setString(4, profession);
        stmt.setString(5, additional);
        stmt.setString(6, id_user);
        stmt.executeUpdate();
        stmt.close();
    }

    /**
     * Executes a UPDATE query in the database for a specified user, and updates only the password (only the attributes id_user and password are used)
     * @param MC The current instantiation of MapCatalog to get connection to database from
     * @throws SQLException
     */
    public void updatePass(MapCatalog MC) throws SQLException{
        String query = "UPDATE user SET password = ? WHERE id_user = ?;";
        //preparation of the statement
        PreparedStatement stmt = MC.getConnection().prepareStatement(query);
        stmt.setString(1, password);
        stmt.setString(2, id_user);
        stmt.executeUpdate();
        stmt.close();
    }

    /**
     * Executes a UPDATE query in the database for a specified user, and updates only the admin rights (only the attributes id_user and admin_* are used)
     * @param MC The current instantiation of MapCatalog to get connection to database from
     * @throws SQLException
     */
    public void updateAdminRights(MapCatalog MC) throws SQLException{
        String query = "UPDATE user SET admin_wms = ?, admin_mapcatalog = ?, admin_wps = ? WHERE id_user = ?;";
        //preparation of the statement
        PreparedStatement stmt = MC.getConnection().prepareStatement(query);
        stmt.setString(1, admin_wms);
        stmt.setString(2, admin_mapcatalog);
        stmt.setString(3, admin_wps);
        stmt.setString(4, id_user);
        stmt.executeUpdate();
        stmt.close();
    }

}
