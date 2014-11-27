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

package pt.uminho.di.cesium.jdbcforzombies.models;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Zombie
 *
 * @author Benjamim Sonntag
 */
public class Zombie {
    
    private long id = -1;
    
    private String name;
    
    private String graveyard;
    
    private final Set<Tweet> tweets = new HashSet<>();
    
    
    public Zombie() {
        
    }
    
    public Zombie(String name, String graveyard) {
        this.name = name;
        this.graveyard = graveyard;
    }
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGraveyard() {
        return graveyard;
    }

    public void setGraveyard(String graveyard) {
        this.graveyard = graveyard;
    }

    public Collection<Tweet> getTweets() {
        return Collections.<Tweet>unmodifiableCollection(tweets);
    }
    
    public boolean addTweet(Tweet tweet) {
        if(tweets.contains(tweet)) {
            return false;
        }
        else {
            tweet.setZombie(this);
            tweets.add(tweet);
            return true;
        }
    }
    
    public boolean removeTweet(Tweet tweet) {
        if(tweets.contains(tweet)) {
            tweets.remove(tweet);
            tweet.setZombie(null);
            return false;
        }
        else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 17 + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        else if (getClass() != obj.getClass()) {
            return false;
        }
        else {
            final Zombie other = (Zombie) obj;
            return this.id == other.id;
        }
    }

    @Override
    public String toString() {
        return "Zombie{id=" + id + ", name=" + name + ", graveyard=" + graveyard + '}';
    }
    
}
