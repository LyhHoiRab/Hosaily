package com.lab.hosaily.core.course.utils.handler;

import com.lab.hosaily.core.course.consts.CourseType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseTypeHandler extends BaseTypeHandler<CourseType> implements TypeHandler<CourseType>{

    public CourseTypeHandler(){

    }

    public void setNonNullParameter(PreparedStatement ps, int i, CourseType parameter, JdbcType jdbcType) throws SQLException{
        ps.setInt(i, parameter.getId());
    }

    public CourseType getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return CourseType.getById(rs.getInt(columnName));
    }

    public CourseType getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return CourseType.getById(rs.getInt(columnIndex));
    }

    public CourseType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return CourseType.getById(cs.getInt(columnIndex));
    }
}
