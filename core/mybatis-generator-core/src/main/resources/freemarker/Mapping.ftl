<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basePackage}.dao.mapper.${modelName}Mapper">
  <resultMap id="BaseResultMap" type="${basePackage}.dao.model.${modelName}">
    <#list pkCol as col><id column="${col.actualColumnName}" jdbcType="${col.jdbcTypeName}" property="${col.javaProperty}" /></#list>
    <#list columns as col>
    <result column="${col.actualColumnName}" jdbcType="${col.jdbcTypeName}" property="${col.javaProperty}" />
    </#list>
  </resultMap>
  <sql id="Base_Column_List">
    <#list pkCol as col>${r'`'}${col.actualColumnName}${r'`'}, </#list><#list columns as col><#if columns?size-1 == col_index>${r'`'}${col.actualColumnName}${r'`'} <#else><#if col_index%3 == 0>${r'`'}${col.actualColumnName}${r'`'}, 
    <#else>${r'`'}${col.actualColumnName}${r'`'}, </#if></#if></#list>
  </sql>
  <select id="selectByPrimaryKey" parameterType="java.lang.String" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from ${tableName}
    where id = ${r'#{id, jdbcType=VARCHAR}'}
  </select>
  <delete id="deleteByPrimaryKey" parameterType="java.lang.String">
    delete from ${tableName}
    where id = ${r'#{id, jdbcType=VARCHAR}'}
  </delete>
  <insert id="insert" parameterType="${basePackage}.dao.model.${modelName}">
    insert into ${tableName} (<#list pkCol as col>${r'`'}${col.actualColumnName}${r'`'}, </#list><#list columns as col><#if col_index%3 == 0><#if columns?size-1 == col_index>${r'`'}${col.actualColumnName}${r'`'} <#else>${r'`'}${col.actualColumnName}${r'`'}, </#if>
    <#else><#if columns?size-1 == col_index>${r'`'}${col.actualColumnName}${r'`'} <#else>${r'`'}${col.actualColumnName}${r'`'}, </#if></#if></#list>)
    values (<#list pkCol as col>${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}, '}</#list>
    <#list columns as col><#if columns?size-1 == col_index>${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'} '}<#else>${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}, '}
    </#if></#list>)
  </insert>
  <insert id="insertSelective" parameterType="${basePackage}.dao.model.${modelName}">
    insert into ${tableName}
    <trim prefix="(" suffix=")" suffixOverrides=",">
    <#list pkCol as col>
      <if test="${col.javaProperty} != null">
        ${r'`'}${col.actualColumnName}${r'`'},
      </if>
    </#list>
    <#list columns as col>
      <if test="${col.javaProperty} != null">
        ${r'`'}${col.actualColumnName}${r'`'},
      </if>
    </#list>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides=",">
    <#list pkCol as col>
      <if test="${col.javaProperty} != null">
        ${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}'},
      </if>
    </#list>
    <#list columns as col>
      <if test="${col.javaProperty} != null">
        ${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}'},
      </if>
    </#list>
    </trim>
  </insert>
  <update id="updateByPrimaryKeySelective" parameterType="${basePackage}.dao.model.${modelName}">
    update ${tableName}
    <set>
    <#list columns as col>
      <if test="${col.javaProperty} != null">
        ${r'`'}${col.actualColumnName}${r'`'} = ${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}'},
      </if>
    </#list>
    </set>
    where id = ${r'#{id, jdbcType=VARCHAR}'}
  </update>
  <update id="updateByPrimaryKey" parameterType="${basePackage}.dao.model.${modelName}">
    update ${tableName}
    set <#list columns as col><#if columns?size -1 == col_index>
        ${r'`'}${col.actualColumnName}${r'`'} = ${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}'} <#else>
        ${r'`'}${col.actualColumnName}${r'`'} = ${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}'}, </#if></#list>
    where id = ${r'#{id, jdbcType=VARCHAR}'}
  </update>
  <select id="queryByIds" parameterType="java.util.List" resultMap="BaseResultMap">
    select <include refid="Base_Column_List" /> from ${tableName} where id in 
    <foreach open="(" close=")" collection="list" item="item" separator=",">
      ${r'#{item, jdbcType=VARCHAR}'}
    </foreach>
  </select>
  <select id="queryEntityList" parameterType="${basePackage}.dao.req.${modelName}Req" resultMap="BaseResultMap">
    select <include refid="Base_Column_List" /> from ${tableName}
    <where>
    <#list reqColumns as col>
      <if test="@${basePackage}.common.util.Ognl@isNotEmpty(${col.javaProperty})">
        and ${r'`'}${col.actualColumnName}${r'`'} = ${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}'}
      </if>
    </#list>
    <#if reqColumns?size lte 0>
        1 != 1
    </#if>
    </where>
  </select>
  <insert id="batchInsert" parameterType="java.util.List">
    insert all 
    <foreach collection="list" item="item" separator=",">
      into ${tableName} (<#list pkCol as col>${r'`'}${col.actualColumnName}${r'`'}, </#list><#list columns as col><#if col_index%3 == 0><#if columns?size-1 == col_index>${r'`'}${col.actualColumnName}${r'`'} <#else>${r'`'}${col.actualColumnName}${r'`'}, </#if>
        <#else><#if columns?size-1 == col_index>${r'`'}${col.actualColumnName}${r'`'} <#else>${r'`'}${col.actualColumnName}${r'`'}, </#if></#if></#list>)
      values(<#list pkCol as col>${r'#{item.'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}, '}</#list>
        <#list columns as col><#if columns?size-1 == col_index>${r'#{item.'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'} '}<#else>${r'#{item.'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}, '}
        </#if></#list>)
    </foreach>
    select 1 from dual 
  </insert>
</mapper>
