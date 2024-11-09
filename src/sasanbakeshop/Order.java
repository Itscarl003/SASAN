package sasanbakeshop;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Order {
    public void oTransaction(){
        
        Scanner sc = new Scanner (System.in);
        String response;
        do{
            
        System.out.println("");
        System.out.println("(((WELCOME TO ORDER)))");   
        System.out.println("");
        System.out.println("1.) ADD ORDER");
        System.out.println("2.) VIEW ORDER");
        System.out.println("3.) UPDATE ORDER");
        System.out.println("4.) DELETE ORDER");
        System.out.println("5.) EXIT ORDER ");
        
        System.out.print("Enter Action: ");
        int action = sc.nextInt();
        Order order = new Order ();

        switch(action){
            case 1:
                order.addOrders();
                order.viewOrders();
                break;
            case 2:       
                order.viewOrders();
                break;
            case 3:
                
                break;
            case 4:
                ;    
                break;
        }
        System.out.println("Do you want to continue? (yes/no)");
        response = sc.next();
    }while(response.equalsIgnoreCase("yes"));
    System.out.println("Thank You, See you soonest!");
    
    }
    
    
   public void addOrders() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        Customer customer = new Customer();
        customer.viewCustomers();
        
        System.out.print("Enter the ID of the Customer: ");
        int cid = sc.nextInt();
        
        String csql = "SELECT c_id FROM tbl_customer WHERE c_id = ?";
        while (conf.getSingleValue(csql, cid) == 0) {
            System.out.print("Customer does not exist, Select Again: ");
            cid = sc.nextInt();
        }
        
        Product product = new Product();
        product.viewProducts();
        
        System.out.print("Enter the ID of the Product: ");
        int pid = sc.nextInt();
        
        String psql = "SELECT p_id FROM tbl_product WHERE p_id = ?";
        while (conf.getSingleValue(psql, pid) == 0) {
            System.out.print("Product does not exist, Select Again: ");
            pid = sc.nextInt();
        }
        
        String stockQuery = "SELECT p_stocks FROM tbl_product WHERE p_id = ?";
        double stock = conf.getSingleValue(stockQuery, pid);
        
        double quantity;
        if (stock == 0) {
            System.out.println("Product is out of stock and not available for ordering.");
            quantity = 0;

            
            String updateStatusQuery = "UPDATE tbl_product SET p_status = 'Not Available' WHERE p_id = ?";
            conf.updateRecord(updateStatusQuery, pid);
        } else {
            System.out.print("Enter Quantity: ");
            quantity = sc.nextDouble();
            
            while (quantity > stock) {
                System.out.println("Requested quantity exceeds available stock. Available stock: " + stock);
                System.out.print("Enter Quantity: ");
                quantity = sc.nextDouble();
            }
        }
        
        
        String priceqry = "SELECT p_price FROM tbl_product WHERE p_id = ?";
        double price = conf.getSingleValue(priceqry, pid);
        double due = price * quantity;
        
        System.out.println("---------------------------");
        System.out.println("Total Due: " + due);
        System.out.println("---------------------------");

        System.out.print("Enter the received cash: ");
        double rcash = sc.nextDouble();
        
        while (rcash < due) {
            System.out.println("Invalid Amount, Try Again: ");
            rcash = sc.nextDouble();
        }
        
        LocalDate currdate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = currdate.format(format);

        String status = (quantity == 0) ? "Not Available" : "Pending";
        
        String qry = "INSERT INTO tbl_order (c_id, p_id, o_quantity, o_due, o_rcash, o_date, o_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        conf.addRecord(qry, cid, pid, quantity, due, rcash, date, status);
    }

    public void viewOrders() {
        String qry = "SELECT o_id, c_fname, p_name, p_price, p_stocks, p_status, o_quantity, o_due, o_rcash, o_date, o_status FROM tbl_order "
                + "LEFT JOIN tbl_customer ON tbl_customer.c_id = tbl_order.c_id "
                + "LEFT JOIN tbl_product ON tbl_product.p_id = tbl_order.p_id";

        String[] hrds = {"O_ID", "Customer Name", "Product Name", "Price", "Stocks", "Product Status", "Order Quantity", "Due", "RCASH", "Date", "Order Status"};
        String[] clms = {"o_id", "c_fname", "p_name", "p_price", "p_stocks", "p_status", "o_quantity", "o_due", "o_rcash", "o_date", "o_status"};
        
        config conf = new config();
        conf.viewRecords(qry, hrds, clms);
    }
}
