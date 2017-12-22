package com.studio.tensor.ldm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.studio.tensor.ldm.pojo.FileInfo;

public interface FileInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileInfo record);

    int insertSelective(FileInfo record);

    FileInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileInfo record);

    int updateByPrimaryKey(FileInfo record);
    
    List<FileInfo> selectFileInfoByUserId(@Param("userId")Integer userId);
    
    List<FileInfo> selectByUserIdAndTag(
    		@Param("userId")Integer userId,
    		@Param("tag")String tag);
    
    List<String> selectTagByUserId(@Param("userId")Integer userId);
}