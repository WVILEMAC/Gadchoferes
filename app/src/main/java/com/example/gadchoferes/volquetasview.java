package com.example.gadchoferes;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gadchoferes.WebServices.Asynchtask;
import com.example.gadchoferes.WebServices.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class volquetasview extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_volquetasview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService(
                "https://uteqia.com/api/volquetas",
                datos, volquetasview.this, volquetasview.this);
        ws.execute("GET");

        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            finish(); // Cierra esta actividad y vuelve a la anterior
        });
    }

    public void processFinish(String result) throws JSONException {
        TextView txtSaludo = findViewById(R.id.txtresp2);
        StringBuilder lstLista = new StringBuilder();

        JSONArray JSONlista = new JSONArray(result);
        for (int i = 0; i < JSONlista.length(); i++) {
            JSONObject chofer = JSONlista.getJSONObject(i);
            lstLista.append(i + 1).append(".- ")
                    .append(chofer.getString("placa")).append("\n")
                    .append("   Dispositivo: ").append(chofer.getString("dispositivo_id")).append("\n")
                    .append("   Estado: ").append(chofer.getString("estado")).append("\n")
                    .append("   Registrado: ").append(chofer.getString("created_at")).append("\n\n");
        }

        txtSaludo.setText(lstLista.toString());
    }
}