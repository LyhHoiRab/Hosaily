package com.lab.hosaily.core.curricula.utils.handler;

import com.lab.hosaily.core.curricula.consts.MediaType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MediaTypeHandler extends BaseTypeHandler<MediaType> implements TypeHandler<MediaType>{

    public MediaTypeHandler(){

    }

    public void setNonNullParameter(PreparedStatement ps, int i, MediaType parameter, JdbcType jdbcType) throws SQLException{
        ps.setInt(i, parameter.getId());
    }

    public MediaType getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return MediaType.getById(rs.getInt(columnName));
    }

    public MediaType getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return MediaType.getById(rs.getInt(columnIndex));
    }

    public MediaType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return MediaType.getById(cs.getInt(columnIndex));
    }
}
