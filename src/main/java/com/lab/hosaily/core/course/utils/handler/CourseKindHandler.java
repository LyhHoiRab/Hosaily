package com.lab.hosaily.core.course.utils.handler;

import com.lab.hosaily.core.course.consts.CourseKind;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseKindHandler extends BaseTypeHandler<CourseKind> implements TypeHandler<CourseKind>{

    public CourseKindHandler(){

    }

    public void setNonNullParameter(PreparedStatement ps, int i, CourseKind parameter, JdbcType jdbcType) throws SQLException{
        ps.setInt(i, parameter.getId());
    }

    public CourseKind getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return CourseKind.getById(rs.getInt(columnName));
    }

    public CourseKind getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return CourseKind.getById(rs.getInt(columnIndex));
    }

    public CourseKind getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return CourseKind.getById(cs.getInt(columnIndex));
    }
}
