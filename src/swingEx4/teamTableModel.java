package swingEx4;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class teamTableModel extends AbstractTableModel {

    private String[] columnNames = {"rank", "team name", "g", "w", "l", "wpct"};
    private ArrayList<teamDAO> teamData;

    public teamTableModel() {
        try {
            DatabaseManager dbManager = DatabaseManager.getInstance();
            teamData = dbManager.getTeamList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getRowCount() {
        return teamData.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        teamDAO data = teamData.get(rowIndex);

        switch(columnIndex) {
            case 0:
                return data.getRank();
            case 1:
                return data.getTeamName();
            case 2:
                return data.getG();
            case 3:
                return data.getW();
            case 4:
                return data.getL();
            case 5:
                return data.getWpct();
            default:
                return null;
        }
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }
}