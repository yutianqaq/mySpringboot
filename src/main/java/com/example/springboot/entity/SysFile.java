package com.example.springboot.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 
* @TableName sys_file
*/
@TableName("sys_file")
public class SysFile implements Serializable {

    /**
    * ID
    */
    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
    * 文件名称
    */
    @ApiModelProperty("文件名称")
    private String fileName;
    /**
    * 文件类型
    */
    @ApiModelProperty("文件类型")
    private String fileType;
    /**
    * 文件大小
    */
    @ApiModelProperty("文件大小")
    private Double fileSize;
    /**
    * 下载url
    */
    @ApiModelProperty("下载url")
    private String fileUrl;
    /**
    * 是否删除
    */
    @ApiModelProperty("是否删除")
    private Integer isDelete;
    /**
    * 是否禁用
    */
    @ApiModelProperty("是否禁用")
    private Integer enable;

    /**
    * ID
    */
    private void setId(Integer id){
    this.id = id;
    }

    /**
    * 文件名称
    */
    private void setFileName(String fileName){
    this.fileName = fileName;
    }

    /**
    * 文件类型
    */
    private void setFileType(String fileType){
    this.fileType = fileType;
    }

    /**
    * 文件大小
    */
    private void setFileSize(Double fileSize){
    this.fileSize = fileSize;
    }

    /**
    * 下载url
    */
    private void setFileUrl(String fileUrl){
    this.fileUrl = fileUrl;
    }

    /**
    * 是否删除
    */
    private void setIsDelete(Integer isDelete){
    this.isDelete = isDelete;
    }

    /**
    * 是否禁用
    */
    private void setEnable(Integer enable){
    this.enable = enable;
    }


    /**
    * ID
    */
    private Integer getId(){
    return this.id;
    }

    /**
    * 文件名称
    */
    private String getFileName(){
    return this.fileName;
    }

    /**
    * 文件类型
    */
    private String getFileType(){
    return this.fileType;
    }

    /**
    * 文件大小
    */
    private Double getFileSize(){
    return this.fileSize;
    }

    /**
    * 下载url
    */
    private String getFileUrl(){
    return this.fileUrl;
    }

    /**
    * 是否删除
    */
    private Integer getIsDelete(){
    return this.isDelete;
    }

    /**
    * 是否禁用
    */
    private Integer getEnable(){
    return this.enable;
    }

}
