package com.cdm.idlefish.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdm.idlefish.R;
import com.cdm.idlefish.activity.HasFabuGoogleActivity;
import com.cdm.idlefish.activity.LoginActivity;
import com.cdm.idlefish.base.BaseFragment;
import com.cdm.idlefish.config.Constants;
import com.cdm.idlefish.view.SettingsCustomView;
import com.orhanobut.hawk.Hawk;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonSettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonSettingsFragment extends BaseFragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private View mUserIconView;
    private SettingsCustomView mViewFabu,mViewCollect,mViewAddress,mViewAppversion,mViewSettings;

    public PersonSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonSettingsFragment newInstance(String param1, String param2) {
        PersonSettingsFragment fragment = new PersonSettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();
    }

    private void initView(View parentView){
        mUserIconView =$(R.id.image_imgv, $(R.id.person_head_layout,parentView));
        mViewFabu = $(R.id.person_fuba,parentView);
        mViewCollect = $(R.id.person_collect_aview,parentView);
        mViewAddress = $(R.id.person_receive_address_aview,parentView);
        mViewAppversion = $(R.id.person_appversion,parentView);
        mViewSettings = $(R.id.person_settings,parentView);
    }

    private void initListener(){
        mUserIconView.setOnClickListener(this);
        mViewFabu.setOnClickListener(this);
        mViewCollect.setOnClickListener(this);
        mViewAddress.setOnClickListener(this);
        mViewAppversion.setOnClickListener(this);
        mViewSettings.setOnClickListener(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_imgv:
                Object object = Hawk.get(Constants.HAWK_ISLOGIN);
                if(object ==null || (Integer)object !=1){
                    startActivityWithoutExtras(LoginActivity.class);
                } else {
                }
                break;
            case R.id.person_fuba:
                startActivityWithoutExtras(HasFabuGoogleActivity.class);
                break;
            case R.id.person_collect_aview:
                startActivityWithoutExtras(HasFabuGoogleActivity.class);
                break;
            case R.id.person_receive_address_aview:
                break;
            case R.id.person_appversion:
                break;
            case R.id.person_settings:
                break;
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
