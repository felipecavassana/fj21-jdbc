package br.com.caelum.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.caelum.jdbc.ConnectionFactory;
import br.com.caelum.jdbc.modelo.Contato;

import com.mysql.jdbc.PreparedStatement;

public class ContatoDAO {
	// a conexao com o banco de dados
	private Connection connection;
	
	public ContatoDAO(){
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void adiciona(Contato contato){
		String sql = "INSERT INTO contatos " + "(nome,email,endereco,data)" + " VALUES (?,?,?,?)";
		
		try{
			// prepared statement para inserção
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
			
			// seta os valores
			stmt.setString(1, contato.getNome());
			stmt.setString(2, contato.getEmail());
			stmt.setString(3, contato.getEndereco());
			stmt.setDate(4, new Date(contato.getDataNascimento().getTimeInMillis()));
			
			//executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e){
			throw new RuntimeException (e);
		}
	}
	
	public List<Contato> getLista(){
		try{
			List<Contato> contatos = new ArrayList<Contato>();
			String sql = "SELECT * FROM contatos";
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				// criando o objeto Contato
				Contato c = new Contato();
				c.setId(rs.getLong("id"));
				c.setNome(rs.getString("nome"));
				c.setEmail(rs.getString("email"));
				c.setEndereco(rs.getString("endereco"));
				
				// montando a data através do Calendar
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("data"));
				c.setDataNascimento(data);
				
				// adicionando o objeto à lista
				contatos.add(c);
			}
			
			rs.close();
			stmt.close();
			return contatos;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void altera(Contato contato){
		String sql = "UPDATE contatos SET nome=?, email=?, endereco=?, data=? WHERE id=?";
		
		try{
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement(sql);
			stmt.setString(1, contato.getNome());
			stmt.setString(2, contato.getEmail());
			stmt.setString(3, contato.getEndereco());
			stmt.setDate(4, new Date(contato.getDataNascimento().getTimeInMillis()));
			stmt.setLong(5, contato.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public void remove (Contato contato){
		try{
			String sql = "DELETE FROM contatos WHERE id=?";
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement(sql);
			stmt.setLong(1, contato.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
}
