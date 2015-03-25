package pku.liumeng.myweatherreader;

import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.TimeZone;


public class MainActivity extends ActionBarActivity{
    private String response_str;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case RESPONSE:
                    response_str = (String)msg.obj;
                    parseXML(response_str);
            }
        }
    };

    public static final int RESPONSE = 1;
    private ImageButton get_button;

//    weather data
    String weather_updatetime;
    String weather_shidu;
    String weather_PM;
    String weather_PMinfo;
    String weather_time;
    String weather_wendu;
    String weather_description;
    String weather_fengli;

    private TextView weather_updatetime_t;
    private TextView weather_shidu_t;
    private TextView weather_wendu_t;
    private TextView weather_PM_t;
    private TextView weather_PMinfo_t;
    private TextView weather_des_t;
    private TextView weather_fengli_t;
    private TextView weather_data_t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_toolbar);

        weather_updatetime_t = (TextView)findViewById(R.id.calender);
        weather_shidu_t = (TextView)findViewById(R.id.temperature);
        weather_PM_t = (TextView)findViewById(R.id.NA);
        weather_PMinfo_t = (TextView)findViewById(R.id.NA2);
        weather_wendu_t = (TextView)findViewById(R.id.temperature2);
        weather_des_t = (TextView)findViewById(R.id.temperature3);
        weather_fengli_t = (TextView)findViewById(R.id.temperature4);
        weather_data_t = (TextView)findViewById(R.id.data);



        get_button = (ImageButton)findViewById(R.id.title_update_btn);
        get_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MyActivity.this,"Hi",Toast.LENGTH_SHORT).show();;
//                Intent intent = new Intent(MyActivity.this, AnotherActivity.class);
//                MyActivity.this.startActivity(intent);

                sendRequest();
                weather_time = StringData();
                weather_updatetime = "今天"+weather_updatetime+"发布";
                weather_shidu = "湿度"+weather_shidu;
                weather_wendu = weather_wendu + "°C";
                weather_updatetime_t.setText(weather_updatetime);
                weather_wendu_t.setText(weather_wendu);
                weather_fengli_t.setText(weather_fengli);
                weather_des_t.setText(weather_description);
                weather_PM_t.setText(weather_PM);
                weather_PMinfo_t.setText(weather_PMinfo);
                weather_shidu_t.setText(weather_shidu);
                weather_data_t.setText(weather_time);
            }
        });
    }

    public String StringData(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        return mMonth + "月" + mDay+"日"+" 星期"+mWay;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection con = null;
                try{
                    URL url = new URL("http://wthrcdn.etouch.cn/WeatherApi?citykey=101010100");
                    con = (HttpURLConnection)url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while ((str=reader.readLine())!= null){
                        response.append(str);
                    }
                    Message msg = new Message();
                    msg.what = RESPONSE;
                    msg.obj = response.toString();
                    handler.sendMessage(msg);
                }
                catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(con!=null){
                        con.disconnect();
                    }
                }
            }
        }).start();
    }

    private void parseXML(String weatherdata){
        try{
            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = fac.newPullParser();
            xmlPullParser.setInput(new StringReader(weatherdata));
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (xmlPullParser.getName().equals("updatetime")){
                            eventType = xmlPullParser.next();
                            weather_updatetime = xmlPullParser.getText();
                        }
                        else if (xmlPullParser.getName().equals("shidu")){
                            eventType = xmlPullParser.next();
                            weather_shidu = xmlPullParser.getText();
                        }
                        else if (xmlPullParser.getName().equals("pm25")){
                            eventType = xmlPullParser.next();
                            weather_PM = xmlPullParser.getText();
                        }
                        else if (xmlPullParser.getName().equals("quality")){
                            eventType = xmlPullParser.next();
                            weather_PMinfo = xmlPullParser.getText();
                        }
                        else if (xmlPullParser.getName().equals("wendu")){
                            eventType = xmlPullParser.next();
                            weather_wendu = xmlPullParser.getText();
                        }
                        else if (xmlPullParser.getName().equals("fengli")){
                            eventType = xmlPullParser.next();
                            weather_fengli = xmlPullParser.getText();
                        }
//                        不知道怎么获取天气的描述
                        else if (xmlPullParser.getName().equals("type")){
                            eventType = xmlPullParser.next();
                            weather_description = xmlPullParser.getText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
