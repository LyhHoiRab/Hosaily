package com.lab.hosaily.core.client.utils;

import com.lab.hosaily.core.client.consts.AgreementState;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgreementStateHandler extends BaseTypeHandler<AgreementState> implements TypeHandler<AgreementState>{

    public AgreementStateHandler(){

    }

    public void setNonNullParameter(PreparedStatement ps, int i, AgreementState parameter, JdbcType jdbcType) throws SQLException{
        ps.setInt(i, parameter.getId());
    }

    public AgreementState getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return AgreementState.getById(rs.getInt(columnName));
    }

    public AgreementState getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return AgreementState.getById(rs.getInt(columnIndex));
    }

    public AgreementState getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return AgreementState.getById(cs.getInt(columnIndex));
    }
}
