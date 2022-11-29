package com.example.mk30nov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView countSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list); //переменная для связи с ListView
        countSensors = findViewById(R.id.countSensors); //переменная для связи вывода количества датчиков на экран

        List<String> sensors = new ArrayList<>(); //список сенсоров для адаптера


        //получим список всех сенсоров
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        //добавим названия и тип сенсоров в список в виде строки в список "sensors"
        for (Sensor sensor: deviceSensors) {
            sensors.add(sensor.getName() + " тип №" + sensor.getType());
        }

        //создадим новый адаптер для listView
        // третий параметр - это запрос к базе данных на получение списка сенсоров
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, sensors);

        //покажем список с помощью listView
        listView.setAdapter(adapter);

        //по нажатию на элемент списка откроем активити для демонстрации работы выбранного датчика
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, SensorActivity.class); // создаем новый интент
                intent.putExtra("typeSensor", deviceSensors.get(i).getType() + ""); // передаем в новое окно информацию о типе датчика
                startActivity(intent); // запускаем новое окно
            }
        });


    }
}