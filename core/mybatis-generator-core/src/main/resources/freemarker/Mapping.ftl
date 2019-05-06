<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.sf.gis.cds.mapper.SchemeMapper">
  <resultMap id="BaseResultMap" type="com.sf.gis.cds.model.Scheme">
    <id column="ID" jdbcType="VARCHAR" property="id" />
    <result column="SCHEME_CODE" jdbcType="VARCHAR" property="schemeCode" />
    <result column="SCHEME_TYPE" jdbcType="CHAR" property="schemeType" />
    <result column="SCHEME_TIME" jdbcType="TIMESTAMP" property="schemeTime" />
    <result column="AUDIT_STATUS" jdbcType="CHAR" property="auditStatus" />
    <result column="AUDIT_DESC" jdbcType="VARCHAR" property="auditDesc" />
    <result column="STATUS" jdbcType="CHAR" property="status" />
    <result column="TRANSPORT_MODE" jdbcType="CHAR" property="transportMode" />
    <result column="DELIVERY_TYPE" jdbcType="CHAR" property="deliveryType" />
  </resultMap>
  <sql id="Base_Column_List">
    ID, SCHEME_CODE, SCHEME_TYPE, SCHEME_TIME, AUDIT_STATUS, AUDIT_DESC, STATUS, TRANSPORT_MODE, 
    DELIVERY_TYPE
  </sql>
  <select id="selectByPrimaryKey" parameterType="java.lang.String" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from CDS_SCHEME
    where ID = #{id,jdbcType=VARCHAR}
  </select>
  <delete id="deleteByPrimaryKey" parameterType="java.lang.String">
    delete from CDS_SCHEME
    where ID = #{id,jdbcType=VARCHAR}
  </delete>
  <insert id="insert" parameterType="com.sf.gis.cds.model.Scheme">
    insert into CDS_SCHEME (ID, SCHEME_CODE, SCHEME_TYPE, 
      SCHEME_TIME, AUDIT_STATUS, AUDIT_DESC, 
      STATUS, TRANSPORT_MODE, DELIVERY_TYPE
      )
    values (#{id,jdbcType=VARCHAR}, #{schemeCode,jdbcType=VARCHAR}, #{schemeType,jdbcType=CHAR}, 
      #{schemeTime,jdbcType=TIMESTAMP}, #{auditStatus,jdbcType=CHAR}, #{auditDesc,jdbcType=VARCHAR}, 
      #{status,jdbcType=CHAR}, #{transportMode,jdbcType=CHAR}, #{deliveryType,jdbcType=CHAR}
      )
  </insert>
  <insert id="insertSelective" parameterType="com.sf.gis.cds.model.Scheme">
    insert into CDS_SCHEME
    <trim prefix="(" suffix=")" suffixOverrides=",">
      <if test="id != null">
        ID,
      </if>
      <if test="schemeCode != null">
        SCHEME_CODE,
      </if>
      <if test="schemeType != null">
        SCHEME_TYPE,
      </if>
      <if test="schemeTime != null">
        SCHEME_TIME,
      </if>
      <if test="auditStatus != null">
        AUDIT_STATUS,
      </if>
      <if test="auditDesc != null">
        AUDIT_DESC,
      </if>
      <if test="status != null">
        STATUS,
      </if>
      <if test="transportMode != null">
        TRANSPORT_MODE,
      </if>
      <if test="deliveryType != null">
        DELIVERY_TYPE,
      </if>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides=",">
      <if test="id != null">
        #{id,jdbcType=VARCHAR},
      </if>
      <if test="schemeCode != null">
        #{schemeCode,jdbcType=VARCHAR},
      </if>
      <if test="schemeType != null">
        #{schemeType,jdbcType=CHAR},
      </if>
      <if test="schemeTime != null">
        #{schemeTime,jdbcType=TIMESTAMP},
      </if>
      <if test="auditStatus != null">
        #{auditStatus,jdbcType=CHAR},
      </if>
      <if test="auditDesc != null">
        #{auditDesc,jdbcType=VARCHAR},
      </if>
      <if test="status != null">
        #{status,jdbcType=CHAR},
      </if>
      <if test="transportMode != null">
        #{transportMode,jdbcType=CHAR},
      </if>
      <if test="deliveryType != null">
        #{deliveryType,jdbcType=CHAR},
      </if>
    </trim>
  </insert>
  <update id="updateByPrimaryKeySelective" parameterType="com.sf.gis.cds.model.Scheme">
    update CDS_SCHEME
    <set>
      <if test="schemeCode != null">
        SCHEME_CODE = #{schemeCode,jdbcType=VARCHAR},
      </if>
      <if test="schemeType != null">
        SCHEME_TYPE = #{schemeType,jdbcType=CHAR},
      </if>
      <if test="schemeTime != null">
        SCHEME_TIME = #{schemeTime,jdbcType=TIMESTAMP},
      </if>
      <if test="auditStatus != null">
        AUDIT_STATUS = #{auditStatus,jdbcType=CHAR},
      </if>
      <if test="auditDesc != null">
        AUDIT_DESC = #{auditDesc,jdbcType=VARCHAR},
      </if>
      <if test="status != null">
        STATUS = #{status,jdbcType=CHAR},
      </if>
      <if test="transportMode != null">
        TRANSPORT_MODE = #{transportMode,jdbcType=CHAR},
      </if>
      <if test="deliveryType != null">
        DELIVERY_TYPE = #{deliveryType,jdbcType=CHAR},
      </if>
    </set>
    where ID = #{id,jdbcType=VARCHAR}
  </update>
  <update id="updateByPrimaryKey" parameterType="com.sf.gis.cds.model.Scheme">
    update CDS_SCHEME
    set SCHEME_CODE = #{schemeCode,jdbcType=VARCHAR},
      SCHEME_TYPE = #{schemeType,jdbcType=CHAR},
      SCHEME_TIME = #{schemeTime,jdbcType=TIMESTAMP},
      AUDIT_STATUS = #{auditStatus,jdbcType=CHAR},
      AUDIT_DESC = #{auditDesc,jdbcType=VARCHAR},
      STATUS = #{status,jdbcType=CHAR},
      TRANSPORT_MODE = #{transportMode,jdbcType=CHAR},
      DELIVERY_TYPE = #{deliveryType,jdbcType=CHAR}
    where ID = #{id,jdbcType=VARCHAR}
  </update>
  <select id="queryByIds" parameterType="java.util.List" resultMap="BaseResultMap">
    select <include refid="Base_Column_List" /> from CDS_SCHEME where ID in 
    <foreach close=")" collection="list" item="item" open="(" separator=",">
      #{item, jdbcType=VARCHAR}
    </foreach>
  </select>
  <select id="queryEntityList" parameterType="com.sf.gis.cds.req.SchemeReq" resultMap="BaseResultMap">
    select <include refid="Base_Column_List" /> from CDS_SCHEME
    <where>
      <if test="@com.sf.gis.cds.common.util.Ognl@isNotEmpty(schemeCode)">
        and SCHEME_CODE = #{schemeCode, jdbcType=VARCHAR}
      </if>
      <if test="@com.sf.gis.cds.common.util.Ognl@isNotEmpty(schemeType)">
        and SCHEME_TYPE = #{schemeType, jdbcType=CHAR}
      </if>
      <if test="@com.sf.gis.cds.common.util.Ognl@isNotEmpty(auditStatus)">
        and AUDIT_STATUS = #{auditStatus, jdbcType=CHAR}
      </if>
      <if test="@com.sf.gis.cds.common.util.Ognl@isNotEmpty(status)">
        and STATUS = #{status, jdbcType=CHAR}
      </if>
      <if test="@com.sf.gis.cds.common.util.Ognl@isNotEmpty(transportMode)">
        and TRANSPORT_MODE = #{transportMode, jdbcType=CHAR}
      </if>
      <if test="@com.sf.gis.cds.common.util.Ognl@isNotEmpty(deliveryType)">
        and DELIVERY_TYPE = #{deliveryType, jdbcType=CHAR}
      </if>
    </where>
  </select>
  <insert id="batchInsert" parameterType="java.util.List">
    insert all 
    <foreach collection="list" item="item" separator=",">
      into CDS_SCHEME (ID, SCHEME_CODE, SCHEME_TYPE, SCHEME_TIME, AUDIT_STATUS, AUDIT_DESC,
      STATUS, TRANSPORT_MODE, DELIVERY_TYPE)
      values(#{item.id, jdbcType=VARCHAR}, #{item.schemeCode, jdbcType=VARCHAR}, #{item.schemeType,
      jdbcType=CHAR}, #{item.schemeTime, jdbcType=TIMESTAMP}, #{item.auditStatus, jdbcType=CHAR},
      #{item.auditDesc, jdbcType=VARCHAR}, #{item.status, jdbcType=CHAR}, #{item.transportMode,
      jdbcType=CHAR}, #{item.deliveryType, jdbcType=CHAR})
    </foreach>
    select 1 from dual 
  </insert>
</mapper>