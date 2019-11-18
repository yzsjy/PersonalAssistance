package com.sjy.personalassistance.iflyAIUI.ui.chat;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sjy.personalassistance.iflyAIUI.repository.translation.DestLanguage;
import com.sjy.personalassistance.iflyAIUI.repository.translation.SrcLanguage;
import com.sjy.personalassistance.iflyAIUI.repository.translation.Translation;
import com.sjy.personalassistance.iflyAIUI.repository.translation.TranslationMode;

import javax.inject.Inject;


public class TranslationViewModel extends ViewModel {
    private Translation mTranslationRepo;

    @Inject
    public TranslationViewModel(Translation translationRepo) {
        mTranslationRepo = translationRepo;
    }

    public LiveData<Boolean> isTranslationEnable() {
        return mTranslationRepo.getLiveTranslationEnable();
    }

    public LiveData<TranslationMode> getTranslationMode() {
        return mTranslationRepo.getTransMode();
    }

    public void setSrcLanguage(SrcLanguage language) {
        mTranslationRepo.setSrcLanguage(language);
    }

    public void setDestLanguage(DestLanguage language) {
        mTranslationRepo.setDestLanguage(language);
    }
}
