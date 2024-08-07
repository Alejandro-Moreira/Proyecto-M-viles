package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.maprealtime.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TerrenoDetailFragment extends Fragment implements OnMapReadyCallback {

    private TextView terrenoNameTextView;
    private TextView terrenoDescriptionTextView;
    private TextView terrenoAreaTextView;
    private GoogleMap mMap;

    private String terrenoName;
    private String terrenoDescription;
    private LatLng terrenoLocation;
    private double terrenoArea;

    public TerrenoDetailFragment() {
        // Required empty public constructor
    }

    public static TerrenoDetailFragment newInstance(String name, String description, LatLng location, double area) {
        TerrenoDetailFragment fragment = new TerrenoDetailFragment();
        Bundle args = new Bundle();
        args.putString("nombre", name);
        args.putString("descripción", description);
        args.putParcelable("localización", location);
        args.putDouble("area", area);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_terreno_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            terrenoName = getArguments().getString("nombre");
            terrenoDescription = getArguments().getString("descripción");
            terrenoLocation = getArguments().getParcelable("localización");
            terrenoArea = getArguments().getDouble("area");
        }

        terrenoNameTextView = view.findViewById(R.id.terrenoName);
        terrenoDescriptionTextView = view.findViewById(R.id.terrenoDescription);
        terrenoAreaTextView = view.findViewById(R.id.terrenoArea);

        terrenoNameTextView.setText(terrenoName);
        terrenoDescriptionTextView.setText(terrenoDescription);
        terrenoAreaTextView.setText(String.format("Área: %.2f metros cuadrados", terrenoArea));

        // Inicializar mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (terrenoLocation != null) {
            mMap.addMarker(new MarkerOptions().position(terrenoLocation).title(terrenoName));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(terrenoLocation, 15));
        }
    }
}