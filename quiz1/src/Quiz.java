import java.io.*;
import java.util.*;

interface ISensor {
    boolean isSave(int reportIdx);
}

abstract class BasicSensor implements ISensor {
    String name;
    String type;
    float[] valueArray;
    int lineNumber;
    float upperBound;
    float lowerBound;
    public BasicSensor (int lineNumber, String name, String datasetName, float upperBound, float lowerBound) {
        this.name = name;
        this.lineNumber = lineNumber;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(datasetName);
            LineNumberReader lnr = new LineNumberReader(fr);
            lnr.skip(Long.MAX_VALUE);
            this.valueArray = new float[lnr.getLineNumber() + 1];
            lnr.close();
            fr = new FileReader(datasetName);
            br = new BufferedReader(fr);
            String currentLine;
            int idx = 0;
            while ((currentLine = br.readLine()) != null) {
                try {
                    this.valueArray[idx] = Float.parseFloat(currentLine);
                }
                catch (NumberFormatException ex) {
                    System.out.println(datasetName + "contains non int type");
                    System.exit(1);
                }
                idx++;
            }
        } catch (IOException ex) {
            System.out.println(datasetName);
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
    public boolean isSave(int reportIdx) {
        if (reportIdx >= this.valueArray.length) {
            return false;
        } else if (this.valueArray[reportIdx] > this.upperBound) {
            return false;
        } else if (this.valueArray[reportIdx] < this.lowerBound) {
            return false;
        } else {
            return true;
        }
    }
}

class PulseSensor extends BasicSensor {
    public PulseSensor(int lineNumber, String name, String datasetName, float upperBound, float lowerBound) {
        super(lineNumber, name, datasetName, upperBound, lowerBound);
        this.type = "PulseSensor";
    }
}

class BloodPressureSensor extends BasicSensor {
    public BloodPressureSensor(int lineNumber, String name, String datasetName, float upperBound, float lowerBound) {
        super(lineNumber, name, datasetName, upperBound, lowerBound);
        this.type = "BloodPressureSensor";
    }
}

class TemperatureSensor extends BasicSensor {
    public TemperatureSensor(int lineNumber, String name, String datasetName, float upperBound, float lowerBound) {
        super(lineNumber, name, datasetName, upperBound, lowerBound);
        this.type = "TemperatureSensor";
    }
}

class ErrorInfo implements Comparable<ErrorInfo> {
    int time;
    float sensorValue;
    int lineNumber;
    String patientName;
    String sensorName;
    public ErrorInfo(int time, float sensorValue, int lineNumber, String sensorName, String patientName) {
        this.time = time;
        this.sensorValue = sensorValue;
        this.lineNumber = lineNumber;
        this.patientName = patientName;
        this.sensorName = sensorName;
    }
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof ErrorInfo)) return false;
        ErrorInfo e = (ErrorInfo) other;
        return time == e.time && sensorValue == e.sensorValue && lineNumber == e.lineNumber && 
               patientName.equals(e.patientName) && sensorName.equals(e.sensorName);
    }
    @Override
    public int compareTo(ErrorInfo e) {
        if (time < e.time) {
            return 1;
        } else if (time > e.time) {
            return -1;
        } else {
            if (lineNumber > e.lineNumber) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}

class Patient {
    String name;
    int patientPeriod;
    int reportIdx;
    Vector<BasicSensor> sensorList;
    public Patient(String name, int patientPeriod) {
        this.name = name;
        this.patientPeriod = patientPeriod;
        this.reportIdx = 0;
        this.sensorList = new Vector<>();
    }
    public void ReportDanger(int time, Set<ErrorInfo> errorList) {
        if (time % this.patientPeriod != 0) {//not yet to report
            return;
        } else {
            for (BasicSensor s : this.sensorList) {
                if (!s.isSave(reportIdx)) {//danger
                    if (reportIdx >= s.valueArray.length) {//fail
                        errorList.add(new ErrorInfo(time, -1, s.lineNumber, s.name, this.name));
                    } else {//exceed bound
                        errorList.add(new ErrorInfo(time, s.valueArray[reportIdx], s.lineNumber, s.name, this.name));
                    }
                }
            }
            reportIdx++;
        }
    }
}

public class Quiz {
    public static void main(String[] args) {        
        BufferedReader br = null;
        FileReader fr = null;
        String inputFileName = args[0];
        int monitorPeriod = 0;
        Patient patient = null;
        Vector<Patient> patientList = new Vector<>();
        try {
            fr = new FileReader(inputFileName);
            br = new BufferedReader(fr);
            String currentLine;
            int lineNo = 1;
            while ((currentLine = br.readLine()) != null) {
                String[] lineArray = currentLine.split(" ");
                try {
                    monitorPeriod = Integer.parseInt(lineArray[0]);
                } catch (NumberFormatException ex) {
                    if (lineArray[0].equals("patient")) {//new patient
                        if (patient != null) {
                            patientList.add(patient);
                        }
                        try {
                            patient = new Patient(lineArray[1], Integer.parseInt(lineArray[2]));
                        } catch (NumberFormatException e) {
                            System.out.println("patientPeriod not int");
                            System.exit(1);
                        }
                    } else {//sensor
                        try {
                            float lowerBound = Float.parseFloat(lineArray[3]);
                            float upperBound = Float.parseFloat(lineArray[4]);
                            if (lineArray[0].equals("BloodPressureSensor")) {
                                patient.sensorList.add(new BloodPressureSensor(lineNo, lineArray[1], lineArray[2], upperBound, lowerBound));
                            } else if (lineArray[0].equals("PulseSensor")) {
                                patient.sensorList.add(new PulseSensor(lineNo, lineArray[1], lineArray[2], upperBound, lowerBound));
                            } else if (lineArray[0].equals("TemperatureSensor")) {
                                patient.sensorList.add(new TemperatureSensor(lineNo, lineArray[1], lineArray[2], upperBound, lowerBound));
                            } else {
                                System.out.println("not support sensor type");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("lowerBound | upperBound not int");
                            System.exit(1);
                        }
                    }
                    continue;
                }
            }
            if (patient != null) {
                patientList.add(patient);
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
        for (int time = 0; time <= monitorPeriod; time++) {//start monitoring
            Set<ErrorInfo> errorList = new TreeSet<>();
            for (Patient p : patientList) {
                p.ReportDanger(time, errorList);
            }
            if (errorList.size() != 0) {
                for (ErrorInfo e : errorList) {
                    if (e.sensorValue == -1) {
                        System.out.println("[" + time + "] " + e.sensorName + " falls");
                    } else {
                        System.out.println("[" + time + "] " + e.patientName + " is in danger! Cause: " + e.sensorName + " " + e.sensorValue);
                    }
                }
            }
        }
        for (Patient p : patientList) {
            if (p != null) {
                System.out.println("patient " + p.name);
                for (BasicSensor s : p.sensorList) {
                    if (s != null) {
                        System.out.println(s.type + " " + s.name);
                        for (int time = 0; time <= monitorPeriod; time += p.patientPeriod) {
                            int reportIdx = time / p.patientPeriod;
                            if (reportIdx < s.valueArray.length) {//get value
                                System.out.println("[" + time + "] " + s.valueArray[reportIdx]);
                            } else {//fall
                                System.out.println("[" + time + "] -1.0");
                            }
                        }
                    }
                }
            }
        }
    }
}