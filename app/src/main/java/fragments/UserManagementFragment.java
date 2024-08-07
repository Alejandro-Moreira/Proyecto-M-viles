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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import models.User;

public class UserManagementFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;
    private FirebaseFirestore firestore;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_management, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList, user -> {
            // Manejar el clic en el elemento del usuario
            Toast.makeText(getContext(), "Clic en: " + user.getEmail(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(userAdapter);

        firestore = FirebaseFirestore.getInstance();

        loadUsers();

        return view;
    }

    private void loadUsers() {
        progressBar.setVisibility(View.VISIBLE);
        firestore.collection("Usuarios")
                .orderBy("email", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            userList.clear();
                            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                User user = document.toObject(User.class);
                                if (user != null) {
                                    userList.add(user);
                                }
                            }
                            userAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getContext(), "No se pudieron cargar los usuarios", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}