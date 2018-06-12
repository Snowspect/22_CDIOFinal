package daoimpl01917;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import JDBC.Connector;
import daointerfaces01917.AfvejningDAO;
import func.Weight_IO;

public class MySQLAfvejningDAO implements AfvejningDAO {
	//Returns nom_netto when given a pb_id and a rb_id.
	public double getNom_netto (int rb_id, int pb_id) throws SQLException {
		Connection sqlCon = Connector.getConn();
	
		double netto = 0.0;
		PreparedStatement getNom_netto = null;
		ResultSet rs = null;
	
		String getNetto = "SELECT nom_netto FROM receptkomponent NATURAL JOIN produktbatch NATURAL JOIN raavarebatch WHERE rb_id=? AND pb_id = ?;";
	
		try {
			getNom_netto = sqlCon.prepareStatement(getNetto);
	
			getNom_netto.setInt(1, rb_id);
			getNom_netto.setInt(2, pb_id);
			rs = getNom_netto.executeQuery();
			if(rs.first()) {
				netto = rs.getDouble(1);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(getNom_netto != null) {
				getNom_netto.close();
			}
		}
		return netto;
	}
	
	//Checks if the weighed netto is within tolerance for that raavare. 
	public boolean checkTolerance(int rb_id, double netto, int pb_id) throws SQLException {
		Connection sqlCon = Connector.getConn();
	
		double tolerance = 0.0;
		PreparedStatement checkTolerance = null;
		ResultSet rs = null;
	
		String checkRaavareTolerance = "SELECT DISTINCT tolerance FROM raavarebatch NATURAL JOIN receptkomponent WHERE rb_id = ?;";
	
		try {
			checkTolerance = sqlCon.prepareStatement(checkRaavareTolerance);
	
			checkTolerance.setInt(1, rb_id);
			rs = checkTolerance.executeQuery();
			if(rs.first()) {
				tolerance = rs.getDouble(1);
				checkTolerance = sqlCon.prepareStatement(checkRaavareTolerance);
				if (netto >= getNom_netto(rb_id, pb_id) * (1 - tolerance) && netto <= getNom_netto(rb_id, pb_id) * (1 + tolerance)) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(checkTolerance != null) {
				checkTolerance.close();
			}
		}
		return false;
	}
}
