package com.js.tripledes_ecb_192bit_key;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.js.tripledesnative.TripleDES;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText edtKey;
    private EditText edtPlainText;
    private EditText edtHex;
    private EditText edtResult;

    private Button btnEncrypt;
    private Button btnDecrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtKey = (EditText) findViewById(R.id.edtKey);
        edtPlainText = (EditText) findViewById(R.id.edtPlainText);
        edtHex = (EditText) findViewById(R.id.edtHex);
        edtResult = (EditText) findViewById(R.id.edtResult);

        btnEncrypt = (Button) findViewById(R.id.btnEncrypt);
        btnDecrypt = (Button) findViewById(R.id.btnDecrypt);
        btnEncrypt.setOnClickListener(this);
        btnDecrypt.setOnClickListener(this);

        edtKey.setText("0123456789abcdeghijklmn");
        edtPlainText.setText("jigar");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEncrypt:
                try {
                    String key = edtKey.getText().toString();
                    String plainText = edtPlainText.getText().toString();

                    byte[] keyBytes = key.getBytes();
                    byte[] textBytes = plainText.getBytes();
                    int resultLen = (8 - textBytes.length%8) + textBytes.length;
                    byte[] encryptData = new byte[resultLen];
                    int ret = TripleDES.encryptData(keyBytes, textBytes, encryptData);

                    if (ret <= 0) {
                        setResult("Encrypt Ret: " + ret);
                        return;
                    }
                    byte[] result = new byte[ret];
                    System.arraycopy(encryptData, 0 , result, 0 , ret);
                    setHexResult(TripleDES.byteToHexString(result));
                    setResult(Base64.encodeToString(result, Base64.DEFAULT));
                } catch (Exception e) {
                    Log.e("Error", "Encryption  Error", e);
                }
                break;
            case R.id.btnDecrypt:
                try {
                    String key2 = edtKey.getText().toString();
                    String plainText2 = edtPlainText.getText().toString();

                    byte[] keyBytes2 = key2.getBytes();
                    byte[] textBytes2 = Base64.decode(plainText2, Base64.DEFAULT);
                    int resultLen2 = (8 - textBytes2.length%8) + textBytes2.length;
                    byte[] plainData = new byte[resultLen2];
                    int ret2 = TripleDES.decryptData(keyBytes2, textBytes2, plainData);

                    if (ret2 <= 0) {
                        setResult("Encrypt Ret: " + ret2);
                        return;
                    }
                    byte[] result2 = new byte[ret2];
                    System.arraycopy(plainData, 0 , result2, 0 , ret2);
                    setHexResult(TripleDES.byteToHexString(result2));
                    setResult(new String(result2).trim());
                } catch (Exception e) {
                    Log.e("Error", "Decryption Error", e);
                }
                break;
        }
    }

    private void setResult(final String data) {
        edtResult.post(new Runnable() {
            @Override
            public void run() {
                edtResult.setText(data);
            }
        });
    }

    private void setHexResult(final String data) {
        edtHex.post(new Runnable() {
            @Override
            public void run() {
                edtHex.setText(data);
            }
        });
    }
}
