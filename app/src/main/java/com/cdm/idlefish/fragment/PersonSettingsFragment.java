package com.cdm.idlefish.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdm.idlefish.R;
import com.cdm.idlefish.activity.HasFabuGoogleActivity;
import com.cdm.idlefish.activity.LoginActivity;
import com.cdm.idlefish.activity.VersionInfoActivity;
import com.cdm.idlefish.base.BaseFragment;
import com.cdm.idlefish.config.Constants;
import com.cdm.idlefish.utils.DialogUtil;
import com.cdm.idlefish.view.CircleImageView;
import com.cdm.idlefish.view.SettingsCustomView;
import com.cdm.utils.ToastUtils;
import com.orhanobut.hawk.Hawk;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonSettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonSettingsFragment extends BaseFragment implements View.OnClickListener,DialogUtil.TakeImageDialogClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int PHOTO_REQUEST_TAKEPHOTO = 101;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 102;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 103;// 结果
    private File tempFile;
    private File tempCutFile;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private CircleImageView mUserIconView;
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
                    //startActivityWithoutExtras(LoginActivity.class);
                }
                updateHeadIcon();
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
                startActivityWithoutExtras(VersionInfoActivity.class);
                break;
            case R.id.person_settings:
                ToastUtils.getInstance().showToast("敬请期待");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:
                if (tempFile == null) {
                    return;
                }
                if (BitmapFactory.decodeFile(tempFile.getPath()) != null) {
                    startPhotoZoom(Uri.fromFile(tempFile), 237);
                }
                break;
            case PHOTO_REQUEST_GALLERY:
                if (data != null) {
                    startPhotoZoom(data.getData(), 237);
                }
                break;
            case PHOTO_REQUEST_CUT:
                if (data != null) {
                    setPicToView(data);
                }
                break;
        }
    }

    /**
     * 将进行剪裁后的图片显示到UI界面上
     */
    private void setPicToView(Intent picdata) {

        if (tempCutFile != null && BitmapFactory.decodeFile(tempCutFile.getPath()) != null) {
            mUserIconView.setImageBitmap(BitmapFactory.decodeFile(tempCutFile.getPath()));
//            submitUserIconToQN();//提交到服务器
        }
    }

    /**
     * 调用系统剪切图片
     *
     * @param uri
     * @param size
     */
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        tempCutFile = new File(Environment.getExternalStorageDirectory(), getCutPhotoFileName());
        intent.putExtra("output", Uri.fromFile(tempCutFile));
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 使用系统当前日期加以调整作为照片的名称
     *
     * @return
     */
    private String getCutPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        String str = "USER_ICON_" + dateFormat.format(date) + ".jpg";
        return str;
    }
    /**
     * 设置用户头像
     */
    private void updateHeadIcon() {
        DialogUtil.showDialog(DialogUtil.GetTakeImageDialog(getActivity(), this), getActivity());
    }

    @Override
    public void takephotoBtnClicked() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径
        tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
    }

    @Override
    public void imageboxClicked() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /**
     * 使用系统当前日期加以调整作为照片的名称
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
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
