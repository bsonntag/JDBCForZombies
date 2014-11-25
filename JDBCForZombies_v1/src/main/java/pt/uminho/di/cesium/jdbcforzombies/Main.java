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

package pt.uminho.di.cesium.jdbcforzombies;

import java.util.Properties;
import pt.uminho.di.cesium.jdbcforzombies.models.Zombie;
import pt.uminho.di.cesium.jdbcforzombies.persistence.RepositoryFactory;
import pt.uminho.di.cesium.jdbcforzombies.persistence.ZombieRepository;

/**
 * Main
 *
 * @author Benjamim Sonntag
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.setProperty("host", "192.168.30.137");
        RepositoryFactory.setProperties(props);
        
        ZombieRepository repo = RepositoryFactory.getZombieRepository();
        
        Iterable<Zombie> zombies = repo.findAll();
        for(Zombie z : zombies) {
            System.out.println(z);
        }
    }
    
}
