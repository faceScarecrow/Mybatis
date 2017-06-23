/**
 * Created by jinyf on 2017/6/20.
 */

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//import org.junit.*;
//import static org.junit.Assert.*;
import po.User;

import java.io.IOException;
import java.io.InputStream;


public class MybatisTest {

    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSession sqlSession;
    @Before
    public void init() throws IOException {
        //mybatis配置文件
        String resource = "SqlMapConfig.xml";
        InputStream input = Resources.getResourceAsStream(resource);
        //使用SqlSessionFactoryBuilder创建sessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(input);
        //通过session工厂获取一个Sqlsession，sqlsession中包括了对数据库操作的sql方法
        sqlSession = sqlSessionFactory.openSession();
    }
    //查询需要数据库中有书库
    @Test
    public void select(){
        User user = sqlSession.selectOne("test.findUserById",3);
        System.out.println(user);
    }
    //插入时注意主键冲突
    @Test
    public void insert(){
        User user = new User();
        user.setId(1);
        user.setUsername("Tom");
        user.setAge(18);
        sqlSession.insert("test.addUser", user);
        sqlSession.commit();
//        assertEquals(40, user.getAge());
    }
    @Test
    public void update(){
        User u = new User();
        u.setUsername("Jerry");
        u.setId(2);
        sqlSession.update("test.updateUser", u);
        sqlSession.commit();
    }
    @Test
    public void delete(){
        User u = new User();
        u.setId(2);
        sqlSession.delete("test.deleteUser", u);
        sqlSession.commit();
    }
    @After
    public void close(){
        if(sqlSession != null) {
            sqlSession.close();
        }
    }
}