package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.genes.model.Arco;
import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public List<Genes> getEssentialGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes WHERE Essential = 'Essential'";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Arco> getEdges() {
		
		String sql = "SELECT DISTINCT i.GeneID1, i.GeneID2, ABS(i.Expression_Corr) AS weight, g1.Chromosome, g2.Chromosome "
				+ "FROM interactions i, genes g1, genes g2 "
				+ "WHERE g1.GeneID = i.GeneID1 AND g2.GeneID = i.GeneID2 "
				+ "AND i.GeneID1 <> i.geneID2 "
				+ "AND g1.Essential = 'Essential' AND g2.Essential = 'Essential' ";
//				+ "AND g1.Chromosome <> g2.Chromosome";
		
		List<Arco> result = new ArrayList<Arco>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				String genes1 = res.getString("i.GeneID1"); 
				String genes2 = res.getString("i.GeneID2"); 
				Double weight = res.getDouble("weight");
				
				if (res.getInt("g1.Chromosome") == res.getInt("g2.Chromosome")) {
					weight = 2 * weight;
				}
				
				Arco arco = new Arco(genes1, genes2, weight);
				
				result.add(arco);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	
	
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
		
}
