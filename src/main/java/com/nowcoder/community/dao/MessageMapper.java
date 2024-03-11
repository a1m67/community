package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    // 查询当前用户的会话列表,针对每个会话值返回一条最新的私信
    List<Message> selectConversations(int userId, int offset, int limit);

    //查到当前用户的绘画数量
    int selectConversationCount(int userId);

    // 查询某个绘画所包含的私信列表
    List<Message> selectLetters(String conversationId, int offset, int limit);

    // 查询某个绘画所包含的私信数量
    int selectLetterCount(String conversationId);

    //查询未读私信的数量
    int selectLetterUnreadCount(int userId, String conversationId);
}
