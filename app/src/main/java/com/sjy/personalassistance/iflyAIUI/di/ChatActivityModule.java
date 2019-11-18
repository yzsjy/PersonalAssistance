package com.sjy.personalassistance.iflyAIUI.di;

import com.sjy.personalassistance.iflyAIUI.ChatActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Dagger2 Activity Module
 */

@Module
public abstract class ChatActivityModule {
    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    public abstract ChatActivity contributesChatActivity();
}
