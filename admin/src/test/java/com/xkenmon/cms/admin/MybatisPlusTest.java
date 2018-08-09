package com.xkenmon.cms.admin;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkenmon.cms.dao.entity.Article;
import org.junit.Test;

/**
 * @author bigmeng
 * @date 2018/8/7
 */
public class MybatisPlusTest {
    private void logSqlSegment(String explain, ISqlSegment sqlSegment) {
        System.out.println(String.format(" ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓   ->(%s)<-   ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓", explain));
        System.out.println(sqlSegment.getSqlSegment());
    }

    private <T> void logParams(QueryWrapper<T> wrapper) {
        wrapper.getParamNameValuePairs().forEach((k, v) ->
            System.out.println("key: '" + k + "'\t\tvalue: '" + v + "'"));
    }

    @Test
    public void test_1(){
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>().select("article_author","article_id","article_weredsfsd").eq("article_id",163);
        System.out.println(wrapper.getSqlSegment());
        System.out.println();

    }

    @Test
    public void testQueryWrapper() {
        logSqlSegment("去除第一个 or,以及自动拼接 and,以及手动拼接 or,以及去除最后的多个or", new QueryWrapper<Article>().or()
                .ge("age", 3).or().ge("age", 3).ge("age", 3).or().or().or().or());

        logSqlSegment("多个 or 相连接,去除多余的 or", new QueryWrapper<Article>()
                .ge("age", 3).or().or().or().ge("age", 3).or().or().ge("age", 3));

        logSqlSegment("嵌套,正常嵌套", new QueryWrapper<Article>()
                .nested(i -> i.eq("id", 1)).eq("id", 1));

        logSqlSegment("嵌套,第一个套外的 and 自动消除", new QueryWrapper<Article>()
                .and(i -> i.eq("id", 1)).eq("id", 1));

        logSqlSegment("嵌套,多层嵌套", new QueryWrapper<Article>()
                .and(i -> i.eq("id", 1).and(j -> j.eq("id", 1))));

        logSqlSegment("嵌套,第一个套外的 or 自动消除", new QueryWrapper<Article>()
                .or(i -> i.eq("id", 1)).eq("id", 1));

        logSqlSegment("嵌套,套内外自动拼接 and", new QueryWrapper<Article>()
                .eq("id", 11).and(i -> i.eq("id", 1)).eq("id", 1));

        logSqlSegment("嵌套,套内外手动拼接 or,去除套内第一个 or", new QueryWrapper<Article>()
                .eq("id", 11).or(i -> i.or().eq("id", 1)).or().eq("id", 1));

        logSqlSegment("多个 order by 和 group by 拼接,自动优化顺序,last方法拼接在最后", new QueryWrapper<Article>()
                .eq("id", 11)
                .last("limit 1")
                .orderByAsc("id", "name", "sex").orderByDesc("age", "txl")
                .groupBy("id", "name", "sex").groupBy("id", "name"));

        logSqlSegment("只存在 order by", new QueryWrapper<Article>()
                .orderByAsc("id", "name", "sex").orderByDesc("age", "txl"));

        logSqlSegment("只存在 group by", new QueryWrapper<Article>()
                .groupBy("id", "name", "sex").groupBy("id", "name"));
    }
}
