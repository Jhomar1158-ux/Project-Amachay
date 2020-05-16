package com.example.amachay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

public class numero_emergencia extends AppCompatActivity {

    private Intent intent;
    private static final int SOLICITUD_PERMISO_CALL_PHONE=1;
    private Button btnCovid,btnSAMU,btnPNP,btnEsSalud,btnBomberos;
    private HashMap<Button,String> buttonsWithNumber;
    private Dialog miDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numero_emergencia);

        //-------------------------------previous activity-------------------------
        intent= new Intent(this,menu_principal.class);
        //-------------------------------------------------------------------------

        buttonsWithNumber=new HashMap<Button,String>(5);
        JoinNumbers();

        miDialog=new Dialog(this);
    }

    private void JoinNumbers(){
        btnCovid=(Button)findViewById(R.id.buttonCovid);
        buttonsWithNumber.put(btnCovid,"113");

        btnSAMU=(Button)findViewById(R.id.buttonSAMU);
        buttonsWithNumber.put(btnSAMU,"106");

        btnPNP=(Button)findViewById(R.id.buttonPNP);
        buttonsWithNumber.put(btnPNP,"105");

        btnEsSalud=(Button)findViewById((R.id.buttonESSALUD));
        buttonsWithNumber.put(btnEsSalud,"106");

        btnBomberos=(Button)findViewById(R.id.buttonBOMBEROS);
        buttonsWithNumber.put(btnBomberos,"116");
    }

    //---------------------------------------Permission--------------------------------------------------------------
    public void clickCall(View view){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+buttonsWithNumber.get((Button)view)));
            startActivity(intent);
        }else{
            permissionCall();
        }
    }

    private void permissionCall(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},SOLICITUD_PERMISO_CALL_PHONE);
    }

    public void onRequestPermissionsResult(int requestCode,String[] permisions,int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permisions,grantResults);;
        if(requestCode==SOLICITUD_PERMISO_CALL_PHONE){
            //Check the list of permissions
            for(int i=0;i<permisions.length;i++){
                if(permisions[i].equals(Manifest.permission.CALL_PHONE)){
                    int grantResult = grantResults[i];
                    if(grantResult== PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"Permission accepted",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }else {
            Toast.makeText(this,"I can't verify permission",Toast.LENGTH_SHORT).show();
        }
    }

    //--------------------------------------------------------------------------------------------------------------------------------
    public void clickDialogue(View view){
        miDialog.setContentView(R.layout.activity_numeros_extra);
        miDialog.show();
    }
    //----------------------------------------------------------------------------------------------------------------------------------
    public void clickReturn(View view){
        startActivity(intent);
    }
}
