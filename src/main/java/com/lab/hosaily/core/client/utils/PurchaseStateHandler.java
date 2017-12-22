package com.lab.hosaily.core.client.utils;

import com.lab.hosaily.core.client.consts.PurchaseState;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseStateHandler extends BaseTypeHandler<PurchaseState> implements TypeHandler<PurchaseState>{

    public PurchaseStateHandler(){

    }

    public void setNonNullParameter(PreparedStatement ps, int i, PurchaseState parameter, JdbcType jdbcType) throws SQLException{
        ps.setInt(i, parameter.getId());
    }

    public PurchaseState getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return PurchaseState.getById(rs.getInt(columnName));
    }

    public PurchaseState getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return PurchaseState.getById(rs.getInt(columnIndex));
    }

    public PurchaseState getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return PurchaseState.getById(cs.getInt(columnIndex));
    }
}
