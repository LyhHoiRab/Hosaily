package com.lab.hosaily.core.client.utils;

import com.lab.hosaily.core.client.consts.AlipayStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlipayStatusHandler extends BaseTypeHandler<AlipayStatus> implements TypeHandler<AlipayStatus>{

        public AlipayStatusHandler(){

        }

        public void setNonNullParameter(PreparedStatement ps, int i, AlipayStatus parameter, JdbcType jdbcType) throws SQLException{
            ps.setInt(i, parameter.getId());
        }

        public AlipayStatus getNullableResult(ResultSet rs, String columnName) throws SQLException{
            return AlipayStatus.getById(rs.getInt(columnName));
        }

        public AlipayStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
            return AlipayStatus.getById(rs.getInt(columnIndex));
        }

        public AlipayStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
            return AlipayStatus.getById(cs.getInt(columnIndex));
        }
}
