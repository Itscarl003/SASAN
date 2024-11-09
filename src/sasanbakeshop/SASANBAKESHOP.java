package sasanbakeshop;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Scanner;


public class SASANBAKESHOP {

    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);    
        boolean exit = true;
        do{
        System.out.println("(((WELCOME TO BAKERY SHOP)))");
        System.out.println("");
        System.out.println("1.) CUSTOMER");
        System.out.println("2.) PRODUCT");
        System.out.println("3.) ORDER");
        System.out.println("4.) EXIT");
        
        System.out.print("Enter Action: ");
        int act = sc.nextInt();
        
        switch(act){
            case 1:
                Customer customer = new Customer ();
                customer.cTransaction();
            break;
            
            case 2:
                Product product = new Product ();
                product.pTransaction();
            break;
            
            case 3:
                Order order = new Order ();
                order.oTransaction();
            break;
          
            case 4:
                System.out.println("SURE NAKA?? (yes/no): ");
                String resp = sc.next();
                    if(resp.equalsIgnoreCase("yes")){
                           exit = false;
                }
            break;   
            
        }
        }while (exit);
        System.out.print("SIGE SUNOD NASAD!");               
    
    }
} 