package com.example.fashionstoreapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Adapters.AnsweredInquiriesAdapter;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.ProductInquiry;
import com.example.fashionstoreapp.RetrofitAPIService.ProductInquiryService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.CommonlistviewBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnsweredInquiriesFragment extends Fragment implements ResponseCallback {

    private CommonlistviewBinding commonlistviewBinding;
    private ProductInquiryService productInquiryService;
    private RecyclerView recyclerView;
    private AnsweredInquiriesAdapter answeredInquiriesAdapter;
    private List<ProductInquiry> productInquiryList = new ArrayList<>();

    public AnsweredInquiriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        commonlistviewBinding = CommonlistviewBinding.inflate(getLayoutInflater());
        View view = commonlistviewBinding.getRoot();
        LoginResponse loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        productInquiryService = new ProductInquiryService();
        productInquiryService.onGetAllProductInquiryAnswered(true, "Bearer " + loginResponse.getToken(), this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = commonlistviewBinding.commonRecycleviewId;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSuccess(Response response) {
        productInquiryList = (List<ProductInquiry>) response.body();
        answeredInquiriesAdapter = new AnsweredInquiriesAdapter(getContext(), productInquiryList);
        recyclerView.setAdapter(answeredInquiriesAdapter);
        answeredInquiriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
    }

}
