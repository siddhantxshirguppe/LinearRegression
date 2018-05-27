package pack;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class Main extends Application {
    //++++++++++++

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/t";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";

   public static ArrayList<Integer> arrdbx = new ArrayList<>();

    public static ArrayList<Integer> arrdby = new ArrayList<>();

    //+++++++++++++






    public void start(Stage mystage) throws Exception
    {


        Parent root = (Parent) FXMLLoader.load(Main.class.getResource("/pack/Main.fxml"));
       ListView<String> simpleList;


        simpleList = new ListView<>(FXCollections.observableArrayList("Item1", "Item2", "Item3", "Item4"));
        simpleList.setEditable(true);

        simpleList.setCellFactory(TextFieldListCell.forListView());

        simpleList.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> t) {
                simpleList.getItems().set(t.getIndex(), t.getNewValue());
                System.out.println("setOnEditCommit");
            }

        });

        simpleList.setOnEditCancel(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> t) {
                System.out.println("setOnEditCancel");
            }
        });
       // URL url = new File("src/pack/Main.fxml");
       // Parent root = FXMLLoader.load(url);
        Scene scn= new Scene(root,1000,1000);
        mystage.setScene(scn);
        mystage.setTitle("LinearRegression");
        mystage.show();





    }
    public static void main(String[] args) {


	// write your code here



        Connection conn = null;
        Statement stmt = null;
        Statement stmt2 = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();

            String sql = "SELECT x FROM xvalue";
            ResultSet rs = stmt.executeQuery(sql);

            String sql2 = "SELECT y FROM yvalue";
            ResultSet rs2 = stmt2.executeQuery(sql2);
            //STEP 5: Extract data from result set
            while(rs.next()){


                String first = rs.getString("x");
                System.out.print("-> " + first);
                arrdbx.add(Integer.parseInt(first));



            }

            while(rs2.next()){


                String second = rs2.getString("y");
                System.out.print("->> " + second);
                arrdby.add(Integer.parseInt(second));


            }




            rs.close();
            rs2.close();

            System.out.print(arrdbx);
            System.out.print(arrdby);
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try

        launch(args);
    }
}
