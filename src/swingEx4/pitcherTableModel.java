package swingEx4;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.table.AbstractTableModel;

public class pitcherTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
    private String[] columnNames = {"Player Name", "Team Name", "ERA", "G", "W", "L", "SV", "HLD", "WPCT", "IP", "H", "HR", "BB", "HBP", "SO", "R", "ER", "WHIP", "CG", "SHO", "QS", "BSV", "TBF", "NP", "AVG", "2B", "3B", "SAC", "SF", "IBB", "WP", "BK", "GS", "WGS", "WGR", "GF", "SVO", "TS", "GDP", "GO", "AO", "GO/AO", "BABIP", "PG", "PIP", "K/9", "BB/9", "K/BB", "OBP", "SLG", "OPS"};
    private ArrayList<pitcherDAO>[] pitcherData;
    private int currentYearIndex;

    public pitcherTableModel(int year) {
        try {
            DatabaseManager dbManager = DatabaseManager.getInstance();
            pitcherData = new ArrayList[1];
            pitcherData[0] = dbManager.getPitcherList(year);
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
    	if (currentYearIndex < 0 || currentYearIndex >= pitcherData.length) {
            return 0;
        }
        return pitcherData[currentYearIndex].size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        pitcherDAO data = pitcherData[currentYearIndex].get(rowIndex);

        switch (columnIndex) {
            case 0:
                return data.getName();
            case 1:
                return data.getTeamName();
            case 2:
                return data.getEra();
            case 3:
                return data.getG();
            case 4:
                return data.getW();
            case 5:
                return data.getL();
            case 6:
                return data.getSv();
            case 7:
                return data.getHld();
            case 8:
                return data.getWpct();
            case 9:
                return data.getIp();
            case 10:
                return data.getH();
            case 11:
                return data.getHr();
            case 12:
                return data.getBb();
            case 13:
                return data.getHbp();
            case 14:
                return data.getSo();
            case 15:
                return data.getR();
            case 16:
                return data.getEr();
            case 17:
                return data.getWhip();
            case 18:
                return data.getCg();
            case 19:
                return data.getSho();
            case 20:
                return data.getQs();
            case 21:
                return data.getBsv();
            case 22:
                return data.getTbf();
            case 23:
                return data.getNp();
            case 24:
                return data.getAvg();
            case 25:
                return data.getDoubleBase();
            case 26:
                return data.getTripleBase();
            case 27:
                return data.getSac();
            case 28:
                return data.getSf();
            case 29:
                return data.getIbb();
            case 30:
                return data.getWp();
            case 31:
                return data.getBk();
            case 32:
                return data.getGs();
            case 33:
                return data.getWgs();
            case 34:
                return data.getWgr();
            case 35:
                return data.getGf();
            case 36:
                return data.getSvo();
            case 37:
                return data.getTs();
            case 38:
                return data.getGdp();
            case 39:
                return data.getGo();
            case 40:
                return data.getAo();
            case 41:
                return data.getGoao();
            case 42:
                return data.getBabip();
            case 43:
                return data.getPg();
            case 44:
                return data.getPip();
            case 45:
                return data.getK9();
            case 46:
                return data.getBb9();
            case 47:
                return data.getKbb();
            case 48:
                return data.getObp();
            case 49:
                return data.getSlg();
            case 50:
                return data.getOps();
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

            Comparator<pitcherDAO> comparator = null;
            switch (columnIndex) {
            	case 0:
            		comparator = Comparator.comparing(pitcherDAO::getName);
            		break;
                case 1:
                    comparator = Comparator.comparing(pitcherDAO::getTeamName);
                    break;
                case 2:
                    comparator = Comparator.comparingDouble(pitcherDAO::getEra).reversed();
                    break;
                case 3:
                    comparator = Comparator.comparingInt(pitcherDAO::getG).reversed();
                    break;
                case 4:
                    comparator = Comparator.comparingInt(pitcherDAO::getW).reversed();
                    break;
                case 5:
                    comparator = Comparator.comparingInt(pitcherDAO::getL).reversed();
                    break;
                case 6:
                    comparator = Comparator.comparingDouble(pitcherDAO::getSv).reversed();
                    break;
                case 7:
                    comparator = Comparator.comparingInt(pitcherDAO::getHld).reversed();
                    break;
                case 8:
                    comparator = Comparator.comparingDouble(pitcherDAO::getWpct).reversed();
                    break;
                case 9:
                	comparator = Comparator.comparingDouble(pitcherDAO::getIp).reversed();
                    break;
                case 10:
                	comparator = Comparator.comparingInt(pitcherDAO::getH).reversed();
                    break;
                case 11:
                	comparator = Comparator.comparingInt(pitcherDAO::getHr).reversed();
                    break;
                case 12:
                	comparator = Comparator.comparingInt(pitcherDAO::getBb).reversed();
                    break;
                case 13:
                	comparator = Comparator.comparingInt(pitcherDAO::getHbp).reversed();
                    break;
                case 14:
                	comparator = Comparator.comparingInt(pitcherDAO::getSo).reversed();
                    break;
                case 15:
                	comparator = Comparator.comparingInt(pitcherDAO::getR).reversed();
                    break;
                case 16:
                	comparator = Comparator.comparingInt(pitcherDAO::getEr).reversed();
                    break;
                case 17:
                	comparator = Comparator.comparingDouble(pitcherDAO::getWhip).reversed();
                    break;
                case 18:
                	comparator = Comparator.comparingInt(pitcherDAO::getCg).reversed();
                    break;
                case 19:
                	comparator = Comparator.comparingInt(pitcherDAO::getSho).reversed();
                    break;
                case 20:
                	comparator = Comparator.comparingInt(pitcherDAO::getQs).reversed();
                    break;
                case 21:
                	comparator = Comparator.comparingInt(pitcherDAO::getBsv).reversed();
                    break;
                case 22:
                	comparator = Comparator.comparingInt(pitcherDAO::getTbf).reversed();
                    break;
                case 23:
                	comparator = Comparator.comparingInt(pitcherDAO::getNp).reversed();
                    break;
                case 24:
                	comparator = Comparator.comparingDouble(pitcherDAO::getAvg).reversed();
                    break;
                case 25:
                	comparator = Comparator.comparingInt(pitcherDAO::getDoubleBase).reversed();
                    break;
                case 26:
                	comparator = Comparator.comparingInt(pitcherDAO::getTripleBase).reversed();
                    break;
                case 27:
                	comparator = Comparator.comparingInt(pitcherDAO::getSac).reversed();
                    break;
                case 28:
                	comparator = Comparator.comparingInt(pitcherDAO::getSf).reversed();
                    break;
                case 29:
                	comparator = Comparator.comparingInt(pitcherDAO::getIbb).reversed();
                    break;
                case 30:
                	comparator = Comparator.comparingInt(pitcherDAO::getWp).reversed();
                    break;
                case 31:
                	comparator = Comparator.comparingInt(pitcherDAO::getBk).reversed();
                    break;
                case 32:
                	comparator = Comparator.comparingInt(pitcherDAO::getGs).reversed();
                    break;
                case 33:
                	comparator = Comparator.comparingInt(pitcherDAO::getWgs).reversed();
                    break;
                case 34:
                	comparator = Comparator.comparingInt(pitcherDAO::getWgr).reversed();
                    break;
                case 35:
                	comparator = Comparator.comparingInt(pitcherDAO::getGf).reversed();
                    break;
                case 36:
                	comparator = Comparator.comparingInt(pitcherDAO::getSvo).reversed();
                    break;
                case 37:
                	comparator = Comparator.comparingInt(pitcherDAO::getTs).reversed();
                    break;
                case 38:
                	comparator = Comparator.comparingInt(pitcherDAO::getGdp).reversed();
                    break;
                case 39:
                	comparator = Comparator.comparingInt(pitcherDAO::getGo).reversed();
                    break;
                case 40:
                	comparator = Comparator.comparingInt(pitcherDAO::getAo).reversed();
                    break;
                case 41:
                	comparator = Comparator.comparingDouble(pitcherDAO::getGoao).reversed();
                    break;
                case 42:
                	comparator = Comparator.comparingDouble(pitcherDAO::getBabip).reversed();
                    break;
                case 43:
                	comparator = Comparator.comparingDouble(pitcherDAO::getPg).reversed();
                    break;
                case 44:
                	comparator = Comparator.comparingDouble(pitcherDAO::getPip).reversed();
                    break;
                case 45:
                	comparator = Comparator.comparingDouble(pitcherDAO::getK9).reversed();
                    break;
                case 46:
                	comparator = Comparator.comparingDouble(pitcherDAO::getBb9).reversed();
                    break;
                case 47:
                	comparator = Comparator.comparingDouble(pitcherDAO::getKbb).reversed();
                    break;
                case 48:
                	comparator = Comparator.comparingDouble(pitcherDAO::getObp).reversed();
                    break;
                case 49:
                	comparator = Comparator.comparingDouble(pitcherDAO::getSlg).reversed();
                    break;
                case 50:
                	comparator = Comparator.comparingDouble(pitcherDAO::getOps).reversed();
                    break;
                default:
                    return;
            }

            Collections.sort(pitcherData[currentYearIndex], comparator);
            fireTableDataChanged();
        }
    }