package Displays;
import java.util.Scanner;

public class mainPrincipal {
	
	public static int imprimirMenu()
	{
		Scanner sc = new Scanner(System.in);
		int opcion = sc.nextInt();
		
		return opcion;
	}

	public static void main(String[] args) {
		imprimirMenu();
		

	}

}
