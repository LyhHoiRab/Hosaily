package com.lab.hosaily.core.client.utils;


import com.lab.hosaily.core.client.consts.PayType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PayTypeHandler extends BaseTypeHandler<PayType> implements TypeHandler<PayType>{

    public PayTypeHandler(){

    }

    public void setNonNullParameter(PreparedStatement ps, int i, PayType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getId());
    }

    public PayType getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return PayType.getById(rs.getInt(columnName));
    }

    public PayType getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return PayType.getById(rs.getInt(columnIndex));
    }

    public PayType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return PayType.getById(cs.getInt(columnIndex));
    }
}
