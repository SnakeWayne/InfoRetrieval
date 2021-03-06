package com.example.infoRetrieval.dao;

import com.example.infoRetrieval.pojo.Countdou;
import com.example.infoRetrieval.pojo.CountdouExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CountdouMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table countdou
     *
     * @mbggenerated Wed Jun 06 09:43:35 CST 2018
     */
    int countByExample(CountdouExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table countdou
     *
     * @mbggenerated Wed Jun 06 09:43:35 CST 2018
     */
    int deleteByExample(CountdouExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table countdou
     *
     * @mbggenerated Wed Jun 06 09:43:35 CST 2018
     */
    int insert(Countdou record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table countdou
     *
     * @mbggenerated Wed Jun 06 09:43:35 CST 2018
     */
    int insertSelective(Countdou record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table countdou
     *
     * @mbggenerated Wed Jun 06 09:43:35 CST 2018
     */
    List<Countdou> selectByExample(CountdouExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table countdou
     *
     * @mbggenerated Wed Jun 06 09:43:35 CST 2018
     */
    int updateByExampleSelective(@Param("record") Countdou record, @Param("example") CountdouExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table countdou
     *
     * @mbggenerated Wed Jun 06 09:43:35 CST 2018
     */
    int updateByExample(@Param("record") Countdou record, @Param("example") CountdouExample example);
}