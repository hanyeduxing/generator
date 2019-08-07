<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basePackage}.dao.${package}.mapper.${modelName}Mapper">
  <resultMap id="BaseResultMap" type="${basePackage}.dao.${package}.model.${modelName}">
    <#list pkCol as col><id column="${col.actualColumnName}" jdbcType="${col.jdbcTypeName}" property="${col.javaProperty}" /></#list>
    <#list columns as col>
    <result column="${col.actualColumnName}" jdbcType="${col.jdbcTypeName}" property="${col.javaProperty}" />
    </#list>
  </resultMap>
  <sql id="Base_Column_List">
    <#list pkCol as col>o.${col.actualColumnName}, </#list><#list columns as col><#if columns?size-1 == col_index>o.${col.actualColumnName} <#else><#if col_index%3 == 0>o.${col.actualColumnName}, 
    <#else>o.${col.actualColumnName}, </#if></#if></#list>
  </sql>
  <select id="selectByPrimaryKey" parameterType="java.lang.String" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from ${tableName} o
    where o.ID = ${r'#{id, jdbcType=CHAR}'}
  </select>
  <delete id="deleteByPrimaryKey" parameterType="java.lang.String">
    delete from ${tableName}
    where ID = ${r'#{id, jdbcType=CHAR}'}
  </delete>
  <insert id="insert" parameterType="${basePackage}.dao.${package}.model.${modelName}">
    insert into ${tableName} (<#list pkCol as col>${col.actualColumnName}, </#list><#list columns as col><#if col_index%3 == 0><#if columns?size-1 == col_index>${col.actualColumnName} <#else>${col.actualColumnName}, </#if>
    <#else><#if columns?size-1 == col_index>${col.actualColumnName} <#else>${col.actualColumnName}, </#if></#if></#list>)
    values (<#list pkCol as col>${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}, '}</#list>
    <#list columns as col><#if columns?size-1 == col_index>${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'} '}<#else>${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}, '}
    </#if></#list>)
  </insert>
  <insert id="insertSelective" parameterType="${basePackage}.dao.${package}.model.${modelName}">
    insert into ${tableName}
    <trim prefix="(" suffix=")" suffixOverrides=",">
    <#list pkCol as col>
      <if test="${col.javaProperty} != null">
        ${col.actualColumnName},
      </if>
    </#list>
    <#list columns as col>
      <if test="${col.javaProperty} != null">
        ${col.actualColumnName},
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
  <update id="updateByPrimaryKeySelective" parameterType="${basePackage}.dao.${package}.model.${modelName}">
    update ${tableName}
    <set>
    <#list columns as col>
      <if test="${col.javaProperty} != null">
        ${col.actualColumnName} = ${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}'},
      </if>
    </#list>
    </set>
    where ID = ${r'#{id, jdbcType=CHAR}'}
  </update>
  <update id="updateByPrimaryKey" parameterType="${basePackage}.dao.${package}.model.${modelName}">
    update ${tableName}
    set <#list columns as col><#if columns?size -1 == col_index>
        ${col.actualColumnName} = ${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}'} <#else>
        ${col.actualColumnName} = ${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}'}, </#if></#list>
    where ID = ${r'#{id, jdbcType=CHAR}'}
  </update>
  <select id="queryByIds" parameterType="java.util.List" resultMap="BaseResultMap">
    select <include refid="Base_Column_List" /> from ${tableName} o where o.ID in 
    <foreach open="(" close=")" collection="list" item="item" separator=",">
      ${r'#{item, jdbcType=VARCHAR}'}
    </foreach>
  </select>
  <select id="queryEntityList" parameterType="${basePackage}.dao.${package}.req.${modelName}Req" resultMap="BaseResultMap">
    select <include refid="Base_Column_List" /> from ${tableName} o
    <where>
    <#list reqColumns as col>
      <if test="@${basePackage}.common.util.Ognl@isNotEmpty(${col.javaProperty})">
      <#if (col.actualColumnName?ends_with('MC'))>
        and o.${col.actualColumnName} like '%' || ${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}'} || '%'
      <#else>
        and o.${col.actualColumnName} = ${r'#{'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}'}
      </#if>
      </if>
    </#list>
    <#if reqColumns?size lte 0>
        1 != 1
    </#if>
    </where>
  </select>
  <insert id="batchInsert" parameterType="java.util.List">
    insert all 
    <foreach collection="list" item="item">
      into ${tableName} (<#list pkCol as col>${col.actualColumnName}, </#list><#list columns as col><#if col_index%3 == 0><#if columns?size-1 == col_index>${col.actualColumnName} <#else>${col.actualColumnName}, </#if>
        <#else><#if columns?size-1 == col_index>${col.actualColumnName} <#else>${col.actualColumnName}, </#if></#if></#list>)
      values(<#list pkCol as col>${r'#{item.'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}, '}</#list>
        <#list columns as col><#if columns?size-1 == col_index>${r'#{item.'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'} '}<#else>${r'#{item.'}${col.javaProperty}, jdbcType=${col.jdbcTypeName}${r'}, '}
        </#if></#list>)
    </foreach>
    select 1 from dual 
  </insert>
</mapper>
