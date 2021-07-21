package cn.test.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author genez
 * @date Created at 2021/07/19
 * @description TODO
 */
@Data
public class User implements Serializable {
	private static final long serialVersionUID = -3397194820734600891L;

	private Long uid;

	private String name;


}
