package sasanbakeshop;

import java.util.Scanner;


public class Product {
    public void pTransaction(){
        
        Scanner sc = new Scanner (System.in);
        String response;
        do{
            
        System.out.println("");
        System.out.println("(((WELCOME TO PRODUCT)))");   
        System.out.println("");
        System.out.println("1.) ADD PRODUCT");
        System.out.println("2.) VIEW PRODUCT");
        System.out.println("3.) UPDATE PRODUCT");
        System.out.println("4.) DELETE PRODUCT");
        System.out.println("5.) EXIT PRODUCT");
        
        System.out.print("Enter Action: ");
        int action = sc.nextInt();
        Product product = new Product ();

        switch(action){
            case 1:
                product.addProducts();
                break;
            case 2:       
                product.viewProducts();
                break;
            case 3:
                product.viewProducts();
                product.updateProducts();
                product.viewProducts();
                break;
            case 4:
                product.viewProducts();
                product.deleteProducts();
                product.viewProducts();    
                break;
        }
        System.out.println("Do you want to continue? (yes/no)");
        response = sc.next();
    }while(response.equalsIgnoreCase("yes"));
    System.out.println("Thank You, See you soonest!");
    
    }
    
    
    public void addProducts () { 
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("Product Name: ");
        String name = sc.nextLine();
        System.out.print("Price: ");
        double price = sc.nextDouble();
        System.out.print("Stocks: ");
        double stck = sc.nextDouble();
        System.out.print("Category: ");
        String cat = sc.next();
        System.out.print("Bake Date: ");
        String date = sc.next();
        String stat;
        if (stck > 0) {
            stat = "Available";
        } else {
            stat = "Not Available";
        }
        
            
        
        String sql = "INSERT INTO tbl_product (p_name, p_price, p_stocks, p_category, p_bakedate, p_status) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addRecord(sql, name, price, stck, cat, date, stat);


    }

    public void viewProducts() {
        config conf = new config();
        String Query = "SELECT * FROM tbl_product";
        String[] Headers = {"Product_ID","Product Name", "Price", "Stocks", "Category", "Bake Date", "Status"};
        String[] Columns = {"p_id", "p_name", "p_price", "p_stocks", "p_category", "p_bakedate", "p_status"};
        
        
        conf.viewRecords(Query, Headers, Columns);
    }
    private void updateProducts() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.println("Enter the ID to update: ");
        int id = sc.nextInt();
  
        while(conf.getSingleValue("SELECT p_id FROM tbl_product WHERE p_id = ?", id) == 0){
        System.out.println("Selected ID doesn't exist!");
        System.out.print("Select Product ID Again: ");
        id = sc.nextInt();
        }
        
        System.out.println("New Product Name: ");
        String nname = sc.next();
        System.out.println("New Price: ");
        double nprice = sc.nextDouble();
        System.out.println("New Stocks: ");
        double nstck = sc.nextDouble();
        System.out.println("New Category: ");
        String ncat = sc.next();
        System.out.println("New Bake Date: ");
        String ndate = sc.next();
        System.out.println("New Status: ");
        String nstat = sc.next();
            
        String qry = "UPDATE tbl_product SET p_name = ?, p_price = ?, p_stock = ?, p_category = ?, p_bakedate =?, p_status = ? WHERE p_id = ?";
        
        
        conf.updateRecord(qry, nname, nprice, nstck, ncat, ndate, nstat, id);         
        
        
    }
    
    private void deleteProducts() {
        Scanner sc = new Scanner (System.in);
        config conf = new config();
        System.out.println("Enter the ID  to delete: ");
        int id = sc.nextInt();
        
        while(conf.getSingleValue("SELECT p_id FROM tbl_product WHERE p_id = ?", id) == 0){
        System.out.println("Selected ID doesn't exist!");
        System.out.print("Select Product ID Again: ");
        id = sc.nextInt();
        }
        
        String qry = "DELETE FROM tbl_product WHERE p_id = ?";
        
       
        conf.deleteRecord(qry, id);
    }
}



