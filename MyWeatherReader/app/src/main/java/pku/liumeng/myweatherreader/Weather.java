package pku.liumeng.myweatherreader;

/**
 * Created by liumeng on 15/3/23.
 */
public class Weather {
    private String weather_updatetime;
    private String weather_shidu;
    private String weather_PM;
    private String weather_quality;
    private String weather_data;
    private String weather_wendu;
    private String weather_description;
    private String weather_fengli;

    public Weather(String weather_updatetime, String weather_shidu, String weather_PM,
                   String weather_quality, String weather_data, String weather_wendu,
                   String weather_description, String weather_fengli)
    {
        this.weather_updatetime = weather_updatetime;
        this.weather_shidu = weather_shidu;
        this.weather_PM = weather_PM;
        this.weather_quality = weather_quality;
        this.weather_data = weather_data;
        this.weather_wendu = weather_wendu;
        this.weather_description = weather_description;
        this.weather_fengli = weather_fengli;
    }

//    weather_Updatetime
    public void SetWeatherUpdatetime(String weather_updatetime)
    {
        this.weather_updatetime = weather_updatetime;
    }

    public String GetWeatherUpdatetime()
    {
        return weather_updatetime;
    }

//    weather_Shidu
    public void SetWeatherShidu(String weather_shidu)
    {
        this.weather_shidu = weather_shidu;
    }

    public String GetWeatherShidu()
    {
        return weather_shidu;
    }

//    weather_PM
    public void SetWeatherPM(String weather_PM)
    {
        this.weather_PM = weather_PM;
    }

    public String GetWeatherPM()
    {
        return weather_PM;
    }

//    weather_quality
    public void SetWeatherQuality(String weather_quality)
    {
        this.weather_quality = weather_quality;
    }

    public String GetWeatherQuality()
    {
        return weather_quality;
    }

//    weather_data
    public void SetWeatherdata(String weather_data)
    {
        this.weather_data = weather_data;
    }

    public String GetWeatherdata()
    {
        return weather_data;
    }

//    weather_wendu
    public void SetWeatherWendu(String weather_wendu)
    {
        this.weather_wendu = weather_wendu;
    }

    public String GetWeatherWendu()
    {
        return weather_wendu;
    }

//    weather_description
    public void SetWeatherDescription(String weather_description)
    {
        this.weather_description = weather_description;
    }

    public String GetWeatherDescription()
    {
        return weather_description;
    }

//    weather_fengli
    public void SetWeatherFengli(String weather_fengli)
    {
        this.weather_fengli = weather_fengli;
    }

    public String GetWeatherFengli()
    {
        return weather_fengli;
    }
}
