package ru.kpfu;

import ru.kpfu.client.WebClient;

import java.util.Scanner;

/**
 *  Simple console interface for testing WebClient
 */

public class Main {
    private static boolean doParamsNeeded = true;
    private static String method;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        WebClient webClient = new WebClient(setHost(sc));
        webClient.setMethod(setMethod(sc));
        setPath(webClient, sc);
        setParams(webClient, sc);
        setBody(webClient, sc);

        System.out.println(webClient.send());
    }

    private static String setHost(Scanner sc){
        System.out.print("Write host: ");
        return sc.nextLine();
    }

    private static String setMethod(Scanner sc){
        System.out.print("Write method: ");
        method = sc.nextLine().toUpperCase();
        return method;
    }

    private static void setPath(WebClient webClient, Scanner sc){
        System.out.print("Do you want to set path? [y/n]:");
        if(sc.nextLine().equals("y")){
            System.out.print("Write path: ");
            webClient.setPath(sc.nextLine());
        }
    }

    private static void setParams(WebClient webClient, Scanner sc) {
        int delInd;
        String paramStr;
        String key;
        String value;
        while (doParamsNeeded){
            System.out.print("Do you want to add parameter? [y/n]: ");
            if(!sc.nextLine().equals("y")){
                doParamsNeeded = false;
                break;
            }
            System.out.print("Add parameter [format: <key>=<value>]: ");
            paramStr = sc.nextLine().replace(" ", "");
            delInd = paramStr.indexOf("=");
            key = paramStr.substring(0, delInd++);
            value = paramStr.substring(delInd);
            webClient.addParameter(key, value);
        }
    }

    private static void setBody(WebClient webClient, Scanner sc){
        if(method.equals("POST")) {
            System.out.print("Add body: ");
            webClient.setBody(sc.nextLine());
        }
    }
}
