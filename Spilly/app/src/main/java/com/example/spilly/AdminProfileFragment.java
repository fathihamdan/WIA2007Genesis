package com.example.spilly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class AdminProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnRequestChange = view.findViewById(R.id.ButtonRequestSchoolChangeTeacher);

        if (btnRequestChange != null) {
            btnRequestChange.setOnClickListener(v -> {
                // 2. Navigate using the action ID from your navgraph.xml
                Navigation.findNavController(v).navigate(
                        R.id.action_adminProfileFragment_to_adminChangeSchool
                );
            });
        }

        Button btnChangePass = view.findViewById(R.id.ButtonChangePassTeacher);
        if (btnChangePass != null) {
            btnChangePass.setOnClickListener(v -> {

                Navigation.findNavController(v).navigate(R.id.action_adminProfileFragment_to_adminChangePasswordFragment);
            });
        }


        View btnLogout = view.findViewById(R.id.ButtonLogOutTeacher);

        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                // 2. Show a toast (Optional)
                Toast.makeText(getContext(), "Logging out...", Toast.LENGTH_SHORT).show();

                // 3. Create an Intent to go back to MainActivity
                android.content.Intent intent = new android.content.Intent(getActivity(), MainActivity.class);

                // 4. CLEAR THE STACK
                // This prevents the user from going "Back" into the Admin panel after logging out
                intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);

                // 5. Close the current AdminActivity
                if (getActivity() != null) {
                    getActivity().finish();
                }
            });
        }
    }
}