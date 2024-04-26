//package com.example.vehicle_accident_app;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothSocket;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.StrictMode;
//import android.os.Vibrator;
//import android.provider.Settings;
//import android.speech.tts.TextToSpeech;
//import android.telephony.SmsManager;
//import android.util.Log;
//import android.view.View;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.UUID;
//
//public class control extends AppCompatActivity {
//    private BluetoothAdapter btAdapter = null;
//    Handler bluetoothIn;
//    int eye_count=0;
//    public static boolean callflag=false,smsflag=false,mflag=false;
//    public static String num="",msg="",sms_msg="",sms_num="";
//    String res="",to_mob="6363257215";
//    final public static int SEND_SMS = 101;
//    // SPP UUID service - this should work for most devices
//    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
//    // String for MAC address
//    private static String address="00:19:09:03:25:89";
//    private BluetoothSocket btSocket = null;
//    private ConnectedThread mConnectedThread;
//    Button b;
//    private OutputStream outStream = null;
//    final int handlerState = 0;
//    Vibrator vibrator;
//    String msg1="";
//    String info="";
//ImageView ivr,ivl,ivu,ivd,ivs;
//TextView tv,tv1;
//    Button btnu,btnd;
//String ins="";
//
//    public static int speed_count=0,accient_count=0,collision_count=0,fire_count=0,alcohol_count=0,drowsiness_count=0,pulse_count=0;
//    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
//    public static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
//    public static final String LOCATION_PREF = "locationPref";
//
//    Context context;
//    Activity activity;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_control);
//        ivr=(ImageView)findViewById(R.id.ivr);
//        ivl=(ImageView)findViewById(R.id.ivl);
//        ivu=(ImageView)findViewById(R.id.ivu);
//        ivd=(ImageView)findViewById(R.id.ivd);
//        ivs=(ImageView)findViewById(R.id.ivs);
//        tv=(TextView)findViewById(R.id.tv);
//        tv1=(TextView)findViewById(R.id.tv1);
//        btnu=(Button)findViewById(R.id.btnu);
//        btnd=(Button)findViewById(R.id.btnd);
//        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
//        checkBTState();
//
//        context = control.this;
//        activity = control.this;
//
//        checkLocationPermission(activity,context,LOCATION_PERMISSION,LOCATION_PREF);
//       // btnd.setEnabled(false);
//        btnu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Window Up",Toast.LENGTH_SHORT).show();
//
//                ins="u";
//             //   btnu.setEnabled(false);
//                try {
//                    sendData(ins);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        btnd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Window Down",Toast.LENGTH_SHORT).show();
//                ins="d";
//              //  btnd.setEnabled(false);
//               // btnu.setEnabled(true);
//                try {
//                    sendData(ins);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        ivr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"Right",Toast.LENGTH_LONG).show();
//                ins="r";
//                try {
//                    sendData(ins);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//        ivl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"Left",Toast.LENGTH_LONG).show();
//                ins="l";
//                try {
//                    sendData(ins);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        ivu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"Forward",Toast.LENGTH_LONG).show();
//                ins="f";
//                try {
//                    sendData(ins);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        ivd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"Backward",Toast.LENGTH_LONG).show();
//                ins="b";
//                try {
//                    sendData(ins);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        ivs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"Stop",Toast.LENGTH_LONG).show();
//                ins="s";
//                try {
//                    sendData(ins);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        final Handler handler = new Handler();
//        Timer timer = new Timer();
//        TimerTask doAsynchronousTask = new TimerTask()
//        {
//            @Override
//            public void run() {
//                handler.post(new Runnable() {
//                    public void run() {
//                        try
//                        {
//
//                           // new update_location().execute();
//
//
//                            String bm=tv.getText().toString();
//                      //      Toast.makeText(getApplicationContext(),"bm="+bm,Toast.LENGTH_SHORT).show();
//                            if (bm.startsWith("@") || bm.endsWith("%")) {
//
//                                bm = bm.substring(2, bm.length() - 2);
//
//                                String a[] = bm.split("#");
//                                if (Double.parseDouble(a[2]) > 9) {
//                                    tv1.setText("Accident Detected");
//                                    msg = "Accident Detected";
//                                    if(accient_count<2) {
//                                        checkAndroidVersion(to_mob);
//                                    }
//                                    accient_count++;
//
//                                }
//
//
//                                if (Double.parseDouble(a[5]) == 0) {
//                                    tv1.setText("Collision Detected");
//                                    msg = "Collision Detected";
//                                    if(collision_count<2) {
//                                        checkAndroidVersion(to_mob);
//                                    }
//                                    collision_count++;
//
//                                }
//
//
//
//
//                                //   new update_location().execute();
//                            }
//
//
//
//                        }
//                        catch (Exception e)
//                        {
//
//                        }
//                    }
//                });
//            }
//        };
//        timer.schedule(doAsynchronousTask, 0, 2000);
//
//
//
//
//        bluetoothIn = new Handler() {
//            public void handleMessage(android.os.Message msg1) {
//                if (msg1.what == handlerState) {
//                    //if message is what we want
//                    try{
//                    String readMessage = (String) msg1.obj;                                                                // msg.arg1 = bytes from connect thread
//
//                    Log.d("m" +
//                            "sg", readMessage);
//                        tv.setText(readMessage);
//                        if(readMessage.toString().toLowerCase().toString().contains("accident"))
//                        {
//                            msg="Accident Detected";
//                            checkAndroidVersion(to_mob);
//
//                        }
//
//
//                }catch(Exception ex)
//                {
//                    ex.printStackTrace();
//                }
//
//
//
//                }
//
//
//            }
//        };
//
//
//    }
//
//    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
//    private void checkBTState() {
//
//        if(btAdapter==null) {
//            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
//        } else {
//            if (btAdapter.isEnabled()) {
//            } else {
//                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBtIntent, 1);
//            }
//        }
//    }
//
//
//    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
//
//        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
//        //creates secure outgoing connecetion with BT device using UUID
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        //Get MAC address from DeviceListActivity via intent
//        Intent intent = getIntent();
//
//        //Get the MAC address from the DeviceListActivty via EXTRA
//        //   address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
//
//        //create device and set the MAC address
//        BluetoothDevice device = btAdapter.getRemoteDevice(address);
//
//        try {
//            btSocket = createBluetoothSocket(device);
//        } catch (IOException e) {
//            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
//        }
//        // Establish the Bluetooth socket connection.
//        try
//        {
//            btSocket.connect();
//        } catch (IOException e) {
//            try
//            {
//                btSocket.close();
//            } catch (IOException e2)
//            {
//                //insert code to deal with this
//            }
//        }
//        try {
//            outStream = btSocket.getOutputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        mConnectedThread = new ConnectedThread(btSocket);
//        mConnectedThread.start();
//
//        //I send a character when resuming.beginning transmission to check device is connected
//        //If it is not an exception will be thrown in the write method and finish() will be called
//        mConnectedThread.write("x");
//    }
//
//    @Override
//    public void onPause()
//    {
//        super.onPause();
//        try
//        {
//            //Don't leave Bluetooth sockets open when leaving activity
//            btSocket.close();
//        } catch (IOException e2) {
//            //insert code to deal with this
//        }
//    }
//
//    public void showToast(String message) {
//        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//    }
//
//    //create new class for connect thread
//    private class ConnectedThread extends Thread {
//        private final InputStream mmInStream;
//        private final OutputStream mmOutStream;
//
//        //creation of the connect thread
//        public ConnectedThread(BluetoothSocket socket) {
//            InputStream tmpIn = null;
//            OutputStream tmpOut = null;
//
//            try {
//                //Create I/O streams for connection
//                tmpIn = socket.getInputStream();
//                tmpOut = socket.getOutputStream();
//            } catch (IOException e) { }
//
//            mmInStream = tmpIn;
//            mmOutStream = tmpOut;
//        }
//
//
//        public void run() {
//            byte[] buffer = new byte[256];
//            int bytes;
//
//            // Keep looping to listen for received messages
//            while (true) {
//                try {
//                    bytes = mmInStream.read(buffer);        	//read bytes from input buffer
//                    String readMessage = new String(buffer, 0, bytes);
//                    // Send the obtained bytes to the UI Activity via handler
//                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
//                } catch (IOException e) {
//                    break;
//                }
//            }
//        }
//        //write method
//        public void write(String input) {
//            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
//            try {
//                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
//            } catch (IOException e) {
//                //if you cannot write, close the application
//                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
//                finish();
//
//            }
//        }
//    }
//    private void sendData(String message) {
//        byte[] msgBuffer = message.getBytes();
//
//
//
//        try {
//            outStream.write(msgBuffer);
//        } catch (IOException e) {
//
//            errorExit("Fatal Error", "Exception");
//        }
//    }
//    private void errorExit(String title, String message){
//        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
//        finish();
//    }
//
//
//
//    private void checkLocationPermission(Activity activity, final Context context, final String Permission, final String prefName) {
//
//        PermissionUtil.checkPermission(activity,context,Permission,prefName,
//                new PermissionUtil.PermissionAskListener() {
//                    @Override
//                    public void onPermissionAsk() {
//
//
//                        ActivityCompat.requestPermissions(control.this,
//                                new String[]{Permission},
//                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//
//                    }
//                    @Override
//                    public void onPermissionPreviouslyDenied() {
//                        //show a dialog explaining permission and then request permission
//
//                        showToast("Permission previously Denied.");
//
//                        ActivityCompat.requestPermissions(control.this,
//                                new String[]{Permission},
//                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//
//                    }
//                    @Override
//                    public void onPermissionDisabled() {
//
//                        askUserToAllowPermissionFromSetting();
//
//                    }
//                    @Override
//                    public void onPermissionGranted() {
//
//                        showToast("Permission Granted.");
//                        getGpsLocation();
//                    }
//                });
//    }
//
//    private void askUserToAllowPermissionFromSetting() {
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                context);
//
//        // set title
//        alertDialogBuilder.setTitle("Permission Required:");
//
//        // set dialog message
//        alertDialogBuilder
//                .setMessage("Kindly allow Permission from App Setting, without this permission app could not show maps.")
//                .setCancelable(false)
//                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        // if this button is clicked, close
//                        // current activity
//                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        Uri uri = Uri.fromParts("package", getPackageName(), null);
//                        intent.setData(uri);
//                        startActivityForResult(intent, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//                        showToast("Permission forever Disabled.");
//                    }
//                })
//                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        // if this button is clicked, just close
//                        // the dialog box and do nothing
//                        dialog.cancel();
//                    }
//                });
//
//        // create alert dialog
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        // show it
//        alertDialog.show();
//    }
//    class update_location extends AsyncTask<Void, Void, String> {
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            try {
//                URL url = new URL(Global.url + "update_location");
//                JSONObject jsn = new JSONObject();
//
//                jsn.put("lat",GPSTracker.latitude);
//                jsn.put("lon",GPSTracker.longitude);
//
//
//                String response = HttpConnection.getResponse(url,jsn);
//                res = response;
//            }
//
//            catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return res;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            Toast.makeText(control.this,s, Toast.LENGTH_SHORT).show();
//
//        }
//    }
//    private void getGpsLocation() {
//        // check if GPS enabled
//        GPSTracker gpsTracker = new GPSTracker(context);
//        if (gpsTracker.getIsGPSTrackingEnabled())
//        {
//            showToast("Gps Values are:"+GPSTracker.latitude+" , "+GPSTracker.longitude);
//        }
//        else
//        {
//            // can't get location
//            // GPS or Network is not enabled
//            // Ask user to enable GPS/network in settings
//            gpsTracker.showSettingsAlert();
//        }
//    }
//
//
//    public void checkAndroidVersion(String mobile){
//
//        if (Build.VERSION.SDK_INT >= 23) {
//            int checkCallPhonePermission = ContextCompat.checkSelfPermission(control.this, Manifest.permission.SEND_SMS);
//            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(control.this,new String[]{Manifest.permission.SEND_SMS},SEND_SMS);
//                return;
//            }else{
//                sendSms(mobile);
//            }
//        } else {
//            sendSms(mobile);
//        }
//    }
//    public void sendSms(String m)
//    {
//
//
//        try {
//
//            SmsManager sm = SmsManager.getDefault();
//            sm.sendTextMessage(m, null, msg, null, null);
//
//
//
//        } catch (Exception ex) {
//            Toast.makeText(control.this, "Your sms has failed...",
//                    Toast.LENGTH_LONG).show();
//            ex.printStackTrace();
//
//        }
//
//
//    }
//
//
//}





package com.example.vehicle_accident_app;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class control extends AppCompatActivity {
    private BluetoothAdapter btAdapter = null;
    Handler bluetoothIn;
    int eye_count = 0;
    public static boolean callflag = false, smsflag = false, mflag = false;
    public static String num = "", msg = "", sms_msg = "", sms_num = "";
    String res = "", to_mob = "6363257215";

    final public static int SEND_SMS = 101;
    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static String address = "00:19:09:03:25:89";
    private BluetoothSocket btSocket = null;
    private ConnectedThread mConnectedThread;
    Button b;
    private OutputStream outStream = null;
    final int handlerState = 0;
    Vibrator vibrator;
    String msg1 = "";
    String info = "";
    ImageView ivr, ivl, ivu, ivd, ivs;
    TextView tv, tv1;
    Button btnu, btnd;
    String ins = "";

    public static int speed_count = 0, accient_count = 0, collision_count = 0, fire_count = 0, alcohol_count = 0, drowsiness_count = 0, pulse_count = 0;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    public static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String LOCATION_PREF = "locationPref";

    Context context;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        ivr = findViewById(R.id.ivr);
        ivl = findViewById(R.id.ivl);
        ivu = findViewById(R.id.ivu);
        ivd = findViewById(R.id.ivd);
        ivs = findViewById(R.id.ivs);
        tv = findViewById(R.id.tv);
        tv1 = findViewById(R.id.tv1);
        btnu = findViewById(R.id.btnu);
        btnd = findViewById(R.id.btnd);
        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

        context = control.this;
        activity = control.this;

        checkLocationPermission(activity, context, LOCATION_PERMISSION, LOCATION_PREF);

        btnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Window Up", Toast.LENGTH_SHORT).show();
                ins = "u";
                try {
                    sendData(ins);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Window Down", Toast.LENGTH_SHORT).show();
                ins = "d";
                try {
                    sendData(ins);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ivr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Right", Toast.LENGTH_LONG).show();
                ins = "r";
                try {
                    sendData(ins);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ivl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Left", Toast.LENGTH_LONG).show();
                ins = "l";
                try {
                    sendData(ins);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ivu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Forward", Toast.LENGTH_LONG).show();
                ins = "f";
                try {
                    sendData(ins);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ivd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Backward", Toast.LENGTH_LONG).show();
                ins = "b";
                try {
                    sendData(ins);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ivs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Stop"
                        , Toast.LENGTH_LONG).show();
                ins = "s";
                try {
                    sendData(ins);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            String bm = tv.getText().toString();
                            if (bm.startsWith("@") || bm.endsWith("%")) {
                                bm = bm.substring(2, bm.length() - 2);
                                String a[] = bm.split("#");
                                if (Double.parseDouble(a[2]) > 9) {
                                    tv1.setText("Accident Detected");
                                    msg = "Accident Detected";
                                    if (accient_count < 2) {
                                        checkAndroidVersion(to_mob);
                                    }
                                    accient_count++;
                                }

                                if (Double.parseDouble(a[5]) == 0) {
                                    tv1.setText("Collision Detected");
                                    msg = "Collision Detected";
                                    if (collision_count < 2) {
                                        checkAndroidVersion(to_mob);
                                    }
                                    collision_count++;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 2000);

        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg1) {
                if (msg1.what == handlerState) {
                    try {
                        String readMessage = (String) msg1.obj;
                        Log.d("msg", readMessage);
                        tv.setText(readMessage);
                        if (readMessage.toLowerCase().contains("accident")) {
                            String locationLink = getLocationLink();
                            msg = "Accident Detected. Location: " + locationLink;
                            checkAndroidVersion(to_mob);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            private String getLocationLink() {
                String locationLink = "";
                try {
                    GPSTracker gpsTracker = new GPSTracker(context);
                    if (gpsTracker.getIsGPSTrackingEnabled()) {
                        double latitude = gpsTracker.getLatitude();
                        double longitude = gpsTracker.getLongitude();
                        // Constructing a simple Google Maps link
                        locationLink = "https://maps.google.com/?q=" + latitude + "," + longitude;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return locationLink;
            }



//            public void handleMessage(android.os.Message msg1) {
//                if (msg1.what == handlerState) {
//                    try {
//                        String readMessage = (String) msg1.obj;
//                        Log.d("msg", readMessage);
//                        tv.setText(readMessage);
//                        if (readMessage.toLowerCase().contains("accident")) {
//                            String locationLink = getLocationLink();
//                            msg = "Accident Detected. Location: " + locationLink;
//                            showAlertToSendMessage();
//                        }
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            }
//
//            private String getLocationLink() {
//                // Get the current GPS coordinates
//                GPSTracker gpsTracker = new GPSTracker(context);
//                double latitude = gpsTracker.getLatitude();
//                double longitude = gpsTracker.getLongitude();
//
//                // Create a Google Maps link with the coordinates
//                String locationLink = "http://maps.google.com/maps?q=" + latitude + "," + longitude;
//
//                return locationLink;
//            }
//
//            private void showAlertToSendMessage() {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setMessage("Accident detected. Sending message in 10 seconds. Cancel?")
//                        .setCancelable(false)
//                        .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                // Cancel sending the message
//                                dialog.dismiss();
//                            }
//                        });
//                final AlertDialog alert = builder.create();
//                alert.show();
//
//                // Set a handler to automatically send the message after 10 seconds
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (alert.isShowing()) {
//                            // If the alert is still showing after 10 seconds, send the message
//                            checkAndroidVersion(to_mob);
//                            alert.dismiss();
//                        }
//                    }
//                }, 10000); // 10 seconds delay
//            }


        };
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {
        if (btAdapter == null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (!btAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        BluetoothDevice device = btAdapter.getRemoteDevice(address);
        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                //insert code to deal with this
            }
        }
        try {
            outStream = btSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();
        mConnectedThread.write("x");
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        public void write(String input) {
            byte[] msgBuffer = input.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void sendData(String message) {
        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
        } catch (IOException e) {
            errorExit("Fatal Error", "Exception");
        }
    }

    private void errorExit(String title, String message) {
        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        finish();
    }

    private void checkLocationPermission(Activity activity, final Context context, final String Permission, final String prefName) {
        PermissionUtil.checkPermission(activity, context, Permission, prefName,
                new PermissionUtil.PermissionAskListener() {
                    @Override
                    public void onPermissionAsk() {
                        ActivityCompat.requestPermissions(control.this,
                                new String[]{Permission},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }

                    @Override
                    public void onPermissionPreviouslyDenied() {
                        showToast("Permission previously Denied.");
                        ActivityCompat.requestPermissions(control.this,
                                new String[]{Permission},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }

                    @Override
                    public void onPermissionDisabled() {
                        askUserToAllowPermissionFromSetting();
                    }

                    @Override
                    public void onPermissionGranted() {
                        showToast("Permission Granted.");
                        getGpsLocation();
                    }
                });
    }

    private void askUserToAllowPermissionFromSetting() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Permission Required:");
        alertDialogBuilder.setMessage("Kindly allow Permission from App Setting, without this permission app could not show maps.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                        showToast("Permission forever Disabled.");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    class update_location extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(Global.url + "update_location");
                JSONObject jsn = new JSONObject();
                jsn.put("lat", GPSTracker.latitude);
                jsn.put("lon", GPSTracker.longitude);
                String response = HttpConnection.getResponse(url, jsn);
                res = response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(control.this, s, Toast.LENGTH_SHORT).show();
        }
    }

    private void getGpsLocation() {
        GPSTracker gpsTracker = new GPSTracker(context);
        if (gpsTracker.getIsGPSTrackingEnabled()) {
            showToast("Gps Values are:" + GPSTracker.latitude + " , " + GPSTracker.longitude);
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    public void checkAndroidVersion(String mobile) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(control.this, Manifest.permission.SEND_SMS);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(control.this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS);
                return;
            } else {
                sendSms(mobile);
            }
        } else {
            sendSms(mobile);
        }
    }

    public void sendSms(String m) {
        try {
            GPSTracker gpsTracker = new GPSTracker(context);
            String locationMessage = "";
            if (gpsTracker.getIsGPSTrackingEnabled()) {
                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();
                locationMessage = "Latitude: " + latitude + ", Longitude: " + longitude + "\n";
            } else {
                locationMessage = "Location not available";
            }

            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(m, null, msg + "\n" + locationMessage, null, null);
        } catch (Exception ex) {
            Toast.makeText(control.this, "Your sms has failed...", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

}
