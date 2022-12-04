package com.example.proyecto_pm1_g_5_resp.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.proyecto_pm1_g_5_resp.ChatFragment;
import com.example.proyecto_pm1_g_5_resp.ContactsFragment;
import com.example.proyecto_pm1_g_5_resp.GruposFragment;

public class AccesoTabsAdapter extends FragmentPagerAdapter {

    public AccesoTabsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ChatFragment chatFragment = new ChatFragment();
                return chatFragment;
            case 1:
                GruposFragment groupFragment = new GruposFragment();
                return groupFragment;
            case 2:
                ContactsFragment contactsFragment = new ContactsFragment();
                return contactsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "chats";
            case 1:
                return "grupos";
            case 2:
                return "contactos";
            default: return null;

        }
    }
}
