package com.lab.hosaily.core.application.utils.handler;

import com.lab.hosaily.core.application.consts.ApplicationType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationTypeHandler extends BaseTypeHandler<ApplicationType> implements TypeHandler<ApplicationType>{

    public ApplicationTypeHandler(){

    }

    public void setNonNullParameter(PreparedStatement ps, int i, ApplicationType parameter, JdbcType jdbcType) throws SQLException{
        ps.setInt(i, parameter.getId());
    }

    public ApplicationType getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return ApplicationType.getById(rs.getInt(columnName));
    }

    public ApplicationType getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return ApplicationType.getById(rs.getInt(columnIndex));
    }

    public ApplicationType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return ApplicationType.getById(cs.getInt(columnIndex));
    }
}
