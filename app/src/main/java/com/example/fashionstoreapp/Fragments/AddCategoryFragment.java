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

import com.example.fashionstoreapp.Adapters.CategoryAdapter;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.DTO.Responses.MessageResponse;
import com.example.fashionstoreapp.Models.Tag;
import com.example.fashionstoreapp.RetrofitAPIService.TagService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.FragmentAddCategoryBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategoryFragment extends Fragment implements ResponseCallback {

    private FragmentAddCategoryBinding fragmentAddCategoryBinding;
    private TagService tagService;
    private LoginResponse loginResponse;
    private List<Tag> tagsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;

    public AddCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAddCategoryBinding = FragmentAddCategoryBinding.inflate(getLayoutInflater());
        View view = fragmentAddCategoryBinding.getRoot();
        getActivity().setTitle("Add Category");
        super.onViewCreated(view, savedInstanceState);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        tagService = new TagService();
        tagService.getAllTags("Bearer " + loginResponse.getToken(), tagsResponseCallback());
        fragmentAddCategoryBinding.addCategoryAddButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCategory();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = fragmentAddCategoryBinding.addCategoryIncludeRecyclerviewId.commonRecycleviewId;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public ResponseCallback tagsResponseCallback() {
        final ResponseCallback tagsResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                tagsList = (List<Tag>) response.body(); //listItem
                categoryAdapter = new CategoryAdapter(getContext(), tagsList);
                recyclerView.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                System.out.println("error messages hererere" + errorMessage);
            }
        };
        return tagsResponseCallback;
    }

    public void saveCategory() {
        String tagName = fragmentAddCategoryBinding.addCategoryTagNameEditTextId.getText().toString();
        if (tagName.isEmpty()||tagName.equals(null)) {
            FancyToast.makeText(getContext(), "Field cannot be empty", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        } else {
            Tag tag = new Tag(tagName);
            tagService.saveCatergory(tag,"Bearer " + loginResponse.getToken(), this);
        }
    }

    @Override
    public void onSuccess(Response response) {
        if (response != null) {
            MessageResponse messageResponse = (MessageResponse) response.body();
            fragmentAddCategoryBinding.addCategoryTagNameEditTextId.setText(" ");
            FancyToast.makeText(getContext(), messageResponse.getMessageResponse(), Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
            tagService.getAllTags("Bearer " + loginResponse.getToken(), tagsResponseCallback());

            categoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getContext(), "Server down", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();

    }


}
