package com.example.fashionstoreapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Adapters.CategoryAdapter;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.Tag;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.TagService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.CommonlistviewBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Response;


public class CategoryFragment extends Fragment implements ResponseCallback {

    private CommonlistviewBinding commonlistviewBinding;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private TagService tagService;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        commonlistviewBinding = commonlistviewBinding.inflate(getLayoutInflater());
        View view = commonlistviewBinding.getRoot();
        getActivity().setTitle("Categories");
        super.onViewCreated(view, savedInstanceState);
        LoginResponse loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        tagService = new TagService();
        tagService.getAllTags("Bearer "+loginResponse.getToken(),this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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
        List<Tag> tagList = (List<Tag>) response.body();
        if (tagList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            commonlistviewBinding.emptyView.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(R.drawable.wishlist_empty)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(commonlistviewBinding.emptyView);
        } else {
            categoryAdapter = new CategoryAdapter(getContext(),tagList);
            recyclerView.setVisibility(View.VISIBLE);
            commonlistviewBinding.emptyView.setVisibility(View.GONE);
            recyclerView.setAdapter(categoryAdapter);
            categoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        System.out.println("error messages hererere" + errorMessage);
    }


//    @Override
//    public void onItemClickListener(Integer id) {
//        Log.e("clicked product detail:", String.valueOf(id));
//        startActivity(new Intent(getActivity(), ProductDetailActivity.class).putExtra("productId", id));
//    }

}
