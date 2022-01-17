package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import db.DB;
import db.DbIntegrityException;

public class Program {

	static Scanner sc = new Scanner(System.in);
	static Connection conn = DB.getConnection();
	static String enter;
	
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
			default: System.out.println();
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
					case 2: listReceitas();
							break;
					case 3: remReceitas();
							break;
					default: System.out.println();
							break;
				} 
			System.out.println("Menu -- Receitas --");
			System.out.println("Seguem as funções disponíveis:");
			System.out.println("1 - Cadastrar Receitas;");
			System.out.println("2 - Listas Receitas e o total delas;");
			System.out.println("3 - Remover receita;");
			System.out.println("4 - Voltar ao Menu Anterior.");
			opcaoReceitas = sc.nextInt();
			} while (opcaoReceitas != 4);
			
			
		}
		
		static void cadReceitas() {
		
			System.out.println("Menu -- Cadastro de Receita --");
			System.out.print("Cadastrar o Valor da Receita: ");
			double receita = sc.nextDouble();
			sc.nextLine();
			System.out.print("Data de Recebimento (aaaa-mm-dd): ");
			String dataRecebimento = sc.nextLine();
			
			System.out.print("Data de Recebimento Esperado (aaaa-mm-dd): ");
			String dataRecebimentoEsperado = sc.nextLine();
			
			System.out.print("Descrição do Recebimento: ");
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
			
			
			System.out.println("Tecle enter para continuar");
			enter = sc.nextLine();
				
		
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
		
		System.out.println("Tecle enter para continuar");
		enter = sc.nextLine();
		}
	
	
		
	// ------------------------------------------------------------------------------
	// Deletar receitas:

		static void remReceitas() {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			System.out.print("Digite o Id da Receita e ser apagada: ");
			int remReceitaCod = sc.nextInt();
			st = conn.prepareStatement("DELETE FROM receitas WHERE id = ?;");
			st.setInt(1,remReceitaCod);
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Pronto. Número de receitas removidas: " + rowsAffected);
			}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
}
		
		
	// ------------------------------------------------------------------------------	
	// Menu e sub-Menus das Despesas:
	
	static void menuDespesas() {
				
		int opcaoDespesas = 0;
		
		do {
			switch (opcaoDespesas) {
			case 1: cadDespesas();
					break;
			case 2: listDespesas();
					break;
			case 3: remDespesas();
					break;
			default: System.out.println();
					break;
		} 
		System.out.println("Menu -- Despesas --");
		System.out.println("Seguem as funções disponíveis:");
		System.out.println("1 - Cadastrar Despesas;");
		System.out.println("2 - Listar Despesas e o total delas;");
		System.out.println("3 - Remover Despesa;");
		System.out.println("4 - Voltar ao Menu Anterior.");
		opcaoDespesas = sc.nextInt();
		} while (opcaoDespesas != 4);
		
		
	}
	
	// ---------------------------------------------------------------
	// Cadastro de despesas:
	
	static void cadDespesas() {
		
		System.out.println("Menu -- Cadastro de Despesa --");
		System.out.print("Cadastrar o Valor da Despesa: ");
		double despesa = sc.nextDouble();
		sc.nextLine();
		System.out.print("Data de Pagamento (aaaa-mm-dd): ");
		String dataPagamento = sc.nextLine();
		
		System.out.print("Data de Pagamento Esperado (aaaa-mm-dd): ");
		String dataPagamentoEsperado = sc.nextLine();
		
		System.out.println("Tipo de Despesa:");
		System.out.println("1 - Alientação;");
		System.out.println("2 - Educação;");
		System.out.println("3 - Lazer;");
		System.out.println("4 - Moradia;");
		System.out.println("5 - Roupa;");
		System.out.println("6 - Saúde;");
		System.out.println("7 - Transporte;");
		System.out.println("8 - Outros.");
		System.out.print("Opção: ");
		int opcaoTipoDespesa = sc.nextInt();
		String tipoDespesa;
		switch (opcaoTipoDespesa) {
		case 1: tipoDespesa = "Alimentação";
				break;
		case 2: tipoDespesa = "Educação";
				break;
		case 3: tipoDespesa = "Lazer";
				break;
		case 4: tipoDespesa = "Moradia";
				break;
		case 5: tipoDespesa = "Roupa";
				break;
		case 6: tipoDespesa = "Saúde";
				break;
		case 7: tipoDespesa = "Transporte";
				break;
		default: tipoDespesa = "Outra";
				break;
		}
		
		System.out.println("Tipo de Conta:");
		System.out.println("1 - Corrente");
		System.out.println("2 - Poupança");
		System.out.println("3 - Carteira");
		System.out.print("Opção: ");
		int opcaoContaDespesa = sc.nextInt();
		String contaDespesa;
		switch (opcaoContaDespesa) {
		case 1: contaDespesa = "Corrente";
				break;
		case 2: contaDespesa = "Poupança";
				break;
		case 3: contaDespesa = "Carteira";
				break;
		default: contaDespesa = "Outra";
				break;
		}
		
		
		
		PreparedStatement st = null;
		try {
			
			conn = DB.getConnection();
			
			st = conn.prepareStatement(
					"INSERT INTO despesas "
					+ "(valor, dataPagamento, dataPagamentoEsperado, tipoDespesa, conta) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setDouble(1, despesa);
			st.setString(2, dataPagamento + " 00:00:00");
			st.setString(3, dataPagamentoEsperado + " 00:00:00");
			st.setString(4, tipoDespesa);
			st.setString(5, contaDespesa);
			
			
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
		
		
		System.out.println("Tecle enter para continuar");
		enter = sc.nextLine();
			
	
	}

// ------------------------------------------------------------------------------
// Listas receitas:

	static void listDespesas() {
	
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
			
	
	try {
		conn = DB.getConnection();
		st = conn.createStatement();
		
		
		rs = st.executeQuery("select * from despesas");
		double totalDespesas = 0;
		while (rs.next()) {
			System.out.println("Id:" + rs.getInt("id") + ", Valor: " + rs.getDouble("valor") + ", Data Pagamento: " + rs.getString("dataPagamento") + ", Data Pagamento Esperado: " + rs.getString("dataPagamentoEsperado") + ", Tipo de Despesa: " + rs.getString("tipoDespesa") + ", Conta: " + rs.getString("conta") + ".");
			totalDespesas += rs.getDouble("valor");
			}
		System.out.println("Valor Total das Despesas: " + totalDespesas);
	}
	catch (SQLException e) {
		e.printStackTrace();
	}
	
	System.out.println("Tecle enter para continuar");
	enter = sc.nextLine();
	}


	
// ------------------------------------------------------------------------------
// Deletar despesas:

	static void remDespesas() {
	Connection conn = null;
	PreparedStatement st = null;
	try {
		conn = DB.getConnection();
		System.out.print("Digite o Id da Receita e ser apagada: ");
		int remDespesasCod = sc.nextInt();
		st = conn.prepareStatement("DELETE FROM despesas WHERE id = ?;");
		st.setInt(1,remDespesasCod);
		
		int rowsAffected = st.executeUpdate();
		
		System.out.println("Pronto. Número de despesas removidas: " + rowsAffected);
		}
	catch (SQLException e) {
		throw new DbIntegrityException(e.getMessage());
	} 
}
	
	
	
	
	
	
	// ------------------------------------------------------------------------------	
	// Menu e sub-Menus das Contas:
	
	static void menuContas() {
		
		
		
		int opcaoContas = 0;
		
		do {
			switch (opcaoContas) {
			case 1: cadContas();
					break;
			case 2: listContas();
					break;
			case 3: remContas();
					break;
			default: System.out.println();
					break;
		} 
		System.out.println("Menu -- Despesas --");
		System.out.println("Seguem as funções disponíveis:");
		System.out.println("1 - Cadastrar Conta;");
		System.out.println("2 - Listar Contas;");
		System.out.println("3 - Remover Conta e o total delas;");
		System.out.println("4 - Voltar ao Menu Anterior.");
		opcaoContas = sc.nextInt();
		} while (opcaoContas != 4);
		
		
		
		
		
	}
	
	// ---------------------------------------------------------------
	// Cadastro de Contas:
		
		static void cadContas() {
			
			System.out.println("Menu -- Cadastro de Contas --");
			System.out.print("Cadastrar o Nome do Banco: ");
			String banco = sc.next();
			sc.nextLine();
			
			System.out.println("Tipo de Conta:");
			System.out.println("1 - Corrente");
			System.out.println("2 - Poupança");
			System.out.println("3 - Carteira");
			System.out.print("Opção: ");
			int opcaoContaBanco = sc.nextInt();
			String contaBanco;
			switch (opcaoContaBanco) {
			case 1: contaBanco = "Corrente";
					break;
			case 2: contaBanco = "Poupança";
					break;
			case 3: contaBanco = "Carteira";
					break;
			default: contaBanco = "Outra";
					break;
			}

			System.out.print("Cadastrar Saldo do Banco: ");
			Double saldoBanco = sc.nextDouble();
			
			
			
			PreparedStatement st = null;
			try {
				
				conn = DB.getConnection();
				
				st = conn.prepareStatement(
						"INSERT INTO bancos "
						+ "(instituicao, tipo, saldo) "
						+ "VALUES "
						+ "(?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);
				
				st.setString(1, banco);
				st.setString(2, contaBanco);
				st.setDouble(3, saldoBanco);
								
				
				int rowsAffected = st.executeUpdate();
				
				if (rowsAffected > 0 ) {
					ResultSet rs = st.getGeneratedKeys();
					while (rs.next()) {
						int id = rs.getInt(1);
						System.out.println("Conta cadastrada. Código do Id : " + id);
					}
				}
				else {
					System.out.println("No rows affected!");
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				
			}
			
			
			System.out.println("Tecle enter para continuar");
			enter = sc.nextLine();
				
		
		}

	// ------------------------------------------------------------------------------
	// Listas contas:

		static void listContas() {
		
			Connection conn = null;
			Statement st = null;
			ResultSet rs = null;
				
		
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			
			
			rs = st.executeQuery("select * from bancos");
			double totalBancos = 0;
			while (rs.next()) {
				System.out.println("Id:" + rs.getInt("id") + ", Instituição: " + rs.getString("instituicao") + ", Tipo da Conta: " + rs.getString("tipo") + ", Saldo: " + rs.getString("saldo") + ".");
				totalBancos += rs.getDouble("saldo");
				}
			System.out.println("Valor Total de Saldo nas Contas: " + totalBancos);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Tecle enter para continuar");
		enter = sc.nextLine();
		}


		
	// ------------------------------------------------------------------------------
	// Deletar despesas:

		static void remContas() {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			System.out.print("Digite o Id da Conta e ser apagada: ");
			int remConta = sc.nextInt();
			st = conn.prepareStatement("DELETE FROM bancos WHERE id = ?;");
			st.setInt(1,remConta);
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Pronto. Número de Contas Removidas: " + rowsAffected);
			}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
	}
	
	

}
