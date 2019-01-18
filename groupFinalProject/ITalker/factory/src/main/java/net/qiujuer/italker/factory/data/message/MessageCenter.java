package net.qiujuer.italker.factory.data.message;

import net.qiujuer.italker.factory.model.card.MessageCard;

public interface MessageCenter {
    void dispatch(MessageCard... cards);
}
