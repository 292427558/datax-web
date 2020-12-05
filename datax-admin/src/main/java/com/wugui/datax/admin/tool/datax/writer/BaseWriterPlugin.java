package com.wugui.datax.admin.tool.datax.writer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.wugui.datatx.core.datasource.BaseDataSource;
import com.wugui.datatx.core.util.Constants;
import com.wugui.datax.admin.entity.JobDatasource;
import com.wugui.datax.admin.tool.datax.BaseDataXPlugin;
import com.wugui.datax.admin.tool.pojo.DataxHbasePojo;
import com.wugui.datax.admin.tool.pojo.DataxHivePojo;
import com.wugui.datax.admin.tool.pojo.DataxMongoDBPojo;
import com.wugui.datax.admin.tool.pojo.DataxRdbmsPojo;
import com.wugui.datax.admin.tool.query.DriverConnectionFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * datax writer base
 *
 * @author zhouhongfa@gz-yibo.com
 * @ClassName BaseWriterPlugin
 * @Version 1.0
 * @since 2019/8/2 16:28
 */
public abstract class BaseWriterPlugin extends BaseDataXPlugin {

    /**
     * 不要在方法里定义正则表达式规则,应定义为常量或字段,能加快正则匹配速度
     */
    private static Pattern p = Pattern.compile("\r\n|\r|\n|\n\r");

    @Override
    public Map<String, Object> build(DataxRdbmsPojo plugin) {
        Map<String, Object> parameter = Maps.newLinkedHashMap();
        JobDatasource jobDatasource = plugin.getJobDatasource();
        BaseDataSource baseDataSource = DriverConnectionFactory.getBaseDataSource(jobDatasource.getType(), jobDatasource.getConnectionParams());

        Map<String, Object> connectionObj = Maps.newLinkedHashMap();
        connectionObj.put("table", plugin.getTables());
        connectionObj.put("jdbcUrl", baseDataSource.getJdbcUrl());
        parameter.put("connection", ImmutableList.of(connectionObj));
        parameter.put("username", baseDataSource.getUser());
        parameter.put("password", baseDataSource.getPassword());
        parameter.put("column", plugin.getRdbmsColumns());
        parameter.put("preSql", splitSql(plugin.getPreSql()));
        parameter.put("postSql", splitSql(plugin.getPostSql()));

        Map<String, Object> writer = Maps.newLinkedHashMap();
        writer.put("name", getName());
        writer.put("parameter", parameter);
        return writer;
    }

    private String[] splitSql(String sql) {
        String[] sqlArr = null;
        if (StringUtils.isNotBlank(sql)) {
            Matcher m = p.matcher(sql);
            String sqlStr = m.replaceAll(Constants.STRING_BLANK);
            sqlArr = sqlStr.split(Constants.SPLIT_COLON);
        }
        return sqlArr;
    }

    @Override
    public Map<String, Object> buildHive(DataxHivePojo dataxHivePojo) {
        return null;
    }

    @Override
    public Map<String, Object> buildHbase(DataxHbasePojo dataxHbasePojo) {
        return null;
    }

    @Override
    public Map<String, Object> buildMongoDB(DataxMongoDBPojo plugin) {
        return null;
    }
}
