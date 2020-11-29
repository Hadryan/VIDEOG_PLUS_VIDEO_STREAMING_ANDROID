package com.gtechnologies.videogplus.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.gtechnologies.videogplus.Interface.LanguageInterface;
import com.gtechnologies.videogplus.Library.Utility;
import com.gtechnologies.videogplus.R;
import com.kyleduo.switchbutton.SwitchButton;

/**
 * Created by Hp on 9/11/2017.
 */

public class Language extends Fragment {

    Context context;
    Utility utility;
    SwitchButton switchButton;
    LanguageInterface languageInterface;

    public Language() {
    }

    @SuppressLint("ValidFragment")
    public Language(Context context, LanguageInterface languageInterface) {
        this.context = context;
        utility = new Utility(this.context);
        this.languageInterface = languageInterface;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_layout, null);
        switchButton = (SwitchButton)view.findViewById(R.id.languageSwitch);
        final String language = utility.getLangauge();
        if(language.equals("en")){
            switchButton.setCheckedImmediately(true);
        }
        else{
            switchButton.setCheckedImmediately(false);
        }
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    utility.writeLanguage("en");
                }
                else{
                    utility.writeLanguage("bn");
                }
                languageInterface.changeLanguage();
            }
        });
        return view;
    }

}
