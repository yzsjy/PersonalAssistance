package com.sjy.personalassistance.iflyAIUI.handler;

import android.text.TextUtils;

import com.sjy.personalassistance.iflyAIUI.model.SemanticResult;
import com.sjy.personalassistance.iflyAIUI.ui.chat.ChatViewModel;
import com.sjy.personalassistance.iflyAIUI.ui.chat.PlayerViewModel;
import com.sjy.personalassistance.iflyAIUI.ui.common.PermissionChecker;

/**
 * 拒识（rc = 4）结果处理
 */

public class HintHandler extends IntentHandler {
    private final StringBuilder defaultAnswer;

    public HintHandler(ChatViewModel model, PlayerViewModel player, PermissionChecker checker) {
        super(model, player, checker);
        defaultAnswer = new StringBuilder();
        defaultAnswer.append("你好，我不懂你的意思");
        defaultAnswer.append(IntentHandler.NEWLINE_NO_HTML);
        defaultAnswer.append(IntentHandler.NEWLINE_NO_HTML);
        defaultAnswer.append("在后台添加更多技能让我变得更聪明吧");
    }

    @Override
    public Answer getFormatContent(SemanticResult result) {
        if(TextUtils.isEmpty(result.answer)) {
            return new Answer(defaultAnswer.toString());
        } else {
            return new Answer(result.answer);
        }
    }
}
