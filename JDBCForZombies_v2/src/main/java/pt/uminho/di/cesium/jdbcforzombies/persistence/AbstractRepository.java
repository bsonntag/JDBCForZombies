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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * AbstractRepository
 *
 * @author Benjamim Sonntag
 */
public abstract class AbstractRepository<T> implements Repository<T> {

    @Override
    public void saveAll(Collection<T> entities) throws PersistenceException {
        for(T entity : entities) {
            save(entity);
        }
    }

    @Override
    public Iterable<T> findAll(Collection<Long> ids) throws PersistenceException {
        List<T> list = new ArrayList<>(ids.size());
        for(long id : ids) {
            list.add(find(id));
        }
        return list;
    }

    @Override
    public void deleteAll(Collection<Long> ids) throws PersistenceException {
        for(long id : ids) {
            delete(id);
        }
    }

    @Override
    public boolean exists(long id) throws PersistenceException {
        return find(id) != null;
    }
    
}
