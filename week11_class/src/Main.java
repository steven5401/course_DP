import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

interface Display {
    public void update();
    public String getName();
}

class CurrentConditions implements Display {
    public WeatherObject weatherObject;
    public String name = "CurrentConditions";
    public String getName() {
        return name;
    }

    public CurrentConditions(WeatherObject w) {
        weatherObject = w;
    }

    public void update() {
        System.out.format("Temperature %.1f\n", weatherObject.temperatureList.get(weatherObject.temperatureList.size() - 1));
        System.out.format("Humidity %.1f\n", weatherObject.humidityList.get(weatherObject.humidityList.size() - 1));
        System.out.format("Pressure %.1f\n", weatherObject.pressureList.get(weatherObject.pressureList.size() - 1));
    }
}

class WeatherStatistics implements Display {
    public WeatherObject weatherObject;
    public String name = "WeatherStatistics";
    public String getName() {
        return name;
    }

    public WeatherStatistics(WeatherObject w) {
        weatherObject = w;
    }

    public void update() {
        System.out.print("Temperature");
        for(double data: weatherObject.temperatureList) {
            System.out.format(" %.1f", data);
        }
        System.out.println("");
        System.out.print("Humidity");
        for(double data: weatherObject.humidityList) {
            System.out.format(" %.1f", data);
        }
        System.out.println("");
        System.out.print("Pressure");
        for(double data: weatherObject.pressureList) {
            System.out.format(" %.1f", data);
        }
        System.out.println("");
    }
}

class SimpleForecast implements Display {
    public WeatherObject weatherObject;
    public String name = "SimpleForecast";
    public String getName() {
        return name;
    }

    public SimpleForecast(WeatherObject w) {
        weatherObject = w;
    }

    public void update() {
        if(weatherObject.humidityList.get(weatherObject.humidityList.size() - 1) > 0.8) {
            System.out.println("Forecast rain.");
        }
        else if(weatherObject.humidityList.get(weatherObject.humidityList.size() - 1) < 0.2) {
            System.out.println("Forecast sunny.");
        }
        else {
            System.out.println("Forecast cloudy.");
        }
    }
}

class Developer {
    WeatherObject asia = new WeatherObject();
    WeatherObject us = new WeatherObject();
    void addData(String area, double temperature, double humidity, double pressure) {
        switch (area) {
            case "Asia":
                asia.addTemperature(temperature);
                asia.addHumidity(humidity);
                asia.addPressure(pressure);
                asia.notify2();
                break;
            case "US":
                us.addTemperature(temperature);
                us.addHumidity(humidity);
                us.addPressure(pressure);
                us.notify2();
                break;
        }
    }
    void attach(String area, String  displayType) {
        Display display = null;

        switch (area) {
            case "Asia":
                switch (displayType) {
                    case "Current":
                        display = new CurrentConditions(asia);
                        asia.attach(display);
                        break;
                    case "Statistics":
                        display = new WeatherStatistics(asia);
                        asia.attach(display);
                        break;
                    case "Forecast":
                        display = new SimpleForecast(asia);
                        asia.attach(display);
                        break;
                }
                break;
            case "US":
                switch (displayType) {
                    case "Current":
                        display = new CurrentConditions(us);
                        us.attach(display);
                        break;
                    case "Statistics":
                        display = new WeatherStatistics(us);
                        us.attach(display);
                        break;
                    case "Forecast":
                        display = new SimpleForecast(us);
                        us.attach(display);
                        break;
                }
                break;
        }
    }
    void detach(String area, String  displayType) {
        Display display = null;
        switch (area) {
            case "Asia":
                switch (displayType) {
                    case "Current":
                        display = new CurrentConditions(asia);
                        asia.detach(display);
                        break;
                    case "Statistics":
                        display = new WeatherStatistics(asia);
                        asia.detach(display);
                        break;
                    case "Forecast":
                        display = new SimpleForecast(asia);
                        asia.detach(display);
                        break;
                }
                break;
            case "US":
                switch (displayType) {
                    case "Current":
                        display = new CurrentConditions(us);
                        us.detach(display);
                        break;
                    case "Statistics":
                        display = new WeatherStatistics(us);
                        us.detach(display);
                        break;
                    case "Forecast":
                        display = new SimpleForecast(us);
                        us.detach(display);
                        break;
                }
                break;
        }
    }
}

public class Main {
	public static void main(String args[]) {
        Developer developer = new Developer();
		try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String currentLine;
            while((currentLine = reader.readLine()) != null) {
            	String[] s = currentLine.split("\\s+");
                switch(s[0]) {
              		case "data":
                  		developer.addData(s[1], Double.parseDouble(s[2]), Double.parseDouble(s[3]), Double.parseDouble(s[4]));
                  		break;
                  	case "attach":
                        developer.attach(s[1], s[2]);
                  		break;
                    case "detach":
                        developer.detach(s[1], s[2]);
                  		break;
                }
            }
        } catch(IOException e) {
		    e.printStackTrace();
		}
    }
}

class WeatherObject {
    String Area;
    ArrayList<Double> temperatureList;
    ArrayList<Double> humidityList;
    ArrayList<Double> pressureList;
    ArrayList<Display> displayList;
    
    public WeatherObject(){
        this.Area = "not initialized";
        this.temperatureList = new ArrayList<Double>();
        this.humidityList = new ArrayList<Double>();
        this.pressureList = new ArrayList<Double>();
        this.displayList = new ArrayList<Display>();
    }
    
    void addTemperature(double temp){
        this.temperatureList.add(temp);
        return;
    }
    
    void addHumidity(double humi){
        this.humidityList.add(humi);
        return;
    }
    
    void setArea(String area){
        this.Area = area;
        return;
    }
    
    void addPressure(double pres){
        this.pressureList.add(pres);
        return;
    }
    
    void attach(Display dis){
        this.displayList.add(dis);
        return;
    }
    
    void detach(Display dis){
        int i=0;
        boolean flag = false;
        for(; i<this.displayList.size(); i++){
            if(this.displayList.get(i).getName().equals(dis.getName())) {
                flag = true;
                break;
            }
        }
        if (flag) this.displayList.remove(i);
        return;
    }
    
    void notify2(){
        for(int i=0; i<this.displayList.size(); i++){
            this.displayList.get(i).update();
        }
        return;
    }

}