package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Scanner;

import db.DB;

public class Program {

	static Scanner sc = new Scanner(System.in);
	static Connection conn = DB.getConnection();
	
	
	public static void main(String[] args) {
		
		
		
		int opcao1 = 0;
		
		
		
		 do {
			switch (opcao1) {
			case 1: menuReceitas(); 
					break;
			case 2: menuDespesas();
					break;
			case 3: menuContas();
					break;
			default: System.out.println("Opção inválida. Opções válidas de 1 até 4. Você digitou: " + opcao1);
					break;
			}
				
			
			System.out.println("Bem vindo ao sistema PubFuture, seu sistema de controle de finanças pessoais.");
			System.out.println();
			System.out.println("Seguem as opções disponíveis do app:");
			System.out.println("1 - Receitas.");
			System.out.println("2 - Despesas.");
			System.out.println("3 - Contas.");
			System.out.println("4 - Sair do app.");
			
			opcao1 = sc.nextInt();
			System.out.println(opcao1);
		} while (opcao1 != 4);
		
		System.out.println("App encerrado. Volte sempre!");
		
				
		
		DB.closeConnection();

	}

	// ------------------------------------------------------------------------------
		// Menu e sub-Menus das Receitas:
		
		static void menuReceitas() {
			
			
			
			int opcaoReceitas = 0;
			
			do {
				switch (opcaoReceitas) {
					case 1: cadReceitas();
							break;
		/*			case 2: editReceitas();
							break;
					case 3: remReceitas();
							break;
			*/		case 4: listReceitas();
							break;
				/*	case 5: listTotReceitas();
							break; */
					default: System.out.println("Opção inválida. Opções válidas de 1 até 6. Você digitou: " + opcaoReceitas);
							break;
				} 
			System.out.println("Menu -- Receitas --");
			System.out.println("Seguem as funções disponíveis:");
			System.out.println("1 - Cadastrar Receitas;");
			System.out.println("2 - Editar Receitas;");
			System.out.println("3 - Remover Receitas;");
			System.out.println("4 - Listar Receitas;");
			System.out.println("5 - Listar Total de Receitas;");
			System.out.println("6 - Voltar ao Menu Anterior.");
			opcaoReceitas = sc.nextInt();
			} while (opcaoReceitas != 6);
			
			
		}
		
		static void cadReceitas() {
		
			System.out.println("Menu -- Cadastro de Receita --");
			System.out.print("Cadastrar o valor da receita: ");
			double receita = sc.nextDouble();
			sc.nextLine();
			System.out.print("Data de recebimento (aaaa-mm-dd): ");
			String dataRecebimento = sc.nextLine();
			
			System.out.print("Data de recebimento esperado (aaaa-mm-dd): ");
			String dataRecebimentoEsperado = sc.nextLine();
			
			System.out.print("Descrição do recebimento: ");
			String descicaoRecebimento = sc.nextLine();
			
			System.out.println("Tipo de conta:");
			System.out.println("1 - Corrente");
			System.out.println("2 - Poupança");
			System.out.println("3 - Carteira");
			System.out.print("Opção: ");
			int opcaoContaRecebimento = sc.nextInt();
			String contaRecebimento;
			switch (opcaoContaRecebimento) {
			case 1: contaRecebimento = "Corrente";
					break;
			case 2: contaRecebimento = "Poupança";
					break;
			case 3: contaRecebimento = "Carteira";
					break;
			default: contaRecebimento = "Outra";
					break;
			}
			
			
			
			int tipoDeReceita;
			String tipoDeReceitaS;
			System.out.println("Tipo da Receita:");
			System.out.println("1 - Salário;");
			System.out.println("2 - Presente;");
			System.out.println("3 - Prêmio;");
			System.out.println("4 - Outros.");
			System.out.print("Opção: ");
			tipoDeReceita = sc.nextInt();
			switch (tipoDeReceita) {
				case 1: tipoDeReceitaS = "Salário";
						break;
				case 2: tipoDeReceitaS = "Presente";
						break;
				case 3: tipoDeReceitaS = "Prêmio";
						break;
				default: tipoDeReceitaS = "Outros";
						break;
			}
			PreparedStatement st = null;
			try {
				
				conn = DB.getConnection();
				
				st = conn.prepareStatement(
						"INSERT INTO receitas "
						+ "(valor, dataRecebimento, dataRecebimentoEsperado, descricao, conta, tipoReceita) "
						+ "VALUES "
						+ "(?, ?, ?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);
				
				st.setDouble(1, receita);
				st.setString(2, dataRecebimento + " 00:00:00");
				st.setString(3, dataRecebimentoEsperado + " 00:00:00");
				st.setString(4, descicaoRecebimento);
				st.setString(5, contaRecebimento);
				st.setString(6, tipoDeReceitaS);
				
				int rowsAffected = st.executeUpdate();
				
				if (rowsAffected > 0 ) {
					ResultSet rs = st.getGeneratedKeys();
					while (rs.next()) {
						int id = rs.getInt(1);
						System.out.println("Receita cadastrada. Código do Id : " + id);
					}
				}
				else {
					System.out.println("No rows affected!");
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				
			}
			
			finally {
				DB.closeStatement(st);
				DB.closeConnection();
			}		
			
			
				
		
		}
	
	// ------------------------------------------------------------------------------
	// Listas receitas:
	
		static void listReceitas() {
		
			Connection conn = null;
			Statement st = null;
			ResultSet rs = null;
				
		
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			
			
			rs = st.executeQuery("select * from receitas");
			double totalReceitas = 0;
			while (rs.next()) {
				System.out.println("Id:" + rs.getInt("id") + ", Valor: " + rs.getDouble("valor") + ", Data Recebimento: " + rs.getString("dataRecebimento") + ", Data Recebimento Esperado: " + rs.getString("dataRecebimentoEsperado") + ", Descrição: " + rs.getString("descricao") + ", Conta: " + rs.getString("conta") + ", Tipo Receita: " + rs.getString("tipoReceita") + ".");
				totalReceitas += rs.getDouble("valor");
				}
			System.out.println("Valor total das Receitas: " + totalReceitas);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
			}
		}
	
	
	// ------------------------------------------------------------------------------	
	// Menu e sub-Menus das Despesas:
	
	static void menuDespesas() {
				
		int opcaoDespesas = 0;
		
		do {
		System.out.println("Menu -- Despesas --");
		System.out.println("Seguem as funções disponíveis:");
		System.out.println("1 - Cadastrar Despesas;");
		System.out.println("2 - Editar Despesas;");
		System.out.println("3 - Remover Despesas;");
		System.out.println("4 - Listar Despesas;");
		System.out.println("5 - Listar Total de Despesas;");
		System.out.println("6 - Voltar ao Menu Anterior.");
		opcaoDespesas = sc.nextInt();
		} while (opcaoDespesas != 6);
		
		
	}
	
	
	
	// ------------------------------------------------------------------------------	
	// Menu e sub-Menus das Despesas:
	
	static void menuContas() {
		
		
		
		int opcaoContas = 0;
		
		do {
		System.out.println("Menu -- Despesas --");
		System.out.println("Seguem as funções disponíveis:");
		System.out.println("1 - Cadastrar Conta;");
		System.out.println("2 - Editar Conta;");
		System.out.println("3 - Remover Conta;");
		System.out.println("4 - Listar Contas;");
		System.out.println("5 - Transferir Saldo Entre Contas;");
		System.out.println("6 - Listar Salto Total;");
		System.out.println("7 - Voltar ao Menu Anterior.");
		opcaoContas = sc.nextInt();
		} while (opcaoContas != 7);
		
		
	}
	

}
