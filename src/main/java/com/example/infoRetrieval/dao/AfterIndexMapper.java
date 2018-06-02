package com.example.infoRetrieval.dao;

import com.example.infoRetrieval.pojo.AfterIndex;
import com.example.infoRetrieval.pojo.AfterIndexExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AfterIndexMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table after_index
     *
     * @mbggenerated Tue May 01 12:50:14 CST 2018
     */
    int countByExample(AfterIndexExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table after_index
     *
     * @mbggenerated Tue May 01 12:50:14 CST 2018
     */
    int deleteByExample(AfterIndexExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table after_index
     *
     * @mbggenerated Tue May 01 12:50:14 CST 2018
     */
    int deleteByPrimaryKey(String term);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table after_index
     *
     * @mbggenerated Tue May 01 12:50:14 CST 2018
     */
    int insert(AfterIndex record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table after_index
     *
     * @mbggenerated Tue May 01 12:50:14 CST 2018
     */
    int insertSelective(AfterIndex record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table after_index
     *
     * @mbggenerated Tue May 01 12:50:14 CST 2018
     */
    List<AfterIndex> selectByExample(AfterIndexExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table after_index
     *
     * @mbggenerated Tue May 01 12:50:14 CST 2018
     */
    AfterIndex selectByPrimaryKey(String term);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table after_index
     *
     * @mbggenerated Tue May 01 12:50:14 CST 2018
     */
    int updateByExampleSelective(@Param("record") AfterIndex record, @Param("example") AfterIndexExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table after_index
     *
     * @mbggenerated Tue May 01 12:50:14 CST 2018
     */
    int updateByExample(@Param("record") AfterIndex record, @Param("example") AfterIndexExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table after_index
     *
     * @mbggenerated Tue May 01 12:50:14 CST 2018
     */
    int updateByPrimaryKeySelective(AfterIndex record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table after_index
     *
     * @mbggenerated Tue May 01 12:50:14 CST 2018
     */
    int updateByPrimaryKey(AfterIndex record);
    List<AfterIndex> selectAll();
}