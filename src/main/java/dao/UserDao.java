package dao;

import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserDao {
    private ConnectionMaker connectionMaker;
    private Connection c;
    private PreparedStatement ps;
    private final DataSource dataSource;
    private final JdbcContext jdbcContext;


    public UserDao(DataSource dataSource){
        this.dataSource=dataSource;
        this.jdbcContext=new JdbcContext(dataSource);
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy st) throws SQLException {
        c=dataSource.getConnection();
        ps=st.makePreparedStatement(c);
        ps.executeUpdate();
        ps.close();
        c.close();
    }

    public void add(User user) throws SQLException {
        jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                ps=c.prepareStatement("insert into users (id,name,password) values(?,?,?)");
                ps.setString(1,user.getId());
                ps.setString(2,user.getName());
                ps.setString(3,user.getPassword());
                return ps;
            }
        });
    }

    public void deleteAll() throws SQLException {
        jdbcContextWithStatementStrategy
                (c->c.prepareStatement("delete from users"));
    }

    public User findById(String id) throws SQLException {
        c = connectionMaker.connectionMaker();
        ps = c.prepareStatement("select *from users where id = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();

        rs.next();
        User user = new User(rs.getString("id"),rs.getString("name"),rs.getString("password"));
        rs.close();
        ps.close();
        c.close();
        if(user == null) throw new EmptyResultDataAccessException(1);
        return user;
    }


}
