package com.lab.hosaily.core.account.utils.handler;

import com.lab.hosaily.core.account.consts.WeChatSex;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WeChatSexHandler extends BaseTypeHandler<WeChatSex> implements TypeHandler<WeChatSex>{

    public WeChatSexHandler(){

    }

    public void setNonNullParameter(PreparedStatement ps, int i, WeChatSex parameter, JdbcType jdbcType) throws SQLException{
        ps.setInt(i, parameter.getId());
    }

    public WeChatSex getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return WeChatSex.getById(rs.getInt(columnName));
    }

    public WeChatSex getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return WeChatSex.getById(rs.getInt(columnIndex));
    }

    public WeChatSex getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return WeChatSex.getById(cs.getInt(columnIndex));
    }
}
