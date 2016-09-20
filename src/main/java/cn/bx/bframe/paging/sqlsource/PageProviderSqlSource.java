package cn.bx.bframe.paging.sqlsource;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;

import cn.bx.bframe.paging.Constant;

public class PageProviderSqlSource extends PageSqlSource implements Constant {

    private SqlSourceBuilder sqlSourceParser;
    private Class<?> providerType;
    private Method providerMethod;
    private Boolean providerTakesParameterObject;
    private Configuration configuration;

    public PageProviderSqlSource(ProviderSqlSource provider) {
        MetaObject metaObject = SystemMetaObject.forObject(provider);
        this.sqlSourceParser = (SqlSourceBuilder) metaObject.getValue("sqlSourceParser");
        this.providerType = (Class<?>) metaObject.getValue("providerType");
        this.providerMethod = (Method) metaObject.getValue("providerMethod");
        this.providerTakesParameterObject = (Boolean) metaObject.getValue("providerTakesParameterObject");
        this.configuration = (Configuration) metaObject.getValue("sqlSourceParser.configuration");
    }

    private SqlSource createSqlSource(Object parameterObject) {
        try {
            String sql;
            if (providerTakesParameterObject) {
                sql = (String) providerMethod.invoke(providerType.newInstance(), parameterObject);
            } else {
                sql = (String) providerMethod.invoke(providerType.newInstance());
            }
            Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
            StaticSqlSource sqlSource = (StaticSqlSource) sqlSourceParser.parse(sql, parameterType, new HashMap<String, Object>());
            return new OrderByStaticSqlSource(sqlSource);
        } catch (Exception e) {
            throw new BuilderException("Error invoking SqlProvider method ("
                    + providerType.getName() + "." + providerMethod.getName()
                    + ").  Cause: " + e, e);
        }
    }

    @Override
    protected BoundSql getDefaultBoundSql(Object parameterObject) {
        SqlSource sqlSource = createSqlSource(parameterObject);
        return sqlSource.getBoundSql(parameterObject);
    }

    @Override
    protected BoundSql getCountBoundSql(Object parameterObject) {
        BoundSql boundSql;
        SqlSource sqlSource = createSqlSource(parameterObject);
        boundSql = sqlSource.getBoundSql(parameterObject);
        return new BoundSql(
                configuration,
                localParser.get().getCountSql(boundSql.getSql()),
                boundSql.getParameterMappings(),
                parameterObject);
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected BoundSql getPageBoundSql(Object parameterObject) {
        BoundSql boundSql;
        if (parameterObject instanceof Map && ((Map) parameterObject).containsKey(PROVIDER_OBJECT)) {
            SqlSource sqlSource = createSqlSource(((Map) parameterObject).get(PROVIDER_OBJECT));
            boundSql = sqlSource.getBoundSql(((Map) parameterObject).get(PROVIDER_OBJECT));
        } else {
            SqlSource sqlSource = createSqlSource(parameterObject);
            boundSql = sqlSource.getBoundSql(parameterObject);
        }
        return new BoundSql(
                configuration,
                localParser.get().getPageSql(boundSql.getSql()),
                localParser.get().getPageParameterMapping(configuration, boundSql),
                parameterObject);
    }

}
