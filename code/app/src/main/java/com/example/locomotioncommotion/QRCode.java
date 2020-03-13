package com.example.locomotioncommotion;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

/**
 * QRCode
 * class for generating a QR code with provided text
 */
public class QRCode extends AppCompatActivity {
    String TAG = "GenerateQRCode";
    EditText editValue;
    ImageView qrImage;
    String inputValue;
    Button qrButton;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_qr);

        qrImage = (ImageView)findViewById(R.id.qrcode);
        editValue = (EditText)findViewById(R.id.editQrText);
        qrButton = (Button)findViewById(R.id.createQrButton);
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputValue = editValue.getText().toString();
                if (inputValue.length() > 0){
                    WindowManager manager = (WindowManager)getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width<height ? width:height;
                    smallerDimension = smallerDimension * 3 / 4;
                    qrgEncoder = new QRGEncoder(inputValue, null, QRGContents.Type.TEXT, smallerDimension);

                    try {
                        bitmap = qrgEncoder.getBitmap();
                        qrImage.setImageBitmap(bitmap);
                    }
                    catch(Exception e) {
                        Log.v(TAG, e.toString());
                    }
                } else {
                    editValue.setError("Required");
                }
            }
        });

    }

}
