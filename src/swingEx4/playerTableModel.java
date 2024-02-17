package swingEx4;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class playerTableModel extends AbstractTableModel {
	
	private String[] columnNames = {"backNumber", "name", "teamName", "position", "birthdate", "physical", "career", "condition" };
	private ArrayList<playerDAO> playerData;
	private DatabaseManager dbManager;
	
	public playerTableModel() {
		try {
            DatabaseManager dbManager = DatabaseManager.getInstance();
            setPlayerData(dbManager.getPlayerList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public int getRowCount() {
		return getPlayerData().size();
    }
	
	public int getColumnCount() {
		return columnNames.length;
    }
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		playerDAO data = getPlayerData().get(rowIndex);

        switch(columnIndex) {
            case 0:
                return data.getBackNumber();
            case 1:
                return data.getName();
            case 2:
                return data.getTeamName();
            case 3:
            	return data.getPosition();
            case 4:
            	return data.getBirthdate();
            case 5:
            	return data.getPhysical();
            case 6:
            	return data.getCareer();
            case 7:
            	return data.getCondition();
            default:
                return null;
        }
    }
	
	public String getColumnName(int column) {
        switch(column) {
            case 0:
                return "BackNumber";
            case 1:
                return "Name";
            case 2:
                return "TeamName";
            case 3:
            	return "Position";
            case 4:
            	return "Birthdate";
            case 5:
            	return "Physical";
            case 6:
            	return "Career";
            case 7:
            	return "Condition";
            default:
                return null;
        }
    }
	
	public void setValueAt(Object value, int row, int col) {
	    playerDAO player = getPlayerData().get(row);
	    switch (col) {
	        case 0:
	            player.setBackNumber((int) value);
	            break;
	        case 1:
	            player.setName((String) value);
	            break;
	        case 2:
	            player.setTeamName((String) value);
	            break;
	        case 3:
	            player.setPosition((String) value);
	            break;
	        case 4:
	            player.setBirthdate((String) value);
	            break;
	        case 5:
	            player.setPhysical((String) value);
	            break;
	        case 6:
	            player.setCareer((String) value);
	            break;
	        case 7:
	            player.setCondition((String) value);
	            try {
	                DatabaseManager dbManager = DatabaseManager.getInstance();
	                dbManager.updatePlayerCondition(player.getName(), player.getCondition(), player.getPosition());
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	            break;
	    }
	    fireTableCellUpdated(row, col);
	}

	public ArrayList<playerDAO> getPlayerData() {
		return playerData;
	}

	public void setPlayerData(ArrayList<playerDAO> playerData) {
		this.playerData = playerData;
	}

}
