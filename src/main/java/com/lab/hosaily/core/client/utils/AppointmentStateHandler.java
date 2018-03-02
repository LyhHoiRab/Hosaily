package com.lab.hosaily.core.client.utils;

import com.lab.hosaily.core.client.consts.AppointmentState;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentStateHandler extends BaseTypeHandler<AppointmentState> implements TypeHandler<AppointmentState>{

    public AppointmentStateHandler(){

    }

    public void setNonNullParameter(PreparedStatement ps, int i, AppointmentState parameter, JdbcType jdbcType) throws SQLException{
        ps.setInt(i, parameter.getId());
    }

    public AppointmentState getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return AppointmentState.getById(rs.getInt(columnName));
    }

    public AppointmentState getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return AppointmentState.getById(rs.getInt(columnIndex));
    }

    public AppointmentState getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return AppointmentState.getById(cs.getInt(columnIndex));
    }
}
