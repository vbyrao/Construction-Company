import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Locale;
import java.util.Scanner;

public class Service {
    static final String SPACE = " ";
    static final String relations = "Relations [RawMaterial, Supplier, SupplierEmail, Address, Contractor, Project, Labor, SupplierBuysRawMaterial, ContractorContactsSupplier, LabourWorksOnProject]";
    static boolean isRelationNameValid(String relationName){
        if(relationName.equalsIgnoreCase("RawMaterial")
                || relationName.equalsIgnoreCase("Supplier")
                || relationName.equalsIgnoreCase("SupplierEmail")
                || relationName.equalsIgnoreCase("Address")
                || relationName.equalsIgnoreCase("Contractor")
                || relationName.equalsIgnoreCase("Project")
                || relationName.equalsIgnoreCase("Labor")
                || relationName.equalsIgnoreCase("SupplierBuysRawMaterial")
                || relationName.equalsIgnoreCase("ContractorContactsSupplier")
                || relationName.equalsIgnoreCase("LabourWorksOnProject")
        ){
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException, SQLException, ParseException {
        Scanner sc = new Scanner(System.in);
        JDBC jdbc = new JDBC();
        String relationName, cellValue;
        int choice;
        ResultSet resultSet;

        if (jdbc.con != null) {
            System.out.printf("Welcome %s%n", jdbc.username);
            do{
                System.out.println("--- Choose ---" +
                                    "\n1.Display " +
                                    "\n2.Insert " +
                                    "\n3.Delete " +
                                    "\n4.Goals " +
                                    "\n0.Exit ");
                choice = Integer.parseInt(sc.nextLine());

                switch (choice){
                    case 0:
                        System.out.println("\t### Exit ###");
                        break;

                    case 1:
                        System.out.println("\t### Display ###" + "\n\t" + relations);
                        System.out.println("\tEnter Relation name: ");
                        relationName = sc.nextLine();
                        if(isRelationNameValid(relationName)){
                            resultSet = jdbc.display(relationName);
                            for(int i=0;i<resultSet.getMetaData().getColumnCount();i++){
                                System.out.printf(resultSet.getMetaData().getColumnName(i+1).concat(SPACE));
                            }
                            System.out.print("\n");
                            while (resultSet.next()){
                                for(int i=0;i<resultSet.getMetaData().getColumnCount();i++){
                                    System.out.printf(resultSet.getString(i+1).concat(SPACE));
                                }
                                System.out.print("\n");
                            }
                        }else{
                            System.out.println("Enter valid relation name");
                        }
                        break;

                    case 2:
                        System.out.println("\t### Insert ###" + "\n\t" + relations);
                        ArrayList<String> arrString = new ArrayList<String>();
                        ArrayList<Date> arrDate = new ArrayList<Date>();
                        System.out.println("\tEnter Relation name: ");
                        relationName = sc.nextLine();
                        if(isRelationNameValid(relationName)){
                            resultSet = jdbc.display(relationName);
                            for(int i=0;i<resultSet.getMetaData().getColumnCount();i++){
                                String curColumnName = resultSet.getMetaData().getColumnName(i+1);
                                System.out.println("Enter ".concat(curColumnName));
                                if(curColumnName.indexOf("DATE")!=-1){  //Date Column
                                    System.out.println("Use Date Format (yyyy-MM-dd)");
                                    cellValue = sc.nextLine();
                                    arrDate.add(new Date(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(cellValue).getTime()));
                                }else{
                                    arrString.add(sc.nextLine());
                                }
                            }
                            if(jdbc.insert(relationName, arrString, arrDate) == 1){
                                System.out.println("Record Inserted Successfully");
                            }else{
                                System.out.println("Error while Inserting record");
                            }
                        }else{
                            System.out.println("Enter valid relation name");
                        }
                        break;

                    case 3:
                        System.out.println("\t### Delete ###" + "\n\t" + relations.substring(0, 84).concat("]"));
                        System.out.println("\tEnter Relation name: ");
                        relationName = sc.nextLine();
                        if(isRelationNameValid(relationName)) {
                            resultSet = jdbc.display(relationName);
                            String primaryColumnName = resultSet.getMetaData().getColumnName(1);
                            System.out.println("\tEnter value for primary attribute " + primaryColumnName);
                            String primaryValue = sc.nextLine();
                            if(jdbc.delete(relationName, primaryColumnName, primaryValue) == 1){
                                System.out.println("Record Deleted Successfully");
                            }else{
                                System.out.println("Error while Deleting record");
                            }
                        }
                        break;

                    case 4:
                        System.out.println("\t### Business Goals ###" + "\n\t" + relations);
                        System.out.println("--- Choose ---" +
                                "\n1.Avg area of all three sectors in a user input City " +
                                "\n2.Avg Labour count and Labour Rate in a user input State " +
                                "\n3.Top 3 zips with highest total selling price across user input Sector \n " +
                                "\n0.Exit ");
                        int choice2 = Integer.parseInt(sc.nextLine());
                        String p = "";
                        System.out.println("Enter parameter name:");
                        p = sc.nextLine();
                        resultSet = jdbc.goals(p,choice2);
                        for(int i=0;i<resultSet.getMetaData().getColumnCount();i++){
                            System.out.printf(resultSet.getMetaData().getColumnName(i+1).concat(SPACE));
                        }
                        System.out.print("\n");
                        while (resultSet.next()){
                            for(int i=0;i<resultSet.getMetaData().getColumnCount();i++){
                                System.out.printf(resultSet.getString(i+1).concat(SPACE));
                            }
                            System.out.print("\n");
                        }
                        break;

                    default:
                        System.out.println("Invalid Input");
                }
            }while(choice!=0);
        } else {
            System.out.println("Connection null");
        }
    }
}

