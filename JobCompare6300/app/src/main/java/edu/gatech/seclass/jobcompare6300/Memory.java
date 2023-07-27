package edu.gatech.seclass.jobcompare6300;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;

class Memory {
    private static final String FILE_NAME = "output.txt";
    private Activity context;
    Memory(){}
    public Memory(Activity context){
        this.context = context;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Request for permission
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }
    // For Job
    public int id = 0;
    public int title = 1;
    public int company = 2;
    public int city = 3;
    public int state = 4;

    public int costOfLiving = 5;
    public int yearlySalary = 6;
    public int yearlyBonus = 7;
    public int leave = 8;
    public int parentalLeave = 9;
    public int lifeInsurance = 10;
    public int trueIfCurrent = 11;

    // For Comparison (fixed index error)
    public int salaryWeight = 1;
    public int bonusWeight = 2;
    public int leaveWeight = 3;
    public int parentalLeaveWeight = 4;
    public int lifeInsuranceWeight = 5;
    private String delimiter = "<%break%>";  // Don't know why delimiting doesn't play well with \n
    private String comparisonId = "<%Comparison ID%>";
    private Boolean trueToIncludeComparisonId = false;
    public void clearJobData(){
        setTxt("");
    }

    // --------------------------------------
    // Comparison Settings
    // --------------------------------------

    public void setComparisonWeights(int salaryWeight, int bonusWeight,
                                     int leaveWeight, int parentalLeaveWeight,
                                     int lifeInsuranceWeight){
        String presentWhole = "";
        String addition =
                String.valueOf(comparisonId) + "," + String.valueOf(salaryWeight) + "," +
                        String.valueOf(bonusWeight) + "," + String.valueOf(leaveWeight) + "," +
                        String.valueOf(parentalLeaveWeight) + "," + String.valueOf(lifeInsuranceWeight);
        // Remove previous entry
        trueToIncludeComparisonId = true;
        if(getJobDataMap().containsKey(comparisonId)){
            removeJobDataById(comparisonId);
        }
        trueToIncludeComparisonId = false;
        // Define presentWhole
        presentWhole = getTxt();
        if(!presentWhole.isEmpty()){presentWhole = presentWhole + delimiter;}
        setTxt(presentWhole + addition);
    }

    public int getComparisonWeight(int type){
        String[] array = new String[0];
        String[] split = getTxt().split(delimiter);
        for (String i : split){
            if (i.equals("")) {
                // Skip blank entries
            }else if (i.startsWith(comparisonId)){
                array = i.split(",");
            }else{
                // Skip all other entries
            }
        }
        return Integer.parseInt(array[type]);
    }

    // --------------------------------------
    // Job
    // --------------------------------------

    public void setJobData(String title, String company, String city,
                           String state, String costOfLiving, String yearlySalary,
                           String yearlyBonus, String leave, String parentalLeave,
                           String lifeInsurance, String trueIfCurrent){
        String presentWhole = "";
        String addition;
        //Define id
        String id = title + " " + company;
        // Define addition
        addition = id + "," + title + "," + company + "," + city + ","
                + state + "," + costOfLiving + "," + yearlySalary + "," + yearlyBonus + ","
                + leave + "," + parentalLeave + "," + lifeInsurance + "," + trueIfCurrent;
        // Remove previous entry
        if(getJobDataMap().containsKey(id)){
            removeJobDataById(id);}
        // Define presentWhole
        presentWhole = getTxt();
        if(!presentWhole.isEmpty()){presentWhole = presentWhole + delimiter;}
        setTxt(presentWhole + addition);
    }

    public Map<String, String[]> getJobDataMap(){
        Map<String, String[]> output;
        output = new Hashtable<>();  // Not sure why this needs to be a hashtable. Dictionary demands defining abstract methods.
        String[] split = getTxt().split(delimiter);
        for (String i : split){
            if (i.equals("")) {
                // Skip blank entries
            }else if (i.startsWith(comparisonId) && !trueToIncludeComparisonId){
                // Skip comparison entry
            }else{
                output.put(i.split(",")[0], i.split(","));
            }
        }
        return output;
    }

    public void removeJobDataById(String id){
        String presentWhole = getTxt();
        String[] split = presentWhole.split(delimiter);
        String futureWhole = "";
        for (String line : split){
            if (line.startsWith(id)){
                // Skip
            }else{
                if(!futureWhole.isEmpty()){futureWhole = futureWhole + delimiter;}
                futureWhole = futureWhole + line;
            }
        }
        setTxt(futureWhole);
    }

    // --------------------------------------
    // Set/Get
    // --------------------------------------

    private void setTxt(String text) {
        FileOutputStream fileOutputStream = null;
        File directory = new File("//sdcard//Download//");
        File file = new File(directory, FILE_NAME);
        //text = text.replace("\n", "");
        try {
            fileOutputStream = new FileOutputStream(file.getAbsolutePath());
            fileOutputStream.write(text.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getTxt()  {
        FileInputStream fileInputStream = null;
        File directory = new File("//sdcard//Download//");
        File file = new File(directory, FILE_NAME);
        try {
            fileInputStream = new FileInputStream(file.getAbsolutePath());
            InputStreamReader isr = new InputStreamReader(fileInputStream);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append(""); // sb.append(text).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public boolean isComparisonWeightPresent() {
        return getTxt().contains(comparisonId);
    }
}
