package com.lab.hosaily.core.account.utils.handler;

import com.lab.hosaily.core.account.consts.AttentionType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AttentionTypeHandler extends BaseTypeHandler<AttentionType> implements TypeHandler<AttentionType>{

    public AttentionTypeHandler(){

    }

    public void setNonNullParameter(PreparedStatement ps, int i, AttentionType parameter, JdbcType jdbcType) throws SQLException{
        ps.setInt(i, parameter.getId());
    }

    public AttentionType getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return AttentionType.getById(rs.getInt(columnName));
    }

    public AttentionType getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return AttentionType.getById(rs.getInt(columnIndex));
    }

    public AttentionType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return AttentionType.getById(cs.getInt(columnIndex));
    }
}
