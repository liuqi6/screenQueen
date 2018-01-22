package com.liuqi.screenqueen.db.bean;

import java.sql.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Description:清单列表
 * Author: liuqi
 * Version: 1.0
 * Create Date Time: 2018/1/22 下午4:18.
 * Update Date Time:
 *
 * @see
 */
public class TodoItem extends RealmObject {
    private boolean status;
    private Date deadlineDate;
    private Date startDate;
    private String content;
    private String thumb;
    private String ext;

    @PrimaryKey
    private String id;
}
