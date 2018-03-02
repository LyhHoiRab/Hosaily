package com.lab.hosaily.core.product.utils;

import com.lab.hosaily.core.product.consts.ServiceType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceTypeHandler extends BaseTypeHandler<ServiceType> implements TypeHandler<ServiceType>{

    public ServiceTypeHandler(){

    }

    public void setNonNullParameter(PreparedStatement ps, int i, ServiceType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getId());
    }

    public ServiceType getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return ServiceType.getById(rs.getInt(columnName));
    }

    public ServiceType getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return ServiceType.getById(rs.getInt(columnIndex));
    }

    public ServiceType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return ServiceType.getById(cs.getInt(columnIndex));
    }
}
