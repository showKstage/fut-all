package org.jeecg.modules.app.service.impl;

import org.jeecg.modules.app.service.ICoursesArticleCommentService;
import org.jeecg.modules.app.entity.CoursesArticleComment;
import org.jeecg.modules.app.mapper.CoursesArticleCommentMapper;
import org.jeecg.modules.app.vo.CoursesArticleCommentVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Description: 文章评论表
 * @Author: jeecg-boot
 * @Date:   2021-06-20
 * @Version: V1.0
 */
@Service
public class CoursesArticleCommentServiceImpl extends ServiceImpl<CoursesArticleCommentMapper, CoursesArticleComment> implements ICoursesArticleCommentService {

    @Resource
    CoursesArticleCommentMapper coursesArticleCommentMapper;

    @Override
    public List<CoursesArticleCommentVo> queryArticleComment(String articleId) {
        //评论列表
        List<CoursesArticleCommentVo> commentList = new ArrayList<>();
        //查询父评论
        List<CoursesArticleComment> parentComment = coursesArticleCommentMapper.queryArticleCommentByArticleId(articleId);
        //查询子评论
        List<CoursesArticleComment> childrenComment = coursesArticleCommentMapper.queryArticleSubCommentByCommentId(articleId);
        for (CoursesArticleComment parent : parentComment) {
            CoursesArticleCommentVo comment = new CoursesArticleCommentVo();
            comment.setCoursesArticleComment(parent);
            List<CoursesArticleComment> childrenCommentList = getChildrenComment(parent.getId(),childrenComment);
            comment.setArticleSubCommentList(childrenCommentList);
            commentList.add(comment);
        }
        return commentList;
    }

    public List<CoursesArticleComment> getChildrenComment(String parentId, List<CoursesArticleComment> childrenComments) {
        List<CoursesArticleComment> children = new ArrayList<>();
        List<CoursesArticleComment> temp = new ArrayList<>();
        for (CoursesArticleComment childrenComment : childrenComments) {
            if (parentId.equals(childrenComment.getParentId()))children.add(childrenComment);
        }
        for (CoursesArticleComment child : children) {
            List<CoursesArticleComment> articleCommentList = getChildrenComment(child.getId(),childrenComments);
            temp.addAll(articleCommentList);
        }
        children.addAll(temp);
        return children;
    }

    @Override
    public int deletesArticleComment(String id) {
        return coursesArticleCommentMapper.deletesArticleComment(id);
    }


    @Override
    public int addArticleCommentLike(String commentId,String userId) {
        String id = UUID.randomUUID().toString();
        return coursesArticleCommentMapper.addArticleCommentLike(id,commentId,userId);
    }

    @Override
    public int deleteArticleCommentLike(String commentId, String userId) {
        return coursesArticleCommentMapper.deleteArticleCommentLike(commentId,userId);
    }

    @Override
    public int getArticleCommentNum(String commentId) {
        return coursesArticleCommentMapper.getArticleCommentNum(commentId);
    }

    @Override
    public int getArticleCommentLikeNum(String commentId) {
        return coursesArticleCommentMapper.getArticleCommentLikeNum(commentId);
    }

    @Override
    public String queryArticleCommentLike(String commentId, String userId) {
        return coursesArticleCommentMapper.queryArticleCommentLike(commentId, userId);
    }

    @Override
    public int queryArticleCommentNum(String articleId) {
        return coursesArticleCommentMapper.queryArticleCommentNum(articleId);
    }
}
