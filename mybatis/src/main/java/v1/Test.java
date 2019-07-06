package v1;

import v1.mapper.BlogMapper;

public class Test {
    public static void main(String[] args) {
        GpSqlSession sqlSession = new GpSqlSession(new GpConfiguration(), new GpExecutor());
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        mapper.selectBlogById(1);
    }
}
