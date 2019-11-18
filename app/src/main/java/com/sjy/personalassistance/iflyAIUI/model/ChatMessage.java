package com.sjy.personalassistance.iflyAIUI.model;

import com.sjy.personalassistance.iflyAIUI.repository.chat.RawMessage;
import com.sjy.personalassistance.iflyAIUI.ui.chat.ChatViewModel;
import com.sjy.personalassistance.iflyAIUI.ui.chat.PlayerViewModel;
import com.sjy.personalassistance.iflyAIUI.ui.common.PermissionChecker;

/**
 * 聊天界面交互消息
 */

public class ChatMessage {
    private RawMessage mMsgImpl;
    private ChatMessageHandler mHandler;
    private ChatViewModel mModel;
    private int mMsgVersion;

    public ChatMessage(RawMessage message, PermissionChecker checker, ChatViewModel viewModel, PlayerViewModel player) {
        mModel = viewModel;
        mMsgImpl = message;
        mHandler = new ChatMessageHandler(viewModel, player, checker, message);
        mMsgVersion = message.version();
    }

    public RawMessage getMessage() {
        return mMsgImpl;
    }

    public ChatMessageHandler getHandler() {
        return mHandler;
    }

    public long getMessageVersion() {
        return mMsgVersion;
    }

    public String getDisplayText() {
        if (mMsgImpl.cacheContent == null) {
            mMsgImpl.cacheContent = mHandler.getFormatMessage();
            mModel.updateMessage(mMsgImpl);
        }

        return mMsgImpl.cacheContent;
    }
}
