package az.sharif.beepy.view;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import az.sharif.beepy.R;
import az.sharif.beepy.adapter.ServiceAdapter;
import az.sharif.beepy.databinding.ActivityMainBinding;
import az.sharif.beepy.model.ServisModel;

public class MainActivity extends AppCompatActivity {

    ArrayList<ServisModel> servicesList;
    private ActivityMainBinding binding;
    LocationManager locationManager;
    LocationListener locationListener;
    ActivityResultLauncher<String> permissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerLauncher();
        LocationUtils();
        ServiceRecycler();
        localeName();
    }

    private void LocationUtils() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = location -> {
        };
    }

    private void registerLauncher() {
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result) {
                //Permission granted
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            } else {
                //Permission denied
                Toast.makeText(this, "Permission needed!", Toast.LENGTH_SHORT).show();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Snackbar.make(binding.getRoot(), "Permission Needed for maps", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", v -> permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)).show();
            } else {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }
    }

    private void ServiceRecycler() {

        binding.serviceRecycler.setLayoutManager(new LinearLayoutManager
                (getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        servicesList = new ArrayList<>();

        servicesList.add(new ServisModel("Nurgul Motors","Car garage", R.drawable.ev1));
        servicesList.add(new ServisModel("First Motors","Bus garage",R.drawable.ev1));
        servicesList.add(new ServisModel("Second Motors","Heavy car garage",R.drawable.ic_baseline_directions_car));

        final ServiceAdapter adapter = new ServiceAdapter(servicesList);
        binding.serviceRecycler.setAdapter(adapter);
    }

    @SuppressLint("MissingPermission")
    private Location getLastKnownLocation() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    private void mapString() {

        double latitude = getLastKnownLocation().getLatitude();
        double longitude = getLastKnownLocation().getLongitude();

        try {
            Geocoder geo = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geo.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses == null) return;
            if (addresses.isEmpty()) {
                Toast.makeText(this, "Waiting for Location", Toast.LENGTH_SHORT).show();
            } else {
                String name = addresses.get(0).getLocality()+ "," + addresses.get(0).getCountryName();
                binding.currentLocation.setText(name);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void localeName() {

        new CountDownTimer(10, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mapString();
            }
        }.start();
    }

}