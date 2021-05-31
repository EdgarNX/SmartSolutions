package com.neo.smartsolutions.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.neo.smartsolutions.HomeActivity;
import com.neo.smartsolutions.R;

import org.xmlpull.v1.XmlPullParser;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View aboutPage = new AboutPage(getContext())
                .isRTL(false)
                .enableDarkMode(false)
                .setImage(R.drawable.ic_title)
                .setDescription(getResources().getString(R.string.app_description))
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("Connect with us")
                .addEmail("edgarnemeth@gmail.com")
                .addFacebook("nemeth.edgar")
                .addTwitter("Edgar43872102")
                .addYoutube("UCtt49EN82U-GLWVsOFMMUXQ")
                .addInstagram("nemethedgar")
                .addGitHub("EdgarNX")

                .create();

        return aboutPage;
    }
}