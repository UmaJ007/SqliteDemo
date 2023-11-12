package com.example.sqlitedemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //references to buttons and other controls on the layout

    Button btn_add, btn_viewAll;
    EditText et_name,et_age;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch sw_activeCustomer;
    ListView lv_customerList;
    ArrayAdapter customerArrayAdapter;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        et_age = findViewById(R.id.et_age);
        et_name = findViewById(R.id.et_name);
        sw_activeCustomer = findViewById(R.id.sw_activeCustomer);
        lv_customerList = findViewById(R.id.lv_customerList);

        ShowCustomersOnListView(dataBaseHelper);

        //button listeners for the add and view all buttons
//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });

        btn_add.setOnClickListener(view -> {

            CustomerModel customerModel;

            try {
                customerModel = new CustomerModel(-1, et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()), sw_activeCustomer.isChecked());
//              Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(MainActivity.this, "Error creating customer", Toast.LENGTH_SHORT).show();
                customerModel = new CustomerModel(-1, "error", 0, false);
            }

            DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

            boolean success = dataBaseHelper.addOne(customerModel);

            Toast.makeText(MainActivity.this, "Success " + success, Toast.LENGTH_SHORT).show();
//            customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
//            lv_customerList.setAdapter(customerArrayAdapter);
            ShowCustomersOnListView(dataBaseHelper);

        });

        btn_viewAll.setOnClickListener(view -> {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
//        List<CustomerModel> everyone = dataBaseHelper.getEveryone();
//           customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.everyone);
//           lv_customerList.setAdapter(customerArrayAdapter);
//        Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_SHORT).show();
            ShowCustomersOnListView(dataBaseHelper);
                });

        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
          //public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                CustomerModel clickedCustomer = (CustomerModel) adapterView.getItemAtPosition(i);
                dataBaseHelper.deleteOne(clickedCustomer);
                ShowCustomersOnListView(dataBaseHelper);
                Toast.makeText(MainActivity.this, "Deleted " +clickedCustomer.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void ShowCustomersOnListView(DataBaseHelper dataBaseHelper) {
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, this.dataBaseHelper.getEveryone());
        lv_customerList.setAdapter(customerArrayAdapter);
    }
}

