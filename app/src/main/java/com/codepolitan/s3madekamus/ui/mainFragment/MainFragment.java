package com.codepolitan.s3madekamus.ui.mainFragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.codepolitan.s3madekamus.R;
import com.codepolitan.s3madekamus.adapter.SearchKamusAdapter;
import com.codepolitan.s3madekamus.helper.KamusHelper;
import com.codepolitan.s3madekamus.helper.PrefManager;
import com.codepolitan.s3madekamus.model.KamusModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tipe_kamus)
    TextView tv_kamus;
    @BindView(R.id.btn_search)
    ImageButton btn_search;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    PrefManager prefManager;
    private KamusHelper kamusHelper;
    private SearchKamusAdapter adapter;

    private List<KamusModel> kamusModels = new ArrayList<>();

    private String query_search;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PrefManager(getContext());
        kamusHelper = new KamusHelper(getContext());
        adapter = new SearchKamusAdapter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);

        if (prefManager.getIsEnglish()) {
            tv_kamus.setText(R.string.enin);
        }else {
            tv_kamus.setText(R.string.inen);
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query_search = et_search.getText().toString();
                loadData(query_search);
            }
        });
    }

    private void loadData(String search) {
        kamusHelper.open();

        if (search.isEmpty()) {
            kamusModels = kamusHelper.getAllData(prefManager.getIsEnglish());
        } else {
            kamusModels = kamusHelper.getDataByName(search, prefManager.getIsEnglish());
        }

        kamusHelper.close();
        adapter.replaceAll(kamusModels);
        recyclerView.setAdapter(adapter);
    }
}
