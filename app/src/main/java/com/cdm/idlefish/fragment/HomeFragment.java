package com.cdm.idlefish.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.cdm.idlefish.R;
import com.cdm.idlefish.activity.GoodsCategoryActivity;
import com.cdm.idlefish.activity.GoodsDetailsInfoActivity;
import com.cdm.idlefish.activity.SearchExpressActivity;
import com.cdm.idlefish.activity.SearchGoodsActivity;
import com.cdm.idlefish.activity.TimeTableActivity;
import com.cdm.idlefish.adapter.HomeGoodsListAdapter;
import com.cdm.idlefish.base.BaseFragment;
import com.cdm.idlefish.config.Constants;
import com.cdm.idlefish.dao.HomeDao;
import com.cdm.idlefish.dao.LoginDao;
import com.cdm.idlefish.entity.BannerItem;
import com.cdm.idlefish.entity.HomeGoodEntity;
import com.cdm.idlefish.view.NetworkImageHolderView;
import com.cdm.network.interf.HttpAuthCallBack;
import com.cdm.network.model.ResultModel;
import com.cdm.utils.ToastUtils;
import com.jcodecraeer.xrecyclerview.ItemClickSupport;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment implements View.OnClickListener{

    private static final String Tag = "HomeFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View view;

    private XRecyclerView mXRecyclerView;
    private View bannerView;
    private ConvenientBanner convenientBanner;
    private List<BannerItem> bannerItems;

    private View headerView;
    private HomeGoodsListAdapter mGoodAdapter;

    private View mSearchLayout;
    private ImageView mImageClassic;
    private View mSearchView;

    private ImageView[] mImageArr = new ImageView[4];

    private int pageNo = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container1, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            //头部轮播图
            bannerView = LayoutInflater.from(getActivity()).inflate(R.layout.view_banner_layout, null);
            convenientBanner = (ConvenientBanner) bannerView.findViewById(R.id.convenientBanner);
            //头部
            headerView = LayoutInflater.from(getActivity()).inflate(R.layout.view_home_goods_list_header, null);
            mImageArr[0] = (ImageView) headerView.findViewById(R.id.view_img_1);
            mImageArr[1] = (ImageView) headerView.findViewById(R.id.view_img_2);
            mImageArr[2] = (ImageView) headerView.findViewById(R.id.view_img_3);
            mImageArr[3] = (ImageView) headerView.findViewById(R.id.view_img_4);
            //加载更多组件
            view = inflater.inflate(R.layout.fragment_home, container1, false);

            mGoodAdapter = new HomeGoodsListAdapter(getContext());
            mXRecyclerView = (XRecyclerView) view.findViewById(R.id.recyclerview);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mXRecyclerView.setLayoutManager(layoutManager);

            mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);


            mSearchLayout = view.findViewById(R.id.tt_fragment_first_page_header_view);
            mImageClassic = (ImageView) view.findViewById(R.id.tt_fragment_first_page_goods_category);
            mSearchView = view.findViewById(R.id.tt_fragment_first_page_search_goods);
            initRefreshView();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        //getGoodsFromHttp();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
//        for(int i=0;i<mImageArr.length;i++){
//            ImageLoader.getInstance().displayImage(mList.get(position).getTopLine(),holder.topline,options);
//        }

        mXRecyclerView.addHeaderView(bannerView);
        mXRecyclerView.addHeaderView(headerView);

        mXRecyclerView.setAdapter(mGoodAdapter);
        convenientBanner.setPageIndicator(new int[]{R.mipmap.icon_point, R.mipmap.icon_point_pre});
        convenientBanner.setManualPageable(true);
        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        convenientBanner.startTurning(5000);
        bannerItems = new ArrayList<>();
