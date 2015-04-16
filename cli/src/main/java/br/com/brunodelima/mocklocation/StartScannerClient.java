package br.com.brunodelima.mocklocation;

import java.util.Scanner;

import br.com.brunodelima.mocklocation.cli.CommandLineInterface;

/**
 * Created by dev on 16/04/2015.
 */
public class StartScannerClient {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CommandLineInterface cli = new CommandLineInterface();
        while (true) {
            String next = sc.nextLine();
            cli.execute(next);
        }
    }
}
