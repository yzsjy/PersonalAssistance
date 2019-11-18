package com.sjy.personalassistance.iflyAIUI.di;

import com.sjy.personalassistance.iflyAIUI.ui.about.AboutFragment;
import com.sjy.personalassistance.iflyAIUI.ui.chat.ChatFragment;
import com.sjy.personalassistance.iflyAIUI.ui.detail.DetailFragment;
import com.sjy.personalassistance.iflyAIUI.ui.settings.SettingsFragment;
import com.sjy.personalassistance.iflyAIUI.ui.test.HttpTestFragement;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Dagger2 Fragment Module
 */

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract ChatFragment contributesChatFragment( );

    @ContributesAndroidInjector
    abstract DetailFragment contributeDetailFragment( );

    @ContributesAndroidInjector
    abstract AboutFragment contributeAboutFragment();

    @ContributesAndroidInjector
    abstract SettingsFragment contributeSettingFragment( );

    @ContributesAndroidInjector
    abstract HttpTestFragement contributeHttpTestFragment();
}
