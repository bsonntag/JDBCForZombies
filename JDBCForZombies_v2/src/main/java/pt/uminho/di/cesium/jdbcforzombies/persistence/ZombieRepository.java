/*
 * Copyright (c) 2014 Benjamim Sonntag
 * 
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package pt.uminho.di.cesium.jdbcforzombies.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pt.uminho.di.cesium.jdbcforzombies.models.Zombie;

/**
 * ZombieRepository
 *
 * @author Benjamim Sonntag
 */
public final class ZombieRepository extends AbstractRepository<Zombie> {
    
    private static final String SELECT_ZOMBIES = "select id, name, graveyard from zombies";
    private static final String SELECT_ZOMBIE = "select id, name, graveyard from zombies where id = ?";
    
    private static final String INSERT_ZOMBIE = "insert into zombies (name, graveyard) values (?, ?)";
    private static final String UPDATE_ZOMBIE = "update zombies set name = ?, graveyard = ? where id = ?";
    
    private static final String DELETE_ZOMBIE = "delete from zombies where id = ?";
    private static final String DELETE_ZOMBIES = "delete from zombies";
    
    private static final String COUNT_ZOMBIES = "select count(*) as n from zombies";
    
    
    private final String url;
    private final String user;
    private final String password;
    
    
    ZombieRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
    
    @Override
    public void save(Zombie zombie) throws PersistenceException {
        String query;
        int autoGenerateKeys;
        boolean isUpdate;
        if(zombie.getId() < 0) {
            isUpdate = false;
            query = INSERT_ZOMBIE;
            autoGenerateKeys = Statement.RETURN_GENERATED_KEYS;
        }
        else {
            isUpdate = true;
            query = UPDATE_ZOMBIE;
            autoGenerateKeys = Statement.NO_GENERATED_KEYS;
        }
        
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(query, autoGenerateKeys);
            
            statement.setString(1, zombie.getName());
            statement.setString(2, zombie.getGraveyard());
            
            if(isUpdate) {
                statement.setLong(3, zombie.getId());
            }
            
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            
            try {
                if(keys.next()) {
                    zombie.setId(keys.getLong(1));
                }
                else {
                    throw new PersistenceException("Error generating id for zombie: " + zombie);
                }
            }
            finally {
                keys.close();
                statement.close();
                connection.close();
            }
            
            RepositoryFactory.getTweetRepository().saveAll(zombie.getTweets());
        }
        catch (SQLException ex) {
            throw new PersistenceException("Error saving zombie: " + zombie, ex);
        }
    }
    
    @Override
    public Zombie find(long id) throws PersistenceException {
        try {
            Zombie zombie;
            
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(SELECT_ZOMBIE);
            
            statement.setLong(1, id);
            
            ResultSet result = statement.executeQuery();
            
            try {
                if(result.next()) {
                    zombie = new Zombie();
                    zombie.setId(result.getLong("id"));
                    zombie.setName(result.getString("name"));
                    zombie.setGraveyard(result.getString("graveyard"));
                }
                else {
                    zombie = null;
                }
            }
            finally {
                result.close();
                statement.close();
                connection.close();
            }
            
            return zombie;
        }
        catch (SQLException ex) {
            throw new PersistenceException("Error finding zombie: " + id, ex);
        }
    }
    
    @Override
    public Iterable<Zombie> findAll() throws PersistenceException {
        try {
            List<Zombie> zombies = new ArrayList<>();
            
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ZOMBIES);
            
            try {
                while(result.next()) {
                    Zombie z = new Zombie();
                    z.setId(result.getInt("id"));
                    z.setName(result.getString("name"));
                    z.setGraveyard(result.getString("graveyard"));
                    zombies.add(z);
                }
            }
            finally {
                result.close();
                statement.close();
                connection.close();
            }
            
            return zombies;
        }
        catch (SQLException ex) {
            throw new PersistenceException("Error finding zombies", ex);
        }
    }
    
    @Override
    public void delete(Zombie zombie) throws PersistenceException {
        try {
            // Delete the zombie's tweets first
            RepositoryFactory.getTweetRepository().deleteAll(zombie.getTweets());
            
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(DELETE_ZOMBIE);
            
            statement.setLong(1, zombie.getId());
            
            try {
                statement.executeUpdate();
                zombie.setId(-1);
            }
            finally {
                statement.close();
                connection.close();
            }
        }
        catch (SQLException ex) {
            throw new PersistenceException("Error deleting zombie: " + zombie, ex);
        }
    }
    
    @Override
    public void deleteAll() throws PersistenceException {
        try {
            // Delete tweets first
            RepositoryFactory.getTweetRepository().deleteAll();
            
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            
            try {
                statement.executeUpdate(DELETE_ZOMBIES);
            }
            finally {
                statement.close();
                connection.close();
            }
        }
        catch (SQLException ex) {
            throw new PersistenceException("Error deleting zombies", ex);
        }
    }
    
    @Override
    public long count() throws PersistenceException {
        try {
            int count;
            
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(COUNT_ZOMBIES);

            try {
                count = result.getInt("n");
            }
            finally {
                statement.close();
                connection.close();
            }
            
            return count;
        }
        catch (SQLException ex) {
            throw new PersistenceException("Error deleting zombies", ex);
        }
    }
    
}
