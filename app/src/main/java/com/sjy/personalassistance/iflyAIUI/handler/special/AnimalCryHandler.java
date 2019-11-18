package com.sjy.personalassistance.iflyAIUI.handler.special;

import com.sjy.personalassistance.iflyAIUI.handler.player.PlayerHandler;
import com.sjy.personalassistance.iflyAIUI.ui.chat.ChatViewModel;
import com.sjy.personalassistance.iflyAIUI.ui.chat.PlayerViewModel;
import com.sjy.personalassistance.iflyAIUI.ui.common.PermissionChecker;


/**
 * 动物叫声
 */
public class AnimalCryHandler extends PlayerHandler {
    public AnimalCryHandler(ChatViewModel model, PlayerViewModel player, PermissionChecker checker) {
        super(model, player, checker);
    }

    /**
     * 动物叫声返回多个可选的音频结果，只取第一个进行播报
     * @return
     */
    @Override
    protected int retrieveCount() {
        return 1;
    }
}
