package br.com.caelum.jdbc.teste;

import java.sql.SQLException;
import java.util.List;

import br.com.caelum.jdbc.dao.ContatoDAO;
import br.com.caelum.jdbc.modelo.Contato;

public class TestaLista {
	public static void main(String[] args) throws SQLException{
		ContatoDAO dao = new ContatoDAO();
		List<Contato> contatos = dao.getLista();
		
		for(Contato c : contatos){
			System.out.println("Nome: " + c.getNome());
			System.out.println("Email: " + c.getEmail());
			System.out.println("Endereço " + c.getEndereco());
			System.out.println("Data de nascimento: " + c.getDataNascimento().getTime() + "\n");
		}
	}
}
