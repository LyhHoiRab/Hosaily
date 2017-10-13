package com.lab.hosaily.core.account.utils.handler;

import com.lab.hosaily.core.account.consts.Gender;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenderHandler extends BaseTypeHandler<Gender> implements TypeHandler<Gender>{

    public GenderHandler(){

    }

    public void setNonNullParameter(PreparedStatement ps, int i, Gender parameter, JdbcType jdbcType) throws SQLException{
        ps.setInt(i, parameter.getId());
    }

    public Gender getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return Gender.getById(rs.getInt(columnName));
    }

    public Gender getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return Gender.getById(rs.getInt(columnIndex));
    }

    public Gender getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return Gender.getById(cs.getInt(columnIndex));
    }
}
