package com.sjy.personalassistance.iflyAIUI.handler;

import com.sjy.personalassistance.iflyAIUI.model.SemanticResult;
import com.sjy.personalassistance.iflyAIUI.ui.chat.ChatViewModel;
import com.sjy.personalassistance.iflyAIUI.ui.chat.PlayerViewModel;
import com.sjy.personalassistance.iflyAIUI.ui.common.PermissionChecker;

public class NoTTSHandler extends IntentHandler {
    public NoTTSHandler(ChatViewModel model, PlayerViewModel player, PermissionChecker checker) {
        super(model, player, checker);
    }

    @Override
    public Answer getFormatContent(SemanticResult result) {
        return new Answer(result.answer, null, null);
    }
}
