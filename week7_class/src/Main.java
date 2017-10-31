import java.io.*;
import java.util.*;

class Department {
    String departmentName;
    HashMap productPrice = new HashMap();
    int[] monthlyPrice = new int[12];
    public Department(String name) {
        this.departmentName = name;
    }
    public void addSales(int month, String name, int price) {
        HashMap temp = (HashMap)productPrice.get(month - 1);
        if (temp == null) {
            temp = new HashMap();
        }
        temp.put(name, price);
        productPrice.put(month - 1, temp);
        monthlyPrice[month - 1] += price;
    }
}
class Store {
    HashMap departmentList = new HashMap();
    public void addDepartment(String name) {
        Department department = new Department(name);
        departmentList.put(name, department);
    }
    public Department getDepartment(String name) {
        return (Department)departmentList.get(name);
    }
}
class ReportCreator {
    public void createMonthlyReport(Store store, String departmentName, int month) {
        Department department = store.getDepartment(departmentName);
        if (department.monthlyPrice[month - 1] == 0) {
            System.out.println("no data in selected month!");
        } else {
            System.out.println("display MonthlyReport for " + departmentName + " month " + month);
            HashMap temp = (HashMap)department.productPrice.get(month - 1);
            for (Object productName : temp.keySet()) {
                System.out.println(productName + " " + temp.get(productName));
            }
        }
    }
    public void createYTDReport(Store store, String departmentName, int month) {
        Department department = store.getDepartment(departmentName);
        if (department.monthlyPrice[month - 1] == 0) {
        } else {
            System.out.println("display YTDSalesChart for " + departmentName);
            for (int mymonth = 1; mymonth != 13; mymonth++) {
                int price = department.monthlyPrice[mymonth - 1];
                if (price != 0) {
                    System.out.println("month " + mymonth + " price " + price);
                }
            }
        }
    }
}
public class Main {
    public static void main(String[] args) {
        Store myStore = new Store();
        ReportCreator myReportCreator = new ReportCreator();
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
                String departmentName = lineArray[1];
                int monthNum = Integer.parseInt(lineArray[2]);
                if (lineArray[0].equals("department")) {
                    Department department = myStore.getDepartment(departmentName);
                    if (department == null) {
                        myStore.addDepartment(departmentName);
                        department = myStore.getDepartment(departmentName);
                    }
                    for (int i = 3; i < lineArray.length; i++) {
                        String name = lineArray[i].split(",")[0];
                        int price = Integer.parseInt(lineArray[i].split(",")[1]);
                        department.addSales(monthNum, name, price);
                    }
                } else if (lineArray[0].equals("select")) {
                    myReportCreator.createMonthlyReport(myStore, departmentName, monthNum);
                    myReportCreator.createYTDReport(myStore, departmentName, monthNum);
                } else {
                    //do nothing
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
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