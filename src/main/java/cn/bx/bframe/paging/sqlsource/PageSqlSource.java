package cn.bx.bframe.paging.sqlsource;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;

import cn.bx.bframe.paging.SqlUtil;
import cn.bx.bframe.paging.parser.Parser;

public abstract class PageSqlSource implements SqlSource {

    protected static final ThreadLocal<Parser> localParser = new ThreadLocal<Parser>();

    public void setParser(Parser parser) {
        localParser.set(parser);
    }

    /**
     * 返回值null - 普通,true - count,false - page
     *
     * @return
     */
    protected Boolean getCount() {
        return SqlUtil.getCOUNT();
    }
    /**
     * 获取正常的BoundSql
     *
     * @param parameterObject
     * @return
     */
    protected abstract BoundSql getDefaultBoundSql(Object parameterObject);

    /**
     * 获取Count查询的BoundSql
     *
     * @param parameterObject
     * @return
     */
    protected abstract BoundSql getCountBoundSql(Object parameterObject);

    /**
     * 获取分页查询的BoundSql
     *
     * @param parameterObject
     * @return
     */
    protected abstract BoundSql getPageBoundSql(Object parameterObject);

    /**
     * 获取BoundSql
     *
     * @param parameterObject
     * @return
     */
    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        Boolean count = getCount();
        if (count == null) {
            return getDefaultBoundSql(parameterObject);
        } else if (count) {
            return getCountBoundSql(parameterObject);
        } else {
            return getPageBoundSql(parameterObject);
        }
    }
}
