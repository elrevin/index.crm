package ru.index_art.indexcrm.activities.enter.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.index_art.indexcrm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonPasswordRequestFragment extends Fragment {


    public CommonPasswordRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_common_password_request, container, false);
    }

}
