package swingEx4;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class hitterTableModel extends AbstractTableModel {
    
    private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Player Name", "Team Name", "average", "G", "PA", "AB", "R", "H", "2B", "3B", "HR", "TB", "RBI", "SAC", "SF", "BB", "IBB", "HBP", "SO", "GDP", "SLG", "OBP", "OPS", "MH", "RISP", "PHBA", "XBH", "GO", "AO", "GO/AO", "GWRBI", "BB/K", "PPA", "ISO", "XR", "GPA"};
	private ArrayList<hitterDAO>[] hitterData;
	private int currentYearIndex;
	private List<hitterDAO> hitterTypeList;	//	새 인스턴스 변수를 추가

	public hitterTableModel(int year, List<hitterDAO> hitterTypeList) {	//	생성자를 추가, 새 목록을 수락
	    try {
	        DatabaseManager dbManager = DatabaseManager.getInstance();
	        hitterData = new ArrayList[1];
	        hitterData[0] = dbManager.getHitterList(year);
	        currentYearIndex = 0;
	        this.hitterTypeList = hitterTypeList;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
    	if (currentYearIndex < 0 || currentYearIndex >= hitterData.length) {
            return 0;
        }
        return hitterData[currentYearIndex].size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        hitterDAO data = hitterData[currentYearIndex].get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return data.getName();
            case 1:
                return data.getTeamName();
            case 2:
                return data.getAverage();
            case 3:
                return data.getG();
            case 4:
                return data.getPa();
            case 5:
                return data.getAb();
            case 6:
                return data.getR();
            case 7:
                return data.getH();
            case 8:
                return data.getDoubleBase();
            case 9:
                return data.getTripleBase();
            case 10:
                return data.getHr();
            case 11:
                return data.getTb();
            case 12:
                return data.getRbi();
            case 13:
                return data.getSac();
            case 14:
                return data.getSf();
            case 15:
                return data.getBb();
            case 16:
                return data.getIbb();
            case 17:
                return data.getHbp();
            case 18:
                return data.getSo();
            case 19:
                return data.getGdp();
            case 20:
                return data.getSlg();
            case 21:
                return data.getObp();
            case 22:
                return data.getOps();
            case 23:
                return data.getMh();
            case 24:
                return data.getRisp();
            case 25:
                return data.getPhba();
            case 26:
                return data.getXbh();
            case 27:
                return data.getGo();
            case 28:
                return data.getAo();
            case 29:
                return data.getGoao();
            case 30:
                return data.getGwrbi();
            case 31:
                return data.getBbk();
            case 32:
                return data.getPpa();
            case 33:
                return data.getIsop();
            case 34:
                return data.getXr();
            case 35:
                return data.getGpa();
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

        Comparator<hitterDAO> comparator = null;
        switch (columnIndex) {
        	case 0:
        		comparator = Comparator.comparing(hitterDAO::getName);
        		break;
            case 1:
                comparator = Comparator.comparing(hitterDAO::getTeamName);
                break;
            case 2:
                comparator = Comparator.comparingDouble(hitterDAO::getAverage).reversed();
                break;
            case 3:
                comparator = Comparator.comparingInt(hitterDAO::getG).reversed();
                break;
            case 4:
                comparator = Comparator.comparingInt(hitterDAO::getPa).reversed();
                break;
            case 5:
                comparator = Comparator.comparingInt(hitterDAO::getAb).reversed();
                break;
            case 6:
                comparator = Comparator.comparingInt(hitterDAO::getR).reversed();
                break;
            case 7:
                comparator = Comparator.comparingInt(hitterDAO::getH).reversed();
                break;
            case 8:
                comparator = Comparator.comparingInt(hitterDAO::getDoubleBase).reversed();
                break;
            case 9:
            	comparator = Comparator.comparingInt(hitterDAO::getTripleBase).reversed();
            	break;
            case 10:
            	comparator = Comparator.comparingInt(hitterDAO::getHr).reversed();
            	break;
            case 11:
            	comparator = Comparator.comparingInt(hitterDAO::getTb).reversed();
            	break;
            case 12:
            	comparator = Comparator.comparingInt(hitterDAO::getRbi).reversed();
            	break;
            case 13:
            	comparator = Comparator.comparingInt(hitterDAO::getSac).reversed();
            	break;
            case 14:
            	comparator = Comparator.comparingInt(hitterDAO::getSf).reversed();
            	break;
            case 15:
            	comparator = Comparator.comparingInt(hitterDAO::getBb).reversed();
            	break;
            case 16:
            	comparator = Comparator.comparingInt(hitterDAO::getIbb).reversed();
            	break;
            case 17:
            	comparator = Comparator.comparingInt(hitterDAO::getHbp).reversed();
            	break;
            case 18:
            	comparator = Comparator.comparingInt(hitterDAO::getSo).reversed();
            	break;
            case 19:
            	comparator = Comparator.comparingInt(hitterDAO::getGdp).reversed();
            	break;
            case 20:
            	comparator = Comparator.comparingDouble(hitterDAO::getSlg).reversed();
            	break;
            case 21:
            	comparator = Comparator.comparingDouble(hitterDAO::getObp).reversed();
            	break;
            case 22:
            	comparator = Comparator.comparingDouble(hitterDAO::getOps).reversed();
            	break;
            case 23:
            	comparator = Comparator.comparingInt(hitterDAO::getMh).reversed();
            	break;
            case 24:
            	comparator = Comparator.comparingDouble(hitterDAO::getRisp).reversed();
            	break;
            case 25:
            	comparator = Comparator.comparingDouble(hitterDAO::getPhba).reversed();
            	break;
            case 26:
            	comparator = Comparator.comparingInt(hitterDAO::getXbh).reversed();
            	break;
            case 27:
            	comparator = Comparator.comparingInt(hitterDAO::getGo).reversed();
            	break;
            case 28:
            	comparator = Comparator.comparingInt(hitterDAO::getAo).reversed();
            	break;
            case 29:
            	comparator = Comparator.comparingDouble(hitterDAO::getGoao).reversed();
            	break;
            case 30:
            	comparator = Comparator.comparingInt(hitterDAO::getGwrbi).reversed();
            	break;
            case 31:
            	comparator = Comparator.comparingDouble(hitterDAO::getBbk).reversed();
            	break;
            case 32:
            	comparator = Comparator.comparingDouble(hitterDAO::getPpa).reversed();
            	break;
            case 33:
            	comparator = Comparator.comparingDouble(hitterDAO::getIsop).reversed();
            	break;
            case 34:
            	comparator = Comparator.comparingDouble(hitterDAO::getXr).reversed();
            	break;
            case 35:
            	comparator = Comparator.comparingDouble(hitterDAO::getGpa).reversed();
            	break;
            default:
                return;
        }

        Collections.sort(hitterData[currentYearIndex], comparator);
        fireTableDataChanged();
    }
    public hitterDAO getRowAt(int rowIndex) {
        return hitterData[currentYearIndex].get(rowIndex);
    }
}
    