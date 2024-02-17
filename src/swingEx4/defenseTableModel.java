package swingEx4;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.table.AbstractTableModel;

public class defenseTableModel extends AbstractTableModel {
    
	private static final long serialVersionUID = 1L;
    private String[] columnNames = {"Player Name", "Team Name", "Position", "G", "GS", "IP", "E", "PKO", "PO", "A", "DP", "FPCT", "PB", "SB", "CS", "CS_PER"};
    private ArrayList<defenseDAO>[] defenseData;
    private int currentYearIndex;
   
    public defenseTableModel(int year) {
        try {
            DatabaseManager dbManager = DatabaseManager.getInstance();
            defenseData = new ArrayList[1];
            defenseData[0] = dbManager.getDefenseList(year);
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
    	if (currentYearIndex < 0 || currentYearIndex >= defenseData.length) {
            return 0;
        }
        return defenseData[currentYearIndex].size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        defenseDAO data = defenseData[currentYearIndex].get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return data.getName();
            case 1:
                return data.getTeamName();
            case 2:
                return data.getPosition();
            case 3:
                return data.getG();
            case 4:
                return data.getGs();
            case 5:
                return data.getIp();
            case 6:
                return data.getE();
            case 7:
                return data.getPko();
            case 8:
                return data.getPo();
            case 9:
                return data.getA();
            case 10:
                return data.getDp();
            case 11:
                return data.getFpct();
            case 12:
                return data.getPb();
            case 13:
                return data.getSb();
            case 14:
                return data.getCs();
            case 15:
                return data.getCsPer();
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    public void setYearData(int yearIndex) {
        currentYearIndex = yearIndex;
        fireTableDataChanged();
    }
    
    public void sortData(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= columnNames.length) {
            return;
        }
        
        Comparator<defenseDAO> comparator = null;
        switch (columnIndex) {
            case 0:
                comparator = Comparator.comparing(defenseDAO::getName);
                break;
            case 1:
                comparator = Comparator.comparing(defenseDAO::getTeamName);
                break;
            case 2:
                comparator = Comparator.comparing(defenseDAO::getPosition);
                break;
            case 3:
                comparator = Comparator.comparingInt(defenseDAO::getG).reversed();
                break;
            case 4:
                comparator = Comparator.comparingInt(defenseDAO::getGs).reversed();
                break;
            case 5:
                comparator = Comparator.comparingDouble(defenseDAO::getIp).reversed();
                break;
            case 6:
                comparator = Comparator.comparingInt(defenseDAO::getE).reversed();
                break;
            case 7:
                comparator = Comparator.comparingInt(defenseDAO::getPko).reversed();
                break;
            case 8:
                comparator = Comparator.comparingInt(defenseDAO::getPo).reversed();
                break;
            case 9:
                comparator = Comparator.comparingInt(defenseDAO::getA).reversed();
                break;
            case 10:
                comparator = Comparator.comparingInt(defenseDAO::getDp).reversed();
                break;
            case 11:
                comparator = Comparator.comparingDouble(defenseDAO::getFpct).reversed();
                break;
            case 12:
                comparator = Comparator.comparingInt(defenseDAO::getPb).reversed();
                break;
            case 13:
                comparator = Comparator.comparingInt(defenseDAO::getSb).reversed();
                break;
            case 14:
                comparator = Comparator.comparingInt(defenseDAO::getCs).reversed();
                break;
            case 15:
                comparator = Comparator.comparingDouble(defenseDAO::getCsPer).reversed();
                break;
            default:
                return;
        }
        
        if (currentYearIndex < 0 || currentYearIndex >= defenseData.length) {
            return;
        }
        
        defenseData[currentYearIndex].sort(comparator);
        fireTableDataChanged();
    }

}
    