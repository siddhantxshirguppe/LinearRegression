package pack;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Maincontroller  implements Initializable{

  public static int  flag=0;
    //----------
    public static double mean(float[] arr)
    {
        float n, sum = 0;
        float average;

        for(int i = 0; i < arr.length ; i++)
        {

            sum = sum + arr[i];
        }

        average = (float)sum / arr.length;
        return average;

    }

    public static double standarddeviation(float[] flt )
    {

        int n=flt.length;
        double numsigma=0;
        double den=n-1;
        float res=0;

        for (int i = 0; i < n; ++i) {

            numsigma = numsigma + (flt[i] - mean(flt)) * (flt[i] - mean(flt));

        }

        res =(float)Math.sqrt(numsigma/den);
        return res;



    }


    public static float[] convertIntToFloat(List<Integer> arr)
    {
        List<Integer> floatList = arr;
        float[] floatArray = new float[floatList.size()];
        int i = 0;

        for (Integer f : floatList) {
            floatArray[i++] = (f != null ? f : Float.NaN); // Or whatever default you want.
        }

        return floatArray;
    }


    public static double Correlation(float[] xs, float[] ys) {

        double numsigma = 0;
        double densigmax = 0;
        double densigmay = 0;
        double den= 0;

        int n = xs.length;

        for (int i = 0; i < n; ++i) {
            numsigma = numsigma + (xs[i] - mean(xs)) * (ys[i] - mean(ys));

            densigmax=densigmax+((xs[i]-mean(xs))*(xs[i]-mean(xs)));
            densigmay=densigmay+((ys[i]-mean(ys))*(ys[i]-mean(ys)));


        }
        System.out.print(mean(ys));
        System.out.print("x bar y bar"+numsigma+"\n"+"xsqr"+densigmax+"\n"+"ysqr"+densigmay+"\n");

        float corellation = (float)(numsigma/Math.sqrt(densigmax*densigmay));

        return corellation;

    }


    //----------



    @FXML  Label lab1;
    @FXML  Button loadchart,butt2;
    @FXML  Button butt1id,dbbutton,finaldbbutton;
    @FXML TextField tf;
    @FXML TextArea intmed,dbvalues;


    @FXML  LineChart<String,Number> linechart;



    @FXML
   public ListView<String> lstview;
    @FXML
    public ListView<String> lstview2;


   ObservableList<String> list = FXCollections.observableArrayList("0");
    ObservableList<String> list2 = FXCollections.observableArrayList("1");

int i=2;
double a,inputx;
float b;

    //--------

    //--------


    String s;

    public void getdbvalues()
    {
        String dbx= Main.arrdbx.toString();
        String dby= Main.arrdby.toString();
        dbvalues.setText("the y values are:\n"+dbx+"\n"+"the x values are "+dby+"\n");


    }


    public void swapdbvalues()
    {

flag=1;


    }


    public void predict()
    {

        s =tf.getText();

        int inputx = Integer.parseInt(s);
        System.out.print("enterx valur"+inputx);


        double outputy = a+b*inputx;
        System.out.print("the predicted value of y is :"+outputy);
        lab1.setText("the predicted value of y is :"+outputy);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XYChart.Series<String,Number> series = new XYChart.Series<String, Number>();
        loadchart.setOnAction((ae)->{
            linechart.getData().clear();

         //series.getData().add(new XYChart.Data<>("45",500));

            linechart.getData().add(series);

        });

        System.out.println("second");
        butt2.setOnAction((ae)-> {

if(flag==0)
{


    List<String> l1 = lstview.getItems();
    List<String> l2 = lstview2.getItems();

    List<Integer> intList = new ArrayList<Integer>();
    List<Integer> intList2 = new ArrayList<Integer>();
    for (String s : l1) intList.add(Integer.valueOf(s));
    for (String s : l2) intList2.add(Integer.valueOf(s));
    intList.remove(intList.size()-1);
    intList2.remove(intList2.size()-1);

    float[] arry =convertIntToFloat(intList);
    float[] arrx =convertIntToFloat(intList2);

    System.out.print(intList);
    System.out.print(intList2);

    double r = Correlation(arrx,arry);

    double Sx = standarddeviation(arrx);
    double Sy = standarddeviation(arry);
    b = (float)r*((float)Sy/(float)Sx);
    a = mean(arry)-b*mean(arrx);

    String mediate = ("Correlation:"+r+"\n")+("Standard Deviation of y:"+Sy+"\n")+("Standard Deviation of x:"+Sx+"\n")+(" b value"+b+"\n")+(" a value"+a+"\n");
    intmed.setText(mediate);

}

else
{

    float[] arry =convertIntToFloat(Main.arrdbx);
    float[] arrx =convertIntToFloat(Main.arrdby);

    System.out.print(Main.arrdbx);
    System.out.print(Main.arrdby);

    double r = Correlation(arrx,arry);

    double Sx = standarddeviation(arrx);
    double Sy = standarddeviation(arry);
    b = (float)r*((float)Sy/(float)Sx);
    a = mean(arry)-b*mean(arrx);

    String mediate = ("Correlation:"+r+"\n")+("Standard Deviation of y:"+Sy+"\n")+("Standard Deviation of x:"+Sx+"\n")+(" b value"+b+"\n")+(" a value"+a+"\n");
    intmed.setText(mediate);

}


















                });

        //--------------
        lstview2.setItems(list2);
        lstview2.setEditable(true);

        lstview2.setCellFactory(TextFieldListCell.forListView());
        lstview2.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
                                    @Override
                                    public void handle(ListView.EditEvent<String> t) {
                                        lstview2.getItems().set(t.getIndex(), t.getNewValue());
                                        System.out.println("Confirmed");
                                    }




                                }


        );

        lstview2.setOnEditCancel(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> t) {
                System.out.println("Canceled");
            }


        });



        //----------------
     lstview.setItems(list);
     lstview.setEditable(true);

        lstview.setCellFactory(TextFieldListCell.forListView());


        lstview.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> t) {
               lstview.getItems().set(t.getIndex(), t.getNewValue());
                System.out.println("confirmed");

                lstview2.getItems().add((i++)+"");

                series.getData().add(new XYChart.Data<String, Number>((i+""),Integer.parseInt(t.getNewValue())));
            }




                                }


        );

      lstview.setOnEditCancel(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> t) {
                System.out.println("Canceled");
            }


        });

      list.addListener(new ListChangeListener<String>() {
          @Override
          public void onChanged(Change<? extends String> c) {


              if(lstview.getSelectionModel().getSelectedIndices().contains(lstview.getItems().size()-1))
                  lstview.getItems().add("0");
              //--------------------

          }
      });






    }




}