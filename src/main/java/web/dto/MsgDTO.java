package web.dto;

/**
 * Message DB用のDTOクラス
 * 
 * @author yura
 */
public class MsgDTO {

    private String id;
    private String msg;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getMsg() {
	return msg;
    }

    public void setMsg(String msg) {
	this.msg = msg;
    }
}
