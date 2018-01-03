package com.husen.sqlite.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.husen.sqlite.R;
import com.husen.sqlite.helper.DatabaseHelper;
import com.husen.sqlite.helper.Session;
import com.husen.sqlite.model.Employee;
import com.husen.sqlite.model.User;

import static com.husen.sqlite.helper.Session.NAME;

public class AddEmployeeActivity extends AppCompatActivity {

    private TextView txtUserName;
    private Button btnAddEmployee, btnLogout;
    private Context context;
    private Employee employee;
    private DatabaseHelper databaseHelper;
    private User user;
    private Gson gson;
    private Session session;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        context = this;
        session = new Session(context);
        sharedPreferences = getSharedPreferences("sqlite",context.MODE_PRIVATE);
        gson = new GsonBuilder().setLenient().create();
        databaseHelper = new DatabaseHelper(context);
        txtUserName = (TextView) findViewById(R.id.txtUserName);
        btnAddEmployee = (Button) findViewById(R.id.btnAddEmployee);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        user = new User();

        txtUserName.setText(sharedPreferences.getString(NAME,""));


        actionListener();
    }

    private void actionListener() {
        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLoggedin(false);
                finish();
                startActivity(new Intent(context,MainActivity.class));

            }
        });
    }

    private void createDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fab_dialog);

        //---- SET DIALOG SIZE
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);

        final EditText edtName,  edtMobile;
        Button btnSave;

        edtName = (EditText) dialog.findViewById(R.id.edtName);
        edtMobile = (EditText) dialog.findViewById(R.id.edtMobile);
        btnSave = (Button) dialog.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName, strMobile;
                strName = edtName.getText().toString().trim();
                strMobile = edtMobile.getText().toString().trim();

                if (TextUtils.isEmpty(strName) || TextUtils.isEmpty(strMobile) )  {
                    Toast.makeText(context," PLEASE INSERT DATA PROPERLY", Toast.LENGTH_SHORT).show();
                    return;
                }

                employee = new Employee();
                employee.setEmployeeName(strName);
                employee.setEmployeePhone(strMobile);
                databaseHelper.addEmployee(employee);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
