package com.eta.modules.bs.model;

import com.eta.core.entity.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "b_test_answer")
@Data
public class TestAnswer extends BaseEntity {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 题目答案
     */
    @Column(name = "subject_id1")
    private String subjectId1;
    /**
     * 题目答案
     */
    @Column(name = "subject_id2")
    private String subjectId2;
    /**
     * 题目答案
     */
    @Column(name = "subject_id3")
    private String subjectId3;
    /**
     * 题目答案
     */
    @Column(name = "subject_id4")
    private String subjectId4;
    /**
     * 题目答案
     */
    @Column(name = "subject_id5")
    private String subjectId5;
    /**
     * 题目答案
     */
    @Column(name = "subject_id6")
    private String subjectId6;
    /**
     * 题目答案
     */
    @Column(name = "subject_id7")
    private String subjectId7;
    /**
     * 考试编号
     */
    @Column(name = "answer_no")
    private String answerNo;
    /**
     * 调查年份
     */
    @Column(name = "visit_year")
    private String visitYear;

    /**
     * 答题人id
     */
    @Column(name = "user_id")
    private Integer userId;
    @Transient
    private String studentName;
    @Transient
    private String className;
    @Transient
    private String majorName;
    @Transient
    private String universitiesName;
    @Transient
    private List<String> yearList;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}