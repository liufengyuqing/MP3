package edu.com;

//LISTITEM类 实现列表项
import java.io.Serializable;
public class ListItem implements Serializable//序列化，保存内存中实例变量的状态
{
	private static final long serialVersionUID = 1L;
	 String name;
	 String path;
	public ListItem()
	{}
	public ListItem(String name, String path)
	{
		this.name = name;
		this.path = path;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getPath()
	{
		return path;
	}
	public void setPath(String path)
	{
		this.path = path;
	}
	public String toString()
	{
		return name;
	}
}

