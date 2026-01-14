package com.example.spilly; // Pastikan ini sama dengan package awak

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

// TAMBAHAN PENTING: Import ini supaya Android kenal huruf 'R'
import com.example.spilly.R;

public class ParentProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // PEMBETULAN: Gunakan "fragment_parent_profile" (tanpa _page)
        // Ini merujuk kepada fail XML awak: fragment_parent_profile.xml
        return inflater.inflate(R.layout.fragment_parent_profile_page, container, false);
    }
}