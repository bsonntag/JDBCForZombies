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

package pt.uminho.di.cesium.jdbcforzombies.views;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import pt.uminho.di.cesium.jdbcforzombies.models.Zombie;

/**
 * ZombieTableModel
 *
 * @author Benjamim Sonntag
 */
public class ZombieTableModel extends AbstractTableModel {
    
    private static final String[] columnNames = { "ID", "Name", "Graveyard" };
    
    private final List<Zombie> zombies;

    public ZombieTableModel() {
        zombies = new ArrayList<>();
    }
    
    public void addRow(Zombie zombie) {
        zombies.add(zombie);
        fireTableRowsInserted(zombies.size()-1, zombies.size()-1);
    }
    
    public void removeRow(int rowIndex) {
        zombies.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
    
    public void updateRow(int rowIndex, Zombie zombie) {
        zombies.set(rowIndex, zombie);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    @Override
    public int getRowCount() {
        return zombies.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int column) {
        if(column >= 0 && column <=3)
            return columnNames[column];
        else
            return "";
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return zombies.get(rowIndex).getId();
            case 1:
                return zombies.get(rowIndex).getName();
            case 2:
                return zombies.get(rowIndex).getGraveyard();
            default:
                throw new AssertionError();
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Long.class;
            case 1:
            case 2:
                return String.class;
            default:
                throw new AssertionError();
        }
    }
    
}
