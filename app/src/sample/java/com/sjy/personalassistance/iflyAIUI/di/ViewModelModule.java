package com.sjy.personalassistance.iflyAIUI.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.sjy.personalassistance.iflyAIUI.ui.about.AboutViewModel;
import com.sjy.personalassistance.iflyAIUI.ui.chat.ChatViewModel;
import com.sjy.personalassistance.iflyAIUI.ui.chat.PlayerViewModel;
import com.sjy.personalassistance.iflyAIUI.ui.chat.TranslationViewModel;
import com.sjy.personalassistance.iflyAIUI.ui.chat.VoiceViewModel;
import com.sjy.personalassistance.iflyAIUI.ui.common.ViewModelFactory;
import com.sjy.personalassistance.iflyAIUI.ui.settings.SettingViewModel;
import com.sjy.personalassistance.iflyAIUI.ui.test.HttpTestViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel.class)
    abstract ViewModel buildChatViewModel(ChatViewModel messagesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(VoiceViewModel.class)
    abstract ViewModel buildVoiceViewModel(VoiceViewModel voiceViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PlayerViewModel.class)
    abstract ViewModel buildPlayerViewModel(PlayerViewModel playerViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel.class)
    abstract ViewModel buildSettingsViewModel(SettingViewModel settingViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel.class)
    abstract ViewModel buildAboutViewModel(AboutViewModel aboutViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TranslationViewModel.class)
    abstract ViewModel buildTransCfgViewModel(TranslationViewModel transCfgViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HttpTestViewModel.class)
    abstract ViewModel buildHttpTestViewModel(HttpTestViewModel httpTestViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
