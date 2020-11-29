package com.gtechnologies.videogplus.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.gtechnologies.videogplus.Adapter.VideoAdapter;
import com.gtechnologies.videogplus.Http.ContentApiClient;
import com.gtechnologies.videogplus.Http.ContentApiInterface;
import com.gtechnologies.videogplus.Library.EndlessScrollListener;
import com.gtechnologies.videogplus.Library.Message;
import com.gtechnologies.videogplus.Library.Utility;
import com.gtechnologies.videogplus.Model.Content;
import com.gtechnologies.videogplus.Model.ContentResponse;
import com.gtechnologies.videogplus.R;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 3/29/2018.
 */

public class Drama extends Fragment {

    Context context;
    Utility utility;
    ListView listView;
    TextView noData;
    GifImageView loadingImage;
    ContentApiInterface apiInterface = ContentApiClient.getBaseClient().create(ContentApiInterface.class);
    ContentResponse contentResponse;
    List<Content> contents = null;
    VideoAdapter adapter;

    public Drama(){}

    @SuppressLint("ValidFragment")
    public Drama(Context context){
        this.context = context;
        contents = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_layout, null);
        if(context==null){
            context = getContext();
        }
        this.utility = new Utility(this.context);
        listView = (ListView)view.findViewById(R.id.listview);
        noData = (TextView)view.findViewById(R.id.noData);
        loadingImage = (GifImageView)view.findViewById(R.id.loadingImage);
        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                utility.logger("Page: "+(page-1)+", Total Count: "+totalItemsCount);
                if(totalItemsCount%getResources().getInteger(R.integer.data_size)==0) {
                    getDrama(page - 1);
                }
                return true;
            }
        });
        getDrama(0);
        utility.setFont(noData);
        noData.setText(utility.getLangauge().equals("bn")?context.getString(R.string.no_data_bn):context.getString(R.string.no_data));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getDrama(int pageNumber){
        if(utility.isNetworkAvailable()) {
            Call<ContentResponse> call = apiInterface.getDrama(context.getString(R.string.authorization_key), utility.getMsisdn(), "", pageNumber, getResources().getInteger(R.integer.data_size));
            call.enqueue(new Callback<ContentResponse>() {
                @Override
                public void onResponse(Call<ContentResponse> call, Response<ContentResponse> response) {
                    try {
                        if (response.isSuccessful() && response.code() == 200) {
                            contentResponse = response.body();
                            contents.addAll(contentResponse.getContents());
                            if (contents.size() > 0) {
                                utility.hideAndShowView(new View[]{listView, noData, loadingImage}, listView);
                                adapter = new VideoAdapter(context, contents);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                listView.setSelection(contents.size() > 20 ? contents.size() - contentResponse.getContents().size() - 2 : 0);
                            } else {
                                utility.hideAndShowView(new View[]{listView, noData, loadingImage}, noData);
                            }
                        } else {
                            utility.logger(String.valueOf(response.code()));
                            //utility.showToast(String.valueOf(response.code()));
                            utility.hideAndShowView(new View[]{listView, noData, loadingImage}, noData);
                        }
                    } catch (Exception ex) {
                        utility.logger(ex.toString());
                        //utility.showToast(ex.toString());
                        utility.hideAndShowView(new View[]{listView, noData, loadingImage}, noData);
                    }
                }

                @Override
                public void onFailure(Call<ContentResponse> call, Throwable t) {
                    utility.logger(t.toString());
                    utility.showToast(Message.HTTP_ERROR);
                }
            });
        }
        else {
            utility.showToast(Message.NO_NET);
        }
    }
}
