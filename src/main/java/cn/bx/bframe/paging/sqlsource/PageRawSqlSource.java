package cn.bx.bframe.paging.sqlsource;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.RawSqlSource;

public class PageRawSqlSource extends PageSqlSource {
    private PageSqlSource sqlSource;

    public PageRawSqlSource(RawSqlSource sqlSource) {
        MetaObject metaObject = SystemMetaObject.forObject(sqlSource);
        this.sqlSource = new PageStaticSqlSource((StaticSqlSource) metaObject.getValue("sqlSource"));
    }
    @Override
    protected BoundSql getDefaultBoundSql(Object parameterObject) {
        return sqlSource.getDefaultBoundSql(parameterObject);
    }

    @Override
    protected BoundSql getCountBoundSql(Object parameterObject) {
        return sqlSource.getCountBoundSql(parameterObject);
    }

    @Override
    protected BoundSql getPageBoundSql(Object parameterObject) {
        return sqlSource.getPageBoundSql(parameterObject);
    }

}
