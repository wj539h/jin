package com.jin.callback;

import java.util.Date;

/**
 *
 * @author	Jakoes Wu <br>
 * @create	2009-7-26 <br>
  * @project	java.util <br>
 * @email:	jakoes.wu@163.com<br>
 * @copyright    
 * @desc <br>
 */
public class User {

    private int id;
    private String userName;
    private Date birth;
    private Date createDate;
    
    
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Date getBirth() {
        return birth;
    }
    public void setBirth(Date birth) {
        this.birth = birth;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    
}
