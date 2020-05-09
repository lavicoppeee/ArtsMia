package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.ArtObject;

public class ArtsmiaDAO {

	public void listObjects(Map<Integer, ArtObject> idMap) {

		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				int id = res.getInt("object_id");
				if (!idMap.containsKey(id)) {
					ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"),
							res.getString("continent"), res.getString("country"), res.getInt("curator_approved"),
							res.getString("dated"), res.getString("department"), res.getString("medium"),
							res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"),
							res.getString("rights_type"), res.getString("role"), res.getString("room"),
							res.getString("style"), res.getString("title"));
					idMap.put(artObj.getId(), artObj);
				}
			}
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
	
	public List<Adiacenza> getAdiacenza(){
		
		String sql="Select eo1.object_id as obj1, eo2.object_id as obj2, count(*) as peso "+
	               "from exhibition_objects as eo1,exhibition_objects as eo2 "+
	 			   "where eo1.exhibition_id = eo2.exhibition_id "+
	               "and eo1.object_id > eo2.object_id "+
	 			   " group by eo1.object_id, eo2.object_id ";
		
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while (res.next()) {
				result.add(new Adiacenza(res.getInt("obj1"),res.getInt("obj2"), res.getInt("peso")));
				}
			
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	 			   
	}
	
}