//        if (Hawk.contains(Constants.HAWK_BANNER_HOME)) {
//            bannerItems = new ArrayList<>();
//            bannerItems = (ArrayList<BannerItem>) Hawk.get(Constants.HAWK_BANNER_HOME);
//            convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
//                @Override
//                public NetworkImageHolderView createHolder() {
//                    return new NetworkImageHolderView();
//                }
//            }, bannerItems);
//        }else{
//            //测试数据
//            bannerItems = new ArrayList<>();
//            Hawk.put(Constants.HAWK_BANNER_HOME, bannerItems);
//            convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
//                @Override
//                public NetworkImageHolderView createHolder() {
//                    return new NetworkImageHolderView();
//                }
//            }, bannerItems);
//        }
        addTestBannerData(bannerItems);
    }

    /**
     * 测试数据
     * @param bannerItems
     */
    private void addTestBannerData(List<BannerItem> bannerItems){
        BannerItem item0 = new BannerItem();
        BannerItem item1 = new BannerItem();
        BannerItem item2 = new BannerItem();
        BannerItem item3 = new BannerItem();
        item0.setId(0);
        item0.setPicPath("http://t2.hddhhn.com/uploads/tu/201806/9999/ac0730029d.jpg");
        item1.setId(1);
        item1.setPicPath("http://t2.hddhhn.com/uploads/tu/201705/9999/f0c33c40d6.jpg");
        item2.setId(2);
        item2.setPicPath("http://t2.hddhhn.com/uploads/tu/201705/9999/29da0c0732.jpg");
        item3.setId(3);
        item3.setPicPath("http://t2.hddhhn.com/uploads/tu/201303/172/1.jpg");
        bannerItems.add(item0);
        bannerItems.add(item1);
        bannerItems.add(item2);
        bannerItems.add(item3);
        Hawk.put(Constants.HAWK_BANNER_HOME, bannerItems);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, bannerItems);
    }


    private void addGoodsTestData(){
        ArrayList<HomeGoodEntity> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            HomeGoodEntity item = new HomeGoodEntity();
            item.setId(i);
            item.setUser_id(i);
            item.setContent(" i =="+i);
            if(i==0){
                item.setUser_name("Tom");
                item.setImage("http://t2.hddhhn.com/uploads/tu/201806/9999/ac0730029d.jpg");
                item.setUser_icon("http://t2.hddhhn.com/uploads/tu/20150420/24178-0HodK0D.jpg");
                item.setTitle("深大校花温心尽显温婉可人写真照");
                item.setContent("温心，90后中国女演员、模特、歌手，她天生拥有清纯甜美的气质，以“深大校花”身份走入大众视线。分享深大校花温心尽显温婉可人写真照");
                item.setTopLine("温心");

                item.setOriginalPrice(200);
                item.setSellPrice(10);
                item.setPhone("021-022112");
                item.setWeight("50");
            }else if(i==1) {
                item.setUser_name("Tonny");
                item.setImage("http://t2.hddhhn.com/uploads/tu/201806/9999/f0dd8ac77d.jpg@" +
                        "http://t2.hddhhn.com/uploads/tu/201806/9999/921b1e70ae.jpg");
                item.setUser_icon("http://t2.hddhhn.com/uploads/tu/20150420/24178-0HodK0D.jpg");
                item.setTitle("深大校花温心尽显温婉可人写真照");
                item.setContent("温心，90后中国女演员、模特、歌手，她天生拥有清纯甜美的气质，以“深大校花”身份走入大众视线。分享深大校花温心尽显温婉可人写真照");
                item.setTopLine("温心");
                item.setOriginalPrice(200);
                item.setSellPrice(10);
                item.setPhone("021-022112");
                item.setWeight("50");
            }else if(i==2) {
                item.setUser_name("Sammy");
                item.setImage("http://t2.hddhhn.com/uploads/tu/201806/9999/f0dd8ac77d.jpg@" +
                        "http://t2.hddhhn.com/uploads/tu/201806/9999/921b1e70ae.jpg@" +
                        "http://www.27270.com/word/dongwushijie/2018/283328_2.html");
                item.setUser_icon("http://t2.hddhhn.com/uploads/tu/20150420/24178-0HodK0D.jpg");
                item.setTitle("深大校花温心尽显温婉可人写真照");
                item.setContent("温心，90后中国女演员、模特、歌手，她天生拥有清纯甜美的气质，以“深大校花”身份走入大众视线。分享深大校花温心尽显温婉可人写真照");
                item.setTopLine("温心");
                item.setOriginalPrice(200);
                item.setSellPrice(10);
                item.setPhone("021-022112");
                item.setWeight("50");
            }else if(i==3) {
                item.setUser_name("lily");
                item.setImage("http://t2.hddhhn.com/uploads/tu/201806/9999/921b1e70ae.jpg@" +
                        "http://www.27270.com/word/dongwushijie/2018/283328_2.html@" +
                        "http://t2.hddhhn.com/uploads/tu/201806/9999/f13ae9f84b.jpg");
                item.setUser_icon("http://t2.hddhhn.com/uploads/tu/20150420/24178-0HodK0D.jpg");
                item.setTitle("深大校花温心尽显温婉可人写真照");
                item.setContent("温心，90后中国女演员、模特、歌手，她天生拥有清纯甜美的气质，以“深大校花”身份走入大众视线。分享深大校花温心尽显温婉可人写真照");
                item.setTopLine("温心");
                item.setOriginalPrice(200);
                item.setSellPrice(10);
                item.setPhone("021-022112");
                item.setWeight("50");
            }else if(i==4) {
                item.setUser_name("jacky");
                item.setImage("http://t2.hddhhn.com/uploads/tu/201806/9999/f0dd8ac77d.jpg@" +
                        "http://t2.hddhhn.com/uploads/tu/201806/9999/921b1e70ae.jpg");
                item.setUser_icon("http://t2.hddhhn.com/uploads/tu/20150420/24178-0HodK0D.jpg");
                item.setTitle("深大校花温心尽显温婉可人写真照");
                item.setContent("温心，90后中国女演员、模特、歌手，她天生拥有清纯甜美的气质，以“深大校花”身份走入大众视线。分享深大校花温心尽显温婉可人写真照");
                item.setTopLine("温心");
                item.setOriginalPrice(200);
                item.setSellPrice(10);
                item.setPhone("021-022112");
                item.setWeight("50");
            }
            list.add(item);
        }
        updateUI(list);
    }

    private void initRefreshView() {
    }

    @Override
    public void onResume() {
        super.onResume();
//        getBannerListFromHttp();//暂时使用测试数据
        addGoodsTestData();
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }

    private void initListener(){
        mImageClassic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithIntent(GoodsCategoryActivity.class,new Intent());
            }
        });
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithIntent(SearchGoodsActivity.class,new Intent());
            }
        });

        for (int i=0;i<mImageArr.length;i++){
            mImageArr[i].setOnClickListener(this);
        }

        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNo = 1 ;
                getGoodsFromHttp();
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        mXRecyclerView.refreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
//                pageNo++ ;
//                getGoodsFromHttp();
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        mXRecyclerView.loadMoreComplete();
                    }
                }, 1000);
            }
        });

        ItemClickSupport.addTo(mXRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if(position<3){
                    return ;
                }
                Intent intent = new Intent();
                intent.putExtra(Constants.GOODS_ID,mGoodAdapter.get(position-3).getId()+"");
                intent.putExtra("GoodsBean",mGoodAdapter.get(position-3));
                startActivityWithIntent(GoodsDetailsInfoActivity.class,intent);
            }
        });

    }

    private void updateUI(ArrayList<HomeGoodEntity> list){
        ArrayList<HomeGoodEntity> _list = list;
        if(Hawk.contains(Constants.HAWK_GOODS_LIST)){
            //Hawk.remove(Constants.HAWK_GOODS_LIST);
            _list = (ArrayList<HomeGoodEntity>)Hawk.get(Constants.HAWK_GOODS_LIST);
        }else{
            Hawk.put(Constants.HAWK_GOODS_LIST, _list);
        }
        if(pageNo == 1){
            mXRecyclerView.refreshComplete();
            mGoodAdapter.clear();
            mGoodAdapter.add(_list);
        } else {
            mXRecyclerView.loadMoreComplete();
            mGoodAdapter.add(_list);
        }
    }

    /**
     * 获取头部轮播图数据
     */
    private void getBannerListFromHttp(){
        showLoadingView();
        LoginDao.getInstanse().doGetBannerList(getActivity(), new HttpAuthCallBack<List<BannerItem>>() {
            @Override
            public void onSucceeded(final List<BannerItem> successObj) {
                closeLoadingView();
                Log.i(Tag, " getBannerListFromHttp successObj:" + successObj);
                if (successObj != null && successObj.size() > 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bannerItems = new ArrayList<>();
                            bannerItems.addAll(successObj);
                            if (Hawk.contains(Constants.HAWK_BANNER_HOME)) {
                                Hawk.remove(Constants.HAWK_BANNER_HOME);
                            }
                            Hawk.put(Constants.HAWK_BANNER_HOME, successObj);
                            convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                                @Override
                                public NetworkImageHolderView createHolder() {
                                    return new NetworkImageHolderView();
                                }
                            }, bannerItems);
                        }
                    });
                }
            }
            @Override
            public void onFailed(ResultModel failObj) {
                closeLoadingView();
                Log.d(Tag, "getBannerListFromHttp failObj-- "+failObj.getMessage());
            }
        });
    }


    private void getGoodsFromHttp(){
        HomeDao.getInstanse().doGetGoodsList(getActivity(),
                new HttpAuthCallBack<List<HomeGoodEntity>>() {
            @Override
            public void onSucceeded(final List<HomeGoodEntity> successObj) {
                if(successObj==null || successObj.size()<1){
                    return ;
                }
                Log.i(Tag, "getGoodsFromHttp onSucceeded: successObj ="+successObj.toString());
                getActivity().runOnUiThread(new Thread(){
                    @Override
                    public void run() {
                        mGoodAdapter.add(successObj);
                        //mListView.setAdapter(mGoodAdapter);
                        mGoodAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailed(final ResultModel failObj) {
                getActivity().runOnUiThread(new Thread(){
                    @Override
                    public void run() {
                        ToastUtils.getInstance().showToast(failObj.getMessage());
                    }
                });
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.view_img_1:
                startActivityWithoutExtras(TimeTableActivity.class);
                break;
            case R.id.view_img_2:
                startActivityWithoutExtras(SearchExpressActivity.class);
                break;
            case R.id.view_img_3:
                break;
            case R.id.view_img_4:
                break;
        }
    }
}
