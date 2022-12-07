package com.example.rcoem_weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Report extends AppCompatActivity {


    private static final String LOG_TAG = "check" ;
    String[] names;
    ListView lvMain;
    DBHandler dbHandler;
    String ddate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        dbHandler = new DBHandler(Report.this);
        Button mButton=(Button)findViewById( R.id.button2);
        mButton.setEnabled(false);
        CheckBox mCheckBox= ( CheckBox ) findViewById( R.id.checkbox);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    mButton.setEnabled(true);

                }else{
                    mButton.setEnabled(false);
                }

            }
        });



        lvMain = (ListView) findViewById(R.id.lvMain);

        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.names,
                android.R.layout.simple_list_item_multiple_choice);
        lvMain.setAdapter(adapter2);



        names = getResources().getStringArray(R.array.names);

        String[] check = new String[]{"Politics","Arts","Sports"};
        names = check;


        final DatePickerDialog[] picker = new DatePickerDialog[1];
        EditText eText;
        Button btnGet;
        TextView tvw;




        eText=(EditText) findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                picker[0] = new DatePickerDialog(Report.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                String dd =  eText.getText().toString();
                                ddate = dd;
                                String ssplit[] = dd.split("/");
                                int day = Integer.parseInt(ssplit[2]);
                                int month = Integer.parseInt(ssplit[1]);
                                year = Integer.parseInt(ssplit[0]);

                                Calendar dob = new GregorianCalendar(day, month, year);
                                Calendar currentDate = new GregorianCalendar();
                                int age = currentDate.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

                                if(age<0) age=0;

                                String s = ("Age = "+age);


                            }
                        }, year, month, day);
                picker[0].show();
            }
        });
    }

    public void onClick(View view){




        String value = "Hello World";

        EditText t1 = findViewById(R.id.entrusr);
        String name = t1.getText().toString();
        //t1= findViewById(R.id.entrmail);
        String email = t1.getText().toString();
        t1 = findViewById(R.id.entrmob);
        String number = t1.getText().toString();
        t1 = findViewById(R.id.editText1);
        String dob = t1.getText().toString();


        RadioGroup rg = findViewById(R.id.radiobut);
        int id_butt = rg.getCheckedRadioButtonId();
        RadioButton rb = findViewById(id_butt);
        String gender = rb.getText().toString();


        ListView lv = findViewById(R.id.lvMain);
        long[] pos = lv.getCheckedItemIds();

        ArrayList<String> arr = new ArrayList<>();

        SparseBooleanArray sbArray = lvMain.getCheckedItemPositions();
        for (int i = 0; i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key))
                arr.add(names[key]);
        }





        String c="";
        for(String s:arr){
            c=c+"|"+s;
        }



        dbHandler.addNewCourse(name, ddate, gender, c);


        Toast.makeText(this, "SUCCESSFULLY REPORTED",
                Toast.LENGTH_SHORT).show();

        //startActivity(i)
    }




}