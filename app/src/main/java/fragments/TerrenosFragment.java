package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maprealtime.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import models.Terreno;

public class TerrenosFragment extends Fragment {

    private RecyclerView terrenosRecyclerView;
    private TerrenosAdapter terrenosAdapter;
    private ProgressBar progressBar;
    private List<Terreno> terrenosList = new ArrayList<>();
    private FirebaseFirestore db;

    public TerrenosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_terrenos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Inicializar vistas
        terrenosRecyclerView = view.findViewById(R.id.terrenosRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        // Configurar RecyclerView
        terrenosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        terrenosAdapter = new TerrenosAdapter(terrenosList, new TerrenosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Terreno terreno) {
                // Navega hasta TerrenoDetailFragment
                Bundle bundle = new Bundle();
                bundle.putString("nombre", terreno.getNombre());
                bundle.putString("detalles", terreno.getDetalles());
                bundle.putString("id", terreno.getId());

                TerrenoDetailFragment detailFragment = new TerrenoDetailFragment();
                detailFragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, detailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        terrenosRecyclerView.setAdapter(terrenosAdapter);

        // Cargar terrenos Firestore
        loadTerrenos();
    }

    private void loadTerrenos() {
        progressBar.setVisibility(View.VISIBLE);
        db.collection("terrenos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            terrenosList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Terreno terreno = document.toObject(Terreno.class);
                                terrenosList.add(terreno);
                            }
                            terrenosAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Error al obtener documentos: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}