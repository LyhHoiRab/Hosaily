package com.lab.hosaily.core.client.utils;

import com.lab.hosaily.core.client.consts.PayState;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PayStateHandler extends BaseTypeHandler<PayState> implements TypeHandler<PayState>{

    public PayStateHandler(){

    }

    public void setNonNullParameter(PreparedStatement ps, int i, PayState parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getId());
    }

    public PayState getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return PayState.getById(rs.getInt(columnName));
    }

    public PayState getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return PayState.getById(rs.getInt(columnIndex));
    }

    public PayState getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return PayState.getById(cs.getInt(columnIndex));
    }
}
