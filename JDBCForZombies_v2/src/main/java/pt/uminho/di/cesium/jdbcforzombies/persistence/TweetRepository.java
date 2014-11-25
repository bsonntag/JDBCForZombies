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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pt.uminho.di.cesium.jdbcforzombies.models.Tweet;

/**
 * TweetRepository
 *
 * @author Benjamim Sonntag
 */
public class TweetRepository extends AbstractRepository<Tweet> {
    
    private static final String SELECT_TWEETS = "select id, zombie_id, text, pub_date from tweets";
    
    
    private final String url;
    private final String user;
    private final String password;
    
    private final ZombieRepository zombieRepository;

    TweetRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        
        this.zombieRepository = RepositoryFactory.getZombieRepository();
    }

    @Override
    public void save(Tweet entity) throws PersistenceException {
        // TODO code application logic here
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Tweet find(long id) throws PersistenceException {
        // TODO code application logic here
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterable<Tweet> findAll() throws PersistenceException {
        try {
            List<Tweet> tweets = new ArrayList<>();
            
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_TWEETS);
            
            try {
                while(result.next()) {
                    Tweet t = new Tweet();
                    t.setId(result.getLong("id"));
                    t.setText(result.getString("text"));

                    Date date = new Date(result.getDate("pub_date").getTime());
                    t.setPublishDate(date);

                    long zombie_id = result.getLong("zombie_id");
                    t.setZombie(zombieRepository.find(zombie_id));

                    tweets.add(t);
                }
            }
            finally {
                result.close();
                statement.close();
                connection.close();
            }
            
            return tweets;
        }
        catch (SQLException ex) {
            throw new PersistenceException("Error fetching tweets", ex);
        }
    }

    @Override
    public void delete(long id) throws PersistenceException {
        // TODO code application logic here
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteAll() throws PersistenceException {
        // TODO code application logic here
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long count() throws PersistenceException {
        // TODO code application logic here
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
