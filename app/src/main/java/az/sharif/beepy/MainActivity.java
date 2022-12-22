package az.sharif.beepy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import java.util.ArrayList;

import az.sharif.beepy.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ArrayList<ServiceModel> servicesList;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ServiceRecycler();
    }

    private void ServiceRecycler() {

        binding.serviceRecycler.setLayoutManager(new LinearLayoutManager
                (getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        servicesList = new ArrayList<>();

        servicesList.add(new ServiceModel("Nurgul Motors","Avtomobil temiri",R.drawable.ev1));
        servicesList.add(new ServiceModel("Ehmed Motors","Tir temiri",R.drawable.ev1));
        servicesList.add(new ServiceModel("Memmed Motors","Kamaz temiri",R.drawable.ev1));

        final ServiceAdapter adapter = new ServiceAdapter(servicesList);
        binding.serviceRecycler.setAdapter(adapter);
    }
}