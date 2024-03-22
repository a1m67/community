package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    public List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    public int selectCountByEntity(int entityType, int entityId);

    public int insertComment(Comment comment);

    Comment selectByCommentId(int id);
}
