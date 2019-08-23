package com.example.dangg.led_control;

import android.app.Activity;
import android.os.Bundle;
import android.os.ILedService;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import android.os.ServiceManager;

public class MainActivity extends Activity {

    private boolean ledon = false;
    private Button button = null;
    private CheckBox checkBox1 = null;
    private CheckBox checkBox2 = null;
    private CheckBox checkBox3 = null;
    private CheckBox checkBox4 = null;
    private ILedService iLedService = null;
    public void button1_click(View view) {
        if(ledon)
        {
            button.setText("全开");
            checkBox1.setChecked(false);
            checkBox2.setChecked(false);
            checkBox3.setChecked(false);
            checkBox4.setChecked(false);

            try {
                for (int i = 0;i<4;i++)
                {
                    iLedService.ledCtrl(i,0);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else
        {
            button.setText("全关");
            checkBox1.setChecked(true);
            checkBox2.setChecked(true);
            checkBox3.setChecked(true);
            checkBox4.setChecked(true);
            try {
                for (int i = 0;i<4;i++)
                {
                    iLedService.ledCtrl(i,1);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        ledon = !ledon;
    }


    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox)view).isChecked();
        String outmsg = "";
        int ledFlag = -1;
        if(checked)
        {
            outmsg = "on";
            ledFlag = 1;
        }
        else
        {
            outmsg = "off";
            ledFlag = 0;
        }

        try {
            switch (view.getId())
            {
                case R.id.LED1:
                    Toast.makeText(getApplicationContext(), "LED1"+outmsg, Toast.LENGTH_SHORT).show();
                    iLedService.ledCtrl(0, ledFlag);
                    break;
                case R.id.LED2:
                    Toast.makeText(getApplicationContext(), "LED2"+outmsg, Toast.LENGTH_SHORT).show();
                    iLedService.ledCtrl(1, ledFlag);
                    break;
                case R.id.LED3:
                    Toast.makeText(getApplicationContext(), "LED3"+outmsg, Toast.LENGTH_SHORT).show();
                    iLedService.ledCtrl(2, ledFlag);
                    break;
                case R.id.LED4:
                    Toast.makeText(getApplicationContext(), "LED4"+outmsg, Toast.LENGTH_SHORT).show();
                    iLedService.ledCtrl(3, ledFlag);
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iLedService = ILedService.Stub.asInterface(ServiceManager.getService("led"));
        button = (Button)findViewById(R.id.button);

        checkBox1 = (CheckBox)findViewById(R.id.LED1);
        checkBox2 = (CheckBox)findViewById(R.id.LED2);
        checkBox3 = (CheckBox)findViewById(R.id.LED3);
        checkBox4 = (CheckBox)findViewById(R.id.LED4);
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
}
