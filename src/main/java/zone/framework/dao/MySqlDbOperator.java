package zone.framework.dao;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.SQLException;

import zone.framework.util.base.ConfigUtil; 
/**
 * 基础数据库操作类
 * 
 * 其他DAO继承此类获取常用的数据库操作方法，基本上你能用到的方法这里都有了，不需要自己建立DAO了。
 * 
 * @author linux liu
 * 
 * @param <T>
 *            模型
 */
public class MySqlDbOperator {
	 
    public static final String name = "com.mysql.jdbc.Driver";  
    public static final String url = ConfigUtil.get("jdbc.url"); 
    public static final String user = ConfigUtil.get("jdbc.username"); 
    public static final String password = ConfigUtil.get("jdbc.password"); 
  
    public Connection conn = null;  
    public PreparedStatement pst = null;  
  
    public void connection() {  
        try {  
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    public int insert(String sql)  
    {  
        int i=0;  
     
        try{  
        	pst =conn.prepareStatement(sql);  
            i=pst.executeUpdate();  
        }  
        catch (SQLException e)  
        {  
            e.printStackTrace();  
        }  
        return i;//返回影响的行数，1为执行成功  
    }  
    
    public void excuteSql(String sql) {  
        try { 
            pst = conn.prepareStatement(sql);//准备执行语句  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  

}
