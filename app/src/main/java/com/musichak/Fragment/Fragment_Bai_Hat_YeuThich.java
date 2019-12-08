package com.musichak.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.musichak.Adapter.BaiHatYeuThichAdapter;
import com.musichak.Model.BaiHat;
import com.musichak.R;
import com.musichak.Service.APIService;
import com.musichak.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Bai_Hat_YeuThich extends Fragment {
    View view;
    RecyclerView recyclerViewBaiHatYeuThich;
    BaiHatYeuThichAdapter baiHatYeuThichAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bai_hat_yeuthich,container,false);
        recyclerViewBaiHatYeuThich = view.findViewById(R.id.recyclerviewBaiHatYeuThich);
        GetData();
        return view;
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.GetListBaiHatYeuThich();
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> listBaiHatYeuThich = (ArrayList<BaiHat>) response.body();
                baiHatYeuThichAdapter = new BaiHatYeuThichAdapter(getActivity(),listBaiHatYeuThich);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewBaiHatYeuThich.setLayoutManager(linearLayoutManager);
                recyclerViewBaiHatYeuThich.setAdapter(baiHatYeuThichAdapter);
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });

    }
}
