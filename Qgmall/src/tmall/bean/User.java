package tmall.bean;

/**
 * @author yc
 * @date 2023/4/23 20:27
 */
public class User {
    private int id;
    private String name;
    private String password;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getAnonymousname(String username){
        if (null==username){
            return null;
        }
        //substring 方法是取左边不取右边的吗？
        if (username.length()<=2){
            return username.substring(0,1)+'*';
        }
        char[] rs = username.toCharArray();

        for (int i = 1; i < username.length()-1; i++) {
            rs[i]='*';
        }
        return new String(rs);
    }
}
