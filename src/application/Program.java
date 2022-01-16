package application;

import java.sql.Connection;
import java.util.Scanner;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int opcao1 = 0;
		
		Connection conn = DB.getConnection();
		
		 do {
			switch (opcao1) {
			case 1: System.out.println("1"); 
					break;
			case 2: System.out.println("2");
					break;
			case 3: System.out.println("3");
					break;
			default: System.out.println("Opção inválida. Opções válidas de 1 até 4. Você digitou: " + opcao1);
					break;
			}
				
			
			System.out.println("Bem vindo ao sistema PubFuture, seu sistema de controle de finanças pessoais.");
			System.out.println("/n /n");
			System.out.println("Seguem as opções disponíveis do app:");
			System.out.println("1 - Receitas.");
			System.out.println("2 - Despesas.");
			System.out.println("3 - Contas.");
			System.out.println("4 - Sair do app.");
			
			opcao1 = sc.nextInt();
			System.out.println(opcao1);
		} while (opcao1 != 4);
		
		System.out.println("App encerrado. Volte sempre!");
		
				
		sc.close();
		DB.closeConnection();

	}

	private static void While(boolean b) {
		// TODO Auto-generated method stub
		
	}

}
