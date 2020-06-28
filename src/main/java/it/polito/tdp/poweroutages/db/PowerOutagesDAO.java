package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.poweroutages.model.Archi;
import it.polito.tdp.poweroutages.model.Nerc;

public class PowerOutagesDAO {
	
	public List<Nerc> loadAllNercs(Map<Integer, Nerc> idMap) {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				try {
					if(!idMap.containsKey(res.getInt("id"))) {
						Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
						nercList.add(n);
						idMap.put(n.getId(), n);
					}
				} catch(Throwable t) {
					t.printStackTrace();
				}
			}
			conn.close();
			return nercList; 

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	//vertici
	
	
	
	//archi
	public List<Archi> getArchi(Map<Integer,Nerc> idMap){
		String sql="SELECT p1.nerc_id as n1, p2.nerc_id as n2, COUNT(DISTINCT MONTH(p1.date_event_began), YEAR(p1.date_event_began)) AS peso " + 
				"FROM nercrelations nr, poweroutages p1, poweroutages p2 " + 
				"WHERE MONTH(p1.date_event_began)=MONTH(p2.date_event_began) " + 
				"		AND YEAR(p1.date_event_began)=YEAR(p2.date_event_began) " + 
				"		AND p1.nerc_id<>p2.nerc_id " + 
				"		AND nr.nerc_one=p1.nerc_id " + 
				"		AND nr.nerc_two=p2.nerc_id " + 
				"GROUP BY p1.nerc_id, p2.nerc_id " + 
				"HAVING peso!=0 ";
		List<Archi> result= new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc ne1= idMap.get(res.getInt("n1"));
				Nerc ne2= idMap.get(res.getInt("n2"));
				
				if(ne1!=null && ne2!=null) {
					Archi a= new Archi(ne1, ne2, res.getDouble("peso"));
					result.add(a);
				}
			}
			conn.close();
			return result;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}	
}
