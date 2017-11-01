import java.io.*;
import java.util.*;

interface Iencapsulator {
    public String createHeader();
    public String createFooter();
}

class CSVEncapsulator implements Iencapsulator {
    public String createHeader() {
        return "<CSV header>";
    }
    public String createFooter() {
        return "<CSV footer>";
    }
}

class XMLEncapsulator implements Iencapsulator {
    public String createHeader() {
        return "<XML header>";
    }
    public String createFooter() {
        return "<XML footer>";
    }
}

class ObjectEncapsulator implements Iencapsulator {
    public String createHeader() {
        return "<Object header>";
    }
    public String createFooter() {
        return "<Object footer>";
    }
}

class Package {
    public String header;
    public String data;
    public String footer;
    public Package(String header, String data, String footer) {
        this.header = header;
        this.data = data;
        this.footer = footer;
    }
}

class Order {
    int orderNum;
    String goodsType;
    String orderData;
    public Order(int orderNum, String goodsType, String orderData) {
        this.orderNum = orderNum;
        this.goodsType = goodsType;
        this.orderData = orderData;
    }
}

class Company {
    String companyType;
    String category;
    public Company(String type, String category) {
        this.companyType = type;
        this.category = category;
    }
    public String accept(Package receivedPackage) {
        return companyType + " company receive";
    }
}

class Assigner {
    HashMap<String, Company> companyList = new HashMap<String, Company>();
    HashMap<Integer, Package> packageList = new HashMap<Integer, Package>();
    HashMap<Integer, Order> orderList = new HashMap<Integer, Order>();
    public void addCompany(String companyType, String category) {
        companyList.put(companyType, new Company(companyType, category));
    }
    public void receiveOrder(Order order) {
        Company company = companyList.get(order.goodsType);
        if (company == null) {
            System.out.println("no company can fulfill such order!");
        } else {
            Iencapsulator encapsulator = null;
            switch (company.category) {
                case "XML":
                    encapsulator = new XMLEncapsulator();
                    break;
                case "CSV":
                    encapsulator = new CSVEncapsulator();
                    break;
                case "Object":
                    encapsulator = new ObjectEncapsulator();
                    break;
                default:
                    return;
            }
            orderList.put(order.orderNum, order);
            packageList.put(order.orderNum, new Package(encapsulator.createHeader(), order.orderData, encapsulator.createFooter()));
            System.out.println("order " + order.orderNum + ": " + order.goodsType + " order created in " + company.category + " format");
        }
    }
    public void transmit(int orderNum) {
        Package transmittingPackage = packageList.get(orderNum);
        if (transmittingPackage == null) {
            System.out.println("no such order!");
        } else {
            Order order = orderList.get(orderNum);
            Company company = companyList.get(order.goodsType);
            System.out.println(company.accept(transmittingPackage) + " order " + orderNum + ":");
            System.out.println(transmittingPackage.header + transmittingPackage.data + transmittingPackage.footer);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Assigner assigner = new Assigner();
        BufferedReader br = null;
        FileReader fr = null;
        String inputFileName = args[0];
        try {
            fr = new FileReader(inputFileName);
            br = new BufferedReader(fr);
            String currentLine;
            int lineNo = 1;
            while ((currentLine = br.readLine()) != null) {
                String[] lineArray = currentLine.split(" ");
                switch (lineArray[0]) {
                    case "company":
                        assigner.addCompany(lineArray[1], lineArray[2]);
                        break;
                    case "order":
                        assigner.receiveOrder(new Order(Integer.parseInt(lineArray[1]), lineArray[2], lineArray[3]));
                        break;
                    case "transmit":
                        assigner.transmit(Integer.parseInt(lineArray[1]));
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception ex) {
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}