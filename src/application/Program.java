package application;

import java.sql.Connection;
import java.util.Scanner;

import db.DB;

public class Program {

	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		
		
		int opcao1 = 0;
		
		//Connection conn = DB.getConnection();
		
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
				case 4: listReceitas();
						break;
				case 5: listTotReceitas();
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
		System.out.print("Data de recebimento (dd/mm/aaaa): ");
		String dataRecebimento = sc.nextLine();
		
		System.out.print("Data de recebimento esperado (dd/mm/aaaa): ");
		String dataRecebimentoEsperado = sc.nextLine();
		
		System.out.print("Descrição do recebimento: ");
		String descicaoRecebimento = sc.nextLine();
		
		System.out.print("Conta: ");
		String contaRecebimento = sc.nextLine();
		
		int tipoDeReceita;
		
		System.out.println("Tipo da Receita: (salário, presente, prêmio, outros): ");
		System.out.println("1 - Salário;");
		System.out.println("2 - Presente;");
		System.out.println("3 - Prêmio;");
		System.out.println("4 - Outros.");
		tipoDeReceita = sc.nextInt();
				
		System.out.println("Receita cadastrada.");
	
	
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
