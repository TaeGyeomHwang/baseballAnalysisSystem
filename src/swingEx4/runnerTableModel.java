package swingEx4;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.table.AbstractTableModel;

public class runnerTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private String[] columnNames = {"Player Name", "Team Name", "G", "SBA", "SB", "CS", "SBPer", "OOB", "PKO"};
    private ArrayList<runnerDAO>[] runnerData;
    private int currentYearIndex;
    
    public runnerTableModel(int year) {
        try {
            DatabaseManager dbManager = DatabaseManager.getInstance();
            runnerData = new ArrayList[1];
            runnerData[0] = dbManager.getRunnerList(year);
            currentYearIndex = 0;
        } catch (SQLException e) {
            e. printStackTrace();
        }
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
    	if (currentYearIndex < 0 || currentYearIndex >= runnerData.length) {
            return 0;
        }
        return runnerData[currentYearIndex].size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        runnerDAO data = runnerData[currentYearIndex].get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return data.getName();
            case 1:
                return data.getTeamName();
            case 2:
                return data.getG();
            case 3:
                return data.getSba();
            case 4:
                return data.getSb();
            case 5:
                return data.getCs();
            case 6:
                return data.getSbPer();
            case 7:
                return data.getOob();
            case 8:
                return data.getPko();
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    public void sortData(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= columnNames.length) {
            return;
        }

        Comparator<runnerDAO> comparator = null;
        switch (columnIndex) {
        	case 0:
        		comparator = Comparator.comparing(runnerDAO::getName);
        		break;
            case 1:
                comparator = Comparator.comparing(runnerDAO::getTeamName);
                break;
            case 2:
                comparator = Comparator.comparingInt(runnerDAO::getG).reversed();
                break;
            case 3:
                comparator = Comparator.comparingInt(runnerDAO::getSba).reversed();
                break;
            case 4:
                comparator = Comparator.comparingInt(runnerDAO::getSb).reversed();
                break;
            case 5:
                comparator = Comparator.comparingInt(runnerDAO::getCs).reversed();
                break;
            case 6:
                comparator = Comparator.comparingDouble(runnerDAO::getSbPer).reversed();
                break;
            case 7:
                comparator = Comparator.comparingInt(runnerDAO::getOob).reversed();
                break;
            case 8:
                comparator = Comparator.comparingInt(runnerDAO::getPko).reversed();
                break;
            default:
                return;
        }

        Collections.sort(runnerData[currentYearIndex], comparator);
        fireTableDataChanged();
    }

    public runnerDAO getRowAt(int rowIndex) {
        if (currentYearIndex < 0 || currentYearIndex >= runnerData.length) {
            return null;
        }
        return runnerData[currentYearIndex].get(rowIndex);
    }

}